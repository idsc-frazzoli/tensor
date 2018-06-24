// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

/** the purpose of the test is to demonstrate that
 * none of the special input cases: NaN, Infty
 * result in a stack overflow error when provided to the
 * scalar unary operators */
public class ScalarUnaryOperatorTest extends TestCase {
  static void _checkOps(Scalar tensor) {
    try {
      Abs.of(tensor);
    } catch (Exception exception) {
      // ---
    }
    try {
      Arg.of(tensor);
    } catch (Exception exception) {
      // ---
    }
    try {
      ArcCos.of(tensor);
    } catch (Exception exception) {
      // ---
    }
    try {
      ArcSin.of(tensor);
    } catch (Exception exception) {
      // ---
    }
    ArcTan.of(tensor);
    try {
      ArcTanh.of(tensor);
    } catch (Exception exception) {
      // ---
    }
    Ceiling.of(tensor);
    Chop._12.of(tensor);
    try {
      Clip.unit().apply(tensor);
    } catch (Exception exception) {
      // ---
    }
    Cos.of(tensor);
    Exp.of(tensor);
    Floor.of(tensor);
    try {
      Log.of(tensor);
    } catch (Exception exception) {
      // ---
    }
    LogisticSigmoid.of(tensor);
    N.DOUBLE.of(tensor);
    try {
      Power.of(tensor, RealScalar.of(.3));
    } catch (Exception exception) {
      // ---
    }
    try {
      Power.of(tensor, RealScalar.of(-.3));
    } catch (Exception exception) {
      // ---
    }
    Power.of(tensor, RealScalar.ZERO);
    try {
      Ramp.of(tensor);
    } catch (Exception exception) {
      // ---
    }
    Round.of(tensor);
    try {
      Sign.of(tensor);
    } catch (Exception exception) {
      // ---
    }
    Sin.of(tensor);
    try {
      Sinc.of(tensor);
    } catch (Exception exception) {
      // ---
    }
    Sinh.of(tensor);
    try {
      Sqrt.of(tensor);
    } catch (Exception exception) {
      // ---
    }
    Tan.of(tensor);
    Tanh.of(tensor);
    try {
      UnitStep.of(tensor);
    } catch (Exception exception) {
      // ---
    }
    // ---
    try {
      Scalars.compare(tensor, RealScalar.ONE);
    } catch (Exception exception) {
      // ---
    }
    // ---
    tensor.map(Decrement.ONE);
    tensor.map(Increment.ONE);
  }

  public void testTrinity() {
    _checkOps(DoubleScalar.INDETERMINATE);
    _checkOps(DoubleScalar.POSITIVE_INFINITY);
    _checkOps(DoubleScalar.NEGATIVE_INFINITY);
  }
}
