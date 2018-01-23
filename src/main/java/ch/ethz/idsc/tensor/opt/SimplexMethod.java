// MATLAB code by Nasser M. Abbasi
// http://12000.org/my_notes/simplex/index.htm
// adapted by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RationalScalar;
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
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.red.ArgMax;
import ch.ethz.idsc.tensor.red.ArgMin;
import ch.ethz.idsc.tensor.sca.Sign;

/** traditional simplex algorithm that performs poorly on Klee-Minty cube */
/* package */ class SimplexMethod {
  static Tensor of(Tensor c, Tensor A, Tensor b, SimplexPivot simplexPivot) {
    final int m = b.length();
    final int n = c.length();
    // System.out.println(m + " x " + n);
    SimplexMethod simplexImpl;
    {
      // Tensor D = DiagonalMatrix.of(b.map(UnitStep.function));
      // IdentityMatrix.of(m)
      Tensor tab = Join.of(1, A, IdentityMatrix.of(m), Partition.of(b, 1));
      Tensor row = Tensors.vector(i -> n <= i && i < n + m ? RealScalar.ONE : RealScalar.ZERO, n + m + 1);
      for (int index = 0; index < m; ++index) // make all entries in bottom row zero
        row = row.subtract(tab.get(index));
      row.set(RealScalar.ZERO, n + m); // set bottom corner to 0
      tab.append(row);
      simplexImpl = new SimplexMethod(tab, Range.of(n, n + m), simplexPivot); // phase 1
    }
    Tensor tab = Join.of(1, //
        TensorMap.of(row -> row.extract(0, n), simplexImpl.tab.extract(0, m), 1), //
        Partition.of(simplexImpl.tab.get(Tensor.ALL, n + m).extract(0, m), 1));
    tab.append(Join.of(c, Tensors.of(RealScalar.ZERO))); // set bottom corner to 0
    return new SimplexMethod(tab, simplexImpl.ind, simplexPivot).getX(); // phase 2
  }

  final Tensor tab; // (m+1) x (n+1)
  final Tensor ind; // vector of length m
  final int m;
  final int n;

  private SimplexMethod(Tensor tab, Tensor ind, SimplexPivot simplexPivot) {
    this.tab = tab;
    this.ind = ind;
    m = tab.length() - 1;
    n = Unprotect.dimension1(tab) - 1;
    if (isOutsideRange(ind, n))
      throw TensorRuntimeException.of(ind);
    while (true) {
      // System.out.println(Pretty.of(tab));
      Tensor c = tab.get(m).extract(0, n);
      final int j = ArgMin.of(numbers(c));
      if (Sign.isNegative(c.Get(j))) {
        { // check if unbounded
          int argmax = ArgMax.of(numbers(tab.get(Tensor.ALL, j).extract(0, m)));
          Sign.requirePositive(tab.Get(argmax, j)); // otherwise problem unbounded
        }
        int p = simplexPivot.get(tab, j, n);
        ind.set(RationalScalar.of(j, 1), p);
        // System.out.println(ind);
        tab.set(tab.get(p).divide(tab.Get(p, j)), p); // normalize
        for (int i = 0; i < tab.length(); ++i)
          if (i != p)
            tab.set(tab.get(i).subtract(tab.get(p).multiply(tab.Get(i, j))), i);
      } else
        break;
    }
  }

  private Tensor getX() {
    Tensor x = Array.zeros(n);
    for (int index = 0; index < ind.length(); ++index)
      x.set(tab.Get(index, n), ind.Get(index).number().intValue());
    return x;
  }

  // helper function to check consistency
  private static boolean isOutsideRange(Tensor ind, int n) {
    return ind.stream() //
        .map(Scalar.class::cast) //
        .map(Scalars::intValueExact) //
        .anyMatch(i -> n <= i);
  }

  // helper function to determine pivot in case entries of vector are of type Quantity
  private static Tensor numbers(Tensor vector) {
    return Tensor.of(vector.stream() //
        .map(Scalar.class::cast) //
        .map(Scalar::number) //
        .map(RealScalar::of));
  }
}
