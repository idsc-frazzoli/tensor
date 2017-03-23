// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Norm.html">Norm</a> */
public enum Norm {
  _1(new RankAdapter<RealScalar>() {
    @Override
    public RealScalar ofScalar(Scalar scalar) {
      return (RealScalar) scalar.abs();
    }

    // |a_1| + ... + |a_n|
    // also known as ManhattanDistance
    @Override
    public RealScalar ofVector(Tensor vector) {
      return (RealScalar) vector.flatten(0) //
          .map(Scalar.class::cast) //
          .map(Scalar::abs) //
          .reduce(Scalar::add) //
          .orElse(ZeroScalar.get());
    }

    @Override
    public RealScalar ofMatrix(Tensor matrix) {
      return Transpose.of(matrix).flatten(0) //
          .map(this::ofVector) //
          .reduce(RealScalar::max) //
          .orElse(ZeroScalar.get());
    }
  }), //
  _2(new RankAdapter<RealScalar>() {
    @Override
    public RealScalar ofScalar(Scalar scalar) {
      return (RealScalar) scalar.abs();
    }

    @Override
    public RealScalar ofVector(Tensor vector) {
      // TODO use hypot if 2 doubles
      return (RealScalar) Sqrt.of(_2squared.of(vector));
    }

    @Override
    public RealScalar ofMatrix(Tensor matrix) {
      // return w.flatten(0).map(RealScalar.class::cast).reduce(RealScalar::max).orElse(ZeroScalar.get());
      throw new UnsupportedOperationException();
    }
  }), //
  _2squared(new RankAdapter<RealScalar>() {
    @Override
    public RealScalar ofScalar(Scalar scalar) {
      return (RealScalar) scalar.absSquared();
    }

    @Override
    public RealScalar ofVector(Tensor vector) {
      return (RealScalar) vector.flatten(0) //
          .map(Scalar.class::cast) //
          .map(Scalar::absSquared) //
          .reduce(Scalar::add) //
          .orElse(ZeroScalar.get());
    }

    @Override
    public RealScalar ofMatrix(Tensor matrix) {
      throw new UnsupportedOperationException();
    }
  }), //
  frobenius(new RankAdapter<RealScalar>() {
    @Override
    public RealScalar ofOther(Tensor tensor) {
      return _2.of(Tensor.of(tensor.flatten(-1)));
    }
  }), //
  inf(new RankAdapter<RealScalar>() {
    @Override
    public RealScalar ofScalar(Scalar scalar) {
      return (RealScalar) scalar.abs();
    }

    // max(|a_1|, ..., |a_n|)
    @Override
    public RealScalar ofVector(Tensor vector) {
      return vector.flatten(0) //
          .map(Scalar.class::cast) //
          .map(Scalar::abs) //
          .map(RealScalar.class::cast) //
          .reduce(RealScalar::max) //
          .orElse(ZeroScalar.get());
    }

    @Override
    public RealScalar ofMatrix(Tensor matrix) {
      return ofVector(Tensor.of(matrix.flatten(0).map(_1::of)));
    }
  });
  private final RankAdapter<RealScalar> rankAdapter;

  private Norm(RankAdapter<RealScalar> rankAdapter) {
    this.rankAdapter = rankAdapter;
  }

  public RealScalar of(Tensor tensor) {
    return rankAdapter.of(tensor);
  }
}
