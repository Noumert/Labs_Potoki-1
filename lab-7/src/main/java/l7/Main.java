package l7;

import mpi.MPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private final static int MASTER = 0;
    private final static int FROM_WORKER = 1;
    private final static int FROM_MASTER = 2;

    public static void main(String[] args) {
        multiplyMatrixWithMpi(args, 500);
        multiplyMatrixWithMpi(args, 1000);
        multiplyMatrixWithMpi(args, 1500);
//        multiplyMatrixWithMpiOneToOne(args, 500);
//        multiplyMatrixWithMpiOneToOne(args, 1000);
//        multiplyMatrixWithMpiOneToOne(args, 1500);
//        multiplyMatrixSequential(500);
//        multiplyMatrixSequential(1000);
//        multiplyMatrixSequential(1500);

    }

    public static void multiplyMatrixWithMpi(String[] args, int matrixSize) {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int numWorkers = MPI.COMM_WORLD.Size();

        if (matrixSize % numWorkers != 0) {
            throw new ArithmeticException("The matrix cannot be divided into equal parts");
        }

        Matrix matrixA = new Matrix(matrixSize);
        Matrix matrixB = new Matrix(matrixSize);
        Matrix matrixC = new Matrix(matrixSize);

        if (rank == MASTER) {
            matrixA.fillNumber(10);
            matrixB.fillNumber(10);
        }

        long startTime = System.currentTimeMillis();

        MPI.COMM_WORLD.Bcast(matrixC.getSizeObj(), 0, 1, MPI.INT, MASTER);
        Matrix recvMatrixA = new Matrix((matrixA.getSize() / numWorkers) * numWorkers);
        Matrix recvMatrixC = new Matrix((matrixC.getSize() / numWorkers) * numWorkers);
        int count = matrixC.getSize() / numWorkers;

        MPI.COMM_WORLD.Scatter(matrixA.getBuffer(), 0, count * matrixA.getSize(), MPI.DOUBLE,
                recvMatrixA.getBuffer(), 0, count * recvMatrixA.getSize(), MPI.DOUBLE, MASTER);
        MPI.COMM_WORLD.Bcast(matrixB.getBuffer(), 0, matrixB.getBuffer().length, MPI.DOUBLE, MASTER);

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < matrixC.getSize(); j++) {
                for (int k = 0; k < matrixA.getSize(); k++) {
                    recvMatrixC.getBuffer()[i * recvMatrixC.getSize() + j]
                            += recvMatrixA.getBuffer()[i * recvMatrixA.getSize() + k] * matrixB.getElement(k, j);
                }
            }
        }
        MPI.COMM_WORLD.Gather(recvMatrixC.getBuffer(), 0, count * recvMatrixC.getSize(),
                MPI.DOUBLE, matrixC.getBuffer(), 0, count * matrixC.getSize(), MPI.DOUBLE, MASTER);

        long totalTime = System.currentTimeMillis() - startTime;

        if (rank == MASTER) {
            System.out.printf("Matrix C (%s x %s)\n", matrixC.getSize(), matrixC.getSize());
            System.out.println("Last element" + matrixC.getElement(matrixC.getSize()-1,matrixC.getSize()-1));
            System.out.printf("Execution time (%s x %s) -> %s ms\n", matrixC.getSize(), matrixC.getSize(), totalTime);
        }

        MPI.Finalize();
    }

    public static long multiplyMatrixSequential(int matrixSize) {
        final Matrix matrixA = new Matrix(matrixSize);
        final Matrix matrixB = new Matrix(matrixSize);
        final Matrix matrixC = new Matrix(matrixSize);

        matrixA.fillNumber(10);
        matrixB.fillNumber(10);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < matrixC.getSize(); i++) {
            for (int j = 0; j < matrixC.getSize(); j++) {
                for (int k = 0; k < matrixA.getSize(); k++) {
                    matrixC.getBuffer()[i * matrixC.getSize() + j]
                            += matrixA.getBuffer()[i * matrixA.getSize() + k] * matrixB.getElement(k, j);
                }
            }
        }
        long totalTime = System.currentTimeMillis() - startTime;
        System.out.printf("Matrix C (%s x %s)\n", matrixC.getSize(), matrixC.getSize());
        System.out.println("Last element" + matrixC.getElement(matrixC.getSize()-1,matrixC.getSize()-1));
        System.out.printf("Execution time (%s x %s) -> %s ms\n", matrixC.getSize(), matrixC.getSize(), totalTime);
        return totalTime;
    }


    public static void multiplyMatrixWithMpiOneToOne(String[] args, int matrixSize){
        int[] rows = {0};
        int[] offset = {0};
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int numWorkers = MPI.COMM_WORLD.Size() - 1;


        Matrix matrixA = new Matrix(matrixSize);
        Matrix matrixB = new Matrix(matrixSize);
        Matrix matrixC = new Matrix(matrixSize);

        double[] resiveA = new double[matrixSize*matrixSize];
        double[] resiveB = new double[matrixSize*matrixSize];
        double[] resiveC = new double[matrixSize*matrixSize];

        if (rank == MASTER) {
            matrixA.fillNumber(10);
            matrixB.fillNumber(10);

            long startTime = System.currentTimeMillis();
            int averow = matrixSize / numWorkers;
            int extra = matrixSize % numWorkers;
            offset[0] = 0;

            for (int dest = 1; dest <= numWorkers; dest++) {
                rows[0] = (dest <= extra) ? averow + 1 : averow;
                MPI.COMM_WORLD.Send(offset,0, 1, MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(rows,0, 1, MPI.INT, dest, FROM_MASTER+1);
                MPI.COMM_WORLD.Send(matrixA.getArray(offset[0]),0,rows[0]*matrixSize, MPI.DOUBLE, dest, FROM_MASTER+2);
                MPI.COMM_WORLD.Send(matrixB.getArray(0),0, matrixSize*matrixSize, MPI.DOUBLE, dest, FROM_MASTER+3);
                offset[0] = offset[0] + rows[0];
            }

            List<Double> result = new ArrayList<>();
            for (int source = 1; source <= numWorkers; source++) {
                MPI.COMM_WORLD.Recv(offset,0,1, MPI.INT, source, FROM_WORKER+3);
                MPI.COMM_WORLD.Recv(rows,0,1, MPI.INT, source, FROM_WORKER+4);
                MPI.COMM_WORLD.Recv(resiveC,0,rows[0]*matrixSize, MPI.DOUBLE, source, FROM_WORKER+5);
                for (int i = 0;i < rows[0]*matrixSize;i++) {
                    result.add(resiveC[i]);
                }
            }

            long totalTime = System.currentTimeMillis() - startTime;
            System.out.println("Last element"   + result.get(matrixSize*matrixSize-1));
            System.out.printf("Execution time (%s x %s) -> %s ms\n", matrixC.getSize(), matrixC.getSize(), totalTime);

        }else {
            MPI.COMM_WORLD.Recv(offset,0, 1, MPI.INT2,MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(rows,0, 1, MPI.INT2,MASTER, FROM_MASTER+1);
            MPI.COMM_WORLD.Recv(resiveA,0, rows[0]*matrixSize, MPI.DOUBLE,MASTER, FROM_MASTER+2);
            MPI.COMM_WORLD.Recv(resiveB,0, matrixSize * matrixSize, MPI.DOUBLE,MASTER, FROM_MASTER+3);
            for (int k = 0; k < matrixSize; k++)
                for (int i = 0; i < rows[0]; i++) {
                    matrixC.setElement(i,k,0.0);
                    for (int j = 0; j < matrixSize; j++)  matrixC.setElement(i,k,
                            matrixC.getElement(i,k) + resiveA[i * matrixSize + j] * resiveB[i * matrixSize + j]);
                }
            MPI.COMM_WORLD.Send(offset,0, 1, MPI.INT, MASTER, FROM_WORKER+3);
            MPI.COMM_WORLD.Send(rows,0, 1, MPI.INT, MASTER, FROM_WORKER+4);
            MPI.COMM_WORLD.Send(matrixC.getArray(0),0, rows[0]*matrixSize, MPI.DOUBLE, MASTER, FROM_WORKER+5);
        }
        MPI.Finalize();
    }
}
