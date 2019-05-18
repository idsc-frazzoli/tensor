// code by gjoel
package ch.ethz.idsc.tensor.crd;

import ch.ethz.idsc.tensor.Tensor;

/* package */ class UnmodifiableCoordinates extends Coordinates {
  public static UnmodifiableCoordinates of(Tensor vector) {
    return vector instanceof Coordinates ? (UnmodifiableCoordinates) vector : of(vector, CoordinateSystem.DEFAULT);
  }

  public static UnmodifiableCoordinates of (Tensor vector, String system) {
    return of(vector, CoordinateSystem.of(system));
  }

  public static UnmodifiableCoordinates of(Tensor vector, CoordinateSystem system) {
    return vector instanceof Coordinates ? (UnmodifiableCoordinates) CompatibleSystemQ.to(system).require(vector): new UnmodifiableCoordinates(vector, system);
  }

  // ---
  public UnmodifiableCoordinates(Tensor vector, CoordinateSystem system) {
    super(vector.unmodifiable(), system);
  }

  @Override
  public Tensor unmodifiable() {
    return this;
  }

  @Override
  public void set(Tensor tensor, Integer... index) {
    throw new UnsupportedOperationException("unmodifiable");
  }
}
