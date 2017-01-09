import java.math.BigDecimal;

/**
 * Created by benevolent0505 on 17/01/09.
 */
public class Matrix2D {

    private double[][] matrix;

    private Matrix2D(double[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        this.matrix = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                BigDecimal num = new BigDecimal(matrix[i][j]);
                this.matrix[i][j] = num.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
            }
        }
        this.matrix = matrix;
    }

    public static Matrix2D createMatrix(double[][] matrix) {
        return new Matrix2D(matrix);
    }

    public Matrix2D add(Matrix2D addMatrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        double[][] resMatrix = new double[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                resMatrix[i][j] = this.matrix[i][j] + addMatrix.getData()[i][j];
            }
        }

        return createMatrix(resMatrix);
    }

    public Matrix2D subtract(Matrix2D subMatrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        double[][] resMatrix = new double[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                resMatrix[i][j] = this.matrix[i][j] - subMatrix.getData()[i][j];
            }
        }

        return createMatrix(resMatrix);
    }

    public Matrix2D multiply(Matrix2D mulMatrix) {
        int row = matrix.length;
        int col = mulMatrix.getData()[0].length;

        double[][] resMatrix = new double[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                for (int k = 0; k < matrix[0].length; k++) {
                    BigDecimal a = new BigDecimal(resMatrix[i][j]); // 既に行列にある数
                    BigDecimal b = new BigDecimal(matrix[i][k])
                        .multiply(new BigDecimal(mulMatrix.getData()[k][j])); // 新たに足す数

                    resMatrix[i][j] = a.add(b).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
                }
            }
        }

        return createMatrix(resMatrix);
    }

    /**
     * 掃き出し法
     */
    public Matrix2D getInvMatrix() {
        if (matrix.length != matrix[0].length) return null;

        int size = matrix.length;

        double[][] copy = matrix;
        double[][] invMatrix = new double[size][size];

        // 単位行列で初期化
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                invMatrix[i][j] = i == j ? 1.0 : 0.0;
            }
        }

        // http://thira.plavox.info/blog/2008/06/_c.html
        for(int i = 0; i < size; i++){
            double tmp = 1 / matrix[i][i];
            for (int j = 0; j < size; j++) {
                copy[i][j] *= tmp;
                invMatrix[i][j] *= tmp;
            }
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    tmp = copy[j][i];
                    for (int k = 0; k < size; k++) {
                        copy[j][k] -= copy[i][k] * tmp;
                        invMatrix[j][k] -= invMatrix[i][k] * tmp;
                    }
                }
            }
        }

        return createMatrix(invMatrix);
    }

    public Matrix2D getSubMatrix2D(int startRow, int endRow, int startColumn, int endColumn) {
        double[][] subMatrix = new double[endRow - startRow + 1][endColumn - startColumn + 1];

        for (int i = 0; i < matrix.length; i++) {
            if (i < startRow || i > endRow) continue;
            for (int j = 0; j < matrix[0].length; j++) {
                if (j < startColumn || j > endColumn) continue;
                subMatrix[i - startRow][j - startColumn] = matrix[i][j];
            }
        }

        return createMatrix(subMatrix);
    }

    public double[][] getData() {
        return this.matrix;
    }

    public static void printMatrix2D(Matrix2D matrix) {
        printMatrix(matrix.getData());
    }

    public static void printMatrix(double[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        Double[][] m = new Double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                m[i][j] = matrix[i][j];
            }
        }

        printMatrix(m);
    }

    public static void printMatrix(Object[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(matrix[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }
}
