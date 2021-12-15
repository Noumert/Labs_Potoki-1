package l7;

import java.util.Arrays;

public class Matrix {
    private final int[] sizeObj;
    private final double[] buffer;

    public Matrix(int size) {
        this.sizeObj = new int[]{size};
        this.buffer = new double[size * size];
    }

    public int[] getSizeObj() {
        return sizeObj;
    }

    public int getSize() {
        return sizeObj[0];
    }

    public double[] getArray(Integer offset) {
        return Arrays.copyOfRange(buffer,offset * getSize(),buffer.length);
    }

    public double[] getBuffer() {
        return buffer;
    }

    public double getElement(int row, int col) {
        return buffer[row * getSize() + col];
    }

    public void setElement(int row, int col, double value) {
        buffer[row * getSize() + col] = value;
    }

    public void fillNumber(double number) {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                setElement(i, j, number);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                str.append(getElement(i, j)).append("\t");
            }
            str.append("\n");
        }

        return str.toString();
    }
}
