package logic;

import exception.LineTooLongException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class FileAnalyzer {
    public static void analyze(String path) throws Exception {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        BotStats stats = new BotStats();
        while ((line = reader.readLine()) != null) {
            if (line.length() > 1024) {
                throw new LineTooLongException("Строка превышает 1024 символа: " + line.length());
            }
            stats.incrementTotal();
            String ip = extractIp(line);
            int lastQuote = line.lastIndexOf("\"");
            int secondLastQuote = line.lastIndexOf("\"", lastQuote - 1);
            if (secondLastQuote < 0 || lastQuote < 0 || secondLastQuote >= lastQuote) continue;
            String userAgent = line.substring(secondLastQuote + 1, lastQuote);
            LogEntry entry = new LogEntry(ip, userAgent);
//            System.out.println(entry);
            String program = extractProgramName(entry.getUserAgent());
            if ("Googlebot".equals(program)) {
                stats.incrementGoogle();
            } else if ("YandexBot".equals(program)) {
                stats.incrementYandex();
            }
        }
        reader.close();
        stats.printStats();
    }

    private static String extractProgramName(String userAgent) {
        int firstBracket = userAgent.indexOf("(");
        int lastBracket = userAgent.indexOf(")");
        if (firstBracket < 0 || lastBracket < 0 || lastBracket <= firstBracket) return "";
        String insideBrackets = userAgent.substring(firstBracket + 1, lastBracket);
        String[] parts = insideBrackets.split(";");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        if (parts.length >= 2) {
            String second = parts[1];
            int slashIndex = second.indexOf("/");
            if (slashIndex > 0) {
                return second.substring(0, slashIndex).trim();
            } else {
                return second;
            }
        }
        return "";
    }

    private static String extractIp(String line) {
        String[] tokens = line.split(" ");
        return tokens.length > 0 ? tokens[0] : "";
    }
}