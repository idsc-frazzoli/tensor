// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.IOException;
import java.util.Comparator;
import java.util.Properties;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Sort;
import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class QuantityComparatorTest extends TestCase {
  public void testSimple() throws ClassNotFoundException, IOException {
    Comparator<Scalar> comparator = Serialization.copy(QuantityComparator.SI());
    Tensor sorted = Sort.of(Tensors.fromString("{4[h], 300[s], 2[min], 180[s]}"), comparator);
    assertEquals(sorted, Tensors.fromString("{2[min], 180[s], 300[s], 4[h]}"));
  }

  public void testUnitless() throws ClassNotFoundException, IOException {
    Comparator<Scalar> comparator = Serialization.copy(QuantityComparator.SI());
    Tensor sorted = Sort.of(Tensors.fromString("{4[rad], 300[deg], 2, 180[rad], -1[rad]}"), comparator);
    assertEquals(sorted, Tensors.fromString("{-1[rad], 2, 4[rad], 300[deg], 180[rad]}"));
  }

  public void testUnknown() throws ClassNotFoundException, IOException {
    Comparator<Scalar> comparator = Serialization.copy(QuantityComparator.SI());
    Tensor sorted = Sort.of(Tensors.fromString("{4[fun], 300[fun], 2[fun], 180[fun]}"), comparator);
    assertEquals(sorted, Tensors.fromString("{2[fun], 4[fun], 180[fun], 300[fun]}"));
  }

  public void testEmpty() throws ClassNotFoundException, IOException {
    UnitSystem unitSystem = Serialization.copy(SimpleUnitSystem.from(new Properties()));
    Comparator<Scalar> comparator = Serialization.copy(QuantityComparator.of(unitSystem));
    Tensor sorted = Sort.of(Tensors.fromString("{4[fun], 300[fun], 2[fun], 180[fun]}"), comparator);
    assertEquals(sorted, Tensors.fromString("{2[fun], 4[fun], 180[fun], 300[fun]}"));
  }

  public void testIncompatibleFail() {
    Comparator<Scalar> comparator = QuantityComparator.SI();
    Tensor vector = Tensors.fromString("{4[h], 300[s], 2[km], 180[s]}");
    try {
      Sort.of(vector, comparator);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail() {
    try {
      QuantityComparator.of(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
