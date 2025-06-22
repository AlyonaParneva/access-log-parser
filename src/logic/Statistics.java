package logic;

import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {
    private long totalTraffic = 0;
    private LocalDateTime minTime = null;
    private LocalDateTime maxTime = null;

    public void addEntry(LogEntry entry) {
        int size = entry.getResponseSize();
        if (size < 0) size = 0;
        totalTraffic += size;

        LocalDateTime timestamp = entry.getTimestamp();
        if (timestamp == null) return;

        if (minTime == null || timestamp.isBefore(minTime)) {
            minTime = timestamp;
        }
        if (maxTime == null || timestamp.isAfter(maxTime)) {
            maxTime = timestamp;
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
}