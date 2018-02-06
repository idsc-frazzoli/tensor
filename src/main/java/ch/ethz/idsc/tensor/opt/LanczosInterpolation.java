// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Floor;

/** A typical application of {@code LanczosInterpolation} is image resizing.
 * For efficiency, the filter should be applied to dimensions separately.
 * 
 * <p>The Lanczos kernel does not have the partition of unity property.
 * A constant signal should not be interpolated using the filter.
 * 
 * https://en.wikipedia.org/wiki/Lanczos_resampling */
public class LanczosInterpolation extends AbstractInterpolation {
  /** @param tensor
   * @param semi positive, typically greater than 1
   * @return */
  public static Interpolation of(Tensor tensor, int semi) {
    return new LanczosInterpolation(tensor, new LanczosKernel(semi));
  }

  /** @param tensor
   * @return Lanczos interpolation operator with {@code semi == 3} */
  public static Interpolation of(Tensor tensor) {
    return new LanczosInterpolation(tensor, LanczosKernel._3);
  }

  // ---
  private final Tensor tensor;
  private final LanczosKernel lanczosKernel;

  private LanczosInterpolation(Tensor tensor, LanczosKernel lanczosKernel) {
    this.tensor = tensor;
    this.lanczosKernel = lanczosKernel;
  }

  @Override // from AbstractInterpolation
  protected Tensor _get(Tensor index) {
    if (index.length() == 0)
      return tensor.copy();
    Tensor sum = tensor;
    for (int pos = 0; pos < index.length(); ++pos) {
      Tensor _sum = sum;
      Scalar value = index.Get(pos);
      int center = Floor.FUNCTION.apply(value).Get().number().intValue();
      sum = IntStream.range(center - lanczosKernel.semi + 1, center + lanczosKernel.semi) //
          .mapToObj(count -> flow(_sum, count, value)) //
          .reduce(Tensor::add).get();
    }
    return sum;
  }

  private Tensor flow(Tensor tensor, int count, Scalar value) {
    return tensor.get(Math.min(Math.max(0, count), tensor.length() - 1)) //
        .multiply(lanczosKernel.inside(value.subtract(RealScalar.of(count))));
  }
}
