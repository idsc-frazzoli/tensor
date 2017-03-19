// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

public interface ScalarVisitor {
  public static void apply(ScalarVisitor scalarVisitor, Tensor tensor) {
    recur(scalarVisitor, new ArrayList<>(), 0, tensor);
  }

  static void recur(ScalarVisitor scalarVisitor, final List<Integer> index, final int depth, final Tensor tensor) {
    int length = tensor.length();
    if (length == Scalar.LENGTH)
      scalarVisitor.visit(Collections.unmodifiableList(index.subList(0, depth)), (Scalar) tensor);
    else {
      if (index.size() <= depth)
        index.add(-1);
      for (int c1 = 0; c1 < length; ++c1) {
        index.set(depth, c1);
        recur(scalarVisitor, index, depth + 1, tensor.get(c1));
      }
    }
  }

  public void visit(List<Integer> index, Scalar scalar);
}
