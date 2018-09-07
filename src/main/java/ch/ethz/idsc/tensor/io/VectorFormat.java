// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.VectorQ;

/** VectorFormat is a file format
 * for import and export of tensors of rank 1,
 * i.e. for tensors that satisfy {@link VectorQ}.
 * Each line corresponds to a scalar entry
 * 
 * VectorFormat is specific to the tensor library. */
public enum VectorFormat {
  ;
  /** @param tensor of rank 1
   * @return stream of strings where each string encodes a {@link Scalar}
   * @throws Exception if given tensor is not a vector */
  public static Stream<String> of(Tensor tensor) {
    return tensor.stream().map(Scalar.class::cast).map(Scalar::toString);
  }

  /** @param stream of strings where each string encodes a {@link Scalar}
   * @return vector of scalars parsed from strings in given stream */
  public static Tensor parse(Stream<String> stream) {
    return Tensor.of(stream.map(Scalars::fromString));
  }
}
