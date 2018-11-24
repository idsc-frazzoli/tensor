// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class DeBoorTest extends TestCase {
  public void testDegree0() {
    Tensor knots = Tensors.empty().unmodifiable();
    Tensor control = Tensors.vector(-1).unmodifiable();
    DeBoor deBoor = DeBoor.of(knots, control);
    assertEquals(deBoor.apply(RealScalar.of(9)), RealScalar.of(-1));
    assertEquals(deBoor.apply(RealScalar.of(9.25)), RealScalar.of(-1));
    assertEquals(deBoor.apply(RealScalar.of(16)), RealScalar.of(-1));
    assertEquals(deBoor.degree(), 0);
    assertEquals(deBoor.knots(), knots);
    assertEquals(deBoor.control(), control);
  }

  public void testLinearUnit() {
    Tensor knots = Tensors.vector(9, 10).unmodifiable();
    Tensor control = Tensors.vector(-1, 3).unmodifiable();
    DeBoor deBoor = DeBoor.of(knots, control);
    assertEquals(deBoor.apply(RealScalar.of(9)), RealScalar.of(-1));
    assertEquals(deBoor.apply(RealScalar.of(9.25)), RealScalar.of(0));
    assertEquals(deBoor.apply(RealScalar.of(10)), RealScalar.of(3));
  }

  public void testLinearDouble() {
    Tensor knots = Tensors.vector(9, 11).unmodifiable();
    Tensor control = Tensors.vector(-1, 3).unmodifiable();
    DeBoor deBoor = DeBoor.of(knots, control);
    assertEquals(deBoor.apply(RealScalar.of(9)), RealScalar.of(-1));
    assertEquals(deBoor.apply(RealScalar.of(9.5)), RealScalar.of(0));
    assertEquals(deBoor.apply(RealScalar.of(10)), RealScalar.of(1));
    assertEquals(deBoor.apply(RealScalar.of(10.5)), RealScalar.of(2));
    assertEquals(deBoor.apply(RealScalar.of(11)), RealScalar.of(3));
    assertEquals(deBoor.degree(), 1);
    assertEquals(deBoor.knots(), knots);
    assertEquals(deBoor.control(), control);
  }

  public void testQuadratic() {
    Tensor knots = Tensors.vector(9, 10, 11, 12).unmodifiable();
    Tensor control = Tensors.vector(-1, 3, 2).unmodifiable();
    Tensor points = Tensors.empty();
    for (Tensor x : Subdivide.of(10, 11, 5))
      points.append(Tensors.of(x, DeBoor.of(knots, control).apply(x.Get())));
    Tensor result = Tensors.fromString( //
        "{{10, 1}, {51/5, 17/10}, {52/5, 11/5}, {53/5, 5/2}, {54/5, 13/5}, {11, 5/2}}");
    assertEquals(result, points);
    assertTrue(ExactScalarQ.all(points));
  }

  public void testQuadratic2() {
    Tensor knots = Tensors.vector(9, 10, 11, 12).unmodifiable();
    Tensor control = Tensors.vector(-1, 3, 2).unmodifiable();
    Tensor points = Tensors.empty();
    DeBoor deBoor = DeBoor.of(knots, control);
    for (Tensor x : Subdivide.of(10, 11, 5))
      points.append(Tensors.of(x, deBoor.apply(x.Get())));
    Tensor result = Tensors.fromString( //
        "{{10, 1}, {51/5, 17/10}, {52/5, 11/5}, {53/5, 5/2}, {54/5, 13/5}, {11, 5/2}}");
    assertEquals(result, points);
    assertTrue(ExactScalarQ.all(points));
    assertEquals(deBoor.degree(), 2);
    assertEquals(deBoor.knots(), knots);
    assertEquals(deBoor.control(), control);
  }

  public void testCubic() {
    Tensor knots = Tensors.vector(9, 10, 11, 12, 13, 14).unmodifiable();
    Tensor control = Tensors.vector(-1, 3, 2, 7).unmodifiable();
    Tensor points = Tensors.empty();
    DeBoor deBoor = DeBoor.of(knots, control);
    for (Tensor x : Subdivide.of(11, 12, 5))
      points.append(Tensors.of(x, deBoor.apply(x.Get())));
    Tensor result = Tensors.fromString( //
        "{{11, 13/6}, {56/5, 893/375}, {57/5, 621/250}, {58/5, 961/375}, {59/5, 2029/750}, {12, 3}}");
    assertEquals(result, points);
    assertTrue(ExactScalarQ.all(points));
    assertEquals(deBoor.degree(), 3);
    assertEquals(deBoor.knots(), knots);
    assertEquals(deBoor.control(), control);
  }

  public void testCubicLimit() {
    Tensor knots = Tensors.vector(11, 11, 11, 12, 13, 14).unmodifiable();
    Tensor control = Tensors.vector(3, 3, 2, 7).unmodifiable();
    Tensor points = Tensors.empty();
    for (Tensor x : Subdivide.of(11, 12, 10))
      points.append(Tensors.of(x, DeBoor.of(knots, control).apply(x.Get())));
    Tensor result = Tensors.fromString( //
        "{{11, 3}, {111/10, 35839/12000}, {56/5, 4429/1500}, {113/10, 11631/4000}, {57/5, 1073/375}, {23/2, 271/96}, {58/5, 1401/500}, {117/10, 33697/12000}, {59/5, 1069/375}, {119/10, 11757/4000}, {12, 37/12}}");
    assertEquals(result, points);
    assertTrue(ExactScalarQ.all(points));
  }

  public void testWikiStyleConstant0() {
    Tensor knots = Tensors.unmodifiableEmpty();
    Tensor control = Tensors.vector(90).unmodifiable();
    DeBoor deBoor = DeBoor.of(knots, control);
    assertEquals(deBoor.apply(RealScalar.of(0)), RealScalar.of(90));
    assertEquals(deBoor.apply(RationalScalar.of(1, 2)), RealScalar.of(90));
    assertEquals(deBoor.apply(RealScalar.of(1)), RealScalar.of(90));
  }

  public void testWikiStyleLinear0() {
    Tensor knots = Tensors.vector(0, 1).unmodifiable();
    Tensor control = Tensors.vector(90, 100).unmodifiable();
    DeBoor deBoor = DeBoor.of(knots, control);
    assertEquals(deBoor.apply(RealScalar.of(0)), RealScalar.of(90));
    assertEquals(deBoor.apply(RationalScalar.of(1, 2)), RealScalar.of(95));
    assertEquals(deBoor.apply(RealScalar.of(1)), RealScalar.of(100));
  }

  public void testWikiStyleLinear1() {
    Tensor knots = Tensors.vector(1, 2).unmodifiable();
    Tensor control = Tensors.vector(100, 120).unmodifiable();
    DeBoor deBoor = DeBoor.of(knots, control);
    assertEquals(deBoor.apply(RealScalar.of(1)), RealScalar.of(100));
    assertEquals(deBoor.apply(RationalScalar.of(3, 2)), RealScalar.of(110));
    assertEquals(deBoor.apply(RealScalar.of(2)), RealScalar.of(120));
  }

  /** example from Wikipedia */
  public void testWikiQuadratic0() {
    Tensor knots = Tensors.vector(0, 0, 1, 2).unmodifiable();
    Tensor control = Tensors.vector(0, 0, 1).unmodifiable();
    DeBoor deBoor = DeBoor.of(knots, control);
    assertEquals(deBoor.apply(RealScalar.of(0)), RealScalar.of(0));
    assertEquals(deBoor.apply(RationalScalar.of(1, 2)), RationalScalar.of(1, 8));
    assertEquals(deBoor.apply(RealScalar.of(1)), RationalScalar.of(1, 2));
  }

  public void testWikiQuadratic1() {
    Tensor knots = Tensors.vector(0, 1, 2, 3).unmodifiable();
    Tensor control = Tensors.vector(0, 1, 0).unmodifiable();
    DeBoor deBoor = DeBoor.of(knots, control);
    assertEquals(deBoor.apply(RealScalar.of(1)), RationalScalar.of(1, 2));
    assertEquals(deBoor.apply(RationalScalar.of(3, 2)), RationalScalar.of(3, 4));
    assertEquals(deBoor.apply(RealScalar.of(2)), RationalScalar.of(1, 2));
  }

  public void testWikiQuadratic2() {
    Tensor knots = Tensors.vector(1, 2, 3, 3).unmodifiable();
    Tensor control = Tensors.vector(1, 0, 0).unmodifiable();
    DeBoor deBoor = DeBoor.of(knots, control);
    assertEquals(deBoor.apply(RealScalar.of(2)), RationalScalar.of(1, 2));
    assertEquals(deBoor.apply(RationalScalar.of(5, 2)), RationalScalar.of(1, 8));
    assertEquals(deBoor.apply(RealScalar.of(3)), RationalScalar.of(0, 2));
  }

  /** example from Wikipedia */
  public void testWikiCubic0() {
    Tensor knots = Tensors.vector(-2, -2, -2, -1, 0, 1).unmodifiable();
    Tensor control = Tensors.vector(0, 0, 0, 6).unmodifiable();
    DeBoor deBoor = DeBoor.of(knots, control);
    assertEquals(deBoor.apply(RealScalar.of(-2)), RealScalar.of(0));
    assertEquals(deBoor.apply(RealScalar.of(-1.5)), RationalScalar.of(1, 8));
    assertEquals(deBoor.apply(RealScalar.of(-1)), RealScalar.of(1));
  }

  public void testWikiCubic1() {
    Tensor knots = Tensors.vector(-2, -2, -1, 0, 1, 2).unmodifiable();
    Tensor control = Tensors.vector(0, 0, 6, 0).unmodifiable();
    DeBoor deBoor = DeBoor.of(knots, control);
    assertEquals(deBoor.apply(RealScalar.of(-1)), RealScalar.of(1));
    assertEquals(deBoor.apply(RationalScalar.of(-1, 2)), RationalScalar.of(23, 8));
    assertEquals(deBoor.apply(RealScalar.of(0)), RealScalar.of(4));
  }

  public void testWikiCubic2() {
    Tensor knots = Tensors.vector(-2, -1, 0, 1, 2, 2).unmodifiable();
    Tensor control = Tensors.vector(0, 6, 0, 0).unmodifiable();
    DeBoor deBoor = DeBoor.of(knots, control);
    assertEquals(deBoor.apply(RealScalar.of(0)), RealScalar.of(4));
    assertEquals(deBoor.apply(RationalScalar.of(1, 2)), RationalScalar.of(23, 8));
    assertEquals(deBoor.apply(RealScalar.of(1)), RealScalar.of(1));
  }

  public void testWikiCubic3() {
    Tensor knots = Tensors.vector(-1, 0, 1, 2, 2, 2).unmodifiable();
    Tensor control = Tensors.vector(6, 0, 0, 0).unmodifiable();
    DeBoor deBoor = DeBoor.of(knots, control);
    assertEquals(deBoor.apply(RealScalar.of(1)), RealScalar.of(1));
    assertEquals(deBoor.apply(RationalScalar.of(3, 2)), RationalScalar.of(1, 8));
    assertEquals(deBoor.apply(RealScalar.of(2)), RealScalar.of(0));
  }

  public void testKnotsScalarFail() {
    try {
      DeBoor.of(RealScalar.ONE, Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testKnotsMatrixFail() {
    try {
      DeBoor.of(HilbertMatrix.of(2), Tensors.vector(1, 2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testControlFail() {
    for (int length = 0; length < 10; ++length)
      if (length != 2)
        try {
          DeBoor.of(Tensors.vector(1, 2), Range.of(0, length));
          assertTrue(false);
        } catch (Exception exception) {
          // ---
        }
  }
}
