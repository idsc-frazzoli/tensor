// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.TensorProduct;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.red.Hypot;
import junit.framework.TestCase;

public class OuterTest extends TestCase {
  public void testSimple() {
    Tensor a = Tensors.vector(1, 2);
    Tensor b = Tensors.vector(3, 4, 5);
    Tensor tensor = Outer.of(TensorProduct::of, a, b);
    assertEquals(Dimensions.of(tensor), Arrays.asList(2, 3));
    assertEquals(tensor, TensorProduct.of(a, b));
  }

  public void testScalar() {
    Tensor tensor = Outer.of(Hypot.BIFUNCTION, Tensors.vector(12, 3), Tensors.vector(1, 7, 9, 0));
    assertEquals(Dimensions.of(tensor), Arrays.asList(2, 4));
    assertEquals(tensor.get(0).extract(2, 4), Tensors.vector(15, 12));
  }

  public void testScalarDivide() {
    Tensor result = Outer.of(Scalar::divide, Tensors.vector(12, 3), Tensors.vector(1, 7, 9, 2));
    assertEquals(Dimensions.of(result), Arrays.asList(2, 4));
    ExactTensorQ.require(result);
    Tensor tensor = Tensors.fromString("{{12, 12/7, 4/3, 6}, {3, 3/7, 1/3, 3/2}}");
    assertEquals(tensor, result);
  }

  public void testMixed() {
    Tensor result = Outer.of(Tensor::multiply, HilbertMatrix.of(2), Tensors.vector(1, 7, 9));
    assertEquals(Dimensions.of(result), Arrays.asList(2, 3, 2));
    ExactTensorQ.require(result);
    Tensor tensor = Tensors.fromString("{{{1, 1/2}, {7, 7/2}, {9, 9/2}}, {{1/2, 1/3}, {7/2, 7/3}, {9/2, 3}}}");
    assertEquals(result, tensor);
  }

  public void testAppend() {
    Tensor matrix = HilbertMatrix.of(2);
    Tensor tensor = Outer.of(Tensor::append, matrix, Tensors.vector(1, 7, 9));
    assertEquals(matrix, HilbertMatrix.of(2));
    assertEquals(Dimensions.of(tensor), Arrays.asList(2, 3, 3));
  }
}
