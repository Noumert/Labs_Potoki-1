import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

public class TextAnalyzerMain {
    private static String text = "";

    public static void main(String[] args) {
        generateText();
        Map<Integer, Integer> statistic = new ConcurrentHashMap<>();
        String[] words = mapTextToWords(text);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        RecursiveAnalyzer recursiveAnalyzer = new RecursiveAnalyzer(words, statistic,words.length/16);
        forkJoinPool.execute(recursiveAnalyzer);
        recursiveAnalyzer.join();
        statistic.forEach((key, value) -> {
            System.out.println("Length: " + key + " Amount: " + value);
        });

        String test10000 = generateText(2500);
        String test100000 = generateText(25000);
        String test1000000 = generateText(250000);
        String test10000000 = generateText(2500000);

        String[] consistently = mapTextToWords(test10000000);
        long startTime = System.currentTimeMillis();
        Arrays.stream(consistently).forEach(word -> {
            Integer wLength = word.length();
            statistic.computeIfPresent(wLength, (key,value)-> value + 1);
            statistic.putIfAbsent(word.length(), 1);
        });
        long endTime = System.currentTimeMillis();
        System.out.println("Total consistently execution time 10.000.000 words: " + (endTime-startTime) + "ms");

        statistic.clear();
        ForkJoinPool forkJoinPool1 = ForkJoinPool.commonPool();
        RecursiveAnalyzer recursiveAnalyzer1 = new RecursiveAnalyzer(mapTextToWords(test10000), statistic,mapTextToWords(test10000).length/16);
        startTime = System.currentTimeMillis();
        forkJoinPool1.execute(recursiveAnalyzer1);
        recursiveAnalyzer1.join();
        endTime = System.currentTimeMillis();
        System.out.println("Total execution time 10.000 words: " + (endTime-startTime) + "ms");

        statistic.clear();
        ForkJoinPool forkJoinPool2 = ForkJoinPool.commonPool();
        RecursiveAnalyzer recursiveAnalyzer2 = new RecursiveAnalyzer(mapTextToWords(test100000), statistic,mapTextToWords(test100000).length/16);
        startTime = System.currentTimeMillis();
        forkJoinPool2.execute(recursiveAnalyzer2);
        recursiveAnalyzer2.join();
        endTime = System.currentTimeMillis();
        System.out.println("Total execution time 100.000 words: " + (endTime-startTime) + "ms");

        statistic.clear();
        ForkJoinPool forkJoinPool3 = ForkJoinPool.commonPool();
        RecursiveAnalyzer recursiveAnalyzer3 = new RecursiveAnalyzer(mapTextToWords(test1000000), statistic,mapTextToWords(test1000000).length/16);
        startTime = System.currentTimeMillis();
        forkJoinPool3.execute(recursiveAnalyzer3);
        recursiveAnalyzer3.join();
        endTime = System.currentTimeMillis();
        System.out.println("Total execution time 1.000.000 words: " + (endTime-startTime) + "ms");

        statistic.clear();
        ForkJoinPool forkJoinPool4 = ForkJoinPool.commonPool();
        RecursiveAnalyzer recursiveAnalyzer4 = new RecursiveAnalyzer(mapTextToWords(test10000000), statistic,mapTextToWords(test10000000).length/16);
        startTime = System.currentTimeMillis();
        forkJoinPool4.execute(recursiveAnalyzer4);
        recursiveAnalyzer4.join();
        endTime = System.currentTimeMillis();
        System.out.println("Total execution time 10.000.000 words: " + (endTime-startTime) + "ms");
    }

    private static String generateText(int quartOfSize){

        StringBuilder text = new StringBuilder();
        text.append(" abc".repeat(quartOfSize));
        text.append(" abcd".repeat(quartOfSize));
        text.append(" abcde".repeat(quartOfSize));
        text.append(" abcdef".repeat( quartOfSize));

        text = new StringBuilder(text.toString().trim());

        return text.toString();
    }

    private static void generateText(){
        for (int i = 0; i < 100; i++) {
            text += " abc";
        }
        for (int i = 0; i < 200; i++) {
            text += " abcd";
        }
        for (int i = 0; i < 300; i++) {
            text += " abcde";
        }
        for (int i = 0; i < 200; i++) {
            text += " abcdef";
        }
        for (int i = 0; i < 100; i++) {
            text += " abcdefg";
        }
        for (int i = 0; i < 200; i++) {
            text += " abcdefgh";
        }
        for (int i = 0; i < 300; i++) {
            text += " abcdefghi";
        }
        for (int i = 0; i < 200; i++) {
            text += " abcdefghij";
        }
        for (int i = 0; i < 100; i++) {
            text += " abcdefghijk";
        }
        for (int i = 0; i < 200; i++) {
            text += " abcdefghijkl";
        }
        for (int i = 0; i < 300; i++) {
            text += " abcdefghijklm";
        }

     text = text.trim();
    }

    private static String[] mapTextToWords(String text) {
        return text.replaceAll("[^\\w ]", "").split(" ");
    }
}
