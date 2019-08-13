// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/** Example:
 * The comparator of the SI unit system sorts the sequence
 * {4[rad], 300[deg], 2, 180[rad], -1[rad]} to
 * {-1[rad], 2, 4[rad], 300[deg], 180[rad]} */
public class QuantityComparator implements Comparator<Scalar>, Serializable {
  private static final Comparator<Scalar> SI = of(BuiltIn.SI.unitSystem);

  /** @param unitSystem non-null
   * @return */
  public static Comparator<Scalar> of(UnitSystem unitSystem) {
    return new QuantityComparator(Objects.requireNonNull(unitSystem));
  }

  /** @return */
  public static Comparator<Scalar> SI() {
    return SI;
  }

  // ---
  private final UnitSystem unitSystem;

  /** @param unitSystem */
  private QuantityComparator(UnitSystem unitSystem) {
    this.unitSystem = unitSystem;
  }

  @Override // from Comparator<Scalar>
  public int compare(Scalar scalar1, Scalar scalar2) {
    return Scalars.compare(unitSystem.apply(scalar1), unitSystem.apply(scalar2));
  }
}
