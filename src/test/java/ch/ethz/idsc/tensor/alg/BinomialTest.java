// code by jph
package ch.ethz.idsc.tensor.alg;

import java.math.BigInteger;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class BinomialTest extends TestCase {
  public void testBasic() {
    assertEquals(Binomial.of(10, Integer.MIN_VALUE), RealScalar.ZERO);
    assertEquals(Binomial.of(10, -13), RealScalar.ZERO);
    assertEquals(Binomial.of(10, -1), RealScalar.ZERO);
    assertEquals(Binomial.of(10, 0), RealScalar.ONE);
    assertEquals(Binomial.of(10, 3), RealScalar.of(120));
    assertEquals(Binomial.of(10, 10), RealScalar.ONE);
    assertEquals(Binomial.of(10, 11), RealScalar.ZERO);
    assertEquals(Binomial.of(10, Integer.MAX_VALUE), RealScalar.ZERO);
  }

  public void testSingleIn() {
    assertEquals(Binomial.of(10).over(0), RealScalar.ONE);
    assertEquals(Binomial.of(RealScalar.of(10)).over(3), RealScalar.of(120));
    assertEquals(Binomial.of(10).over(10), RealScalar.ONE);
  }

  public void testRow() {
    assertEquals(Binomial.of(0).row, Tensors.vector(1));
    assertEquals(Binomial.of(1).row, Tensors.vector(1));
    assertEquals(Binomial.of(2).row, Tensors.vector(1, 2));
    assertEquals(Binomial.of(3).row, Tensors.vector(1, 3));
    assertEquals(Binomial.of(RealScalar.of(4)).row, Tensors.vector(1, 4, 6));
    assertEquals(Binomial.of(5).row, Tensors.vector(1, 5, 10));
  }

  public void testTreadSafe() {
    IntStream.range(0, 20000).parallel() //
        .forEach(n -> Binomial.of(50 + (n % 500)));
    // System.out.println(Binomial.MEMO_REUSE);
  }

  public void testOrder() {
    assertEquals(Binomial.of(0, 0), RealScalar.ONE);
    assertEquals(Binomial.of(0, 7), RealScalar.ZERO);
    assertEquals(Binomial.of(3, 7), RealScalar.ZERO);
    // case defined in Mathematica
    // System.out.println(Binomial.of(-3, 7));
  }

  public void testDecimal() {
    assertTrue(Chop._08.close(Binomial.of(RealScalar.of(5), RealScalar.of(8.915)), DoubleScalar.of(-0.0001814896744175351)));
    assertTrue(Chop._08.close(Binomial.of(RealScalar.of(3.21), RealScalar.of(4.5)), DoubleScalar.of(-0.03395179589776722)));
    assertTrue(Chop._08.close(Binomial.of(RealScalar.of(10.21), RealScalar.of(3)), DoubleScalar.of(128.66999350000037)));
    assertTrue(Chop._08.close(Binomial.of(RealScalar.of(8.81), RealScalar.of(11.3)), DoubleScalar.of(0.0011937860196171754)));
  }

  public void testScalar() {
    assertEquals(Binomial.of(RealScalar.of(4), RealScalar.of(2)), RealScalar.of(6));
  }

  public void testFailNK() {
    try {
      Binomial.of(-3, 0);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailN() {
    try {
      Binomial.of(RealScalar.of(10.21));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Binomial.of(-1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testLarge() {
    Scalar res = Binomial.of(1000, 500);
    BigInteger bi = new BigInteger(
        "270288240945436569515614693625975275496152008446548287007392875106625428705522193898612483924502370165362606085021546104802209750050679917549894219699518475423665484263751733356162464079737887344364574161119497604571044985756287880514600994219426752366915856603136862602484428109296905863799821216320");
    assertEquals(res, RealScalar.of(bi));
  }

  public void testLargeFail() {
    try {
      Binomial.of(RealScalar.of(123412341234324L), RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Binomial.of(RealScalar.of(-123412341234324L), RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
