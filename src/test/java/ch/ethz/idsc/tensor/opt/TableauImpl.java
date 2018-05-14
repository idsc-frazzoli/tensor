// MATLAB code by Nasser M. Abbasi
// http://12000.org/my_notes/simplex/index.htm
// adapted by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Objects;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.alg.Partition;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.red.ArgMax;
import ch.ethz.idsc.tensor.red.ArgMin;
import ch.ethz.idsc.tensor.sca.SignInterface;

/* package */ class TableauImpl {
  public static Tensor of(Tensor c, Tensor A, Tensor b) {
    int m = A.length() - 1;
    int n = Unprotect.dimension1(A) - 1;
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
        Partition.of(tab.get(Tensor.ALL, n + m).extract(0, m), 1));
    tab.append(Join.of(c, Tensors.of(RealScalar.ZERO)));
    tab = simplex(tab);
    return get_current_x(tab);
  }

  private static Tensor simplex(Tensor tab) {
    int m = tab.length() - 1;
    int n = Unprotect.dimension1(tab) - 1;
    // int count = 0;
    while (true) {
      // System.out.println("********************************");
      // System.out.println("current tableau");
      // System.out.println(Pretty.of(tab));
      Tensor c = tab.get(m).extract(0, n);
      final int j = ArgMin.of(c);
      if (((SignInterface) c.Get(j)).signInt() == -1) {
        int argmax = ArgMax.of(tab.get(Tensor.ALL, j).extract(0, m));
        if (((SignInterface) tab.Get(argmax, j)).signInt() != 1)
          throw TensorRuntimeException.of(tab); // problem unbounded
        Integer pivot = null;
        Scalar min = null;
        for (int i = 0; i < m; ++i)
          if (((SignInterface) tab.Get(i, j)).signInt() == 1) {
            Scalar value = tab.Get(i, n).divide(tab.Get(i, j));
            if (Objects.isNull(min) || 0 < Scalars.compare(min, value)) {
              min = value;
              pivot = i;
            }
          }
        // System.out.println("pivot row is " + pivot_row);
        // normalize
        tab.set(tab.get(pivot).divide(tab.Get(pivot, j)), pivot);
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
    int m = tab.length() - 1;
    int n = Unprotect.dimension1(tab) - 1;
    Tensor x = Array.zeros(n);
    for (int j = 0; j < n; ++j) {
      int len = Math.toIntExact(tab.get(Tensor.ALL, j).stream() //
          .map(Scalar.class::cast) //
          .filter(Scalars::isZero) //
          .count());
      if (len == m)
        x.set(tab.get(ArgMax.of(tab.get(Tensor.ALL, j)), n), j);
    }
    return x;
  }
}
