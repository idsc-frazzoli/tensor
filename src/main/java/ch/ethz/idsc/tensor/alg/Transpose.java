// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.mat.ConjugateTranspose;
import ch.ethz.idsc.tensor.sca.Conjugate;

/** Transpose is consistent with Mathematica::Transpose
 * 
 * <p>Transpose does <b>not</b> conjugate the elements.
 * For that purpose, use {@link Conjugate}, or {@link ConjugateTranspose}.
 * 
 * <p>related to MATLAB::permute
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Transpose.html">Transpose</a> */
public enum Transpose {
  ;
  /** function is used to transpose a matrix
   * 
   * Remark:
   * if the input tensor is a matrix, function Transpose.of(tensor)
   * is identical albeit faster than Transpose.of(tensor, 1, 0).
   * 
   * <p>The function also operates on matrices with tensors as entries. Example:
   * <pre>
   * Transpose.of({{1, {2, 2}}, {{3}, 4}, {5, {6}}}) == {{1, {3}, 5}, {{2, 2}, 4, {6}}}
   * </pre>
   * 
   * <p>Special cases that are consistent with Mathematica:
   * <pre>
   * Transpose[{{},{}}] == {}
   * Transpose[{}] throws an Exception
   * </pre>
   * 
   * @param tensor with rank at least 2
   * @return tensor with the two first dimensions transposed and the remaining dimensions left as-is
   * @throws Exception if input is a vector or scalar */
  public static Tensor of(Tensor tensor) {
    int length = Unprotect.dimension1(tensor);
    if (length == Scalar.LENGTH)
      throw TensorRuntimeException.of(tensor);
    return Tensors.vector(i -> tensor.get(Tensor.ALL, i), length);
  }

  /** transpose according to permutation sigma.
   * function conforms to Mathematica::Transpose except for scalar input:
   * <pre>
   * Tensor::Transpose[scalar, {}] == scalar
   * Mathematica::Transpose[scalar, {}] is undefined
   * </pre>
   * 
   * @param tensor with array structure
   * @param sigma is a permutation with sigma.length == rank of tensor
   * @return */
  public static Tensor of(Tensor tensor, Integer... sigma) {
    if (ScalarQ.of(tensor) && sigma.length == 0)
      return tensor;
    if (!ArrayQ.ofRank(tensor, sigma.length))
      throw TensorRuntimeException.of(tensor);
    List<Integer> dims = Dimensions.of(tensor);
    int[] size = new int[dims.size()];
    IntStream.range(0, size.length).forEach(index -> size[index] = dims.get(index));
    Size mySize = Size.of(size);
    Size tensorSize = mySize.permute(sigma);
    Tensor data = Tensor.of(tensor.flatten(-1));
    int[] inverse = new int[sigma.length];
    IntStream.range(0, sigma.length).forEach(index -> inverse[sigma[index]] = index);
    List<Tensor> list = new ArrayList<>();
    for (MultiIndex src : tensorSize)
      list.add(data.Get(mySize.indexOf(src.permute(inverse))));
    Integer[] tsize = new Integer[sigma.length]; // int[] to Integer[]
    IntStream.range(0, sigma.length).forEach(index -> tsize[index] = tensorSize.size(index));
    return ArrayReshape.of(list.stream(), tsize);
  }

  /** generalization of {@link #of(Tensor, Integer...)} as function
   * only requires that tensor has array structure up to sigma.length
   * 
   * {@link #nonArray(Tensor, Integer...)} is typically a bit slower than
   * {@link #of(Tensor, Integer...)}
   * 
   * @param tensor with array structure up to sigma.length
   * @param sigma is a permutation
   * @return
   * @throws Exception */
  public static Tensor nonArray(Tensor tensor, Integer... sigma) {
    Tensor _sigma = Tensors.vector(sigma);
    if (!Sort.of(_sigma).equals(Range.of(0, sigma.length)))
      throw TensorRuntimeException.of(_sigma); // sigma does not encode a permutation
    return Array.of(list -> tensor.get(permute(list, sigma)), //
        inverse(Dimensions.of(tensor), _sigma));
  }

  // helper function
  private static List<Integer> inverse(List<Integer> list, Tensor sigma) {
    return IntStream.of(Ordering.INCREASING.of(sigma)) //
        .mapToObj(list::get).collect(Collectors.toList());
  }

  // helper function
  private static List<Integer> permute(List<Integer> list, Integer... sigma) {
    return Stream.of(sigma).map(list::get).collect(Collectors.toList());
  }
}
