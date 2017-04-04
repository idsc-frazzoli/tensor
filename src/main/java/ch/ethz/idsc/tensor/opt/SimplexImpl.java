// MATLAB code by Nasser M. Abbasi
// http://12000.org/my_notes/simplex/index.htm
// adapted by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Arrays;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.alg.MapThread;
import ch.ethz.idsc.tensor.alg.Partition;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;

/* package */ class SimplexImpl {
  static Tensor of(Tensor c, Tensor A, Tensor b) {
    final int m = b.length();
    final int n = c.length();
    SimplexAlgorithm simplexImpl;
    {
      Tensor tab = MapThread.of(Join::of, Arrays.asList(A, IdentityMatrix.of(m), Partition.of(b, 1)), 1);
      Tensor row = Tensors.vector(i -> n <= i && i < n + m ? RealScalar.ONE : ZeroScalar.get(), n + m + 1);
      for (int index = 0; index < m; ++index) // make all entries in bottom row zero
        row = row.subtract(tab.get(index));
      row.set(ZeroScalar.get(), n + m); // set bottom corner to 0
      tab.append(row);
      Tensor ind = Join.of(Tensors.vector(i -> RealScalar.ONE.negate(), n), Range.of(m));
      simplexImpl = new SimplexAlgorithm(tab, ind); // phase 1
    }
    Tensor tab = MapThread.of(Join::of,
        Arrays.asList( //
            TensorMap.of(t -> t.extract(0, n), simplexImpl.tab.extract(0, m), 1), //
            Partition.of(simplexImpl.tab.get(-1, n + m).extract(0, m), 1)),
        1);
    tab.append(Join.of(c, Tensors.of(ZeroScalar.get()))); // set bottom corner to 0
    return new SimplexAlgorithm(tab, simplexImpl.ind.extract(0, c.length())).getX(); // phase 2
  }
}
