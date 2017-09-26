// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.QuantityTensor;
import ch.ethz.idsc.tensor.qty.Unit;
import ch.ethz.idsc.tensor.red.Tally;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class RescaleTest extends TestCase {
  public void testScalar() {
    try {
      Rescale.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testEmpty() {
    assertEquals(Rescale.of(Tensors.empty()), Tensors.empty());
  }

  public void testEqual() {
    assertEquals(Rescale.of(Tensors.vector(2, 2, 2, 2)), Array.zeros(4));
  }

  public void testSimple() {
    Tensor res = Rescale.of(Tensors.vector(-.7, .5, 1.2, 5.6, 1.8));
    Tensor sol = Tensors.vector(0., 0.190476, 0.301587, 1., 0.396825);
    assertEquals(res.subtract(sol).map(Chop._05), Array.zeros(5));
  }

  public void testMatrix() {
    assertEquals(Rescale.of(Tensors.fromString("{{2,2,2},{2,2}}")), Tensors.fromString("{{0,0,0},{0,0}}"));
    assertEquals(Rescale.of(Tensors.fromString("{{1,2,3}}")), Tensors.fromString("{{0,1/2,1}}"));
    assertEquals(Rescale.of(Tensors.fromString("{{10,20,30}}")), Tensors.fromString("{{0,1/2,1}}"));
  }

  public void testInfty() {
    Tensor vec = Tensors.vector(-.7, .5, 1.2, Double.POSITIVE_INFINITY, 1.8);
    Tensor res = Rescale.of(vec);
    // System.out.println(res);
    assertTrue(2 < Tally.of(res).size());
  }

  public void testAllInfty() {
    Tensor tensor = Tensors.vector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    Tensor scaled = Rescale.of(tensor);
    assertEquals(tensor, scaled);
    // System.out.println(scaled);
    // assertTrue(Chop.NONE.allZero(scaled));
  }

  public void testAllInfty2() {
    Tensor tensor = Tensors.vector(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    Tensor scaled = Rescale.of(tensor);
    assertEquals(tensor, scaled);
    // System.out.println(scaled);
    // assertTrue(Chop.NONE.allZero(scaled));
  }

  public void testMixed1() {
    Tensor tensor = Tensors.vector(Double.NaN, Double.POSITIVE_INFINITY, 0, 1, 3, Double.NaN);
    Tensor scaled = Rescale.of(tensor);
    assertEquals(scaled.toString(), "{NaN, Infinity, 0, 1/3, 1, NaN}");
  }

  public void testMixed2() {
    Tensor tensor = Tensors.vector(Double.NaN, 0, 0, 0, Double.NaN);
    Tensor scaled = Rescale.of(tensor);
    assertEquals(tensor.toString(), scaled.toString());
  }

  public void testQuantity() {
    Tensor vector = QuantityTensor.of( //
        Tensors.vector(1, 2, -3, 4, -1, 2, 1, 0, 3, 2, 1, 2), //
        Unit.of("s"));
    @SuppressWarnings("unused")
    Tensor rescal = Rescale.of(vector);
    // System.out.println(rescal);
    // TODO not conforming to Mathematica
  }
}
