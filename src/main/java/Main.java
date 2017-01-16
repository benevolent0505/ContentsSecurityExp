import java.io.IOException;

/**
 * Created by benevolent0505 on 17/01/09.
 */
public class Main {

    public static String PARTNER_IP = "127.0.0.1";

    public static void main(String[] args) {
        runNonPPDMSequence();
    }

    public static void runAsATYUUGAKU() throws IOException {
        Connector connector = new Connector("TYUUGAKU", PARTNER_IP);

        double[][] grade = IOManager.input("seiseki.txt");
        JuniorHighSchool chugaku = new JuniorHighSchool(grade);

        // Mを送信
        connector.sendTable(chugaku.getRandomMatrix());

        // A'を送信
        connector.sendTable(chugaku.getAPrime1Matrix());

        // B'を受信
        double[][] bPrime1 = connector.getTable();
        chugaku.receiveBPrime1Matrix(bPrime1);

        // A''を送信
        connector.sendTable(chugaku.getAPrime2Matrix());

        // 合否行列を受信
        double[][] admission = connector.getTable();
        IOManager.output(admission, "admission.txt");

        String[][] result = chugaku.getAdmissionResult(admission);
        Matrix2D.printMatrix(result);

        /**通信を行う際に使用します*/
        connector.endConnection();
    }

    public static void runAsAYOBIKOU() throws IOException {
        Connector connector = new Connector("YOBIKOU", PARTNER_IP);

        double[][] weight = IOManager.input("omomi.txt");
        double[][] minimumLine = IOManager.input("saiteiten.txt");
        PrepSchool yobiko = new PrepSchool(weight, minimumLine);

        // Mを受信
        double[][] randomMatrix = connector.getTable();
        yobiko.receiveRandomMatrix(randomMatrix);

        // A'を受信
        double[][] aPrime1 = connector.getTable();
        yobiko.receiveAPrime1Matrix(aPrime1);

        // B'を送信
        connector.sendTable(yobiko.getBPrime1Matrix());

        // A''を受信
        double[][] aPrime2 = connector.getTable();
        yobiko.receiveAPrime2Matrix(aPrime2); // receiveAPrime2Matrix

        Matrix2D.printMatrix(yobiko.getAptitudeMatrix());
        IOManager.output(yobiko.getAptitudeMatrix(), "aptitude.txt");

        // 合否行列を送信
        connector.sendTable(yobiko.getAdmissionMatrix());

        /**通信を行う際に使用します*/
        connector.endConnection();
    }

    public static void runNonPPDMSequence() {
        // 行列読み取り
        Matrix2D weight = Matrix2D.createMatrix(IOManager.input("omomi.txt"));
        Matrix2D grade = Matrix2D.createMatrix(IOManager.input("seiseki.txt"));
        double[][] minimumLine = IOManager.input("saiteiten.txt");

        // 適性行列の作成
        double[][] aptitude = grade.multiply(weight).getData();

        // 合否行列の作成
        int row = aptitude.length;
        int col = aptitude[0].length;

        double[][] admission = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                admission[i][j] = aptitude[i][j] - minimumLine[0][j] >= 0 ? 1.0 : 0.0;
            }
        }

        System.out.println("適性行列");
        Matrix2D.printMatrix(aptitude);
        IOManager.output(aptitude, "aptitude.txt");

        System.out.println("合否行列");
        Matrix2D.printMatrix(admission);
        IOManager.output(admission, "admission.txt");
    }
}
