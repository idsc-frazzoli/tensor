// code by jph
package ch.ethz.idsc.tensor;

public class TensorRuntimeException extends RuntimeException {
  public static final int MAX_LENGTH = 32;

  /** @param tensor
   * @return */
  public static TensorRuntimeException of(Tensor tensor) {
    if (tensor == null)
      return new TensorRuntimeException("null");
    String string = tensor.toString();
    int length = string.length();
    if (MAX_LENGTH < length)
      string = string.substring(0, MAX_LENGTH) + " ...";
    return new TensorRuntimeException(tensor, string);
  }

  private TensorRuntimeException(String string) {
    super(string);
  }

  private TensorRuntimeException(Tensor tensor, String string) {
    this(tensor.getClass().getName() + "=" + string);
  }
}
