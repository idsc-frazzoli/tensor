// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.ArrayList;
import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Dot;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.io.Pretty;
import ch.ethz.idsc.tensor.mat.LinearSolve;

public class LieAlgebras {
  /** @param x matrix
   * @param y matrix
   * @return */
  public static Tensor bracketMatrix(Tensor x, Tensor y) {
    return Dot.of(x, y).subtract(Dot.of(y, x));
  }

  /** @return ad tensor of 3-dimensional Heisenberg Lie-algebra */
  public static Tensor heisenberg() {
    Tensor ad = Array.zeros(3, 3, 3);
    ad.set(RealScalar.of(+1), 2, 1, 0);
    ad.set(RealScalar.of(-1), 2, 0, 1);
    return ad;
  }

  /** @return ad tensor of 3-dimensional so(3) */
  public static Tensor so3() {
    Tensor ad = Array.zeros(3, 3, 3);
    ad.set(RealScalar.of(+1), 2, 1, 0);
    ad.set(RealScalar.of(-1), 2, 0, 1);
    ad.set(RealScalar.of(+1), 0, 2, 1);
    ad.set(RealScalar.of(-1), 0, 1, 2);
    ad.set(RealScalar.of(+1), 1, 0, 2);
    ad.set(RealScalar.of(-1), 1, 2, 0);
    return ad;
  }

  public static Tensor from(Tensor c) {
    // System.out.println(Tensor.of(a.flatten(1)).dimensions());
    Tensor a = TensorMap.of(t -> Tensor.of(t.flatten(1)), c, 1);
    System.out.println(Dimensions.of(a));
    System.out.println(a);
    System.out.println(Pretty.of(a));
    Tensor s = a.dot(Transpose.of(a));
    System.out.println(Pretty.of(s));
    List<Tensor> list = new ArrayList<>();
    for (int i = 0; i < c.length() - 1; ++i)
      for (int j = i + 1; j < c.length(); ++j) {
        Tensor d = bracketMatrix(c.get(i), c.get(j));
        list.add(Tensor.of(d.flatten(1)));
      }
    Tensor rhs = a.dot(Transpose.of(Tensor.of(list.stream())));
    Tensor sol = LinearSolve.of(s, rhs);
    System.out.println(Pretty.of(sol));
    return null;
  }
}
