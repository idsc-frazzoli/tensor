// code by jph
package ch.ethz.idsc.tensor.img;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.mat.SpectrogramArray;
import ch.ethz.idsc.tensor.sca.Abs;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Spectrogram.html">Spectrogram</a> */
public enum Spectrogram {
  ;
  /** @param vector
   * @return array plot of the spectrogram of given vector */
  public static Tensor of(Tensor vector) {
    return of(vector, ColorDataGradients.VISIBLESPECTRUM);
  }

  /** @param vector
   * @param function
   * @return array plot of the spectrogram of given vector with colors specified by given function */
  public static Tensor of(Tensor vector, Function<Scalar, ? extends Tensor> function) {
    return ArrayPlot.of(array(vector), function);
  }

  /** @param vector
   * @return truncated and transposed {@link SpectrogramArray} for visualization */
  public static Tensor array(Tensor vector) {
    Tensor tensor = SpectrogramArray.of(vector).map(Abs.FUNCTION);
    int half = Unprotect.dimension1(tensor) / 2;
    return Tensors.vector(i -> tensor.get(Tensor.ALL, half - i - 1), half);
  }
}
