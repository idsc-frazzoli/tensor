// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** LONGTERM
 * StringFormat should be the replacement of TensorParser
 * that is used in {@link Tensors#fromString(String)} */
class StringFormat<T> {
  public static Tensor parse(String string, Function<String, Scalar> function) {
    return new StringFormat<>(new TensorJoiningInverse(function), string).parse();
  }

  public static Tensor parse(String string) {
    return parse(string, Scalars::fromString);
  }

  // ---
  private final JoiningInverse<T> inverseJoining;
  private final String string;
  private int head = 0;
  private int index = 0;

  private StringFormat(JoiningInverse<T> inverseJoining, String string) {
    this.inverseJoining = inverseJoining;
    this.string = string;
  }

  private void handle(int chr) {
    if (chr == Tensor.OPENING_BRACKET) {
      inverseJoining.prefix();
      head = index + 1;
    } else //
    if (chr == ',') {
      inverseJoining.delimiter(string.substring(head, index));
      head = index + 1;
    } else //
    if (chr == Tensor.CLOSING_BRACKET) {
      inverseJoining.suffix(string.substring(head, index));
      head = index + 1;
    }
    ++index;
  }

  private T parse() {
    try {
      string.chars().forEach(this::handle);
      if (head != index)
        inverseJoining.delimiter(string.substring(head, index));
      return inverseJoining.emit();
    } catch (Exception exception) {
      // ---
    }
    return inverseJoining.fallback(string);
  }
}

interface JoiningInverse<T> {
  void prefix();

  void suffix(String string);

  /** @param string from last separator to delimiter excluding */
  void delimiter(String string);

  T emit();

  T fallback(String string);
}

class TensorJoiningInverse implements JoiningInverse<Tensor> {
  private final Function<String, Scalar> function;
  private final Deque<Tensor> deque = new ArrayDeque<>();

  public TensorJoiningInverse(Function<String, Scalar> function) {
    this.function = function;
    deque.push(Tensors.empty());
  }

  private void handle(String _string) {
    String string = _string.trim();
    if (!string.isEmpty())
      deque.peek().append(function.apply(string));
  }

  @Override
  public void prefix() {
    deque.push(Tensors.empty());
  }

  @Override
  public void suffix(String string) {
    handle(string);
    Tensor pop = deque.pop();
    deque.peek().append(pop);
  }

  @Override
  public void delimiter(String string) {
    handle(string);
  }

  @Override
  public Tensor emit() {
    Tensor tensor = deque.pop();
    if (!deque.isEmpty() || 1 < tensor.length())
      throw new RuntimeException();
    return tensor.get(0);
  }

  @Override
  public Tensor fallback(String string) {
    return StringScalar.of(string);
  }
}
