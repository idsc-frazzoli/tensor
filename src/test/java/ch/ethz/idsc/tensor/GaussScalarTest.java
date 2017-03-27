// code by jph
package ch.ethz.idsc.tensor;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ch.ethz.idsc.tensor.mat.LinearSolve;
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

  public void testMatrix() {
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
      a.plus(b);
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
