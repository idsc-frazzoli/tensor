// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;

/** Transpose is consistent with Mathematica::Transpose
 * Transpose is consistent with MATALB::permute
 * <br/>
 * Transpose does <b>not</b> conjugate the elements.
 * <br/>
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Transpose.html">Transpose</a> */
public class Transpose {
  /** @param matrix
   * @return matrix transposed */
  public static Tensor of(Tensor matrix) {
    return of(matrix, 1, 0);
  }

  /** transpose according to permutation sigma.
   * function conforms to Mathematica::Transpose
   * 
   * Warning: different convention from MATLAB::permute !
   * 
   * @param tensor with array structure
   * @param sigma is permutation with rank of tensor == sigma.length
   * @return */
  public static Tensor of(Tensor tensor, int... sigma) { // TODO consider making Integer...
    if (!Dimensions.isArray(tensor))
      throw new IllegalArgumentException();
    if (TensorRank.of(tensor) != sigma.length)
      throw new IllegalArgumentException();
    // ---
    List<Integer> dims = Dimensions.of(tensor);
    int[] size = new int[dims.size()];
    for (int index = 0; index < size.length; ++index)
      size[index] = dims.get(index);
    Size mySize = new Size(size);
    Size myTensorSize = mySize.permute(sigma);
    Tensor data = Tensor.of(tensor.flatten(-1));
    int[] inverse = new int[sigma.length];
    for (int index = 0; index < sigma.length; ++index)
      inverse[sigma[index]] = index;
    List<Tensor> list = new ArrayList<>();
    for (MultiIndex src : myTensorSize)
      list.add(data.Get(mySize.indexOf(src.permute(inverse))));
    Tensor transpose = Tensor.of(list.stream());
    for (int index = myTensorSize.size.length - 1; 0 < index; --index)
      transpose = Partition.of(transpose, myTensorSize.size[index]);
    return transpose;
  }
}
