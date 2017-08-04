// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

/** default threshold in Mathematica is 1e-10:
 * Chop[1.000*^-10] != 0
 * Chop[0.999*^-10] == 0
 * 
 * symbolic expressions are not chopped:
 * Chop[1/1000000000000000] != 0, but
 * Chop[1/1000000000000000.] == 0
 * 
 * unlike in Mathematica, the tensor library does not predefine a default threshold
 * for each use of chop, the application has to specify the threshold
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Chop.html">Chop</a> */
public class Chop implements ScalarUnaryOperator {
  public static final Chop _05 = below(1e-05);
  public static final Chop _06 = below(1e-06);
  public static final Chop _07 = below(1e-07);
  public static final Chop _08 = below(1e-08);
  public static final Chop _09 = below(1e-09);
  /** default threshold for numerical truncation to 0 in Mathematica:
   * strictly below 1e-10 */
  public static final Chop _10 = below(1e-10);
  public static final Chop _11 = below(1e-11);
  public static final Chop _12 = below(1e-12);
  public static final Chop _13 = below(1e-13);
  public static final Chop _14 = below(1e-14);
  public static final Chop _15 = below(1e-15);
  public static final Chop _16 = below(1e-16);
  public static final Chop _17 = below(1e-17);
  public static final Chop _18 = below(1e-18);
  public static final Chop _19 = below(1e-19);
  public static final Chop _20 = below(1e-20);
  public static final Chop _30 = below(1e-30);
  public static final Chop _40 = below(1e-40);
  public static final Chop _50 = below(1e-50);
  /** NONE is the identity {@link ScalarUnaryOperator}
   * NONE is used to check for <em>exact</em> zero content:
   * Chop.NONE.allZero(tensor) */
  public static final Chop NONE = below(0);

  /** @param threshold
   * @return function that performs the chop operation at given threshold */
  public static Chop below(double threshold) {
    return new Chop(threshold);
  }

  private final double threshold;

  private Chop(double threshold) {
    if (threshold < 0)
      throw new RuntimeException();
    this.threshold = threshold;
  }

  /** @return non-negative numeric threshold defining an open interval
   * (-threshold, threshold) in which values are mapped to 0 */
  public double threshold() {
    return threshold;
  }

  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof ChopInterface) {
      ChopInterface chopInterface = (ChopInterface) scalar;
      return chopInterface.chop(this);
    }
    return scalar;
  }

  /** @param lhs
   * @param rhs
   * @return true, if difference between lhs and rhs is entry-wise below chop threshold
   * @throws Exception if difference of lhs and rhs cannot be computed,
   * for example due to different dimensions */
  public boolean close(Tensor lhs, Tensor rhs) {
    return allZero(lhs.subtract(rhs));
  }

  /** @param tensor
   * @return true, if all entries of Chop.of(tensor) equal to {@link Scalar#zero()} */
  public boolean allZero(Tensor tensor) {
    return !tensor.flatten(-1) //
        .map(Scalar.class::cast) //
        .map(this) //
        .anyMatch(Scalars::nonZero);
  }

  /** @param tensor
   * @return */
  @SuppressWarnings("unchecked")
  public <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(this);
  }
}
