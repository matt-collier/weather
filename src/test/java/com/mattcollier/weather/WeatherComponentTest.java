package com.mattcollier.weather;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WeatherApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
class WeatherComponentTest {

    private static final String OPEN_WEATHER_MAP_PATH = "/data/2.5/onecall";

    @Autowired
    private MockMvc mockMvc;
    
    private String requestQuery;
    private String response;

    @BeforeEach
    void setUp() {
        stubFor(get(urlPathMatching(OPEN_WEATHER_MAP_PATH))
                        .willReturn(okJson(readTestFile("open-weather-response.json")))
        );
    }

    @Test
    void shouldReturnWarmestDayForNext7Days() throws Exception {
        givenRequest("/warmest-day?longitude=50.825&latitude=-0.1388");
        whenWarmestDayCallIsMade(status().isOk());
        verifyResponseEqualTo(new JSONObject().put("date", "2021-11-07")
                                              .put("temperature", 13.56)
                                              .put("humidity", 65).toString());
    }

    private void givenRequest(final String request) {
        this.requestQuery = request;
    }

    private void whenWarmestDayCallIsMade(final ResultMatcher resultMatcher) throws Exception {
        final MockHttpServletResponse mockHttpServletResponse =
                mockMvc.perform(MockMvcRequestBuilders.get(requestQuery))
                       .andExpect(resultMatcher)
                       .andReturn()
                       .getResponse();

        response = mockHttpServletResponse.getContentAsString();
    }

    private void verifyResponseEqualTo(final String expectedResponseJson) throws JSONException {
        JSONAssert.assertEquals(expectedResponseJson, response, JSONCompareMode.LENIENT);
    }

    private String readTestFile(final String filename) {
        return readFileToString("weather-component-test/" + filename);
    }

    public String readFileToString(final String filename) {
        final var url = ClassLoader.getSystemResource(filename);
        if (url == null) {
            throw new RuntimeException(String.format("unable to find resource: %s", filename));
        }
        try {
            return FileUtils.readFileToString(new File(url.getFile()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(String.format("could not load resource: %s", filename), e);
        }
    }
}
