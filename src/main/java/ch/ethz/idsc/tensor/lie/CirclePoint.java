// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.HashMap;
import java.util.Map;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.opt.Pi;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Sin;

/* package */ enum CirclePoint {
  INSTANCE;
  // ---
  private final Map<Scalar, Tensor> map = new HashMap<>();

  private CirclePoint() {
    map.put(RationalScalar.of(0, 12), Tensors.vector(+1, +0));
    map.put(RationalScalar.of(1, 12), Tensors.of(Cos.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(1, 6))), RationalScalar.HALF));
    map.put(RationalScalar.of(2, 12), Tensors.of(RationalScalar.HALF, Sin.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(2, 6)))));
    map.put(RationalScalar.of(3, 12), Tensors.vector(+0, +1));
    map.put(RationalScalar.of(4, 12), Tensors.of(RationalScalar.HALF.negate(), Sin.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(4, 6)))));
    map.put(RationalScalar.of(5, 12), Tensors.of(Cos.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(5, 6))), RationalScalar.HALF));
    map.put(RationalScalar.of(6, 12), Tensors.vector(-1, +0));
    map.put(RationalScalar.of(7, 12), Tensors.of(Cos.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(7, 6))), RationalScalar.HALF.negate()));
    map.put(RationalScalar.of(8, 12), Tensors.of(RationalScalar.HALF.negate(), Sin.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(8, 6)))));
    map.put(RationalScalar.of(9, 12), Tensors.vector(+0, -1));
    map.put(RationalScalar.of(10, 12), Tensors.of(RationalScalar.HALF, Sin.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(10, 6)))));
    map.put(RationalScalar.of(11, 12), Tensors.of(Cos.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(11, 6))), RationalScalar.HALF.negate()));
  }

  /** @param scalar
   * @return */
  public boolean contains(Scalar scalar) {
    return map.containsKey(scalar);
  }

  public Tensor turns(Scalar scalar) {
    return map.get(scalar);
  }
}
