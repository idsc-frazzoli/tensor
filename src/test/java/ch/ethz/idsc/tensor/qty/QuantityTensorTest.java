// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.HashSet;
import java.util.Set;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.alg.Subsets;
import ch.ethz.idsc.tensor.lie.Permutations;
import junit.framework.TestCase;

public class QuantityTensorTest extends TestCase {
  public void testScalar() {
    Tensor tensor = QuantityTensor.of(RealScalar.ONE, Unit.of("N"));
    assertEquals(tensor, Quantity.of(1, "N"));
  }

  public void testVector() {
    Tensor vector = Tensors.vector(1, 2, 3);
    Tensor nuvec = QuantityTensor.of(vector, Unit.of("m*kg^2"));
    assertEquals(nuvec, //
        Tensors.fromString("{1[kg^2*m], 2[kg^2*m], 3[kg^2*m]}"));
  }

  public void testExample() {
    Tensor vector = Tensors.vector(2, 3, -1);
    Tensor nuvec = QuantityTensor.of(vector, Unit.of("m*s^-1"));
    assertEquals(nuvec, //
        Tensors.fromString("{2[m*s^-1], 3.0[m * s^+1 * s^-2], -1.0[s^-1 * m]}"));
  }

  private static void _checkAssociativity(Tensor tensor) {
    for (int k = 2; k <= tensor.length(); ++k) {
      for (Tensor subset : Subsets.of(tensor, k)) {
        Set<String> set = new HashSet<>();
        for (Tensor perm : Permutations.of(Range.of(0, subset.length()))) {
          Scalar scalar = perm.stream() //
              .map(Scalar.class::cast) //
              .map(Scalar::number) //
              .mapToInt(Number::intValue) //
              .mapToObj(subset::Get) //
              .reduce(Scalar::add).get();
          set.add(scalar.toString());
        }
        if (set.size() != 1) {
          System.out.println(subset);
          System.out.println(set);
          fail();
        }
      }
    }
  }

  public void testSymAssociativity() {
    _checkAssociativity(Tensors.fromString("{0[A], 0[B], 0[C], 0}"));
    _checkAssociativity(Tensors.fromString("{0.0[A], 0[B], 0.0[C], 0, 0.0}"));
  }

  private static void _checkAssociativity(Scalar prep, Tensor tensor) {
    for (int k = 1; k <= tensor.length(); ++k) {
      for (Tensor _subset : Subsets.of(tensor, k)) {
        Tensor subset = _subset.copy().append(prep);
        Set<String> set = new HashSet<>();
        for (Tensor perm : Permutations.of(Range.of(0, subset.length()))) {
          Scalar scalar = perm.stream() //
              .map(Scalar.class::cast) //
              .map(Scalar::number) //
              .mapToInt(Number::intValue) //
              .mapToObj(subset::Get) //
              .reduce(Scalar::add).get();
          set.add(scalar.toString());
        }
        if (set.size() != 1) {
          System.out.println(subset);
          System.out.println(set);
          fail();
        }
      }
    }
  }

  public void testMixAssociativity() {
    _checkAssociativity(Quantity.of(3, "m"), Tensors.fromString("{0[A], 0[B], 0[C], 0}"));
    _checkAssociativity(Quantity.of(3, "m"), Tensors.fromString("{0.0[A], 0[B], 0, 0.0}"));
  }

  public void testFail() {
    Scalar q = Quantity.of(1, "s");
    try {
      QuantityTensor.of(q, Unit.of("m*kg^2"));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      QuantityTensor.of(Tensors.of(q, q), Unit.of("m*kg^2"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
