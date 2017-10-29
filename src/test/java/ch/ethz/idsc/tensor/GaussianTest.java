// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.Expectation;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class GaussianTest extends TestCase {
  public void testPlusGaussian() {
    Scalar a = Gaussian.of(10, 1);
    Scalar b = Gaussian.of(-2, 2);
    Scalar c = a.add(b);
    assertEquals(c, Gaussian.of(8, 3));
  }

  public void testPlusReal() {
    Scalar a = Gaussian.of(10, 1);
    Scalar b = RealScalar.of(3);
    Scalar c = a.add(b);
    assertEquals(c, Gaussian.of(13, 1));
  }

  public void testMultiply() {
    Scalar a = Gaussian.of(5, 2);
    Scalar b = RealScalar.of(-3);
    Scalar c = a.multiply(b);
    assertEquals(c, Gaussian.of(-15, 2 * 9));
  }

  public void testMean() {
    Tensor vector = Tensors.of(Gaussian.of(2, 3), Gaussian.of(3, 1), Gaussian.of(-3, 1));
    Scalar mean = Mean.of(vector).Get();
    assertTrue(mean instanceof Gaussian);
    Scalar actual = Gaussian.of(Scalars.fromString("2/3"), Scalars.fromString("5/9"));
    assertEquals(mean, actual);
  }

  public void testGaussianWithQuantity() {
    Scalar gq1 = Gaussian.of( //
        Quantity.of(3, "m"), //
        Quantity.of(2, "m^2"));
    Scalar gq2 = Gaussian.of( //
        Quantity.of(-3, "m"), //
        Quantity.of(1, "m^2"));
    Scalar gq3 = gq1.add(gq2);
    Scalar ga3 = Gaussian.of( //
        Quantity.of(0, "m"), //
        Quantity.of(3, "m^2"));
    assertEquals(gq3, ga3);
    Scalar qs = Quantity.of(7, "s");
    Scalar gq4 = gq1.multiply(qs);
    Scalar ga4 = Gaussian.of( //
        Quantity.of(21, "m*s"), //
        Quantity.of(98, "m^2*s^2"));
    assertEquals(gq4, ga4);
  }

  public void testDistribution() {
    Gaussian gaussian = (Gaussian) Gaussian.of(-200, 10);
    Distribution distribution = gaussian.distribution();
    Scalar mean = Mean.of(RandomVariate.of(distribution, 20)).Get();
    assertTrue(Chop.below(3).close(mean, RealScalar.of(-200)));
  }

  public void testDistWithQuantity() {
    Gaussian gq1 = (Gaussian) Gaussian.of( //
        Quantity.of(3, "m"), //
        Quantity.of(2, "m^2"));
    Distribution distribution = gq1.distribution(); // operates on Quantity
    Scalar rand = RandomVariate.of(distribution); // produces quantity with [m]
    assertTrue(rand instanceof Quantity);
    assertEquals(Expectation.mean(distribution), Quantity.of(3, "m"));
    assertTrue(Chop._12.close( // exact would be nice
        Expectation.variance(distribution), Quantity.of(2, "m^2")));
  }

  public void testFail() {
    try {
      Gaussian.of(2, -3);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
