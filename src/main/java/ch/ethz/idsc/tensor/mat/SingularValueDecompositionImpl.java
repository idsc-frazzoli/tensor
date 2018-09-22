// code adapted by jph
package ch.ethz.idsc.tensor.mat;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.red.CopySign;
import ch.ethz.idsc.tensor.red.Hypot;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.red.Norm2Squared;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Increment;
import ch.ethz.idsc.tensor.sca.Sign;
import ch.ethz.idsc.tensor.sca.Sqrt;

/* package */ class SingularValueDecompositionImpl implements SingularValueDecomposition {
  /** Difference between 1.0 and the minimum double greater than 1.0
   * DBL_EPSILON == 2.220446049250313E-16 */
  private static final Scalar DBL_EPSILON = DoubleScalar.of(Math.nextUp(1.0) - 1.0);
  private static final int MAX_ITERATIONS = 28;
  // ---
  private final int rows;
  private final int cols;
  private final Tensor u;
  private final Tensor w;
  private final Tensor r;
  private final Tensor v;

  /** @param A with cols <= rows
   * @param epsilon influences if levelW manipulates w and u
   * @param maxIterations */
  /* package */ SingularValueDecompositionImpl(Tensor A) {
    rows = A.length();
    cols = Unprotect.dimension1(A);
    if (rows < cols)
      throw new IllegalArgumentException();
    u = A.copy();
    w = Array.zeros(cols);
    r = Array.zeros(cols);
    // ---
    for (int i = 0; i < cols; ++i) {
      initU1(i);
      initU2(i);
    }
    Chop chop = Chop.below(Norm._1.ofMatrix(Tensors.of(w, r)).multiply(DBL_EPSILON).number().doubleValue());
    // ---
    v = Array.zeros(cols, cols);
    v.set(RealScalar.ONE, cols - 1, cols - 1);
    for (int i = cols - 2; 0 <= i; --i)
      initV(i);
    for (int i = cols - 1; 0 <= i; --i)
      initU3(i);
    for (int i = cols - 1; 0 <= i; --i) {
      for (int iteration = 0; iteration <= MAX_ITERATIONS; ++iteration) {
        int l = levelW(i, chop);
        if (l == i)
          break;
        if (iteration == MAX_ITERATIONS)
          throw new RuntimeException("no convergence");
        rotateUV(l, i);
      }
      positiveW(i);
    }
  }

  @Override // from SingularValueDecomposition
  public Tensor getU() {
    return u.unmodifiable();
  }

  @Override // from SingularValueDecomposition
  public Tensor values() {
    return w.unmodifiable();
  }

  @Override // from SingularValueDecomposition
  public Tensor getV() {
    return v.unmodifiable();
  }

  private void initU1(int i) {
    Scalar p = RealScalar.ZERO;
    Scalar scale = Norm._1.ofVector(u.extract(i, rows).get(Tensor.ALL, i));
    if (Scalars.nonZero(scale)) {
      IntStream.range(i, rows).forEach(k -> u.set(x -> x.divide(scale), k, i));
      Scalar s = Norm2Squared.ofVector(u.extract(i, rows).get(Tensor.ALL, i));
      Scalar f = u.Get(i, i);
      p = CopySign.of(Sqrt.FUNCTION.apply(s), f).negate();
      Scalar h = f.multiply(p).subtract(s);
      u.set(f.subtract(p), i, i);
      for (int j = i + 1; j < cols; ++j) {
        final int fj = j;
        Scalar dot = u.stream() //
            .skip(i) //
            .map(row -> row.Get(i).multiply(row.Get(fj))) //
            .reduce(Scalar::add).get();
        StaticHelper.addScaled(i, rows, u, i, j, dot.divide(h));
      }
      IntStream.range(i, rows).forEach(k -> u.set(x -> x.multiply(scale), k, i));
    }
    w.set(scale.multiply(p), i);
  }

  private void initU2(int i) {
    final int ip1 = i + 1;
    if (ip1 != cols) {
      Scalar p = RealScalar.ZERO;
      Scalar scale = Norm._1.ofVector(u.get(i).extract(ip1, cols));
      if (Scalars.nonZero(scale)) {
        IntStream.range(ip1, cols).forEach(k -> u.set(x -> x.divide(scale), i, k));
        {
          Scalar s = Norm2Squared.ofVector(u.get(i).extract(ip1, cols));
          Scalar f = u.Get(i, ip1);
          p = CopySign.of(Sqrt.FUNCTION.apply(s), f).negate();
          Scalar h = f.multiply(p).subtract(s);
          u.set(f.subtract(p), i, ip1);
          IntStream.range(ip1, cols).forEach(k -> r.set(u.Get(i, k).divide(h), k));
        }
        Tensor ui = u.get(i);
        for (int j = ip1; j < rows; ++j) {
          Scalar s = (Scalar) u.get(j).extract(ip1, cols).dot(ui.extract(ip1, cols));
          for (int k = ip1; k < cols; ++k) {
            Scalar srv = s.multiply(r.Get(k));
            u.set(x -> x.add(srv), j, k);
          }
        }
        IntStream.range(ip1, cols).forEach(k -> u.set(x -> x.multiply(scale), i, k));
      }
      r.set(scale.multiply(p), ip1);
    }
  }

  private void initV(int i) {
    final int ip1 = i + 1;
    Scalar p = r.Get(ip1);
    if (Scalars.nonZero(p)) {
      Tensor ui = u.get(i);
      IntStream.range(ip1, cols).forEach(j -> v.set(ui.Get(j).divide(ui.Get(ip1)).divide(p), j, i));
      Tensor uiEx = ui.extract(ip1, cols);
      for (int j = ip1; j < cols; ++j) {
        final int fj = j;
        StaticHelper.addScaled(ip1, cols, v, i, j, //
            (Scalar) uiEx.dot(Tensor.of(v.stream().skip(ip1).map(row -> row.Get(fj)))));
      }
    }
    IntStream.range(ip1, cols).forEach(j -> v.set(RealScalar.ZERO, i, j));
    IntStream.range(ip1, cols).forEach(j -> v.set(RealScalar.ZERO, j, i));
    v.set(RealScalar.ONE, i, i);
  }

  private void initU3(int i) {
    final int ip1 = i + 1;
    IntStream.range(ip1, cols).forEach(j -> u.set(RealScalar.ZERO, i, j));
    Scalar p = w.Get(i);
    if (Scalars.isZero(p))
      IntStream.range(i, rows).forEach(j -> u.set(RealScalar.ZERO, j, i));
    else {
      Scalar gi = p;
      for (int j = ip1; j < cols; ++j) {
        final int fj = j;
        Scalar s = u.stream() //
            .skip(ip1) // ip1 until rows
            .map(row -> row.Get(i).multiply(row.Get(fj))) //
            .reduce(Scalar::add).get();
        StaticHelper.addScaled(i, rows, u, i, j, s.divide(u.Get(i, i)).divide(gi));
      }
      IntStream.range(i, rows).forEach(j -> u.set(x -> x.divide(gi), j, i));
    }
    u.set(Increment.ONE, i, i);
  }

  private int levelW(int k, Chop chop) {
    for (int l = k; l > 0; --l) {
      if (chop.allZero(r.Get(l)))
        return l;
      if (chop.allZero(w.Get(l - 1))) {
        Scalar c = RealScalar.ZERO;
        Scalar s = RealScalar.ONE;
        for (int i = l; i < k + 1; ++i) {
          Scalar f = s.multiply(r.Get(i));
          r.set(c.multiply(r.Get(i)), i);
          if (chop.allZero(f)) // <- never true in tests
            break;
          Scalar g = w.Get(i);
          Scalar h = Hypot.of(f, g);
          w.set(h, i);
          c = g.divide(h);
          s = f.divide(h).negate();
          StaticHelper.rotate(u, rows, c, s, i, l - 1);
        }
        return l;
      }
    }
    return 0;
  }

  /** @param l < i
   * @param i > 0 */
  private void rotateUV(int l, int i) {
    Scalar x = w.Get(l);
    Scalar y = w.Get(i - 1);
    Scalar z = w.Get(i);
    Scalar p = r.Get(i - 1);
    Scalar h = r.Get(i);
    Scalar hy = h.multiply(y);
    Scalar f = y.subtract(z).multiply(y.add(z)).add(p.subtract(h).multiply(p.add(h))).divide(hy.add(hy));
    p = Hypot.of(f, RealScalar.ONE);
    f = x.subtract(z).multiply(x.add(z)).add(h.multiply(y.divide(f.add(CopySign.of(p, f))).subtract(h))).divide(x);
    Scalar s = RealScalar.ONE;
    Scalar c = RealScalar.ONE;
    for (int j = l; j < i; ++j) {
      int jp1 = j + 1;
      p = r.Get(jp1);
      y = w.Get(jp1);
      h = s.multiply(p);
      p = c.multiply(p);
      z = Hypot.of(f, h);
      r.set(z, j);
      c = f.divide(z);
      s = h.divide(z);
      StaticHelper.rotate(v, cols, c, s, jp1, j);
      f = x.multiply(c).add(p.multiply(s));
      p = p.multiply(c).subtract(x.multiply(s));
      h = y.multiply(s);
      y = y.multiply(c);
      z = Hypot.of(f, h);
      w.set(z, j);
      if (Scalars.nonZero(z)) { // <- never false in tests
        c = f.divide(z);
        s = h.divide(z);
      }
      StaticHelper.rotate(u, rows, c, s, jp1, j);
      f = c.multiply(p).add(s.multiply(y));
      x = c.multiply(y).subtract(s.multiply(p));
    }
    r.set(RealScalar.ZERO, l);
    r.set(f, i);
    w.set(x, i);
  }

  private void positiveW(int i) {
    Scalar z = w.Get(i);
    if (Sign.isNegative(z)) {
      w.set(z.negate(), i);
      v.set(Tensor::negate, Tensor.ALL, i);
    }
  }
}
