import java.util.ArrayList;
import java.util.List;

public class StripedMult {
    public Result multMatrixs(double[][] matrix1,double[][] matrix2,int THREADS_NUMBER){
        int resultRows = matrix1.length;
        int resultColumns = matrix2[0].length;
        Result result = new Result(resultRows,resultColumns);

        //not for last
        int numberOfRowsForEveryThreads = resultRows/THREADS_NUMBER;
        int numberOfRowsForLastThreads = resultRows - (numberOfRowsForEveryThreads * (THREADS_NUMBER-1));


            List<Thread> threadList = new ArrayList<>();
            for (int j = 0; j < THREADS_NUMBER-1; j++) {
                int startRow = j * numberOfRowsForEveryThreads;
                int endRow = (j + 1) * numberOfRowsForEveryThreads - 1;
                threadList.add(new StripedThread(matrix1,matrix2, startRow, endRow,result));
            }
            int startRow = resultRows-numberOfRowsForLastThreads;
            int endRow = resultRows - 1;
            threadList.add(new StripedThread(matrix1,matrix2,startRow,endRow,result));
            threadList.forEach(Thread::start);
            threadList.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        return result;
    }
}
