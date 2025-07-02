package logic;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Statistics {
    private long totalTraffic = 0;
    private LocalDateTime minTime = null;
    private LocalDateTime maxTime = null;

    private Set<String> existingPages = new HashSet<>();
    private Set<String> missingPages = new HashSet<>();
    private Map<String, Integer> osCount = new HashMap<>();
    private Map<String, Integer> browserCount = new HashMap<>();

    private List<LogEntry> entries = new ArrayList<>();
    private int errorRequestCount = 0;
    private int realVisitCount = 0;
    private Set<String> realUserIps = new HashSet<>();


    public void addEntry(LogEntry entry) {
        entries.add(entry);

        int size = entry.getResponseSize();
        if (size < 0) size = 0;
        totalTraffic += size;

        LocalDateTime timestamp = entry.getTimestamp();
        if (timestamp != null) {
            if (minTime == null || timestamp.isBefore(minTime)) {
                minTime = timestamp;
            }
            if (maxTime == null || timestamp.isAfter(maxTime)) {
                maxTime = timestamp;
            }
        }

        int code = entry.getResponseCode();
        if (code >= 400 && code <= 599) {
            errorRequestCount++;
        }

        String agent = entry.getUserAgent().toString().toLowerCase();
        if (!agent.contains("bot")) {
            realVisitCount++;
            realUserIps.add(entry.getIp());
        }

        if (entry.getResponseCode() == 200) {
            existingPages.add(entry.getRequestPath());
        }

        if (entry.getResponseCode() == 404) {
            missingPages.add(entry.getRequestPath());
        }

        String os = entry.getUserAgent().getOs();
        if (os != null && !os.isBlank()) {
            osCount.put(os, osCount.getOrDefault(os, 0) + 1);
        }

        String browser = entry.getUserAgent().getBrowser();
        if (browser != null && !browser.isBlank()) {
            browserCount.put(browser, browserCount.getOrDefault(browser, 0) + 1);
        }
    }

    public double getTrafficRate() {
        if (minTime == null || maxTime == null) return 0;
        long hours = Duration.between(minTime, maxTime).toHours();
        if (hours <= 0) return totalTraffic;
        return (double) totalTraffic / hours;
    }

    public long getTotalTraffic() {
        return totalTraffic;
    }

    public Set<String> getExistingPages() {
        return existingPages;
    }

    public Set<String> getMissingPages() {
        return missingPages;
    }

    public Map<String, Double> getOsStatistics() {
        Map<String, Double> result = new HashMap<>();
        int total = osCount.values().stream().mapToInt(Integer::intValue).sum();
        for (Map.Entry<String, Integer> entry : osCount.entrySet()) {
            result.put(entry.getKey(), entry.getValue() / (double) total);
        }
        return result;
    }

    public Map<String, Double> getBrowserStatistics() {
        Map<String, Double> result = new HashMap<>();
        int total = browserCount.values().stream().mapToInt(Integer::intValue).sum();
        for (Map.Entry<String, Integer> entry : browserCount.entrySet()) {
            result.put(entry.getKey(), entry.getValue() / (double) total);
        }
        return result;
    }

    public double getAvgVisitsPerHour() {
        if (minTime == null || maxTime == null) return 0;
        long hours = Duration.between(minTime, maxTime).toHours();
        return hours > 0 ? (double) realVisitCount / hours : realVisitCount;
    }

    public double getAvgErrorsPerHour() {
        if (minTime == null || maxTime == null) return 0;
        long hours = Duration.between(minTime, maxTime).toHours();
        return hours > 0 ? (double) errorRequestCount / hours : errorRequestCount;
    }

    public double getAvgVisitsPerUser() {
        if (realUserIps.isEmpty()) return 0;
        return (double) realVisitCount / realUserIps.size();
    }
}