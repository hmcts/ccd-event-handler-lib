package uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd;

import java.util.Map;
import java.util.function.Supplier;

public class CcdClient {

    private final Supplier<String> s2sTokenSupplier;

    public CcdClient(Supplier<String> s2sTokenSupplier) {
        this.s2sTokenSupplier = s2sTokenSupplier;
    }

    public String createCase(Map<String, Object> data, String idamToken) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
