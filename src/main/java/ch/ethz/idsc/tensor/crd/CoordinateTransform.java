// code by gjoel
package ch.ethz.idsc.tensor.crd;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;

public abstract class CoordinateTransform implements TensorUnaryOperator {
  protected final TensorUnaryOperator tensorUnaryOperator;
  protected final CoordinateSystem from;
  protected final CoordinateSystem to;

  protected CoordinateTransform(TensorUnaryOperator tensorUnaryOperator, CoordinateSystem from, CoordinateSystem to) {
    this.tensorUnaryOperator = tensorUnaryOperator;
    this.from = from;
    this.to = to;
  }

  public CoordinateSystem from() {
    return from;
  }

  public CoordinateSystem to() {
    return to;
  }

  public CoordinateTransform inverse() {
    return new CoordinateTransform(inverseTensorUnaryOperator(), to, from) {
      @Override
      protected TensorUnaryOperator inverseTensorUnaryOperator() {
        return tensorUnaryOperator;
      }
    };
  }

  public CoordinateTransform leftMultiply(CoordinateTransform transform) {
    return transform.rightMultiply(this);
  }

  public CoordinateTransform rightMultiply(CoordinateTransform transform) {
    TensorUnaryOperator operator = coords -> transform.apply(apply(coords));
    return new CoordinateTransform(operator, from, transform.to) {
      @Override
      protected TensorUnaryOperator inverseTensorUnaryOperator() {
        return coords -> inverse().apply(transform.inverse().apply(coords));
      }
    };
  }

  @Override // from TensorUnaryOperator
  public Tensor apply(Tensor coords) {
    Coordinates result = (Coordinates) tensorUnaryOperator.apply(CompatibleSystemQ.to(from).require(coords));
    return CompatibleSystemQ.to(to).require(result);
  }

  protected abstract TensorUnaryOperator inverseTensorUnaryOperator();
}
