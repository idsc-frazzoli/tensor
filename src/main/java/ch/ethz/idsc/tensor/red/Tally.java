// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.pdf.BinCounts;
import ch.ethz.idsc.tensor.pdf.CDF;

/** consistent with Mathematica:
 * Tally requires a list as input. Tally of a scalar, for example
 * Mathematica::Tally[100] gives an error.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Tally.html">Tally</a> */
public enum Tally {
  ;
  /** Hint: the keys in the map are references to the elements in the provided tensor.
   * This is a feature and not a bug.
   * 
   * @param tensor
   * @return map that assigns elements of tensor their multiplicity in tensor
   * @throws Exception if given tensor is a {@link Scalar} */
  public static Map<Tensor, Long> of(Tensor tensor) {
    return tensor.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }

  /** Hint: the keys in the map are references to the elements in the provided tensor.
   * This is a feature and not a bug.
   * 
   * function can be used to compute
   * <ul>
   * <li>a histogram,
   * <li>a cumulative distribution function, see {@link CDF}, or
   * <li>{@link BinCounts}
   * </ul>
   * 
   * @param tensor
   * @return navigable map that assigns entries of the tensor their multiplicity in the tensor
   * @throws Exception if given tensor is a {@link Scalar} */
  public static NavigableMap<Tensor, Long> sorted(Tensor tensor) {
    return tensor.stream().collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));
  }
}
