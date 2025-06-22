package logic;

public class UserAgent {
    private final String os;
    private final String browser;

    public UserAgent(String userAgent) {
        this.os = extractOS(userAgent);
        this.browser = extractBrowser(userAgent);
    }

    private String extractOS(String agent) {
        if (agent.contains("Windows")) return "Windows";
        if (agent.contains("Macintosh") || agent.contains("Mac OS")) return "macOS";
        if (agent.contains("Linux")) return "Linux";
        return "Other";
    }

    private String extractBrowser(String agent) {
        if (agent.contains("Edge")) return "Edge";
        if (agent.contains("OPR") || agent.contains("Opera")) return "Opera";
        if (agent.contains("Chrome")) return "Chrome";
        if (agent.contains("Firefox")) return "Firefox";
        if (agent.contains("Safari")) return "Safari";
        return "Other";
    }

    public String getOs() {
        return os;
    }

    public String getBrowser() {
        return browser;
    }
}