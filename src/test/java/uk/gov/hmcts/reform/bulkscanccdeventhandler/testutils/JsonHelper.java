package uk.gov.hmcts.reform.bulkscanccdeventhandler.testutils;

public final class JsonHelper {
    private JsonHelper() {
        // util class
    }

    public static String json(String jsonWithSingleQuotes) {
        return jsonWithSingleQuotes.replace("'", "\"");
    }
}
