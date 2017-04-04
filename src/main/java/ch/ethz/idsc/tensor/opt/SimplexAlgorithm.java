// MATLAB code by Nasser M. Abbasi
// http://12000.org/my_notes/simplex/index.htm
// adapted by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.red.ArgMax;
import ch.ethz.idsc.tensor.red.ArgMin;

/* package */ class SimplexAlgorithm {
  final Tensor tab;
  final Tensor ind;
  final int m;
  final int n;

  SimplexAlgorithm(Tensor tab, Tensor inv) {
    this.tab = tab;
    this.ind = inv;
    m = tab.length() - 1;
    n = inv.length();
    while (true) {
      Tensor c = tab.get(m).extract(0, n);
      final int j = ArgMin.of(c);
      if (((RealScalar) c.Get(j)).signInt() == -1) {
        { // check if unbounded
          int argmax = ArgMax.of(tab.get(-1, j).extract(0, m));
          if (((RealScalar) tab.Get(argmax, j)).signInt() != 1)
            throw TensorRuntimeException.of(tab); // problem unbounded
        }
        int pivot = getPivot(j);
        inv.set(RealScalar.of(pivot), j);
        tab.set(tab.get(pivot).multiply(tab.Get(pivot, j).invert()), pivot); // normalize
        for (int i = 0; i < m + 1; ++i)
          if (i != pivot)
            tab.set(tab.get(i).subtract(tab.get(pivot).multiply(tab.Get(i, j))), i);
      } else
        break;
    }
  }

  private int getPivot(int j) {
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
    return pivot;
  }

  Tensor getX() {
    return ind.map(i -> 0 <= i.number().intValue() ? //
        tab.Get(i.number().intValue(), n) : ZeroScalar.get());
  }
}
