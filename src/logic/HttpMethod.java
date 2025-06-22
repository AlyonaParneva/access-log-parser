package logic;

public enum HttpMethod {
    GET, POST, PUT, DELETE, HEAD, OPTIONS, PATCH, UNKNOWN;

    public static HttpMethod fromString(String method) {
        try {
            return HttpMethod.valueOf(method);
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}