// code by jph
package ch.ethz.idsc.tensor;

import java.util.List;

import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.Unit;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class ScalarDivideTest extends TestCase {
  private static void _check(Scalar a, Scalar b) {
    if (Scalars.nonZero(b)) {
      Scalar ab = a.divide(b);
      Scalar ba = b.under(a);
      assertEquals(ab, ba);
      assertEquals(ab.toString(), ba.toString());
    }
    if (Scalars.nonZero(a) && Scalars.nonZero(b)) {
      Scalar ab = a.divide(b);
      Scalar ba = a.under(b).reciprocal();
      Chop._13.requireClose(ab, ba);
    }
  }

  public void testSimple() {
    Unit ua = Unit.of("m^2*s^-3");
    Unit ub = Unit.of("kg*CHF^-1");
    List<Scalar> list = ScalarAddTest.SCALARS;
    for (int i = 0; i < list.size(); ++i)
      for (int j = i; j < list.size(); ++j) {
        Scalar a = list.get(i);
        Scalar b = list.get(j);
        _check(a, b);
        _check(Quantity.of(a, ua), Quantity.of(b, ua));
        _check(Quantity.of(a, ua), Quantity.of(b, ub));
        _check(Quantity.of(a, ub), Quantity.of(b, ua));
        _check(Quantity.of(a, ub), Quantity.of(b, ub));
      }
  }
}
