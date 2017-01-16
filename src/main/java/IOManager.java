import java.io.*;
import java.util.Scanner;

/**
 * Created by benevolent0505 on 17/01/09.
 */
public class IOManager {

    /**
     * 表形式のファイル前提
     */
    public static double[][] input(String filename) {
        double[][] matrix;
        Scanner scanner = new Scanner(IOManager.class.getClassLoader().getResourceAsStream(filename));
        scanner.useDelimiter("\n");
        scanner.next(); // 1行目はheaderなので無視

        int row = 0;
        int col = 0;
        while (scanner.hasNext()) {
            String strs[] = scanner.next().split(",", 0);
            col = strs.length - 1;
            row += 1;
        }
        scanner.reset();
        scanner = new Scanner(IOManager.class.getClassLoader().getResourceAsStream(filename));
        scanner.next();

        int counter = 0;
        matrix = new double[row][col];
        while (scanner.hasNext()) {
            String strs[] = scanner.next().split(",", 0);
            for (int i = 0; i < strs.length - 1; i++) {
                // +1は, 行のheaderを無視するため
                matrix[counter][i] = Double.parseDouble(strs[i + 1]);
            }
            counter += 1;
        }

        return matrix;
    }

    public static void output(double[][] matrix, String filename) {
        String convMatrix[][] = new String[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                convMatrix[i][j] = Double.toString(matrix[i][j]);
            }
        }

        IOManager.output(convMatrix, filename);
    }

    public static void output(String[][] matrix, String filename) {
        File file = new File(filename);

        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));

            int row = matrix.length;
            int col = matrix[0].length;

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    // 行末のチェック
                    if (j != col -1) {
                        writer.print(matrix[i][j]);
                        writer.print(", ");
                    } else {
                        // 最終行の改行を取り除く
                        if (i == row - 1) {
                            writer.print(matrix[i][j]);
                        } else {
                            writer.println(matrix[i][j]);
                        }
                    }
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
