// code by jph
package ch.ethz.idsc.tensor.io.obj;

import java.util.StringTokenizer;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/* package */ enum StaticHelper {
  ;
  static Tensor three(String string) {
    StringTokenizer stringTokenizer = new StringTokenizer(string);
    return Tensors.of( //
        Scalars.fromString(stringTokenizer.nextToken()), //
        Scalars.fromString(stringTokenizer.nextToken()), //
        Scalars.fromString(stringTokenizer.nextToken()));
  }
}
