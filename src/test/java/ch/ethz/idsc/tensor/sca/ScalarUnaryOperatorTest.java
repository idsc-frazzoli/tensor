// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

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
    ArcTanh.of(tensor);
    Ceiling.of(tensor);
    Chop._12.of(tensor);
    try {
      Clip.UNIT.apply(tensor);
    } catch (Exception exception) {
      // ---
    }
    Cos.of(tensor);
    Exp.of(tensor);
    Floor.of(tensor);
    Log.of(tensor);
    LogisticSigmoid.of(tensor);
    N.of(tensor);
    Power.of(tensor, RealScalar.of(.3));
    Power.of(tensor, RealScalar.of(-.3));
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

  public void testSimple() {
    _checkOps(DoubleScalar.INDETERMINATE);
    _checkOps(DoubleScalar.POSITIVE_INFINITY);
    _checkOps(DoubleScalar.NEGATIVE_INFINITY);
  }
}
