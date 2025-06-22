package logic;

import java.util.Objects;

public class LogEntry {
    private final String ip;
    private final String userAgent;

    public LogEntry(String ip, String userAgent) {
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return "LogEntry{ip='" + ip + "', userAgent='" + userAgent + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogEntry)) return false;
        LogEntry that = (LogEntry) o;
        return Objects.equals(ip, that.ip) && Objects.equals(userAgent, that.userAgent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, userAgent);
    }
}