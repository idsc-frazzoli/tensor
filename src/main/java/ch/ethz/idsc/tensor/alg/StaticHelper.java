// code by jph
package ch.ethz.idsc.tensor.alg;

/* package */ enum StaticHelper {
  ;
  static int[] static_permute(int[] size, int[] sigma) {
    int[] dims = new int[size.length];
    for (int index = 0; index < size.length; ++index)
      dims[sigma[index]] = size[index];
    return dims;
  }
}
