// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.TensorRank;

// doesn't do anything special for non-arrays yet
public class Pretty {
  /** @param tensor
   * @return string expression of tensor for use in System.out.println */
  public static String of(Tensor tensor) {
    return new Pretty(tensor).toString();
  }

  private static String spaces(int level) {
    String string = "";
    for (int index = 0; index < level; ++index)
      string += " ";
    return string;
  }

  final StringBuilder stringBuilder = new StringBuilder();
  final String format;
  private int level = 0;

  private Pretty(Tensor tensor) {
    final int max = tensor.flatten(-1) //
        .map(Tensor::toString) //
        .mapToInt(String::length).max().orElse(0);
    format = " %" + max + "s ";
    recur(tensor);
  }

  void recur(Tensor tensor) {
    if (!Dimensions.isArray(tensor))
      stringBuilder.append(tensor.toString());
    else
      switch (TensorRank.of(tensor)) {
      case 0:
        stringBuilder.append(String.format(format, tensor.toString()));
        break;
      case 1:
        stringBuilder.append(spaces(level) + "[");
        for (Tensor s : tensor)
          recur(s);
        stringBuilder.append("]\n");
        break;
      default:
        stringBuilder.append(spaces(level) + "[\n");
        ++level;
        for (Tensor s : tensor)
          recur(s);
        --level;
        stringBuilder.append(spaces(level) + "]\n");
      }
  }

  @Override
  public String toString() {
    return stringBuilder.toString().trim();
  }

  public static void main(String[] args) {
    // Tensor m = Tensors.of(Tensors.vectorInt(2, -3, 4), Tensors.vectorDouble(2.3, -.2), Tensors.empty());
    {
      Tensor m = Tensors.of(Tensors.of(RationalScalar.of(3, 2), RationalScalar.of(-23, 2444), RationalScalar.of(31231, 2)), Tensors.vectorDouble(2.3, .3, -.2));
      System.out.println(Pretty.of(m));
    }
    System.out.println("---");
    {
      Tensor m = Tensors.of( //
          Tensors.of(Tensors.vectorInt(2, -3, 4), Tensors.vectorDouble(2.3, .3, -.2)), //
          Tensors.of(Tensors.vectorInt(2, -3, 4), Tensors.vectorDouble(-2.3, .3, -.2)));
      System.out.println(Pretty.of(m));
    }
    System.out.println("---");
    {
      Tensor m = Tensors.of( //
          Tensors.of(RationalScalar.of(3, 2), Tensors.vectorDouble(2.3, .3, -.2)), //
          Tensors.of(Tensors.vectorInt(2, -3, 4), Tensors.vectorDouble(-2.3, .3, -.2)));
      // System.out.println(m.isArray());
      System.out.println(Pretty.of(m));
    }
    // System.out.println(Pretty.of(m));
  }
}
