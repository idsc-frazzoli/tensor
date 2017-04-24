// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.TensorRank;

/** Pretty helps to format tensors for easy reading in the console.
 * 
 * <p>Pretty can be used to export tensors as string expressions to MATLAB.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixForm.html">MatrixForm</a> */
public class Pretty {
  /** @param tensor
   * @return string expression of tensor for use in System.out.println */
  public static String of(Tensor tensor) {
    return new Pretty(tensor).toString();
  }

  final StringBuilder stringBuilder = new StringBuilder();
  final String format;
  private int level = 0;
  final int rank;

  private Pretty(Tensor tensor) {
    final int max = tensor.flatten(-1) //
        .map(Tensor::toString) //
        .mapToInt(String::length) //
        .max().orElse(0);
    format = " %" + max + "s ";
    rank = TensorRank.of(tensor);
    if (Dimensions.isArray(tensor))
      recurArray(tensor);
    else
      recur(tensor);
  }

  private void recur(Tensor tensor) {
    stringBuilder.append(Utils.spaces(level) + "[\n");
    ++level;
    for (Tensor entry : tensor)
      if (Dimensions.isArray(entry))
        recurArray(entry);
      else
        recur(entry);
    --level;
    stringBuilder.append(Utils.spaces(level) + "]\n");
  }

  private void recurArray(Tensor tensor) {
    switch (TensorRank.of(tensor)) {
    case 0: // scalar
      stringBuilder.append(String.format(format, tensor.toString()));
      break;
    case 1: // vector
      stringBuilder.append(Utils.spaces(level) + "[");
      for (Tensor entry : tensor) {
        Scalar scalar = (Scalar) entry;
        recurArray(scalar);
      }
      stringBuilder.append("]\n");
      break;
    default:
      stringBuilder.append(Utils.spaces(level) + "[\n");
      ++level;
      for (Tensor entry : tensor)
        recurArray(entry);
      --level;
      stringBuilder.append(Utils.spaces(level) + "]\n");
    }
  }

  @Override
  public String toString() {
    return stringBuilder.toString().trim();
  }
}
