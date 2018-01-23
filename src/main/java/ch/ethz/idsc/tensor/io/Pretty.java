// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.ArrayQ;
import ch.ethz.idsc.tensor.alg.TensorRank;

/** Pretty helps to format tensors for easy reading in the console.
 * 
 * <p>Pretty can be used to export tensors as string expressions to MATLAB.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixForm.html">MatrixForm</a> */
public class Pretty {
  /** @param tensor
   * @return string expression of tensor for use in System.out.println
   * @see Put#string(Tensor) */
  public static String of(Tensor tensor) {
    return new Pretty(tensor).toString();
  }

  // ---
  private final StringBuilder stringBuilder = new StringBuilder();
  private final String format;
  private int level = 0;

  private Pretty(Tensor tensor) {
    final int max = tensor.flatten(-1) //
        .map(Object::toString) //
        .mapToInt(String::length) //
        .max().orElse(0);
    format = " %" + max + "s ";
    if (ArrayQ.of(tensor))
      recurArray(tensor);
    else
      recur(tensor);
  }

  private void recur(Tensor tensor) {
    stringBuilder.append(spaces(level) + "[\n");
    ++level;
    for (Tensor entry : tensor)
      if (ArrayQ.of(entry))
        recurArray(entry);
      else
        recur(entry);
    --level;
    stringBuilder.append(spaces(level) + "]\n");
  }

  private void recurArray(Tensor tensor) {
    switch (TensorRank.of(tensor)) {
    case 0: // scalar
      stringBuilder.append(String.format(format, tensor.toString()));
      break;
    case 1: // vector
      stringBuilder.append(spaces(level) + "[");
      for (Tensor entry : tensor) {
        Scalar scalar = (Scalar) entry;
        recurArray(scalar);
      }
      stringBuilder.append("]\n");
      break;
    default:
      stringBuilder.append(spaces(level) + "[\n");
      ++level;
      for (Tensor entry : tensor)
        recurArray(entry);
      --level;
      stringBuilder.append(spaces(level) + "]\n");
    }
  }

  @Override
  public String toString() {
    return stringBuilder.toString().trim();
  }

  // helper function
  private static String spaces(int level) {
    return IntStream.range(0, level).mapToObj(i -> " ").collect(Collectors.joining());
  }
}