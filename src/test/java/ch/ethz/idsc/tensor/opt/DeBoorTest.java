// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RationalScalar;
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
    assertEquals(DeBoor.of(1, d, t, RealScalar.of(10)), RealScalar.of(1));
    assertEquals(DeBoor.of(1, d, t, RealScalar.of(10.5)), RealScalar.of(2));
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
    for (Tensor _x : Subdivide.of(11, 12, 10)) {
      Scalar x = _x.Get();
      Tensor r = DeBoor.of(3, d, t, x);
      points.append(Tensors.of(x, r));
    }
    Tensor result = Tensors.fromString( //
        "{{11, 3}, {111/10, 35839/12000}, {56/5, 4429/1500}, {113/10, 11631/4000}, {57/5, 1073/375}, {23/2, 271/96}, {58/5, 1401/500}, {117/10, 33697/12000}, {59/5, 1069/375}, {119/10, 11757/4000}, {12, 37/12}}");
    assertEquals(result, points);
  }

  public void testWikiStyleConstant0() {
    Tensor d = Tensors.vector(90).unmodifiable();
    Tensor t = Tensors.vector().unmodifiable();
    assertEquals(DeBoor.of(0, d, t, RealScalar.of(0)), RealScalar.of(90));
    assertEquals(DeBoor.of(0, d, t, RationalScalar.of(1, 2)), RealScalar.of(90));
    assertEquals(DeBoor.of(0, d, t, RealScalar.of(1)), RealScalar.of(90));
  }

  public void testWikiStyleLinear0() {
    Tensor d = Tensors.vector(90, 100).unmodifiable();
    Tensor t = Tensors.vector(0, 1).unmodifiable();
    assertEquals(DeBoor.of(1, d, t, RealScalar.of(0)), RealScalar.of(90));
    assertEquals(DeBoor.of(1, d, t, RationalScalar.of(1, 2)), RealScalar.of(95));
    assertEquals(DeBoor.of(1, d, t, RealScalar.of(1)), RealScalar.of(100));
  }

  public void testWikiStyleLinear1() {
    Tensor d = Tensors.vector(100, 120).unmodifiable();
    Tensor t = Tensors.vector(1, 2).unmodifiable();
    assertEquals(DeBoor.of(1, d, t, RealScalar.of(1)), RealScalar.of(100));
    assertEquals(DeBoor.of(1, d, t, RationalScalar.of(3, 2)), RealScalar.of(110));
    assertEquals(DeBoor.of(1, d, t, RealScalar.of(2)), RealScalar.of(120));
  }

  /** example from Wikipedia */
  public void testWikiQuadratic0() {
    Tensor d = Tensors.vector(0, 0, 1).unmodifiable();
    Tensor t = Tensors.vector(0, 0, 1, 2).unmodifiable();
    assertEquals(DeBoor.of(2, d, t, RealScalar.of(0)), RealScalar.of(0));
    assertEquals(DeBoor.of(2, d, t, RationalScalar.of(1, 2)), RationalScalar.of(1, 8));
    assertEquals(DeBoor.of(2, d, t, RealScalar.of(1)), RationalScalar.of(1, 2));
  }

  public void testWikiQuadratic1() {
    Tensor d = Tensors.vector(0, 1, 0).unmodifiable();
    Tensor t = Tensors.vector(0, 1, 2, 3).unmodifiable();
    assertEquals(DeBoor.of(2, d, t, RealScalar.of(1)), RationalScalar.of(1, 2));
    assertEquals(DeBoor.of(2, d, t, RationalScalar.of(3, 2)), RationalScalar.of(3, 4));
    assertEquals(DeBoor.of(2, d, t, RealScalar.of(2)), RationalScalar.of(1, 2));
  }

  public void testWikiQuadratic2() {
    Tensor d = Tensors.vector(1, 0, 0).unmodifiable();
    Tensor t = Tensors.vector(1, 2, 3, 3).unmodifiable();
    assertEquals(DeBoor.of(2, d, t, RealScalar.of(2)), RationalScalar.of(1, 2));
    assertEquals(DeBoor.of(2, d, t, RationalScalar.of(5, 2)), RationalScalar.of(1, 8));
    assertEquals(DeBoor.of(2, d, t, RealScalar.of(3)), RationalScalar.of(0, 2));
  }

  /** example from Wikipedia */
  public void testWikiCubic0() {
    Tensor d = Tensors.vector(0, 0, 0, 6).unmodifiable();
    Tensor t = Tensors.vector(-2, -2, -2, -1, 0, 1).unmodifiable();
    assertEquals(DeBoor.of(3, d, t, RealScalar.of(-2)), RealScalar.of(0));
    assertEquals(DeBoor.of(3, d, t, RealScalar.of(-1.5)), RationalScalar.of(1, 8));
    assertEquals(DeBoor.of(3, d, t, RealScalar.of(-1)), RealScalar.of(1));
  }

  public void testWikiCubic1() {
    Tensor d = Tensors.vector(0, 0, 6, 0).unmodifiable();
    Tensor t = Tensors.vector(-2, -2, -1, 0, 1, 2).unmodifiable();
    assertEquals(DeBoor.of(3, d, t, RealScalar.of(-1)), RealScalar.of(1));
    assertEquals(DeBoor.of(3, d, t, RationalScalar.of(-1, 2)), RationalScalar.of(23, 8));
    assertEquals(DeBoor.of(3, d, t, RealScalar.of(0)), RealScalar.of(4));
  }

  public void testWikiCubic2() {
    Tensor d = Tensors.vector(0, 6, 0, 0).unmodifiable();
    Tensor t = Tensors.vector(-2, -1, 0, 1, 2, 2).unmodifiable();
    assertEquals(DeBoor.of(3, d, t, RealScalar.of(0)), RealScalar.of(4));
    assertEquals(DeBoor.of(3, d, t, RationalScalar.of(1, 2)), RationalScalar.of(23, 8));
    assertEquals(DeBoor.of(3, d, t, RealScalar.of(1)), RealScalar.of(1));
  }

  public void testWikiCubic3() {
    Tensor d = Tensors.vector(6, 0, 0, 0).unmodifiable();
    Tensor t = Tensors.vector(-1, 0, 1, 2, 2, 2).unmodifiable();
    assertEquals(DeBoor.of(3, d, t, RealScalar.of(1)), RealScalar.of(1));
    assertEquals(DeBoor.of(3, d, t, RationalScalar.of(3, 2)), RationalScalar.of(1, 8));
    assertEquals(DeBoor.of(3, d, t, RealScalar.of(2)), RealScalar.of(0));
  }
}
