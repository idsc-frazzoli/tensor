// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.ArrayQ;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Flatten;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.qty.Quantity;

/** The tensor library does not differentiate between column- and row-vectors.
 * Vectors, i.e. tensors or rank 1, are exported to MATLAB as column vectors, i.e.
 * their size in MATLAB is of the form [n, 1].
 * 
 * <p>String expressions of certain Scalar types, for instance those of
 * {@link DecimalScalar}, or {@link Quantity} may not be parsed correctly by MATLAB.
 * The user can provide a customized scalar-to-string mapping to facilitate the desired
 * import of these values.
 * 
 * <p>Scalars of type {@link Quantity} also require a custom conversion to string.
 * The tests contains an example.
 * 
 * <p>Hint:
 * for the export of vectors and matrices, {@link Pretty} may also be a solution. */
public enum MatlabExport {
  ;
  /** @param tensor with array structure
   * @param function that maps a {@link Scalar} in the given tensor to a string expression
   * @return lines of MATLAB function that returns tensor
   * @throws Exception if given tensor does not satisfy {@link ArrayQ} */
  public static Stream<String> of(Tensor tensor, Function<Scalar, String> function) {
    ArrayQ.require(tensor);
    List<String> list = new LinkedList<>();
    list.add("function a=anonymous");
    list.add("% auto-generated code. do not modify.");
    list.add("Infinity=Inf;");
    list.add("I=i;");
    if (ScalarQ.of(tensor))
      list.add("a=" + function.apply(tensor.Get()) + ";");
    else {
      List<Integer> dims = Dimensions.of(tensor);
      Integer[] sigma = new Integer[dims.size()];
      IntStream.range(0, dims.size()).forEach(i -> sigma[i] = dims.size() - i - 1);
      if (dims.size() == 1)
        dims.add(1); // [n, 1]
      list.add("a=zeros(" + dims + ");");
      int count = 0;
      for (Tensor _scalar : Flatten.of(Transpose.of(tensor, sigma))) {
        ++count;
        Scalar scalar = _scalar.Get();
        if (Scalars.nonZero(scalar))
          list.add("a(" + count + ")=" + function.apply(scalar) + ";");
      }
    }
    return list.stream();
  }

  /** Hint: when exporting the strings to a file use {@link Export}
   * and specify a file with extension "m"
   * 
   * @param tensor with array structure
   * @return lines of MATLAB function that returns tensor
   * @throws Exception if given tensor does not satisfy {@link ArrayQ}
   * @see Export */
  public static Stream<String> of(Tensor tensor) {
    return of(tensor, Object::toString);
  }
}
