// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Subdivide;
import junit.framework.TestCase;

public class DeBoorTest extends TestCase {
  public void testDegree0() {
    Tensor d = Tensors.vector(-1).unmodifiable();
    Tensor t = Tensors.empty().unmodifiable();
    assertEquals(DeBoor.of(0, d, t, RealScalar.of(9)), RealScalar.of(-1));
    assertEquals(DeBoor.of(0, d, t, RealScalar.of(9.25)), RealScalar.of(-1));
    assertEquals(DeBoor.of(0, d, t, RealScalar.of(16)), RealScalar.of(-1));
  }

  public void testLinearUnit() {
    Tensor d = Tensors.vector(-1, 3).unmodifiable();
    Tensor t = Tensors.vector(9, 10).unmodifiable();
    assertEquals(DeBoor.of(1, d, t, RealScalar.of(9)), RealScalar.of(-1));
    assertEquals(DeBoor.of(1, d, t, RealScalar.of(9.25)), RealScalar.of(0));
    assertEquals(DeBoor.of(1, d, t, RealScalar.of(10)), RealScalar.of(3));
  }

  public void testLinearDouble() {
    Tensor d = Tensors.vector(-1, 3).unmodifiable();
    Tensor t = Tensors.vector(9, 11).unmodifiable();
    assertEquals(DeBoor.of(1, d, t, RealScalar.of(9)), RealScalar.of(-1));
    assertEquals(DeBoor.of(1, d, t, RealScalar.of(9.5)), RealScalar.of(0));
    assertEquals(DeBoor.of(1, d, t, RealScalar.of(11)), RealScalar.of(3));
  }

  public void testQuadratic() {
    Tensor d = Tensors.vector(-1, 3, 2).unmodifiable();
    Tensor t = Tensors.vector(9, 10, 11, 12).unmodifiable();
    Tensor points = Tensors.empty();
    for (Tensor _x : Subdivide.of(10, 11, 5)) {
      Scalar x = _x.Get();
      Tensor r = DeBoor.of(2, d, t, x);
      points.append(Tensors.of(x, r));
    }
    Tensor result = Tensors.fromString( //
        "{{10, 1}, {51/5, 17/10}, {52/5, 11/5}, {53/5, 5/2}, {54/5, 13/5}, {11, 5/2}}");
    assertEquals(result, points);
  }

  public void testCubic() {
    Tensor d = Tensors.vector(-1, 3, 2, 7).unmodifiable();
    Tensor t = Tensors.vector(9, 10, 11, 12, 13, 14).unmodifiable();
    Tensor points = Tensors.empty();
    for (Tensor _x : Subdivide.of(11, 12, 5)) {
      Scalar x = _x.Get();
      Tensor r = DeBoor.of(3, d, t, x);
      points.append(Tensors.of(x, r));
    }
    Tensor result = Tensors.fromString( //
        "{{11, 13/6}, {56/5, 893/375}, {57/5, 621/250}, {58/5, 961/375}, {59/5, 2029/750}, {12, 3}}");
    assertEquals(result, points);
  }

  public void testCubicLimit() {
    Tensor d = Tensors.vector(3, 3, 2, 7).unmodifiable();
    Tensor t = Tensors.vector(11, 11, 11, 12, 13, 14).unmodifiable();
    Tensor points = Tensors.empty();
    for (Tensor _x : Subdivide.of(11, 12, 20)) {
      Scalar x = _x.Get();
      Tensor r = DeBoor.of(3, d, t, x);
      points.append(Tensors.of(x, r));
    }
    // Tensor result = Tensors.fromString( //
    // "{{11, 13/6}, {56/5, 893/375}, {57/5, 621/250}, {58/5, 961/375}, {59/5, 2029/750}, {12, 3}}");
    // assertEquals(result, points);
    // System.out.println(points);
  }
}
