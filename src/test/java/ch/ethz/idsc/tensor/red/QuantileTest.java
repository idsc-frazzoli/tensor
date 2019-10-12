// code by jph
package ch.ethz.idsc.tensor.red;

import java.security.SecureRandom;
import java.util.Random;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Sort;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.opt.Pi;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class QuantileTest extends TestCase {
  public void testMultiple() {
    Tensor vector = Tensors.vector(0, 2, 1, 4, 3);
    Tensor q = Quantile.of(vector, Tensors.fromString("{0, 1/5, 2/5, 3/5, 4/5, 1}"));
    Tensor r = Tensors.vector(0, 0, 1, 2, 3, 4);
    assertEquals(q, r);
  }

  public void testScalar() {
    Tensor vector = Tensors.vector(0, 2, 1, 4, 3);
    Tensor q = Quantile.of(vector, RealScalar.of(0.71233));
    assertEquals(q, RealScalar.of(3));
    ScalarUnaryOperator scalarUnaryOperator = Quantile.of(vector);
    Scalar p = scalarUnaryOperator.apply(RealScalar.of(0.71233));
    assertEquals(p, RealScalar.of(3));
  }

  public void testBounds() {
    Tensor vector = Tensors.vector(0, 2, 1, 4, 3);
    try {
      Quantile.of(vector, RealScalar.of(1.01));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Quantile.of(vector, RealScalar.of(-0.01));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailSorted() {
    Tensor vector = Tensors.vector(0, 2, 1, 4, 3);
    try {
      Quantile.ofSorted(vector, Tensors.vector(0.3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testQuantity() {
    Scalar qs1 = Quantity.of(1, "m");
    Scalar qs2 = Quantity.of(4, "m");
    Scalar qs3 = Quantity.of(2, "m");
    Tensor vector = Tensors.of(qs1, qs2, qs3);
    assertEquals(Quantile.of(vector, RealScalar.ZERO), qs1);
    assertEquals(Quantile.of(vector, RealScalar.ONE), qs2);
    Scalar qs4 = Quantity.of(2, "s");
    try {
      Sort.of(Tensors.of(qs1, qs4)); // comparison fails
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testLimitTheorem() {
    Random random = new SecureRandom();
    Tensor tensor = Array.of(l -> RealScalar.of(random.nextDouble()), 5000);
    Tensor weight = Tensors.vector(0.76, 0.1, 0.25, 0.5, 0.05, 0.95, 0, 0.5, 0.99, 1);
    Tensor quantile = Quantile.of(tensor, weight);
    Tensor deviation = quantile.subtract(weight);
    Scalar maxError = Norm.INFINITY.of(deviation);
    assertTrue(Scalars.lessThan(maxError, RealScalar.of(0.05)));
  }

  public void testFailComplex() {
    Tensor tensor = Tensors.vector(-3, 2, 1, 100);
    Tensor weight = Tensors.of(RealScalar.ONE, ComplexScalar.of(1, 2));
    try {
      Quantile.of(tensor, weight);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailQuantity0() {
    Tensor tensor = Tensors.vector(-3, 2, 1, 100);
    Tensor weight = Tensors.of(Quantity.of(0, "m"));
    try {
      Quantile.of(tensor, weight);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailQuantity1() {
    Tensor tensor = Tensors.vector(-3, 2, 1, 100);
    Tensor weight = Tensors.of(Quantity.of(0.2, "m"));
    try {
      Quantile.of(tensor, weight);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailScalar() {
    try {
      Quantile.of(Pi.VALUE);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailMatrix() {
    try {
      Quantile.of(HilbertMatrix.of(3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNull() {
    try {
      Quantile.of(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
