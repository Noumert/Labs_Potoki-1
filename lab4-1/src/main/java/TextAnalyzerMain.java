import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;

public class TextAnalyzerMain {
    private static String text = "";

    public static void main(String[] args) {
        Integer[] a = new Integer[100];
        for (int i = 0; i < 100; i++) {
            a[i] = i;
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        RecursiveAnalyzer recursiveAnalyzer = new RecursiveAnalyzer(a,1);
        forkJoinPool.execute(recursiveAnalyzer);
        recursiveAnalyzer.join();

    }

}
