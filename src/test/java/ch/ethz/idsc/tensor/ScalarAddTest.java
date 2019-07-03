// code by jph
package ch.ethz.idsc.tensor;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.Unit;
import junit.framework.TestCase;

public class ScalarAddTest extends TestCase {
  static final List<Scalar> SCALARS = Arrays.asList( //
      RealScalar.ZERO, RealScalar.ONE, //
      RationalScalar.HALF, //
      RationalScalar.of(-7, 3), RealScalar.of(-3.4), //
      ComplexScalar.of(2, 3), ComplexScalar.of(6, -8.3), //
      ComplexScalar.of(RealScalar.of(2.3), RationalScalar.of(2, 9)), //
      ComplexScalar.of(RationalScalar.of(-7, 13), RationalScalar.of(53, 887)));

  private static void _check(Scalar a, Scalar b) {
    {
      Scalar ab = a.add(b);
      Scalar ba = b.add(a);
      assertEquals(ab, ba);
      assertEquals(ab.toString(), ba.toString());
    }
    {
      Scalar ab = a.subtract(b);
      Scalar ba = b.subtract(a).negate();
      assertEquals(ab, ba);
      if (Scalars.nonZero(ab) || Scalars.nonZero(ba))
        assertEquals(ab.toString(), ba.toString());
    }
  }

  public void testSimple() {
    Unit unit = Unit.of("m^2*s^-3");
    for (int i = 0; i < SCALARS.size(); ++i)
      for (int j = i; j < SCALARS.size(); ++j) {
        Scalar a = SCALARS.get(i);
        Scalar b = SCALARS.get(j);
        _check(a, b);
        _check(Quantity.of(a, unit), Quantity.of(b, unit));
      }
  }
}
