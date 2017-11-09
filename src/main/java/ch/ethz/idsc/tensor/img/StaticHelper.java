// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.VectorQ;

import java.util.function.UnaryOperator;

/* package */ enum StaticHelper {
  ;
  private static final Tensor TRANSPARENT = Tensors.vectorDouble(0, 0, 0, 0).unmodifiable();

  /* package */ static Tensor transparent() {
    return TRANSPARENT.copy();
  }

  static Tensor apply(Tensor vector, int radius, UnaryOperator<Tensor> function) {
      VectorQ.elseThrow(vector);
      if (radius < 0) throw TensorRuntimeException.of(vector, RealScalar.of(radius));
      Tensor result = Tensors.empty();
      for (int i = 0; i < vector.length(); i++) {
          int lowerBound = Math.max(0, i - radius);
          int upperBound = Math.min(vector.length() - 1, i + radius);
          result.append(function.apply(vector.extract(lowerBound, upperBound + 1)));
      }
      return result;
  }
}
