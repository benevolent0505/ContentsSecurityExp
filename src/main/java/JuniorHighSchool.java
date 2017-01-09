import java.util.Random;

/**
 * Created by benevolent0505 on 17/01/09.
 */
public class JuniorHighSchool {

    private double[][] GRADE_MATRIX;
    private double[][] RANDOM_MATRIX;

    private double[][] A_PRIME_1_MATRIX;
    private double[][] A_PRIME_2_MATRIX;

    public JuniorHighSchool(double[][] gradeMatrix){
        GRADE_MATRIX = gradeMatrix;

        int subjectNum = gradeMatrix[0].length;
        RANDOM_MATRIX = makeRandomMatrix(subjectNum);
    }

    public double[][] getRandomMatrix() {
        return RANDOM_MATRIX;
    }

    public double[][] makeRandomMatrix(int subjectNum) {
        double[][] randomMatrix = new double[subjectNum][subjectNum];
        Random rnd = new Random();

        for (int i = 0; i < subjectNum; i++) {
            for (int j = 0; j < subjectNum; j++) {
                randomMatrix[i][j] = (double)rnd.nextInt(10);
            }
        }

        return randomMatrix;
    }

    public double[][] getAPrime1Matrix() {
        if (A_PRIME_1_MATRIX != null) return A_PRIME_1_MATRIX;

        this.A_PRIME_1_MATRIX = calcAPrime1Matrix(GRADE_MATRIX, RANDOM_MATRIX);
        return A_PRIME_1_MATRIX;
    }

    public double[][] calcAPrime1Matrix(double[][] gradeMatrix, double[][] randomMatrix) {
        int sRow = 0;
        int eRow = randomMatrix.length - 1;
        int sCol = 0;
        int eCol = randomMatrix[0].length / 2 - 1;

        Matrix2D mLeft = Matrix2D.createMatrix(randomMatrix).getSubMatrix2D(sRow, eRow, sCol, eCol);
        Matrix2D a = Matrix2D.createMatrix(gradeMatrix);

        return a.multiply(mLeft).getData();
    }

    public void receiveBPrime1Matrix(double[][] bPrime1) {
        if (A_PRIME_2_MATRIX != null) return;

        A_PRIME_2_MATRIX = calcAPrime2Matrix(GRADE_MATRIX, RANDOM_MATRIX, bPrime1);
    }

    public double[][] getAPrime2Matrix() {
        return A_PRIME_2_MATRIX;
    }

    public double[][] calcAPrime2Matrix(double[][] gradeMatrix, double[][] randomMatrix, double[][] bPrimeMatrix) {
        int sRow = 0;
        int eRow = randomMatrix.length - 1;
        int sCol = randomMatrix[0].length / 2;
        int eCol = randomMatrix[0].length - 1;

        Matrix2D mRight = Matrix2D.createMatrix(randomMatrix).getSubMatrix2D(sRow, eRow, sCol, eCol);
        Matrix2D a = Matrix2D.createMatrix(gradeMatrix);
        Matrix2D bPrime = Matrix2D.createMatrix(bPrimeMatrix);

        return a.multiply(mRight).multiply(bPrime).getData();
    }

    public String[][] getAdmissionResult(double[][] admissionMatrix) {
        int row = admissionMatrix.length;
        int col = admissionMatrix[0].length;

        String[][] resultMatrix = new String[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                resultMatrix[i][j] = admissionMatrix[i][j] == 1 ? "合" : "否";
            }
        }

        return resultMatrix;
    }
}
