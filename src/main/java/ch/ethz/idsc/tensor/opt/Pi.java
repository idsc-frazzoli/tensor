// https://en.wikipedia.org/wiki/Chudnovsky_algorithm
// adapted by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Sqrt;

public enum Pi {
  ;
  private static final Scalar _545140134 = RealScalar.of(545140134);
  private static final Scalar _262537412640768000 = RealScalar.of(-262537412640768000L);
  private static final Scalar _12 = RealScalar.of(12);
  private static final Scalar _426880 = RealScalar.of(426880);
  private static final Scalar _16 = RealScalar.of(16);

  /** @param precision is approximately the number of correct digits in the decimal encoding
   * @return */
  public static Scalar in(int precision) {
    Scalar K = RealScalar.of(6);
    Scalar M = RealScalar.ONE;
    Scalar L = RealScalar.of(13591409);
    Scalar X = RealScalar.ONE;
    Scalar S = RealScalar.of(13591409);
    int k = 0;
    Scalar root = Sqrt.of(DecimalScalar.of("10005", precision));
    Scalar prev = RealScalar.ZERO;
    while (true) {
      ++k;
      M = Power.of(K, 3).subtract(_16.multiply(K)).multiply(M).divide(Power.of(k, 3));
      L = L.add(_545140134);
      X = X.multiply(_262537412640768000);
      S = S.add(M.multiply(L).divide(X));
      K = K.add(_12);
      Scalar next = _426880.divide(S).multiply(root);
      if (next.equals(prev))
        return next;
      prev = next;
    }
  }
}
