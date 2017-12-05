// code by jph and gjoel
package ch.ethz.idsc.tensor.pdf;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import ch.ethz.idsc.tensor.io.Serialization;

/* package */ enum StaticHelper {
  ;
  static int hashCode(Serializable serializable) {
    try {
      return Arrays.hashCode(Serialization.of(serializable));
    } catch (IOException exception) {
      throw new RuntimeException(exception.getMessage());
    }
  }

  static boolean equals(Serializable s1, Serializable s2) {
    try {
      return Arrays.equals(Serialization.of(s1), Serialization.of(s2));
    } catch (IOException exception) {
      throw new RuntimeException(exception.getMessage());
    }
  }
}
