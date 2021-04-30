package co.uk.golunch.util.exception;

public class ErrorInfo {
    private final String url;
    private final String message;

    public ErrorInfo(CharSequence url, String message) {
        this.url = url.toString();
        this.message = message;
    }
}
