// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class PrettyTest extends TestCase {
  public void testDummy() {
    // ---
  }

  public static void main(String[] args) {
    // Tensor m = Tensors.of(Tensors.vectorInt(2, -3, 4), Tensors.vectorDouble(2.3, -.2), Tensors.empty());
    {
      Tensor m = Tensors.of( //
          Tensors.vector(1, 2, 3), //
          Tensors.vector(4, 5) //
      );
      System.out.println(Pretty.of(m));
    }
    System.out.println("---");
    {
      Tensor m = Tensors.of(Tensors.of(RationalScalar.of(3, 2), RationalScalar.of(-23, 2444), RationalScalar.of(31231, 2)), Tensors.vectorDouble(2.3, .3, -.2));
      System.out.println(Pretty.of(m));
    }
    System.out.println("---");
    {
      Tensor m = Tensors.of( //
          Tensors.of(Tensors.vector(2, -3, 4), Tensors.vector(2.3, .3, -.2)), //
          Tensors.of(Tensors.vector(2, -3, 4), Tensors.vector(-2.3, .3, -.2)));
      System.out.println(Pretty.of(m));
    }
    System.out.println("---");
    {
      Tensor m = Tensors.of( //
          Tensors.of(RationalScalar.of(3, 2), Tensors.vector(2.3, .3, -.2)), //
          Tensors.of(Tensors.vector(2, -3, 4), Tensors.vector(-2.3, .3, -.2)));
      // System.out.println(m.isArray());
      System.out.println(Pretty.of(m));
    }
    System.out.println("---");
    {
      Tensor m = Tensors.of( //
          Tensors.of(Tensors.vector(33.2), RationalScalar.of(3, 2), Tensors.vector(2.3, .3, -.2)), //
          Tensors.of(Tensors.vector(2, -3, 4), Tensors.vector(-2.3, .3, -.2)));
      // System.out.println(m.isArray());
      System.out.println(Pretty.of(m));
    }
    System.out.println("---");
    {
      System.out.println(Pretty.of(RealScalar.NEGATIVE_INFINITY));
    }
    System.out.println("---");
    {
      System.out.println(Pretty.of(Tensors.vectorDouble(.2, .3, Double.NEGATIVE_INFINITY)));
    }
  }
}
