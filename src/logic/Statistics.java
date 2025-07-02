package logic;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Statistics {
    private long totalTraffic = 0;
    private LocalDateTime minTime = null;
    private LocalDateTime maxTime = null;

    private Set<String> existingPages = new HashSet<>();
    private Map<String, Integer> osCount = new HashMap<>();

    public void addEntry(LogEntry entry) {
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

        if (entry.getResponseCode() == 200) {
            existingPages.add(entry.getRequestPath());
        }

        String os = entry.getUserAgent().getOs();
        if (os != null && !os.isBlank()) {
            osCount.put(os, osCount.getOrDefault(os, 0) + 1);
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

    public Map<String, Double> getOsStatistics() {
        Map<String, Double> result = new HashMap<>();
        int total = osCount.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : osCount.entrySet()) {
            result.put(entry.getKey(), entry.getValue() / (double) total);
        }

        return result;
    }
}