// code by jph
package ch.ethz.idsc.tensor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Numel;

/** Exception thrown when a problem is encountered related to the types
 * {@link Tensor}, and {@link Scalar}. */
public class TensorRuntimeException extends RuntimeException {
  private static final int MAX_NUMEL = 10;
  private static final int MAX_LENGTH = 32;

  /** @param briefing
   * @param tensors
   * @return exception with message consisting of briefing and truncated string expressions of given tensors
   * @throws Exception if any of the listed tensors is null */
  public static TensorRuntimeException of(String briefing, Tensor... tensors) { // function suggested by @njenwei
    return new TensorRuntimeException((tensors.length == 0 ? briefing : briefing + ": ") + message(tensors));
  }

  /** @param tensors
   * @return exception with message consisting of truncated string expressions of given tensors
   * @throws Exception if any of the listed tensors is null */
  public static TensorRuntimeException of(Tensor... tensors) {
    return new TensorRuntimeException(message(tensors));
  }

  /***************************************************/
  private TensorRuntimeException(String string) {
    super(string);
  }

  private static String message(Tensor... tensors) {
    return Stream.of(tensors).map(TensorRuntimeException::format).collect(Collectors.joining("; "));
  }

  private static String format(Tensor tensor) {
    if (Numel.of(tensor) <= MAX_NUMEL)
      return formatContent(tensor);
    return "T" + Dimensions.of(tensor);
  }

  /** function causes out of memory exception for large tensors
   * and therefore should only be invoked for small tensors.
   * 
   * @param tensor
   * @return */
  private static String formatContent(Tensor tensor) {
    String string = tensor.toString();
    int length = string.length();
    if (MAX_LENGTH < length)
      string = string.substring(0, MAX_LENGTH) + " ...";
    return string;
  }
}
