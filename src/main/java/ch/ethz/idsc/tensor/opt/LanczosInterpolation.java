// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Objects;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Floor;

/** A typical application of {@code LanczosInterpolation} is image resizing.
 * For efficiency, the filter should be applied to dimensions separately.
 * 
 * <p>The Lanczos kernel does not have the partition of unity property.
 * A constant signal should not be interpolated using the filter.
 * 
 * https://en.wikipedia.org/wiki/Lanczos_resampling */
public class LanczosInterpolation extends AbstractInterpolation {
  /** @param tensor non-null
   * @param semi positive, typically greater than 1
   * @return
   * @throws Exception if given tensor is null
   * @throws Exception if semi is not positive */
  public static Interpolation of(Tensor tensor, int semi) {
    return new LanczosInterpolation(tensor, new LanczosKernel(semi));
  }

  /** @param tensor non-null
   * @return Lanczos interpolation operator with {@code semi == 3}
   * @throws Exception if given tensor is null */
  public static Interpolation of(Tensor tensor) {
    return new LanczosInterpolation(tensor, LanczosKernel._3);
  }

  // ---
  private final Tensor tensor;
  private final LanczosKernel lanczosKernel;

  private LanczosInterpolation(Tensor tensor, LanczosKernel lanczosKernel) {
    this.tensor = Objects.requireNonNull(tensor);
    this.lanczosKernel = lanczosKernel;
  }

  @Override // from Interpolation
  public Tensor get(Tensor index) {
    if (Tensors.isEmpty(index))
      return tensor.copy();
    Tensor sum = tensor;
    for (Tensor value : index)
      sum = at(sum, value.Get());
    return sum;
  }

  @Override // from Interpolation
  public Tensor at(Scalar index) {
    return at(tensor, index);
  }

  private Tensor at(Tensor tensor, Scalar index) {
    int center = Floor.FUNCTION.apply(index).number().intValue();
    return IntStream.range(center - lanczosKernel.semi + 1, center + lanczosKernel.semi) //
        .mapToObj(count -> flow(tensor, count, index)) //
        .reduce(Tensor::add).get();
  }

  private Tensor flow(Tensor tensor, int count, Scalar value) {
    return tensor.get(Math.min(Math.max(0, count), tensor.length() - 1)) //
        .multiply(lanczosKernel.apply(value.subtract(RealScalar.of(count))));
  }
}
