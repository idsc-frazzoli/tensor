// code adapted by jph
package ch.ethz.idsc.tensor.mat;

import java.util.List;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.red.CopySign;
import ch.ethz.idsc.tensor.red.Hypot;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Increment;
import ch.ethz.idsc.tensor.sca.Sqrt;

class SingularValueDecompositionImpl implements SingularValueDecomposition {
  private final int rows;
  private final int cols;
  private final Tensor u;
  private final Tensor w;
  private final Tensor r;
  private final Tensor v;
  /** elements of w below this threshold will be considered as 0 */
  private double w_threshold = 1e-12;

  /** @param A
   * @param epsilon influences if levelW manipulates w and u
   * @param maxIterations */
  SingularValueDecompositionImpl(Tensor A, double epsilon, int maxIterations) {
    List<Integer> dimensions = Dimensions.of(A);
    rows = dimensions.get(0);
    cols = dimensions.get(1);
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
    double eps = Norm._1.of(Tensors.of(w, r)).multiply(DoubleScalar.of(epsilon)).number().doubleValue();
    // ---
    v = Array.zeros(cols, cols);
    v.set(RealScalar.ONE, cols - 1, cols - 1);
    for (int i = cols - 2; i >= 0; --i) {
      initV(i);
      v.set(RealScalar.ONE, i, i);
    }
    // ---
    for (int i = Math.min(rows, cols) - 1; i >= 0; --i)
      initU3(i);
    // ---
    for (int i = cols - 1; i >= 0; --i) {
      for (int iteration = 0; iteration <= maxIterations; ++iteration) {
        int l = levelW(i, eps);
        if (l == i)
          break;
        if (iteration == maxIterations)
          throw new RuntimeException("no convergence");
        rotateUV(l, i);
      }
      positiveW(i);
    }
  }

  @Override
  public Tensor getU() {
    return u.unmodifiable();
  }

  @Override
  public Tensor values() {
    return w.unmodifiable();
  }

  @Override
  public Tensor getV() {
    return v.unmodifiable();
  }

  @Override
  public void setThreshold(double w_threshold) {
    this.w_threshold = w_threshold;
  }

  /** @return threshold strictly below which singular values are considered to be zero */
  @Override
  public double getThreshold() {
    return w_threshold;
  }

  private void initU1(int i) {
    Scalar p = ZeroScalar.get();
    Scalar scale = ZeroScalar.get();
    if (i < rows) {
      scale = Norm._1.of(u.extract(i, rows).get(Tensor.ALL, i));
      if (!scale.equals(ZeroScalar.get())) {
        Scalar fi = scale.invert();
        IntStream.range(i, rows).forEach(k -> u.set(x -> x.multiply(fi), k, i));
        Scalar s = Norm._2Squared.of(u.extract(i, rows).get(Tensor.ALL, i));
        Scalar f = u.Get(i, i);
        p = CopySign.bifunction.apply(Sqrt.function.apply(s), f).negate();
        Scalar h = f.multiply(p).subtract(s);
        u.set(f.subtract(p), i, i);
        Scalar fs = scale;
        for (int j = i + 1; j < cols; ++j)
          _addscaled(i, rows, u, i, j, //
              ((Scalar) u.extract(i, rows).get(Tensor.ALL, i).dot(u.extract(i, rows).get(Tensor.ALL, j))).divide(h));
        IntStream.range(i, rows).forEach(k -> u.set(x -> x.multiply(fs), k, i));
      }
    }
    w.set(scale.multiply(p), i);
  }

  private void initU2(int i) {
    final int ip1 = i + 1;
    Scalar p = ZeroScalar.get();
    Scalar scale = ZeroScalar.get();
    if (i < rows && ip1 != cols) {
      scale = Norm._1.of(u.get(i).extract(ip1, cols));
      if (!scale.equals(ZeroScalar.get())) {
        Scalar si = scale.invert();
        IntStream.range(ip1, cols).forEach(k -> u.set(x -> x.multiply(si), i, k));
        {
          Scalar s = Norm._2Squared.of(u.get(i).extract(ip1, cols));
          Scalar f = u.Get(i, ip1);
          p = CopySign.bifunction.apply(Sqrt.function.apply(s), f).negate();
          Scalar h = f.multiply(p).subtract(s);
          u.set(f.subtract(p), i, ip1);
          IntStream.range(ip1, cols).forEach(k -> r.set(u.Get(i, k).divide(h), k));
        }
        for (int j = ip1; j < rows; ++j) {
          Scalar s = (Scalar) u.get(j).extract(ip1, cols).dot(u.get(i).extract(ip1, cols));
          for (int k = ip1; k < cols; ++k) {
            Scalar srv = s.multiply(r.Get(k));
            u.set(x -> x.add(srv), j, k);
          }
        }
        Scalar fs = scale;
        IntStream.range(ip1, cols).forEach(k -> u.set(x -> x.multiply(fs), i, k));
      }
    }
    if (i < cols - 1)
      r.set(scale.multiply(p), ip1);
  }

  private void initV(int i) {
    final int ip1 = i + 1;
    Scalar p = r.Get(ip1);
    if (!p.equals(ZeroScalar.get())) {
      IntStream.range(ip1, cols).forEach(j -> v.set(u.Get(i, j).divide(u.Get(i, ip1)).divide(p), j, i));
      for (int j = ip1; j < cols; ++j)
        _addscaled(ip1, cols, v, i, j, //
            (Scalar) u.get(i).extract(ip1, cols).dot(v.extract(ip1, cols).get(Tensor.ALL, j)));
    }
    IntStream.range(ip1, cols).forEach(j -> v.set(ZeroScalar.get(), i, j));
    IntStream.range(ip1, cols).forEach(j -> v.set(ZeroScalar.get(), j, i));
  }

  private void initU3(int i) {
    final int ip1 = i + 1;
    IntStream.range(ip1, cols).forEach(j -> u.set(ZeroScalar.get(), i, j));
    Scalar p = w.Get(i);
    if (p.equals(ZeroScalar.get()))
      IntStream.range(i, rows).forEach(j -> u.set(ZeroScalar.get(), j, i));
    else {
      Scalar gi = p.invert();
      for (int j = ip1; j < cols; ++j) {
        Scalar s = (Scalar) u.extract(ip1, rows).get(Tensor.ALL, i).dot(u.extract(ip1, rows).get(Tensor.ALL, j));
        _addscaled(i, rows, u, i, j, s.divide(u.Get(i, i)).multiply(gi));
      }
      IntStream.range(i, rows).forEach(j -> u.set(x -> x.multiply(gi), j, i));
    }
    u.set(Increment.ONE, i, i);
  }

  private int levelW(int k, double eps) {
    for (int l = k; l > 0; --l) {
      if (r.Get(l).abs().number().doubleValue() <= eps)
        return l;
      if (w.Get(l - 1).abs().number().doubleValue() <= eps) {
        Scalar c = ZeroScalar.get();
        Scalar s = RealScalar.ONE;
        for (int i = l; i < k + 1; ++i) {
          Scalar f = s.multiply(r.Get(i));
          r.set(c.multiply(r.Get(i)), i);
          if (f.abs().number().doubleValue() <= eps)
            break;
          Scalar g = w.Get(i);
          Scalar h = Hypot.bifunction.apply(f, g);
          w.set(h, i);
          h = h.invert();
          c = g.multiply(h);
          s = f.negate().multiply(h);
          _rotate(u, rows, c, s, i, l - 1);
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
    p = Hypot.bifunction.apply(f, RealScalar.ONE);
    f = x.subtract(z).multiply(x.add(z)).add(h.multiply(y.divide(f.add(CopySign.bifunction.apply(p, f))).subtract(h))).divide(x);
    Scalar s = RealScalar.ONE;
    Scalar c = RealScalar.ONE;
    for (int j = l; j < i; ++j) {
      int jp1 = j + 1;
      p = r.Get(jp1);
      y = w.Get(jp1);
      h = s.multiply(p);
      p = c.multiply(p);
      z = Hypot.bifunction.apply(f, h);
      r.set(z, j);
      c = f.divide(z);
      s = h.divide(z);
      _rotate(v, cols, c, s, jp1, j);
      f = x.multiply(c).add(p.multiply(s));
      p = p.multiply(c).subtract(x.multiply(s));
      h = y.multiply(s);
      y = y.multiply(c);
      z = Hypot.bifunction.apply(f, h);
      w.set(z, j);
      if (!z.equals(ZeroScalar.get())) {
        z = z.invert();
        c = f.multiply(z);
        s = h.multiply(z);
      }
      _rotate(u, rows, c, s, jp1, j);
      f = c.multiply(p).add(s.multiply(y));
      x = c.multiply(y).subtract(s.multiply(p));
    }
    r.set(ZeroScalar.get(), l);
    r.set(f, i);
    w.set(x, i);
  }

  private void positiveW(int i) {
    Scalar z = w.Get(i);
    if (z.number().doubleValue() < 0) {
      w.set(z.negate(), i);
      IntStream.range(0, cols).forEach(j -> v.set(x -> x.negate(), j, i));
    }
  }

  private static void _addscaled(int l, int cols, Tensor v, int i, int j, Scalar s) {
    for (int k = l; k < cols; ++k) {
      Scalar a = s.multiply(v.Get(k, i));
      v.set(x -> x.add(a), k, j);
    }
  }

  private static void _rotate(Tensor m, int length, Scalar c, Scalar s, int i, int j) {
    for (int k = 0; k < length; ++k) {
      Scalar x = m.Get(k, j);
      Scalar z = m.Get(k, i);
      m.set(x.multiply(c).add(z.multiply(s)), k, j);
      m.set(z.multiply(c).subtract(x.multiply(s)), k, i);
    }
  }
}
