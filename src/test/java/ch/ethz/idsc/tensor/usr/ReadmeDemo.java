// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Pretty;
import ch.ethz.idsc.tensor.mat.Inverse;
import ch.ethz.idsc.tensor.opt.LinearProgramming;
import ch.ethz.idsc.tensor.sca.Sqrt;

public class ReadmeDemo {
  public static void demoInverse() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { 2, -3, 2 }, { 4, 9, -3 }, { -1, 3, 2 } });
    System.out.println(Pretty.of(matrix));
    System.out.println(Pretty.of(Inverse.of(matrix)));
  }

  public static void demoLP() {
    Tensor x = LinearProgramming.maxLessEquals( //
        Tensors.fromString("[1, 1]"), //
        Tensors.fromString("[[4, -1],[2, 1],[-5, 2]]"), //
        Tensors.fromString("[8, 7, 2]"));
    System.out.println(x);
  }

  public static void demoSqrt() {
    Scalar fraction = RationalScalar.of(-9, 16);
    System.out.println(Sqrt.of(fraction));
  }

  public static void main(String[] args) {
    demoSqrt();
    demoInverse();
  }
}
