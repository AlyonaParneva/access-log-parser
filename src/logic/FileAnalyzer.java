package logic;

import exception.LineTooLongException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileAnalyzer {
    public static void analyze(String path) throws Exception {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        int totalLines = 0;
        int maxLength = Integer.MIN_VALUE;
        int minLength = Integer.MAX_VALUE;
        while ((line = reader.readLine()) != null) {
            int length = line.length();
            totalLines++;
            if (length > 1024) {
                throw new LineTooLongException("Строка превышает 1024 символа: " + length);
            }
            maxLength = Math.max(maxLength, length);
            minLength = Math.min(minLength, length);
        }
        reader.close();

        System.out.println("Общее количество строк: " + totalLines);
        System.out.println("Максимальная длина строки: " + maxLength);
        System.out.println("Минимальная длина строки: " + minLength);
    }
}

