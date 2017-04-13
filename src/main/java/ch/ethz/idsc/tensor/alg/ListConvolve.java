// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Total;

/** One application of {@link ListConvolve} is the computation of the coefficients
 * of the product of two polynomials.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ListConvolve.html">ListConvolve</a> */
public enum ListConvolve {
  ;
  // ---
  public static Tensor of(Tensor kernel, Tensor tensor) {
    List<Integer> mask = Dimensions.of(kernel);
    List<Integer> size = Dimensions.of(tensor);
    List<Integer> dims = new ArrayList<>();
    for (int index = 0; index < mask.size(); ++index)
      dims.add(size.get(index) - mask.get(index) + 1);
    return Array.of(ofs -> Total.of(Flatten.of( //
        kernel.pmul(_extract(Reverse::of, tensor, ofs, mask)), -1)), dims);
  }

  // helper function: multi-level extract
  // TODO extraction can be done more efficiently
  /* package */ static Tensor _extract(Function<Tensor, Tensor> function, Tensor tensor, List<Integer> ofs, List<Integer> mask) {
    final int rank = ofs.size();
    Integer[] sigma = new Integer[rank]; // [r, 0, 1, 2, ..., r - 1]
    IntStream.range(0, rank).forEach(i -> sigma[i] = (i - 1 + rank) % rank);
    for (int index = 0; index < rank; ++index)
      tensor = Transpose.of(function.apply( //
          tensor.extract(ofs.get(index), ofs.get(index) + mask.get(index))), sigma);
    return tensor;
  }
}
