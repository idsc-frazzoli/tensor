// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.pdf.BinCounts;
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
   * <ul>
   * <li>a histogram,
   * <li>a cumulative distribution function, see {@link CDF}, or
   * <li>{@link BinCounts}
   * </ul>
   * 
   * @param tensor
   * @return navigable map that assigns entries of the tensor their multiplicity in the tensor */
  public static NavigableMap<Tensor, Long> sorted(Tensor tensor) {
    return tensor.flatten(0).collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));
  }
}
