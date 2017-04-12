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
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class GaussScalarTest extends TestCase {
  public void testSome() {
    long prime = 7919;
    for (int v = 1; v < prime; ++v) {
      Scalar num = GaussScalar.of(v, prime);
      Scalar inv = num.invert();
      assertEquals(num.multiply(inv), GaussScalar.of(1, prime));
      assertEquals(inv.multiply(num), GaussScalar.of(1, prime));
    }
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

  public void testPrime() {
    try {
      GaussScalar.of(2, 20001);
      assertTrue(false);
    } catch (Exception exception) {
      // empty
    }
    try {
      GaussScalar.of(2, 100101);
      assertTrue(false);
    } catch (Exception exception) {
      // empty
    }
  }

  public void testIllegal() {
    Scalar a = GaussScalar.of(4, 7);
    Scalar b = DoubleScalar.of(4.33);
    try {
      a.add(b);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      a.multiply(b);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSqrt() {
    Scalar a = GaussScalar.of(4, 7);
    Scalar s = GaussScalar.of(2, 7);
    assertEquals(Sqrt.of(a), s);
    Scalar n2 = Norm._2.of(Tensors.of(s));
    Scalar n2s = Norm._2Squared.of(Tensors.of(s));
    assertEquals(n2, s);
    assertEquals(n2s, a);
  }

  public void testSort() {
    Tensor v = Tensors.of(GaussScalar.of(4, 7), GaussScalar.of(1, 7), GaussScalar.of(2, 7), ZeroScalar.get());
    Tensor r = Tensors.of(ZeroScalar.get(), GaussScalar.of(1, 7), GaussScalar.of(2, 7), GaussScalar.of(4, 7));
    Tensor s = Sort.of(v);
    assertEquals(s, r);
  }

  public void testArgMax() {
    Tensor v = Tensors.of(GaussScalar.of(1, 7), GaussScalar.of(4, 7), GaussScalar.of(2, 7), ZeroScalar.get());
    int i = ArgMax.of(v);
    assertEquals(i, 1);
  }

  public void testPower() {
    Scalar res = Power.of(GaussScalar.of(4, 7), 0);
    assertEquals(res, GaussScalar.of(1, 7));
    // TODO more tests once power is implemented correctly
  }

  public void testSerializable() throws Exception {
    Scalar a = GaussScalar.of(4, 7);
    assertEquals(a, Serialization.parse(Serialization.of(a)));
  }

  public void testHash() {
    Scalar g = GaussScalar.of(4, 7);
    Scalar d = DoubleScalar.of(4.33);
    Scalar z = ZeroScalar.get();
    Scalar c = ComplexScalar.of(RealScalar.of(2.), RealScalar.of(3.4));
    Set<Scalar> set = new HashSet<>();
    set.add(g);
    set.add(d);
    set.add(z);
    set.add(c);
    assertEquals(set.size(), 4);
  }
}
