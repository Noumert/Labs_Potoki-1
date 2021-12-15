import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class RecursiveAnalyzer extends RecursiveAction {

    private final Integer[] a;
    List<Integer> statistic;
    private final int THRESHOLD;

    public RecursiveAnalyzer(Integer[] a,int threads) {
        this.a = a;
        this.THRESHOLD = threads;
    }

    @Override
    protected void compute() {
        if (a.length > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else {
            processing(a);
        }
    }

    private List<RecursiveAnalyzer> createSubtasks() {
        List<RecursiveAnalyzer> subtasks = new ArrayList<>();
        subtasks.add(new RecursiveAnalyzer(Arrays.copyOfRange(a, 0, a.length / 2),THRESHOLD));
        subtasks.add(new RecursiveAnalyzer(Arrays.copyOfRange(a, a.length / 2, a.length),THRESHOLD));
        return subtasks;
    }

    private void processing(Integer[] subWords) {
        System.out.println("Complete task"+a);
    }
}