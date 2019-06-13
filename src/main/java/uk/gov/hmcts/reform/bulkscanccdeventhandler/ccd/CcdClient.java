package uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd;

import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.CcdApi;

import java.util.function.Supplier;

public class CcdClient {

    private final CcdApi api;
    private final Supplier<String> s2sTokenSupplier;

    // region constructor
    public CcdClient(CcdApi api, Supplier<String> s2sTokenSupplier) {
        this.api = api;
        this.s2sTokenSupplier = s2sTokenSupplier;
    }
    // endregion

    public String createCase(Object data, String idamToken) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
