import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

public class StripedAndFoxMult {
    final static int firstMatrixRows1 = 25;
    final static int firstMatrixColumnsSecondMatrixRows1 = 20;
    final static int secondMatrixColumns1 = 25;

    public static double[][] createMatrixBySize(int rows,int columns){
        Random random = new Random();
        double[][] result = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
//                result[i][j] = (random.nextInt(20)-10);
                result[i][j]=1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        StripedMult stripedMult = new StripedMult();
        double[][] matrix1 = createMatrixBySize(3,2);
        System.out.println(Arrays.deepToString(matrix1).replace("], [","\n")
                .replace("[[","")
                .replace("]]",""));
        System.out.println("X");
        double[][] matrix2 = createMatrixBySize(2,3);
        System.out.println(Arrays.deepToString(matrix2).replace("], [","\n")
                .replace("[[","")
                .replace("]]",""));
        long startTime = System.currentTimeMillis();
        Result result =
                stripedMult.multMatrixs(matrix1,matrix2,1);
        long endTime = System.currentTimeMillis();
        System.out.println("1 потік розмірність матриць 500 час: " + (endTime-startTime) + "ms");
        System.out.println(Arrays.deepToString(result.result).replace("], [","\n")
                .replace("[[","")
                .replace("]]",""));

        startTime = System.currentTimeMillis();
        stripedMult.multMatrixs(matrix1,matrix2,4);
        endTime = System.currentTimeMillis();
        System.out.println("4 потоки розмірність матриць 500 час: " + (endTime-startTime) + "ms");


        startTime = System.currentTimeMillis();
        Result result1 = stripedMult.multMatrixs(matrix1,matrix2,8);
        endTime = System.currentTimeMillis();
        System.out.println("8 потоки розмірність матриць 500 час: " + (endTime-startTime) + "ms");
//        System.out.println(Arrays.deepToString(matrix2).replace("], [","\n")
//                .replace("[[","")
//                .replace("]]",""));


        System.out.println("//////////////////////////////");

        matrix1 = createMatrixBySize(1500,1500);
        matrix2 = createMatrixBySize(1500,1500);

        startTime = System.currentTimeMillis();
        stripedMult.multMatrixs(matrix1,matrix2,1);
        endTime = System.currentTimeMillis();
        System.out.println("1 потоки розмірність матриць 1500 час: " + (endTime-startTime) + "ms");


        startTime = System.currentTimeMillis();
        Result result2 = stripedMult.multMatrixs(matrix1,matrix2,4);
        endTime = System.currentTimeMillis();
        System.out.println("4 потоки розмірність матриць 1500 час: " + (endTime-startTime) + "ms");
//        System.out.println("gfggggggggggggggggggggggggg");
//        System.out.println(result2.result[1499][1499]);


        startTime = System.currentTimeMillis();
        stripedMult.multMatrixs(matrix1,matrix2,8);
        endTime = System.currentTimeMillis();
        System.out.println("8 потоки розмірність матриць 1500 час: " + (endTime-startTime) + "ms");


        matrix1 = createMatrixBySize(3000,3000);
        matrix2 = createMatrixBySize(3000,3000);

        startTime = System.currentTimeMillis();
        stripedMult.multMatrixs(matrix1,matrix2,1);
        endTime = System.currentTimeMillis();
        System.out.println("1 потоки розмірність матриць 3000 час: " + (endTime-startTime) + "ms");

        startTime = System.currentTimeMillis();
        stripedMult.multMatrixs(matrix1,matrix2,4);
        endTime = System.currentTimeMillis();
        System.out.println("4 потоки розмірність матриць 3000 час: " + (endTime-startTime) + "ms");


        startTime = System.currentTimeMillis();
        stripedMult.multMatrixs(matrix1,matrix2,8);
        endTime = System.currentTimeMillis();
        System.out.println("8 потоки розмірність матриць 3000 час: " + (endTime-startTime) + "ms");
    }
}
