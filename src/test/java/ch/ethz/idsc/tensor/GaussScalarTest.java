// code by jph
package ch.ethz.idsc.tensor;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ch.ethz.idsc.tensor.alg.Sort;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.mat.LinearSolve;
import ch.ethz.idsc.tensor.red.ArgMax;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.red.Norm2Squared;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class GaussScalarTest extends TestCase {
  public void testReciprocal() {
    long prime = 7919;
    for (int v = 1; v < prime; ++v) {
      Scalar num = GaussScalar.of(v, prime);
      Scalar inv = num.reciprocal();
      assertEquals(num.multiply(inv), GaussScalar.of(1, prime));
      assertEquals(inv.multiply(num), GaussScalar.of(1, prime));
    }
  }

  public void testGetter() {
    GaussScalar num = (GaussScalar) GaussScalar.of(32, 193);
    assertEquals(num.number().intValue(), 32);
    assertEquals(num.number().longValue(), 32);
    assertEquals(num.prime(), 193);
  }

  public void testMatrix1() {
    Tensor m = Tensors.matrix(new Scalar[][] { //
        { GaussScalar.of(0, 7), GaussScalar.of(3, 7) }, //
        { GaussScalar.of(1, 7), GaussScalar.of(3, 7) } //
    });
    Tensor b = Tensors.matrix(new Scalar[][] { //
        { GaussScalar.of(6, 7), GaussScalar.of(4, 7) }, //
        { GaussScalar.of(5, 7), GaussScalar.of(0, 7) } //
    });
    Tensor a = LinearSolve.withoutAbs(m, b);
    assertEquals(m.dot(a), b);
  }

  public void testMatrix2() {
    Tensor m = Tensors.matrix(new Scalar[][] { //
        { GaussScalar.of(0, 7), GaussScalar.of(3, 7) }, //
        { GaussScalar.of(1, 7), GaussScalar.of(3, 7) } //
    });
    Tensor b = Tensors.matrix(new Scalar[][] { //
        { GaussScalar.of(6, 7), GaussScalar.of(4, 7) }, //
        { GaussScalar.of(5, 7), GaussScalar.of(0, 7) } //
    });
    Tensor a = LinearSolve.of(m, b);
    assertEquals(m.dot(a), b);
  }

  public void testExtendedGcd() {
    Random r = new Random();
    for (int c1 = 0; c1 < 1000; ++c1) {
      ExtendedGcd extendedGcd = new ExtendedGcd(r.nextInt(100000) - 50000, -7920);
      assertTrue(extendedGcd.isConsistent());
    }
  }

  public void testNegativePrime() { // this is a "feature"
    Scalar a = GaussScalar.of(2, -7);
    Scalar b = GaussScalar.of(3, -7);
    assertEquals(GaussScalar.of(-2, -7), a.add(b));
  }

  public void testSqrt() {
    Scalar a = GaussScalar.of(4, 7);
    Scalar s = GaussScalar.of(2, 7);
    assertEquals(Sqrt.of(a), s);
    Scalar n2 = Norm._2.ofVector(Tensors.of(s));
    Scalar n2s = Norm2Squared.ofVector(Tensors.of(s));
    assertEquals(n2, s);
    assertEquals(n2s, a);
  }

  public void testSqrt0() {
    Scalar zero = GaussScalar.of(0, 7);
    assertEquals(Sqrt.of(zero), zero);
  }

  public void testSqrt11() {
    final int prime = 11;
    int count = 0;
    for (int c = 0; c < prime; ++c) {
      Scalar s = GaussScalar.of(c, prime);
      try {
        Scalar sqrt = Sqrt.of(s);
        ++count;
        assertEquals(sqrt.multiply(sqrt), s);
      } catch (Exception exception) {
        // ---
      }
    }
    assertEquals(count, 6);
  }

  public void testNumber() {
    Scalar scalar = GaussScalar.of(9, 23);
    assertTrue(scalar.number() instanceof Long);
  }

  public void testSort() {
    Tensor v = Tensors.of(GaussScalar.of(4, 7), GaussScalar.of(1, 7), GaussScalar.of(2, 7), GaussScalar.of(0, 7));
    Tensor r = Tensors.of(GaussScalar.of(0, 7), GaussScalar.of(1, 7), GaussScalar.of(2, 7), GaussScalar.of(4, 7));
    Tensor s = Sort.of(v);
    assertEquals(s, r);
  }

  public void testArgMax() {
    Tensor v = Tensors.of(GaussScalar.of(1, 7), GaussScalar.of(4, 7), GaussScalar.of(2, 7), GaussScalar.of(0, 7));
    int i = ArgMax.of(v);
    assertEquals(i, 1);
  }

  public void testPower() {
    int prime = 677;
    final Scalar val = GaussScalar.of(432, prime);
    Scalar now = GaussScalar.of(1, prime);
    for (int index = 0; index < prime; ++index) {
      assertEquals(Power.of(val, index), now);
      now = now.multiply(val);
    }
  }

  public void testSerializable() throws Exception {
    Scalar a = GaussScalar.of(4, 7);
    assertEquals(a, Serialization.parse(Serialization.of(a)));
    assertEquals(a, Serialization.copy(a));
  }

  public void testHash() {
    Scalar g = GaussScalar.of(4, 7);
    Scalar d = DoubleScalar.of(4.33);
    Scalar z = RealScalar.ZERO;
    Scalar c = ComplexScalar.of(RealScalar.of(2.), RealScalar.of(3.4));
    Set<Scalar> set = new HashSet<>();
    set.add(g);
    set.add(d);
    set.add(z);
    set.add(c);
    assertEquals(set.size(), 4);
  }

  public void testEquals() {
    assertFalse(GaussScalar.of(3, 7).equals(GaussScalar.of(4, 7)));
    assertFalse(GaussScalar.of(3, 7).equals(GaussScalar.of(3, 11)));
  }

  public void testEqualsMisc() {
    assertFalse(GaussScalar.of(3, 7).equals("hello"));
  }
}
