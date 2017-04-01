// code by jph
package ch.ethz.idsc.tensor;

// TODO name is preliminary
/* package */ abstract class BasicScalar extends AbstractScalar {
  @Override // from Scalar
  public Scalar conjugate() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public Scalar abs() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public Scalar absSquared() {
    // possible default implementation:
    // Scalar abs = abs(); // <-
    // return abs.multiply(abs);
    // possible default implementation:
    // return multiply(conjugate()); // <- this may be inconsistent with implementation of abs()
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public Number number() {
    throw TensorRuntimeException.of(this);
  }
}
