// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class FourierTest extends TestCase {
  public void test2() {
    Tensor vector = Tensors.fromString("{1 + 2*I, 3 + 11*I}");
    Tensor expect = Tensors.fromString("{2.828427124746190 + 9.19238815542512*I, -1.414213562373095 - 6.36396103067893*I}");
    Chop._12.requireClose(Fourier.of(vector), expect);
    Chop._12.requireClose(InverseFourier.of(Fourier.of(vector)), vector);
  }

  public void test2Quantity() {
    Tensor vector = Tensors.fromString("{1 + 2*I[m], 3 + 11*I[m]}");
    Tensor expect = Tensors.fromString("{2.828427124746190 + 9.19238815542512*I[m], -1.414213562373095 - 6.36396103067893*I[m]}");
    Chop._12.requireClose(Fourier.of(vector), expect);
    Chop._12.requireClose(InverseFourier.of(Fourier.of(vector)), vector);
  }

  public void test4() {
    Tensor vector = Tensors.vector(1, 2, 0, 0);
    Tensor tensor = Fourier.of(vector);
    Tensor expect = Tensors.fromString("{1.5, 0.5 + I, -0.5, 0.5 - I}");
    Chop._12.requireClose(FourierMatrix.of(vector.length()).dot(vector), expect);
    Chop._12.requireClose(vector.dot(FourierMatrix.of(vector.length())), expect);
    Chop._12.requireClose(tensor, expect);
    Tensor backed = Fourier.of(expect);
    Chop._12.requireClose(backed, Tensors.vector(1, 0, 0, 2));
  }

  public void testRandom() {
    Distribution distribution = NormalDistribution.standard();
    for (int n = 0; n < 7; ++n)
      for (int count = 0; count < 10; ++count) {
        Tensor real = RandomVariate.of(distribution, 1 << n);
        Tensor imag = RandomVariate.of(distribution, 1 << n);
        Tensor vector = real.add(imag.multiply(ComplexScalar.I));
        Tensor result = Fourier.of(vector);
        Tensor dotmat = vector.dot(FourierMatrix.of(vector.length()));
        Chop._12.requireClose(dotmat, result);
      }
  }

  public void testFailScalar() {
    try {
      Fourier.of(RealScalar.ONE);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailEmpty() {
    try {
      Fourier.of(Tensors.empty());
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailMatrix() {
    try {
      Fourier.of(HilbertMatrix.of(4));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
