import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Created by benevolent0505 on 17/01/09.
 */
public class PrepSchool {

    private double[][] WEIGHT_MATRIX;
    private double[][] MINIMUM_LINE_MATRIX;
    private double[][] INV_RANDOM_MATRIX;

    private double[][] B_PRIME_1_MATRIX;
    private double[][] B_PRIME_2_MATRIX;

    private double[][] APTITUDE_MATRIX;
    private double[][] ADMISSION_MATRIX;

    public PrepSchool(double[][] weight, double[][] minimumLine) {
        WEIGHT_MATRIX = weight;
        MINIMUM_LINE_MATRIX = minimumLine;
    }

    public void receiveRandomMatrix(double[][] randomMatrix) {
        INV_RANDOM_MATRIX = convertInvMatrix(randomMatrix);
        B_PRIME_1_MATRIX = calcBPrime1Matrix(INV_RANDOM_MATRIX, WEIGHT_MATRIX);
    }

    private double[][] convertInvMatrix(double[][] matrix) {
//        RealMatrix m = MatrixUtils.createRealMatrix(matrix);
//        return MatrixUtils.blockInverse(m, 0).getData();
        Matrix2D m = Matrix2D.createMatrix(matrix);
        return m.getInvMatrix().getData();
    }

    public double[][] getBPrime1Matrix() {
        if (B_PRIME_1_MATRIX != null) return B_PRIME_1_MATRIX;

        B_PRIME_1_MATRIX = calcBPrime1Matrix(INV_RANDOM_MATRIX, WEIGHT_MATRIX);
        return B_PRIME_1_MATRIX;
    }

    public double[][] calcBPrime1Matrix(double[][] invRandomMatrix, double[][] weight) {
        int sRow = invRandomMatrix.length / 2;
        int eRow = invRandomMatrix.length - 1;
        int sCol = 0;
        int eCol = invRandomMatrix[0].length - 1;

        Matrix2D invMBottom = Matrix2D.createMatrix(invRandomMatrix).getSubMatrix2D(sRow, eRow, sCol, eCol);
        Matrix2D b = Matrix2D.createMatrix(weight);

        return invMBottom.multiply(b).getData();
    }

    public void receiveAPrime1Matrix(double[][] aPrime1) {
        if (B_PRIME_2_MATRIX != null) return;

        B_PRIME_2_MATRIX = calcBPrime2Matrix(aPrime1, INV_RANDOM_MATRIX, WEIGHT_MATRIX);
    }

    public double[][] calcBPrime2Matrix(double[][] aPrime, double[][] invRandomMatrix, double[][] weight) {
        int sRow = 0;
        int eRow = invRandomMatrix.length / 2 - 1;
        int sCol = 0;
        int eCol = invRandomMatrix[0].length - 1;

        Matrix2D aPrimeMatrix = Matrix2D.createMatrix(aPrime);
        Matrix2D invMTop = Matrix2D.createMatrix(invRandomMatrix).getSubMatrix2D(sRow, eRow, sCol, eCol);
        Matrix2D b = Matrix2D.createMatrix(weight);

        return aPrimeMatrix.multiply(invMTop).multiply(b).getData();
    }

    public void receiveAPrime2Matrix(double[][] aPrime2) {
        if (APTITUDE_MATRIX != null) return;

        APTITUDE_MATRIX = calcAptitudeMatrix(aPrime2, B_PRIME_2_MATRIX);
    }

    public double[][] calcAptitudeMatrix(double[][] aPrime2, double[][] bPrime2) {
        Matrix2D aPrime2Matrix = Matrix2D.createMatrix(aPrime2);
        Matrix2D bPrime2Matrix = Matrix2D.createMatrix(bPrime2);

        return aPrime2Matrix.add(bPrime2Matrix).getData();
    }

    public double[][] getAdmissionMatrix() {
        if (ADMISSION_MATRIX != null) return ADMISSION_MATRIX;

        ADMISSION_MATRIX = calcAdmissionMatrix(APTITUDE_MATRIX, MINIMUM_LINE_MATRIX);
        return ADMISSION_MATRIX;
    }

    public double[][] calcAdmissionMatrix(double[][] aptitude, double[][] minimumLine) {
        int row = aptitude.length;
        int col = aptitude[0].length;

        double[][] admissionMatrix = new double[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j <col; j++) {
                admissionMatrix[i][j] = aptitude[i][j] - minimumLine[0][j] >= 0 ? 1.0 : 0.0;
            }
        }

        return admissionMatrix;
    }
}
