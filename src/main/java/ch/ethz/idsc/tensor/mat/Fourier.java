// code by N. M. Brenner
// adapted by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.ComplexEmbedding;

/** consistent with Mathematica for input vectors of length of power of 2
 * 
 * Mathematica::Fourier[{}] throws an Exception
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Fourier.html">Fourier</a> */
public enum Fourier {
  ;
  /** @param vector of length of power of 2
   * @return */
  public static Tensor of(Tensor vector) {
    int n = vector.length();
    if (n == 0 || 0 != (n & (n - 1)))
      throw TensorRuntimeException.of(vector);
    double[] data = new double[n * 2];
    int index = -1;
    for (Tensor scalar : vector) {
      ComplexEmbedding complexEmbedding = (ComplexEmbedding) scalar;
      data[++index] = complexEmbedding.real().number().doubleValue();
      data[++index] = complexEmbedding.imag().number().doubleValue();
    }
    replace(data, 1);
    double factor = Math.sqrt(vector.length());
    for (int i = 0; i < data.length; ++i)
      data[i] /= factor; // favor precision
    return Tensors.vector(i -> ComplexScalar.of(data[i * 2 + 0], data[i * 2 + 1]), vector.length());
  }

  // decimation-in-time or Cooley-Tukey FFT
  private static void replace(double[] data, int isign) {
    int n = data.length / 2;
    int nn = n << 1;
    int j = 1;
    for (int i = 1; i < nn; i += 2) {
      if (j > i) {
        swap(data, j - 1, i - 1);
        swap(data, j, i);
      }
      int m = n;
      while (m >= 2 && j > m) {
        j -= m;
        m >>= 1;
      }
      j += m;
    }
    int mmax = 2;
    while (nn > mmax) {
      int istep = mmax << 1;
      double thalf = isign * Math.PI / mmax;
      double wtemp = Math.sin(thalf);
      double wpr = -2 * wtemp * wtemp;
      double wpi = Math.sin(thalf + thalf);
      double wr = 1;
      double wi = 0;
      for (int m = 1; m < mmax; m += 2) {
        for (int i = m; i <= nn; i += istep) {
          j = i + mmax;
          double tempr = wr * data[j - 1] - wi * data[j];
          double tempi = wr * data[j] + wi * data[j - 1];
          data[j - 1] = data[i - 1] - tempr;
          data[j] = data[i] - tempi;
          data[i - 1] += tempr;
          data[i] += tempi;
        }
        double wc = wr;
        wr = wr * wpr - wi * wpi + wr;
        wi = wi * wpr + wc * wpi + wi;
      }
      mmax = istep;
    }
  }

  private static void swap(double[] data, int i, int j) {
    double val = data[i];
    data[i] = data[j];
    data[j] = val;
  }
}
