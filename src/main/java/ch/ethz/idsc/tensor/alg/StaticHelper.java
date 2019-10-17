// code by jph
package ch.ethz.idsc.tensor.alg;

/* package */ enum StaticHelper {
  ;
  /** Example:
   * Permute[{ 2, 3, 4 }, { 2, 0, 1 }] == {3, 4, 2}
   * 
   * @param size
   * @param sigma
   * @return */
  static int[] permute(int[] size, int[] sigma) {
    int[] dims = new int[size.length];
    for (int index = 0; index < size.length; ++index)
      dims[sigma[index]] = size[index];
    return dims;
  }
}
