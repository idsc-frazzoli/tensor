// MATLAB code by Nasser M. Abbasi
// http://12000.org/my_notes/simplex/index.htm
// adapted by jph
package ch.ethz.idsc.tensor.opt;

import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.alg.Partition;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.red.ArgMax;
import ch.ethz.idsc.tensor.red.ArgMin;

/* package */ class TableauImpl {
  public static Tensor of(Tensor c, Tensor A, Tensor b) {
    List<Integer> dims = Dimensions.of(A);
    int m = dims.get(0);
    int n = dims.get(1);
    Tensor tab;
    {
      tab = Join.of(1, A, IdentityMatrix.of(m), Partition.of(b, 1));
      Tensor row = Tensors.vector(i -> n <= i && i < n + m ? RealScalar.ONE : RealScalar.ZERO, n + m + 1);
      for (int index = 0; index < m; ++index) // make all entries in bottom row zero
        row = row.subtract(tab.get(index));
      row.set(RealScalar.ZERO, n + m); // mysterious
      tab.append(row);
      tab = simplex(tab); // make phase 1
    }
    // phase 2
    tab = Join.of(1, //
        TensorMap.of(row -> row.extract(0, n), tab.extract(0, m), 1), //
        Partition.of(tab.get(-1, n + m).extract(0, m), 1));
    tab.append(Join.of(c, Tensors.of(RealScalar.ZERO)));
    tab = simplex(tab);
    return get_current_x(tab);
  }

  private static Tensor simplex(Tensor tab) {
    List<Integer> dims = Dimensions.of(tab);
    int m = dims.get(0) - 1;
    int n = dims.get(1) - 1;
    // int count = 0;
    while (true) {
      // System.out.println("********************************");
      // System.out.println("current tableau");
      // System.out.println(Pretty.of(tab));
      Tensor c = tab.get(m).extract(0, n);
      final int j = ArgMin.of(c);
      if (((RealScalar) c.Get(j)).signInt() == -1) {
        int argmax = ArgMax.of(tab.get(-1, j).extract(0, m));
        if (((RealScalar) tab.Get(argmax, j)).signInt() != 1)
          throw TensorRuntimeException.of(tab); // problem unbounded
        Integer pivot = null;
        RealScalar min = null;
        for (int i = 0; i < m; ++i)
          if (((RealScalar) tab.Get(i, j)).signInt() == 1) {
            RealScalar value = (RealScalar) tab.Get(i, n).divide(tab.Get(i, j));
            if (min == null || 0 < min.compareTo(value)) {
              min = value;
              pivot = i;
            }
          }
        // System.out.println("pivot row is " + pivot_row);
        // normalize
        tab.set(tab.get(pivot).multiply(tab.Get(pivot, j).invert()), pivot);
        for (int i = 0; i < m + 1; ++i)
          if (i != pivot)
            tab.set(tab.get(i).subtract(tab.get(pivot).multiply(tab.Get(i, j))), i);
        // System.out.println("current basic feasible sol");
        // System.out.println(get_current_x(tab));
        // ++count;
      } else
        break;
    }
    // System.out.println(count);
    return tab;
  }

  // this methodology is surprisingly robust, but still bad style
  // therefore the alternative implementation SimplexImpl was created
  private static Tensor get_current_x(Tensor tab) {
    List<Integer> dims = Dimensions.of(tab);
    int m = dims.get(0) - 1;
    int n = dims.get(1) - 1;
    Tensor x = Array.zeros(n);
    for (int j = 0; j < n; ++j) {
      int len = (int) tab.get(-1, j).flatten(0) //
          .map(Scalar.class::cast) //
          .filter(s -> s.equals(RealScalar.ZERO)).count();
      if (len == m)
        x.set(tab.get(ArgMax.of(tab.get(-1, j)), n), j);
    }
    return x;
  }
}
