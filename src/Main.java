import exception.LineTooLongException;
import logic.FileAnalyzer;
import logic.FileValidator;
import logic.Statistics;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int validFileCount = 0;
        while (true) {
            System.out.print("Введите путь к файлу: ");
            String path = scanner.nextLine();

            if (!FileValidator.isValidFile(path)) {
                System.out.println("Путь указан неверно: либо файл не существует, либо это не файл, а папка.");
                continue;
            }
            validFileCount++;
            System.out.println("Путь указан верно. Это файл номер " + validFileCount);
            try {
                Statistics stats = FileAnalyzer.analyze(path);

                System.out.println("Среднее посещений в час (без ботов): " + stats.getAvgVisitsPerHour());
                System.out.println("Среднее ошибочных запросов в час: " + stats.getAvgErrorsPerHour());
                System.out.println("Средняя посещаемость одним пользователем: " + stats.getAvgVisitsPerUser());

            } catch (LineTooLongException e) {
                System.err.println("ОШИБКА: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
