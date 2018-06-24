// code by jph
package ch.ethz.idsc.tensor;

import java.util.List;

import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class UnprotectTest extends TestCase {
  public void testDimension1() {
    assertTrue(Unprotect.dimension1(Tensors.vector(1, 2, 3)) == Scalar.LENGTH);
    assertTrue(Unprotect.dimension1(HilbertMatrix.of(2, 4)) == 4);
    assertTrue(Unprotect.dimension1(Array.zeros(2, 3, 4)) == 3);
  }

  public void testFail1() {
    Tensor unstruct = Tensors.fromString("{{-1,0,1,2},{3,4,5}}");
    assertEquals(unstruct.length(), 2);
    try {
      Unprotect.dimension1(unstruct);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail2() {
    try {
      Unprotect.dimension1(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testList() {
    Tensor tensor = Tensors.vector(3, 2, 0, 1.234);
    List<Tensor> list = Unprotect.list(tensor);
    assertEquals(list.get(0), RealScalar.of(3));
    assertEquals(list.get(3), RealScalar.of(1.234));
  }

  public void testListFail() {
    Tensor tensor = Tensors.vector(3, 2, 0, 1.234).unmodifiable();
    try {
      Unprotect.list(tensor);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testReferencesScalar() {
    try {
      Unprotect.references(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testReferencesNull() {
    try {
      Unprotect.references(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
