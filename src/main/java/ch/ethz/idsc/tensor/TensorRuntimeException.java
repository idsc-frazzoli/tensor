// code by jph
package ch.ethz.idsc.tensor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/** exception commonly used when something goes wrong within the tensor library */
public class TensorRuntimeException extends RuntimeException {
  private static final int MAX_LENGTH = 32;

  /** @param briefing
   * @param tensors
   * @return exception with message consisting of briefing and truncated string expressions of given tensors
   * @throws Exception if any of the listed tensors is null */
  public static TensorRuntimeException of(String briefing, Tensor... tensors) { // function suggested by @njenwei
    return new TensorRuntimeException( //
        (tensors.length == 0 ? briefing : briefing + ": ") + //
            Stream.of(tensors).map(TensorRuntimeException::format).collect(Collectors.joining(", ")));
  }

  /** @param tensors
   * @return exception with message consisting of truncated string expressions of given tensors
   * @throws Exception if any of the listed tensors is null */
  public static TensorRuntimeException of(Tensor... tensors) {
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
