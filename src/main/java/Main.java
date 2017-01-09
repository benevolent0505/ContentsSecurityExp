import java.io.IOException;

/**
 * Created by benevolent0505 on 17/01/09.
 */
public class Main {

    public static String PARTNER_IP = "";

    public static void main(String[] args) {
        runPPDMSequence();
    }

    public static void runAsATYUUGAKU() throws IOException {
        Connector connector = new Connector("TYUUGAKU", PARTNER_IP);

        double[][] grade = Converter.convert("seiseki.txt");
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

        String[][] result = chugaku.getAdmissionResult(admission);
        Matrix2D.printMatrix(result);
    }

    public static void runAsAYOBIKOU() throws IOException {
        Connector connector = new Connector("YOBIKOU", PARTNER_IP);

        double[][] weight = Converter.convert("omomi.txt");
        double[][] minimumLine = Converter.convert("saiteiten.txt");
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
        yobiko.receiveAPrime2Matrix(aPrime2);

        // 合否行列を送信
        connector.sendTable(yobiko.getAdmissionMatrix());
    }

    public static void runPPDMSequence() {
        double[][] weight = Converter.convert("debug/weight.txt");
        double[][] grade = Converter.convert("debug/grade.txt");
        double[][] minimumLine = Converter.convert("debug/lowline.txt");

        JuniorHighSchool chugaku = new JuniorHighSchool(grade);
        PrepSchool yobiko = new PrepSchool(weight, minimumLine);

        double[][] randomMatrix = chugaku.getRandomMatrix();
        yobiko.receiveRandomMatrix(randomMatrix);

        double[][] aPrime1 = chugaku.getAPrime1Matrix();
        yobiko.receiveAPrime1Matrix(aPrime1);

        double[][] bPrime1 = yobiko.getBPrime1Matrix();
        chugaku.receiveBPrime1Matrix(bPrime1);

        double[][] aPrime2 = chugaku.getAPrime2Matrix();
        yobiko.receiveAPrime2Matrix(aPrime2);

        double[][] admission = yobiko.getAdmissionMatrix();

        String[][] result = chugaku.getAdmissionResult(admission);
        Matrix2D.printMatrix(result);
    }
}
