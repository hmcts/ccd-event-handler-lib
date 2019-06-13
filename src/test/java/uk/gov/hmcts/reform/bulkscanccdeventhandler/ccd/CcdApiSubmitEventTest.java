package uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.common.collect.ImmutableMap;
import feign.Feign;
import feign.FeignException;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.CcdApi;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.CaseDataReq;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.CaseDataResp;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.Event;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static uk.gov.hmcts.reform.bulkscanccdeventhandler.testutils.JsonHelper.json;

public class CcdApiSubmitEventTest {

    private WireMockServer wireMockServer;
    private CcdApi ccdApi;

    // region setup
    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        ccdApi = Feign.builder()
            .decoder(new JacksonDecoder())
            .encoder(new JacksonEncoder())
            .target(CcdApi.class, "http://localhost:8080");
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }
    // endregion

    @Test
    void should_send_valid_request() {
        // given
        String id = "12345";
        stubFor(
            post(urlEqualTo("/caseworkers/user1/jurisdictions/jur1/case-types/caseType1/cases?ignore-warning=false"))
                .withHeader("Authorization", equalTo("idam-token1"))
                .withHeader("ServiceAuthorization", equalTo("s2s-token1"))
                .withRequestBody(equalToJson(json(
                    "{ "
                        + "'event': {"
                        + "    'id': 'eventId1'"
                        + "},"
                        + "'data': {"
                        + "    'foo': 'bar'"
                        + "},"
                        + "'event_token': 'token'"
                        + "}"
                )))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(json("{ 'id' : '" + id + "' }"))
                ));

        // when
        CaseDataResp resp = ccdApi.submitEvent(
            new CaseDataReq(
                new Event("eventId1"),
                ImmutableMap.of("foo", "bar"),
                "token"
            ),
            "user1",
            "idam-token1",
            "s2s-token1",
            "jur1",
            "caseType1",
            false
        );

        // then
        assertThat(resp.id).isEqualTo(id);
    }

    @Test
    void should_throw_an_exception_when_request_failed() {
        // given
        stubFor(post(anyUrl()).willReturn(serverError()));

        // when
        Throwable exc = catchThrowable(
            () -> ccdApi.submitEvent(
                new CaseDataReq(null, null, "token"),
                "user1",
                "idam-token1",
                "s2s-token1",
                "jur1",
                "caseType1",
                false
            )
        );

        // then
        assertThat(exc).isInstanceOf(FeignException.InternalServerError.class);
    }
}
