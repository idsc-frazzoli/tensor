// code by jph
package ch.ethz.idsc.tensor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TensorRuntimeException extends RuntimeException {
  public static final int MAX_LENGTH = 32;

  /** @param tensor
   * @return */
  public static TensorRuntimeException of(Tensor... tensors) {
    // if (Stream.of(tensors).filter(Objects::isNull).findAny().isPresent())
    // return new TensorRuntimeException("null");
    return new TensorRuntimeException( //
        Stream.of(tensors).map(TensorRuntimeException::format).collect(Collectors.joining(", ")));
  }

  private TensorRuntimeException(String string) {
    super(string);
  }

  // helper function
  private static String format(Tensor tensor) {
    String string = tensor.toString();
    int length = string.length();
    if (MAX_LENGTH < length)
      string = string.substring(0, MAX_LENGTH) + " ...";
    return string;
  }
}
