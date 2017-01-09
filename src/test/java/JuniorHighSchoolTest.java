import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by benevolent0505 on 17/01/09.
 */
public class JuniorHighSchoolTest {

    private static final double[][] GRADE = new double[][] {
        {80.0, 70.0},
        {60.0, 40.0}
    };
    private static final double[][] RANDOM_Matrix = new double[][] {
        {2.0, 1.0},
        {5.0, 3.0}
    };
    private static final int SUBJECT_NUMBER = 2;

    private JuniorHighSchool school;

    @Before
    public void before() {
        school = new JuniorHighSchool(GRADE);
    }

    @Test
    public void makeRandomMatrix() throws Exception {
        double[][] matrix = school.makeRandomMatrix(SUBJECT_NUMBER);

        assertEquals("行数が科目数と異なる", matrix.length, SUBJECT_NUMBER);
        assertEquals("行数が科目数と異なる", matrix[0].length, SUBJECT_NUMBER);
    }

    @Test
    public void calcAPrime1Matrix() throws Exception {
        double[][] exp = new double[][] {
            {510.0},
            {320.0}
        };

        assertArrayEquals("A'の計算が異なる", exp, school.calcAPrime1Matrix(GRADE, RANDOM_Matrix));
    }

    @Test
    public void calcAPrime2Matrix() throws Exception {
        double[][] bPrime = new double[][] {
            {-1.8, -4.2}
        };

        double[][] exp = new double[][] {
            {-522.0, -1218.0},
            {-324.0, -756.0}
        };

        assertArrayEquals("A''の計算が異なる", exp, school.calcAPrime2Matrix(GRADE, RANDOM_Matrix, bPrime));
    }

    @Test
    public void getAdmissionResult() throws Exception {
        double[][] admissionMatrix = new double[][] {
            {1.0, 1.0},
            {0.0, 1.0}
        };

        String[][] exp = new String[][] {
            {"合", "合"},
            {"否", "合"}
        };

        assertArrayEquals("合否行列から取得する結果が異なる", exp, school.getAdmissionResult(admissionMatrix));
    }

}
