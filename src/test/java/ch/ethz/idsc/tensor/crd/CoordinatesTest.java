// code by gjoel
package ch.ethz.idsc.tensor.crd;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class CoordinatesTest extends TestCase {
  private static final CoordinateSystem CS = CoordinateSystem.of("test");
  private static final Tensor VECTOR = Tensors.vector(1, 2, 3);

  public void testSimple() {
    Coordinates coords = Coordinates.of(VECTOR);
    assertEquals(coords, Coordinates.of(coords));
    boolean thrown = false;
    try {
      Coordinates.of(coords, CS);
    } catch (UnsupportedOperationException e) {
      thrown = true;
    }
    assertTrue(thrown);
  }

  public void testVector() {
    Coordinates coords = Coordinates.of(VECTOR);
    assertEquals(VECTOR, coords.vector());
  }

  public void testSystem() {
    Coordinates coords = Coordinates.of(VECTOR);
    assertEquals(CoordinateSystem.DEFAULT, coords.system());
  }

  public void testUnmodifiable() {
    Coordinates coords = Coordinates.of(VECTOR, CS);
    UnmodifiableCoordinatesTest.testSimple(coords.unmodifiable());
  }

  public void testOperations() {
    Coordinates coords = Coordinates.of(VECTOR, CS);
    Coordinates coordsX2 = (Coordinates) coords.add(coords);
    assertEquals(VECTOR.negate(), ((Coordinates) coords.negate()).vector()); //  negate
    assertEquals(VECTOR.multiply(RealScalar.of(2)), coordsX2.vector()); // add
    assertEquals(CS.origin(), coords.subtract(coords)); // subtract
    assertEquals(coordsX2, coords.multiply(RealScalar.of(2))); // multiply
    assertEquals(coords, coordsX2.divide(RealScalar.of(2))); // divide
    assertEquals(coords.negate(), coords.map(Tensor::negate)); // map
  }

  public void testEquals() {
    assertEquals(Coordinates.of(VECTOR, CS), Coordinates.of(VECTOR, CS));
    assertFalse(Coordinates.of(VECTOR, CS).equals(Coordinates.of(VECTOR)));
    assertFalse(Coordinates.of(VECTOR, CS).equals(CS.origin()));
  }
}
