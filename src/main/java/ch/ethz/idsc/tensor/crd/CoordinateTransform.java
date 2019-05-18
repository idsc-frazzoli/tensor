// code by gjoel
package ch.ethz.idsc.tensor.crd;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Tensor;

public abstract class CoordinateTransform implements Function<Tensor, Tensor> {
  protected final CoordinateSystem from;
  protected final CoordinateSystem to;

  protected CoordinateTransform(CoordinateSystem from, CoordinateSystem to) {
    this.from = from;
    this.to = to;
  }

  @Override // from Function
  public Tensor apply(Tensor coords) {
    Coordinates result = protected_apply(CompatibleSystemQ.in(from).require(coords));
    return CompatibleSystemQ.in(to).require(result);
  }

  abstract Coordinates protected_apply(Coordinates coords);
}
