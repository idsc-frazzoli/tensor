// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class TensorStreamTest extends TestCase {
  public void testStream() {
    Tensor row = IdentityMatrix.of(5).stream().skip(2).findFirst().get();
    assertEquals(row, UnitVector.of(5, 2));
  }

  public void testReduction() {
    Tensor a = Tensors.vectorDouble(2., 1.123, .3123);
    boolean value = a.flatten(-1) //
        .map(Scalar.class::cast) //
        .map(Scalar::number) //
        .map(Number::doubleValue) //
        .map(d -> d > 0) //
        .reduce(Boolean::logicalAnd) //
        .get();
    assertTrue(value);
  }

  public void testNorm3() {
    Tensor a = Tensors.vectorLong(2, -3, 4, -1);
    double ods = a.flatten(0) //
        .map(Scalar.class::cast) //
        .map(Scalar::abs) //
        .map(Scalar::number) //
        .map(Number::doubleValue) //
        .reduce(Double::max) //
        .get();
    assertEquals(ods, 4.0);
  }

  public void testNorm4() {
    Tensor a = Tensors.vectorLong(2, -3, 4, -1);
    double ods = a.flatten(0) //
        .map(s -> (Scalar) s) //
        .map(Scalar::abs) //
        .map(Scalar::number) //
        .map(Number::doubleValue) //
        .reduce(Double::sum) //
        .get();
    assertEquals(ods, 10.0);
  }
}
