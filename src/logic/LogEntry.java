package logic;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class LogEntry {
    private final String ip;
    private final LocalDateTime timestamp;
    private final HttpMethod method;
    private final String path;
    private final int statusCode;
    private final int responseSize;
    private final String referer;
    private final UserAgent userAgent;

    public LogEntry(String logLine) {
        String[] quoteParts = logLine.split("\"");
        String[] prefixParts = logLine.split(" ", 4);
        this.ip = prefixParts[0];

        String dateTimeRaw = logLine.substring(logLine.indexOf('[') + 1, logLine.indexOf(']'));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeRaw, formatter);
        this.timestamp = zonedDateTime.toLocalDateTime();

        String[] requestParts = quoteParts[1].split(" ");
        this.method = HttpMethod.fromString(requestParts[0]);
        this.path = requestParts.length > 1 ? requestParts[1] : "";

        String[] statusParts = quoteParts[2].trim().split(" ");
        this.statusCode = Integer.parseInt(statusParts[0]);
        this.responseSize = Integer.parseInt(statusParts[1]);

        this.referer = quoteParts.length > 3 ? quoteParts[3] : "";
        String userAgentRaw = quoteParts.length > 5 ? quoteParts[5] : "";
        this.userAgent = new UserAgent(userAgentRaw);
    }

    public String getIp() {
        return ip;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    public int getResponseCode() {
        return statusCode;
    }

    public String getRequestPath() {
        return path;
    }
}