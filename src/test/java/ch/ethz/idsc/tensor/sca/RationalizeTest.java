// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import junit.framework.TestCase;

public class RationalizeTest extends TestCase {
  public void testBasics1000() {
    final Scalar max = RealScalar.of(1000);
    ScalarUnaryOperator suo = Rationalize.withDenominatorLessEquals(max);
    // assertEquals(Rationalize.of(RealScalar.of(Math.PI), RealScalar.of(102)).toString(), "311/99");
    assertEquals(suo.apply(RealScalar.of(2 / 3.)).toString(), "2/3");
    assertEquals(suo.apply(RealScalar.of(-2 / 3.)).toString(), "-2/3");
    assertEquals(suo.apply(RealScalar.of(13)).toString(), "13");
    assertEquals(suo.apply(RealScalar.of(-13)).toString(), "-13");
    assertEquals(suo.apply(RealScalar.of(4)), RealScalar.of(4));
    assertEquals(suo.apply(RealScalar.of(-4)), RealScalar.of(-4));
    Scalar tenth = RealScalar.of(0.1);
    assertEquals(suo.apply(tenth).toString(), "1/10");
    assertEquals(suo.apply(RealScalar.of(0)).toString(), "0");
  }

  private static void betterEquals(Scalar value) {
    Scalar eps = RationalScalar.of(1, 100);
    Scalar hi = Ceiling.toMultipleOf(eps).apply(value);
    Scalar lo = Floor.toMultipleOf(eps).apply(value);
    ScalarUnaryOperator suo = Rationalize.withDenominatorLessEquals(100);
    Scalar me = suo.apply(value);
    Scalar e1 = value.subtract(lo).abs();
    Scalar e2 = value.subtract(hi).abs();
    Scalar be = value.subtract(me).abs();
    assertTrue(Scalars.lessEquals(be, e1));
    assertTrue(Scalars.lessEquals(be, e2));
  }

  public void testLong() {
    RandomVariate.of(UniformDistribution.of(-20, 20), 1000).stream() //
        .map(Scalar.class::cast) //
        .forEach(RationalizeTest::betterEquals);
  }

  public void testBasics5() {
    final Scalar max = RealScalar.of(5);
    ScalarUnaryOperator suo = Rationalize.withDenominatorLessEquals(max);
    Scalar tenth = RealScalar.of(0.1);
    assertEquals(suo.apply(tenth).toString(), "1/5");
    assertEquals(suo.apply(tenth.negate()), RealScalar.ZERO);
  }

  public void testBasics4() {
    final Scalar max = RealScalar.of(4);
    ScalarUnaryOperator suo = Rationalize.withDenominatorLessEquals(max);
    Scalar tenth = RealScalar.of(0.1);
    assertEquals(suo.apply(tenth), RealScalar.ZERO);
    assertEquals(suo.apply(tenth.negate()), RealScalar.ZERO);
  }

  public void testRational() {
    Scalar THND = RealScalar.of(1000);
    ScalarUnaryOperator suo = Rationalize.withDenominatorLessEquals(THND);
    // final Scalar THND = RealScalar.of(1000);
    // assertEquals(Rationalize.of(RealScalar.of(Math.PI), RealScalar.of(102)).toString(), "311/99");
    assertEquals(suo.apply(RationalScalar.of(2, 3)), RationalScalar.of(2, 3));
    for (int num = 76510; num <= 76650; ++num) {
      RationalScalar input = (RationalScalar) RationalScalar.of(num, 10000);
      final Scalar result = suo.apply(input);
      if (Scalars.lessThan(THND, RealScalar.of(input.denominator()))) {
        assertFalse(input.equals(result));
        Scalar residual = N.DOUBLE.apply(input.subtract(result));
        assertTrue(Chop._04.allZero(residual));
      } else {
        assertTrue(input.equals(result));
      }
    }
  }

  public void testSol1() {
    ScalarUnaryOperator suo = Rationalize.withDenominatorLessEquals(RealScalar.of(6));
    Scalar tenth = RealScalar.of(0.1);
    // double comp = 1/6.0; // 0.1666...
    // System.out.println(comp);
    assertEquals(suo.apply(tenth), RationalScalar.of(1, 6));
    assertEquals(suo.apply(tenth.negate()), RationalScalar.of(-1, 6));
  }

  public void testRoundConsistency() {
    Tensor vector = Tensors.vectorDouble(-2.5, -2, -1.5, -1, -0.5, 0, 0.1, 0.5, 1, 1.5, 2, 2.5);
    List<Long> round = vector.stream() //
        .map(RealScalar.class::cast) //
        .map(RealScalar::number) //
        .map(Number::doubleValue) //
        .map(Math::round) //
        .collect(Collectors.toList());
    Tensor ratio = vector.map(Rationalize.withDenominatorLessEquals(RealScalar.ONE));
    // System.out.println(Rationalize.of(RealScalar.of(-11.5), 1));
    assertEquals(ratio, Tensors.vector(round));
  }

  private static void denCheck(Scalar scalar, Scalar max) {
    Tensor re = Rationalize.withDenominatorLessEquals(max).apply(scalar);
    RationalScalar rs = (RationalScalar) re;
    // System.out.println("---");
    // System.out.println(rs.denominator().longValue());
    // System.out.println(max);
    assertTrue(Scalars.lessEquals(RealScalar.of(rs.denominator()), max));
  }

  public void testDenominator() {
    Random random = new Random();
    Distribution distribution = UniformDistribution.of(-0.5, 0.5);
    for (Tensor scalar : RandomVariate.of(distribution, 100)) {
      Scalar max = RealScalar.of(random.nextInt(10_000_000));
      denCheck((Scalar) scalar, max);
    }
  }

  public void testFailPositive() {
    try {
      Rationalize.withDenominatorLessEquals(RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailIntegerQ() {
    try {
      Rationalize.withDenominatorLessEquals(RealScalar.of(1.23));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
