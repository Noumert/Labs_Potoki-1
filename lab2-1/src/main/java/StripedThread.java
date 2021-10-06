public class StripedThread extends Thread {
    private int startRowIndex;
    private int endRowIndex;
    private Result result;
    private double[][] matrix1;
    private double[][] matrix2;

    public StripedThread(double[][] matrix1, double[][] matrix2, int startRowIndex, int endRowIndex, Result result) {
        this.endRowIndex = endRowIndex;
        this.startRowIndex = startRowIndex;
        this.result = result;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
    }

    @Override
    public void run() {
//        Instant instantStart = Instant.now();
        int resultColumns = result.result[0].length;
        int iterations = matrix2.length;
        for (int k = 0; k < iterations; k++) {
            for (int i = startRowIndex; i <= endRowIndex; i++) {
                for (int j = 0; j < resultColumns; j++) {
//                    System.out.println("c[" + i + "][" + j + "]");
//                    System.out.println("a[" + i + "][" + iteration + "]");
//                    System.out.println("b[" + iteration + "][" + j + "]");
                    result.result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
//        Instant instantEnd = Instant.now();
//        System.out.println(Thread.currentThread().getName() + " " + Duration.between(instantStart, instantEnd));
    }
}
