// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class ScalarUnaryOperatorTest extends TestCase {
  static void _checkOps(Scalar tensor) {
    Abs.of(tensor);
    Arg.of(tensor);
    // ArcCos.of(tensor); // TODO FAIL
    // ArcSin.of(tensor);
    // ArcTan.of(tensor);
    // ArcTanh.of(tensor);
    Ceiling.of(tensor);
    Chop.of(tensor);
    Clip.UNIT.apply(tensor);
    Cos.of(tensor);
    Exp.of(tensor);
    Floor.of(tensor);
    Log.of(tensor);
    LogisticSigmoid.of(tensor);
    N.of(tensor);
    Power.of(tensor, RealScalar.of(.3));
    Power.of(tensor, RealScalar.of(-.3));
    Power.of(tensor, RealScalar.ZERO);
    Ramp.of(tensor);
    Round.of(tensor);
    Sign.of(tensor);
    Sin.of(tensor);
    Sinc.of(tensor);
    Sinh.of(tensor);
    Sqrt.of(tensor);
    Tan.of(tensor);
    Tanh.of(tensor);
    UnitStep.of(tensor);
    // ---
    tensor.map(Decrement.ONE);
    tensor.map(Increment.ONE);
  }

  public void testSimple() {
    _checkOps(RealScalar.INDETERMINATE);
    _checkOps(RealScalar.POSITIVE_INFINITY);
    _checkOps(RealScalar.NEGATIVE_INFINITY);
  }
}
