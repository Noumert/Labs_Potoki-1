import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class RecursiveAnalyzer extends RecursiveAction {

    private final String[] words;
    Map<Integer, Integer> statistic;
    private final int THRESHOLD;

    public RecursiveAnalyzer(String[] words, Map<Integer, Integer> statistic,int threads) {
        this.words = words;
        this.statistic = statistic;
        this.THRESHOLD = threads;
    }

    @Override
    protected void compute() {
        if (words.length > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else {
            processing(words);
        }
    }

    private List<RecursiveAnalyzer> createSubtasks() {
        List<RecursiveAnalyzer> subtasks = new ArrayList<>();
        subtasks.add(new RecursiveAnalyzer(Arrays.copyOfRange(words, 0, words.length / 2), statistic,THRESHOLD));
        subtasks.add(new RecursiveAnalyzer(Arrays.copyOfRange(words, words.length / 2, words.length), statistic,THRESHOLD));
        return subtasks;
    }

    private void processing(String[] subWords) {
        Map<Integer,Integer> lStatistic = new ConcurrentHashMap<>();
        Arrays.stream(subWords).forEach(word -> {
            Integer wLength = word.length();
            lStatistic.computeIfPresent(wLength, (key,value)-> value + 1);
            lStatistic.putIfAbsent(word.length(), 1);
        });
        lStatistic.forEach((key,value) -> {
            statistic.computeIfPresent(key, (key1,value1)-> value1 + value);
            statistic.putIfAbsent(key, value);
        });
    }
}