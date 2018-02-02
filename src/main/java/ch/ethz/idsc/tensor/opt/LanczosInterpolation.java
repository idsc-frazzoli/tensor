// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** https://en.wikipedia.org/wiki/Lanczos_resampling */
// LONGTERM implementation is inefficient, possibly too many multiplications by zero
public class LanczosInterpolation extends AbstractInterpolation {
  /** @param tensor
   * @param size
   * @return */
  public static Interpolation of(Tensor tensor, int size) {
    return new LanczosInterpolation(tensor, size);
  }

  // ---
  private final Tensor tensor;
  private final int size;
  private final LanczosKernel lanczosKernel;

  private LanczosInterpolation(Tensor tensor, int size) {
    this.tensor = tensor;
    this.size = size;
    lanczosKernel = new LanczosKernel(size);
  }

  @Override // from AbstractInterpolation
  protected Tensor _get(Tensor index) {
    if (index.length() == 0)
      return tensor.copy();
    Tensor sum = tensor;
    for (int pos = 0; pos < index.length(); ++pos) {
      Tensor _sum = sum;
      Scalar value = index.Get(pos);
      int min = value.Get().number().intValue() - size;
      int max = value.Get().number().intValue() + size;
      sum = IntStream.range(min, max) //
          .mapToObj(count -> flow(_sum, count, value)) //
          .reduce(Tensor::add).get();
    }
    return sum;
  }

  private Tensor flow(Tensor tensor, int count, Scalar value) {
    int ind = Math.min(Math.max(0, count), tensor.length() - 1);
    Scalar weight = lanczosKernel.apply(value.subtract(RealScalar.of(count)));
    return tensor.get(ind).multiply(weight);
  }
}
