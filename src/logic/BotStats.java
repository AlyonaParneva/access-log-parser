package logic;

public class BotStats {
    private int totalLines = 0;
    private int googleCount = 0;
    private int yandexCount = 0;

    public void incrementTotal() {
        totalLines++;
    }

    public void incrementGoogle() {
        googleCount++;
    }

    public void incrementYandex() {
        yandexCount++;
    }

    public void printStats() {
        System.out.println("Общее количество строк: " + totalLines);
        System.out.println("Запросов от Googlebot: " + googleCount);
        System.out.println("Запросов от YandexBot: " + yandexCount);

        if (totalLines > 0) {
            System.out.printf("Доля Googlebot: %.2f%%%n", (googleCount * 100.0 / totalLines));
            System.out.printf("Доля YandexBot: %.2f%%%n", (yandexCount * 100.0 / totalLines));
        }
    }
}