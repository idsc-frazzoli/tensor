// code by jph
package ch.ethz.idsc.tensor;

public class TensorRuntimeException extends RuntimeException {
  public static final int MAX_LENGTH = 32;

  public static TensorRuntimeException of(Tensor tensor) {
    String string = tensor.toString();
    int length = string.length();
    if (MAX_LENGTH < length)
      string = string.substring(0, MAX_LENGTH) + " ...";
    return new TensorRuntimeException(tensor, string);
  }

  private TensorRuntimeException(Tensor tensor, String string) {
    super(tensor.getClass().getName() + "=" + string);
  }
}
