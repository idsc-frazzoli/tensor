// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ConvexHullTest extends TestCase {
  public void testSimple() {
    Tensor points = Tensors.matrix(new Number[][] { //
        { -1, 2 }, //
        { 1, -1 }, //
        { 3, -1 }, //
        { 1, 1 }, //
        { 2, 2 }, //
        { 2, 2 }, //
        { 2, 2 }, //
        { 0, -1 }, //
        { 0, -1 }, //
        { 2, -1 }, //
        { 2, -1 }, //
        { 2, 2 }, //
        { 2, 2 }, //
        { 2, 2 } }); //
    Tensor actual = ConvexHull.of(points.unmodifiable());
    Tensor expected = Tensors.fromString("{{0, -1}, {3, -1}, {2, 2}, {-1, 2}}");
    assertEquals(expected, actual);
  }
}
