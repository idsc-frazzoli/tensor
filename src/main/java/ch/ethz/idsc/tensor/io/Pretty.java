// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.TensorRank;

// doesn't do anything special for non-arrays yet
public class Pretty {
  /** @param tensor
   * @return string expression of tensor for use in System.out.println */
  public static String of(Tensor tensor) {
    return new Pretty(tensor).toString();
  }

  final StringBuilder stringBuilder = new StringBuilder();
  final String format;
  private int level = 0;

  private Pretty(Tensor tensor) {
    final int max = tensor.flatten(-1) //
        .map(Tensor::toString) //
        .mapToInt(String::length) //
        .max().orElse(0);
    format = " %" + max + "s ";
    recur(tensor);
  }

  private void recur(Tensor tensor) {
    if (!Dimensions.isArray(tensor))
      stringBuilder.append(tensor.toString());
    else
      switch (TensorRank.of(tensor)) {
      case 0:
        stringBuilder.append(String.format(format, tensor.toString()));
        break;
      case 1:
        stringBuilder.append(Utils.spaces(level) + "[");
        for (Tensor s : tensor)
          recur(s);
        stringBuilder.append("]\n");
        break;
      default:
        stringBuilder.append(Utils.spaces(level) + "[\n");
        ++level;
        for (Tensor s : tensor)
          recur(s);
        --level;
        stringBuilder.append(Utils.spaces(level) + "]\n");
      }
  }

  @Override
  public String toString() {
    return stringBuilder.toString().trim();
  }
}
