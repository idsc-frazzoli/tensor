// code by gjoel
package ch.ethz.idsc.tensor.crd;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.VectorQ;

public class Coordinates implements Tensor {
  public static Coordinates of(Tensor vector) {
    return vector instanceof Coordinates ? (Coordinates) vector : of(vector, CoordinateSystem.DEFAULT);
  }

  public static Coordinates of(Tensor vector, CoordinateSystem system) {
    return vector instanceof Coordinates ? CompatibleSystemQ.in(system).require(vector): new Coordinates(vector, system);
  }

  // ---
  private final Tensor vector;
  private final CoordinateSystem system;

  protected Coordinates(Tensor vector, CoordinateSystem system) {
    this.vector = VectorQ.require(vector);
    this.system = system;
  }

  public Tensor vector() {
    return vector.unmodifiable();
  }

  public CoordinateSystem system() {
    return system;
  }

  @Override
  public Tensor unmodifiable() {
    return new UnmodifiableCoordinates(vector, system);
  }

  @Override
  public Tensor copy() {
    return this;
  }

  @Override
  public Tensor get(Integer... index) {
    return vector.get(index);
  }

  @Override
  public Scalar Get(Integer... index) {
    return vector.Get(index);
  }

  @Override
  public Tensor get(List<Integer> index) {
    return vector.get(index);
  }

  @Override
  public void set(Tensor tensor, Integer... index) {
    if (ScalarQ.of(tensor) && index.length == 1)
      vector.set(tensor, index[0]);
    else
      throw TensorRuntimeException.of(tensor, this);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends Tensor> void set(Function<T, ? extends Tensor> function, Integer... index) {
    set(function.apply((T) Get(index)), index);
  }

  @Override
  public Tensor append(Tensor tensor) {
    throw new UnsupportedOperationException("unmodifiable");
  }

  @Override
  public int length() {
    return vector.length();
  }

  @Override
  public Stream<Tensor> stream() {
    return vector.stream();
  }

  @Override
  public Stream<Tensor> flatten(int level) {
    if (level == 0)
      return stream();
    throw new IndexOutOfBoundsException();
  }

  @Override
  public Tensor extract(int fromIndex, int toIndex) {
    return vector.extract(fromIndex, toIndex);
  }

  @Override
  public Tensor block(List<Integer> fromIndex, List<Integer> dimensions) {
    return vector.block(fromIndex, dimensions);
  }

  @Override
  public Tensor negate() {
    return new Coordinates(vector.negate(), system);
  }

  @Override
  public Tensor add(Tensor tensor) {
    return doIfAllowed(tensor, vector::add);
  }

  @Override
  public Tensor subtract(Tensor tensor) {
    return doIfAllowed(tensor, vector::subtract);
  }

  @Override
  public Tensor pmul(Tensor tensor) {
    return doIfAllowed(tensor, vector::pmul);
  }

  @Override
  public Tensor dot(Tensor tensor)  {
    return doIfAllowed(tensor, vector::dot);
  }

  protected Coordinates doIfAllowed(Tensor coords, Function<Tensor, Tensor> function) {
    if (CompatibleSystemQ.to(this).with(coords))
      return new Coordinates(function.apply(((Coordinates) coords).vector()), system);
    throw TensorRuntimeException.of(coords, this);
  }

  @Override
  public Tensor multiply(Scalar scalar)  {
    return new Coordinates(vector.multiply(scalar), system);
  }

  @Override
  public Tensor divide(Scalar scalar)  {
    return new Coordinates(vector.divide(scalar), system);
  }

  @Override
  public Tensor map(Function<Scalar, ? extends Tensor> function) {
    return new Coordinates(vector.map(function), system);
  }

  @Override // from Iterable
  public Iterator<Tensor> iterator() {
    return vector.iterator();
  }

  @Override // from Object
  public boolean equals(Object obj) {
    return obj instanceof Coordinates && ((Coordinates) obj).vector().equals(vector) && ((Coordinates) obj).system().equals(system);
  }
}
