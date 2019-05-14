// code by N. M. Brenner
// adapted by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** Fourier transform is restricted to vectors with length of power of 2.
 * Functionality works for vectors with entries of type {@link Quantity}.
 * 
 * <p>Consistent with Mathematica:
 * Mathematica::Fourier[{}] throws an Exception
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Fourier.html">Fourier</a> */
public enum Fourier {
  ;
  /** Hint: uses decimation-in-time or Cooley-Tukey FFT
   * 
   * @param vector of length of power of 2
   * @return */
  public static Tensor of(Tensor vector) {
    final int n = vector.length();
    if (n == 0 || 0 != (n & (n - 1)))
      throw TensorRuntimeException.of(vector);
    Scalar[] array = vector.stream().map(Scalar.class::cast).toArray(Scalar[]::new);
    {
      int j = 0;
      for (int i = 0; i < n; ++i) {
        if (j > i) {
          Scalar val = array[i];
          array[i] = array[j];
          array[j] = val;
        }
        int m = n >> 1;
        while (m >= 1 && j >= m) {
          j -= m;
          m >>= 1;
        }
        j += m;
      }
    }
    int mmax = 1;
    int isign = 1;
    while (n > mmax) {
      int istep = mmax << 1;
      double thalf = isign * Math.PI / istep;
      double wtemp = Math.sin(thalf);
      Scalar wp = ComplexScalar.of(-2 * wtemp * wtemp, Math.sin(thalf + thalf));
      Scalar w = RealScalar.ONE;
      for (int m = 0; m < mmax; ++m) {
        for (int i = m; i < n; i += istep) {
          int j = i + mmax;
          Scalar temp = array[j].multiply(w);
          array[j] = array[i].subtract(temp);
          array[i] = array[i].add(temp);
        }
        w = w.add(w.multiply(wp));
      }
      mmax = istep;
    }
    return Tensors.of(array).divide(Sqrt.FUNCTION.apply(RealScalar.of(n)));
  }
}
