import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by benevolent0505 on 17/01/09.
 */
public class PrepSchoolTest {

    private static final double[][] WEIGHT_MATRIX = new double[][] {
        {0.8, 1.2},
        {1.1, 0.9}
    };
    private static final double[][] MINIMUM_LINE_MATRIX = new double[][] {
        {120.0, 100.0}
    };
    private static final double[][] INV_RANDOM_MATRIX = new double[][] {
        {3.0, -1.0},
        {-5.0, 2.0}
    };

    private PrepSchool school;

    @Before
    public void before() {
        school = new PrepSchool(WEIGHT_MATRIX, MINIMUM_LINE_MATRIX);
    }

    @Test
    public void receiveRndMatrix() throws Exception {

    }

    @Test
    public void getBPrime1Matrix() throws Exception {
        double[][] exp = new double[][] {
            {-1.8, -4.2},
        };

        assertArrayEquals("B'の計算結果が異なる", exp, school.calcBPrime1Matrix(INV_RANDOM_MATRIX, WEIGHT_MATRIX));
    }

    @Test
    public void getBPrime2Matrix() throws Exception {
        double[][] aPrime = new double[][] {
            {510.0},
            {320.0}
        };

        double[][] exp = new double[][] {
            {663.0, 1377.0},
            {416.0, 864.0}
        };

        assertArrayEquals("B''の計算結果が異なる", exp, school.calcBPrime2Matrix(aPrime, INV_RANDOM_MATRIX, WEIGHT_MATRIX));
    }

}
