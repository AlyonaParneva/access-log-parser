package logic;

import exception.LineTooLongException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class FileAnalyzer {
    public static Statistics analyze(String path) throws Exception {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        Statistics statistics = new Statistics();
        while ((line = reader.readLine()) != null) {
            if (line.length() > 1024) {
                throw new LineTooLongException("Строка превышает 1024 символа: " + line.length());
            }
            try {
                LogEntry entry = new LogEntry(line);
                statistics.addEntry(entry);
            } catch (Exception e) {
                System.err.println("Ошибка при разборе строки: " + e.getMessage());
            }
        }
        reader.close();

        System.out.println("Общий трафик: " + statistics.getTotalTraffic() + " байт");
        System.out.println("Средняя скорость трафика: " + statistics.getTrafficRate() + " байт/час");

        return statistics;
    }
}