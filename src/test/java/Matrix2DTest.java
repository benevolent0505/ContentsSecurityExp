import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by benevolent0505 on 17/01/09.
 */
public class Matrix2DTest {

    @Test
    public void add() throws Exception {
        Matrix2D a = Matrix2D.createMatrix(new double[][] {
            {-522.0, -1218.0},
            {-324.0, -756.0}
        });
        Matrix2D b = Matrix2D.createMatrix(new double[][] {
            {663.0, 1377.0},
            {416.0, 864.0}
        });

        double[][] exp = new double[][] {
            {141.0, 159.0},
            {92.0, 108.0}
        };

        assertArrayEquals("足し算の結果が異なる", exp, a.add(b).getData());
    }

    @Test
    public void subtract() throws Exception {
        Matrix2D a = Matrix2D.createMatrix(new double[][] {
            {141.0, 159.0},
            {92.0, 108.0}
        });
        Matrix2D b = Matrix2D.createMatrix(new double[][] {
            {120.0, 100.0},
            {120.0, 100.0}
        });

        double[][] exp = new double[][] {
            {21.0, 59.0},
            {-28.0, 8.0}
        };

        assertArrayEquals("引き算の結果が異なる", exp, a.subtract(b).getData());
    }

    @Test
    public void multiply() throws Exception {
        Matrix2D a = Matrix2D.createMatrix(new double[][] {
            {80.0, 70.0},
            {60.0, 40.0}
        });
        Matrix2D mLeft = Matrix2D.createMatrix(new double[][] {
            {2.0},
            {5.0}
        });
        Matrix2D b = Matrix2D.createMatrix(new double[][] {
            {0.8, 1.2},
            {1.1, 0.9}
        });
        Matrix2D mInvBtm = Matrix2D.createMatrix(new double[][] {
            {-5.0, 2.0}
        });

        double[][] exp1 = new double[][] {
            {510.0},
            {320.0}
        };
        double[][] exp2 = new double[][] {
            {-1.8, -4.2},
        };

        assertArrayEquals("2-2, 1-2行列のかけ算の結果が異なる", exp1, a.multiply(mLeft).getData());
        assertArrayEquals("2-1, 2-2行列かけ算の結果が異なる", exp2, mInvBtm.multiply(b).getData());
    }

    @Test
    public void getInvMatrix() throws Exception {
        Matrix2D m = Matrix2D.createMatrix(new double[][] {
            {2.0, 1.0},
            {5.0, 3.0}
        });

        double[][] exp = new double[][] {
            {3.0, -1.0},
            {-5.0, 2.0}
        };

        assertArrayEquals("逆行列の結果が異なる", exp, m.getInvMatrix().getData());
    }

    @Test
    public void getSubMatrix2D() throws Exception {
        Matrix2D m = Matrix2D.createMatrix(new double[][] {
            {2.0, 1.0},
            {5.0, 3.0}
        });
        Matrix2D mInv = Matrix2D.createMatrix(new double[][] {
            {3.0, -1.0},
            {-5.0, 2.0}
        });

        double[][] exp1 = new double[][] {
            {1.0},
            {3.0}
        };
        double[][] exp2 = new double[][] {
            {3.0, -1.0}
        };

        assertArrayEquals("mから切り出した行列が異なる", exp1, m.getSubMatrix2D(0, 1, 1, 1).getData());
        assertArrayEquals("mInvから切り出した行列が異なる", exp2, mInv.getSubMatrix2D(0, 0, 0, 1).getData());
    }

    @Test
    public void getData() throws Exception {
        Matrix2D a = Matrix2D.createMatrix(new double[][] {
            {80.0, 70.0},
            {60.0, 40.0}
        });

        double[][] exp = new double[][] {
            {80.0, 70.0},
            {60.0, 40.0}
        };

        assertArrayEquals("元の配列が得られない", exp, a.getData());
    }

}
