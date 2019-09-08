// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.pdf.CDF;
import ch.ethz.idsc.tensor.pdf.EmpiricalDistribution;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/HistogramTransform.html">HistogramTransform</a> */
public enum HistogramTransform {
  ;
  private static final Scalar _255 = RealScalar.of(255);

  /** @param tensor
   * @return */
  public static Tensor of(Tensor tensor) {
    Dimensions dimensions = new Dimensions(tensor);
    switch (dimensions.list().size()) {
    case 2:
      return grayscale(tensor);
    }
    throw TensorRuntimeException.of(tensor);
  }

  private static Tensor grayscale(Tensor tensor) {
    int[] values = new int[256];
    tensor.flatten(1).map(Scalar.class::cast) //
        .forEach(scalar -> ++values[scalar.number().intValue()]);
    CDF cdf = CDF.of(EmpiricalDistribution.fromUnscaledPDF(Tensors.vectorInt(values)));
    return tensor.map(scalar -> cdf.p_lessEquals(scalar).multiply(_255));
  }
}
