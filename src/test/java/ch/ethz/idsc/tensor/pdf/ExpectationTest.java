// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.function.Function;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Accumulate;
import ch.ethz.idsc.tensor.alg.Last;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class ExpectationTest extends TestCase {
  private static void _check(Distribution distribution) {
    Scalar mean = Expectation.mean(distribution);
    {
      Scalar E_X = Expectation.of(Function.identity(), distribution);
      assertTrue(Chop._12.close(E_X, mean));
    }
    {
      Scalar E_X = Expectation.of(s -> s.multiply(RealScalar.of(2)), distribution);
      assertTrue(Chop._12.close(E_X, mean.multiply(RealScalar.of(2))));
    }
    {
      Scalar E_X = Expectation.of(s -> s.multiply(RealScalar.of(2)), distribution);
      assertTrue(Chop._12.close(E_X, mean.multiply(RealScalar.of(2))));
    }
    Scalar var = Expectation.variance(distribution);
    {
      Scalar Var = Expectation.of(s -> AbsSquared.of(s.subtract(mean)), distribution);
      assertTrue(Chop._12.close(Var, var));
    }
  }

  public void testExact() {
    _check(DiscreteUniformDistribution.of(4, 10));
    _check(BernoulliDistribution.of(RationalScalar.of(2, 7)));
    _check(BinomialDistribution.of(10, RationalScalar.of(3, 7)));
    _check(HypergeometricDistribution.of(10, 40, 100));
    _check(EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(3, 2, 1, 4)));
  }

  public void testEmpiricalDistribution() {
    int upper = 200;
    Tensor unscaledPDF = RandomVariate.of(DiscreteUniformDistribution.of(0, 10000), upper);
    Clip clip = Clip.function(2, 8);
    Distribution distribution = EmpiricalDistribution.fromUnscaledPDF(unscaledPDF);
    ScalarUnaryOperator suo = clip;
    Scalar expect = Expectation.of(suo, distribution);
    Tensor accumulate = Accumulate.of(unscaledPDF);
    Scalar scale = Last.of(accumulate).Get();
    Tensor pdf = unscaledPDF.divide(scale);
    Scalar result = pdf.dot(Range.of(0, upper).map(suo)).Get();
    assertEquals(expect, result);
    assertTrue(ExactScalarQ.of(expect));
    assertTrue(ExactScalarQ.of(result));
    Scalar variance = Expectation.variance(distribution);
    assertTrue(ExactScalarQ.of(variance));
    double varDouble = variance.number().doubleValue();
    assertTrue(2500 < varDouble);
    assertTrue(varDouble < 4500);
  }

  public void testNumeric() {
    _check(PoissonDistribution.of(RationalScalar.of(4, 3)));
    _check(GeometricDistribution.of(RealScalar.of(.3)));
    _check(EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(3, .2, 1, .4)));
  }

  public void testFail() {
    try {
      Expectation.of(s -> s, NormalDistribution.standard());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail2() {
    Distribution distribution = GompertzMakehamDistribution.of(RealScalar.of(1), RealScalar.of(2));
    try {
      Expectation.mean(distribution);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
