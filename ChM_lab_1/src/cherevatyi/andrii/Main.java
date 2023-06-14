package cherevatyi.andrii;

public class Main {

    public static void main(String[] args) {
        double[][] matrix = {
                {6.92, 1.28, 0.79, 1.15, -0.66, 2.1},
                {0.92, 3.5, 1.3, -1.62, 1.02, 0.72},
                {1.15, -2.46, 6.1, 2.1, 1.483, 3.87},
                {1.33, 0.16, 2.1, 5.44, -18, 13.8},
                {1.14, -1.68, -1.217, 9, -3, -1.08} };
        printMatrix(matrix,false);
        double [][]triangleMatrix = convertToTriangle(matrix);
        printMatrix(triangleMatrix,false);
        printMatrix(solveMatrix(triangleMatrix),false);
    }

    private static double[][] convertToTriangle(double[][] matrix) {
        double [][] triangleMatrix = new double [matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) System.arraycopy(matrix[i], 0, triangleMatrix[i], 0, matrix[i].length);
        int len = triangleMatrix.length;
        for (int i = len - 1; i > 0; i--) {
            int j = len - i;
            while (j < len){
                double m = triangleMatrix[j][i] / triangleMatrix[len - i - 1][i];
                int k = 0;
                while (k < (len + 1)){
                    triangleMatrix[j][k] = triangleMatrix[j][k] - m * triangleMatrix[len - i - 1][k];
                    k++;
                }
                j++;
                System.out.println("Проміжний результат:");
                printMatrix(triangleMatrix,true);
            }
        }
        return triangleMatrix;
    }

    public static void printMatrix(double[][] matrix, boolean isIntermediate) {
        if(!isIntermediate) {
            boolean isTriangle = true;
            if (matrix.length == 1) System.out.println("Корені:");
            else {
                for (int i = 1; i < matrix.length; i++) {
                    for (int j = matrix[0].length - 2; j > matrix[0].length - i - 2; j--) {
                        if (matrix[i][j] < -0.00001 || matrix[i][j] > 0.00001) {
                            isTriangle = false;
                            break;
                        }
                    }
                }
                if (isTriangle) System.out.println("Трикутна:");
                else System.out.println("Початкова матриця:");
            }
        }
        for (double[] doubles : matrix) {
            for (double j : doubles) {
                if (Math.abs(Math.round(j) - j) < 0.001) {
                    System.out.printf("%9d    ", (int) j);
                    continue;
                }
                System.out.printf("%9.4f    ", j);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static double[][] solveMatrix(double[][] triangleMatrix) {
        int len = triangleMatrix[0].length - 1;
        double[][] result = new double[1][len];
        int i = len - 1;
        while (i >= 0) {
            result[0][len - i - 1] = triangleMatrix[i][len] / triangleMatrix[i][len - i - 1];
            for (int j = len - 1; j > i; j--) {
                result[0][len - i - 1] = result[0][len - i - 1] - triangleMatrix[i][len - j - 1]
                        * result[0][len - j - 1] / triangleMatrix[i][len - i - 1];
            }
            i--;
        }
        return result;
    }
}
