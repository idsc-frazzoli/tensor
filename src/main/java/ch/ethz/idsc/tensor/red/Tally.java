// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.pdf.CDF;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Tally.html">Tally</a> */
public enum Tally {
  ;
  /** @param tensor
   * @return map that assigns elements of tensor their multiplicity in tensor */
  public static Map<Tensor, Long> of(Tensor tensor) {
    return tensor.flatten(0).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }

  /** function can be used to compute
   * a histogram,
   * a cumulative distribution function, see {@link CDF}, or
   * {@link BinCounts}
   * 
   * @param vector
   * @return navigable map that assigns entries of the vector their multiplicity in the vector */
  public static NavigableMap<Tensor, Long> sorted(Tensor vector) {
    return vector.flatten(0).collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));
  }
}
