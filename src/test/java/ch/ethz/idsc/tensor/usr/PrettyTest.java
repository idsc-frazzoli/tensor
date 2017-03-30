package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Pretty;
import junit.framework.TestCase;

public class PrettyTest extends TestCase {
  public void testDummy() {
  }

  public static void main(String[] args) {
    // Tensor m = Tensors.of(Tensors.vectorInt(2, -3, 4), Tensors.vectorDouble(2.3, -.2), Tensors.empty());
    {
      Tensor m = Tensors.of(Tensors.of(RationalScalar.of(3, 2), RationalScalar.of(-23, 2444), RationalScalar.of(31231, 2)), Tensors.vectorDouble(2.3, .3, -.2));
      System.out.println(Pretty.of(m));
    }
    System.out.println("---");
    {
      Tensor m = Tensors.of( //
          Tensors.of(Tensors.vectorInt(2, -3, 4), Tensors.vectorDouble(2.3, .3, -.2)), //
          Tensors.of(Tensors.vectorInt(2, -3, 4), Tensors.vectorDouble(-2.3, .3, -.2)));
      System.out.println(Pretty.of(m));
    }
    System.out.println("---");
    {
      Tensor m = Tensors.of( //
          Tensors.of(RationalScalar.of(3, 2), Tensors.vectorDouble(2.3, .3, -.2)), //
          Tensors.of(Tensors.vectorInt(2, -3, 4), Tensors.vectorDouble(-2.3, .3, -.2)));
      // System.out.println(m.isArray());
      System.out.println(Pretty.of(m));
    }
    // System.out.println(Pretty.of(m));
  }
}
