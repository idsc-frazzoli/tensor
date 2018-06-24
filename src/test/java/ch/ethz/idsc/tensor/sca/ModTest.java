// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ModTest extends TestCase {
  public void testOffset() {
    Mod mod = Mod.function(4, -2);
    assertEquals(mod.apply(RealScalar.ONE), RealScalar.ONE);
    assertEquals(mod.apply(RealScalar.ONE.negate()), RealScalar.ONE.negate());
    assertEquals(mod.apply(RealScalar.of(3)), RealScalar.ONE.negate());
    assertEquals(mod.apply(RealScalar.of(2)), RealScalar.of(-2));
    assertEquals(mod.apply(RealScalar.of(-2)), RealScalar.of(-2));
  }

  public void testPi() {
    Mod mod = Mod.function(2 * Math.PI, -Math.PI);
    assertEquals(mod.apply(RealScalar.ONE), RealScalar.ONE);
    assertEquals(mod.apply(RealScalar.ONE.negate()), RealScalar.ONE.negate());
    assertEquals(mod.apply(RealScalar.of(3)), RealScalar.of(3));
    assertEquals(mod.apply(RealScalar.of(2)), RealScalar.of(2));
    assertEquals(mod.apply(RealScalar.of(-2)), RealScalar.of(-2));
    assertEquals(mod.apply(RealScalar.of(-4)), RealScalar.of(-4 + 2 * Math.PI));
    assertEquals(mod.apply(RealScalar.of(5)), RealScalar.of(5 - 2 * Math.PI));
  }

  public void testPart() {
    Mod mod = Mod.function(3, 1);
    assertEquals(mod.apply(RealScalar.ZERO), RealScalar.of(3));
  }

  public void testPartInteger() {
    Mod mod = Mod.function(3);
    assertEquals(mod.apply(RealScalar.of(5)), RealScalar.of(2));
    assertEquals(mod.apply(RealScalar.of(3)), RealScalar.ZERO);
  }

  public void testPartDouble() {
    Mod mod = Mod.function(2.3);
    assertEquals(mod.apply(RealScalar.of(5)), RealScalar.of(5 - 2.3 - 2.3));
    assertEquals(mod.apply(RealScalar.of(2.3)), RealScalar.ZERO);
  }

  public void testRational1() {
    Scalar m = RationalScalar.of( //
        new BigInteger("816345827635482763548726354817635487162354876135284765"), //
        new BigInteger("89354817623548127345928347902837409827304897917"));
    Scalar n = RationalScalar.of( //
        new BigInteger("876215837615238675"), //
        new BigInteger("29386548765867571711"));
    Scalar d = RationalScalar.of( //
        new BigInteger("-9874698736"), //
        new BigInteger("230817253875"));
    String mathem = "-103027088435315211090616226024636019095132932417575467867214490110/2625829705559600168587308022574969971429853524559950547309532025987";
    Scalar expected = Scalars.fromString(mathem);
    {
      assertEquals(Mod.function(n, d).apply(m), expected);
    }
    {
      Scalar r = Mod.function(n, d).apply(N.DOUBLE.of(m));
      assertEquals(Chop._09.apply(r.subtract(expected)), RealScalar.ZERO);
    }
  }

  public void testRational2() {
    Scalar m = RationalScalar.of( //
        new BigInteger("816345827635482763548726354817635487162354876135284765"), //
        new BigInteger("89354817623548127345928347902837409827917"));
    Scalar n = RationalScalar.of( //
        new BigInteger("876215837615238675"), //
        new BigInteger("29386548765867571711332"));
    Scalar d = RationalScalar.of( //
        new BigInteger("-3453453453453459874698736"), //
        new BigInteger("230817253875123123"));
    String mathem = "-4365252651220514098051928821648538890491086296970254009125271304896855/291758856173288907624108202447331994414076589308384576368761716";
    Scalar expected = Scalars.fromString(mathem);
    {
      assertEquals(Mod.function(n, d).apply(m), expected);
    }
    {
      Scalar r = Mod.function(n, d).apply(N.DOUBLE.of(m));
      assertEquals(Chop.below(.01).apply(r.subtract(expected)), RealScalar.ZERO);
    }
  }

  public void testTemplate() {
    Mod mod = Mod.function(RealScalar.of(5));
    assertEquals(mod.of(RealScalar.of(6)), RealScalar.ONE);
    assertEquals(mod.of(Tensors.vector(-1, 3, 6)), Tensors.vector(4, 3, 1));
  }

  public void testNegative() {
    Mod mod = Mod.function(RealScalar.of(-5));
    @SuppressWarnings("unused")
    Scalar m = mod.apply(RealScalar.of(2));
    // desired behavior not clear
    // System.out.println(m);
  }

  public void testComplex() {
    // Mathematica Mod[10, 2 + 3 I] == -2 I
    Mod mod = Mod.function(ComplexScalar.of(2, 3));
    Scalar res = mod.apply(RealScalar.of(10));
    // -2 I + (2 + 3 I) + I (2 + 3 I) == -1 + 3 I
    assertEquals(res, ComplexScalar.of(-1, 3));
  }

  public void testDecimal1() {
    Scalar pi = DecimalScalar.of("3.141592653589793238462643383279502884197169399375105820974944592");
    Mod mod = Mod.function(pi);
    Scalar re = mod.apply(RealScalar.of(100));
    assertTrue(re instanceof DecimalScalar);
    // Mathematica gives
    // ................................. 2.610627738716409607658055118335410589887748619371719549776717638
    assertTrue(re.toString().startsWith("2.6106277387164096076580551183354105898877486193717195497767176"));
  }

  private static void _checkComplexSet(Scalar n, int size) {
    Mod mod = Mod.function(n);
    Set<Scalar> set = new HashSet<>();
    for (Tensor re : Range.of(-7, 8)) {
      for (Tensor im : Range.of(-7, 8)) {
        Scalar z = ComplexScalar.of(re.Get(), im.Get());
        set.add(mod.apply(z));
      }
    }
    // size is consistent with Mathematica
    assertEquals(set.size(), size);
    // for n = 2 + 3 * I
    // Mathematica has representatives as
    // {-2, -1, -1 - I, -1 + I, 0, -I, I, -2 I, 2 I, 1, 1 - I, 1 + I, 2}
    // Tensor lib has
    // [1+2*I, I, 0, -2+3*I, -1+4*I, 4*I, -2+2*I, -1+3*I, 3*I, -1+2*I, 2*I, 1+3*I, -1+I]
  }

  public void testComplexSet() {
    _checkComplexSet(ComplexScalar.of(2, 3), 13);
    // _checkComplexSet(ComplexScalar.of(1, 3), 13); // not consistent with Mathematica
    // _checkComplexSet(ComplexScalar.of(2, 4), 13);
  }

  public void testQuantity() {
    Scalar qs1 = Quantity.of(5, "s");
    Scalar qs2 = Quantity.of(3, "s");
    Scalar qs3 = Quantity.of(2, "s");
    Scalar res = Mod.function(qs2).apply(qs1);
    assertEquals(res, qs3);
  }

  public void testFail() {
    try {
      Mod.function(RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Mod.function(RealScalar.ZERO, RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
