// code by jph
package ch.ethz.idsc.tensor;

/** auxiliary functions used in {@link ComplexScalarImpl} */
/* package */ enum ComplexHelper {
  ;
  /** complex division with emphasis on numerical accuracy
   * 
   * @param n_re not instance of {@link ComplexScalar}
   * @param n_im not instance of {@link ComplexScalar}
   * @param d_re not instance of {@link ComplexScalar}
   * @param d_im not instance of {@link ComplexScalar}
   * @return (n_re + n_im * I) / (d_re + d_im * I) */
  static Scalar division(Scalar n_re, Scalar n_im, Scalar d_re, Scalar d_im) {
    if (Scalars.isZero(d_im))
      return ComplexScalarImpl.of(n_re.divide(d_re), n_im.divide(d_re));
    final Scalar res_re1;
    final Scalar res_im1;
    if (Scalars.isZero(d_re)) {
      res_re1 = n_re.multiply(d_re);
      res_im1 = n_im.multiply(d_re);
    } else {
      Scalar r1 = c_dcd(d_re, d_im);
      res_re1 = n_re.divide(r1);
      res_im1 = n_im.divide(r1);
    }
    Scalar r2 = c_dcd(d_im, d_re);
    Scalar res_re2 = n_im.divide(r2);
    Scalar res_im2 = n_re.divide(r2).negate();
    return ComplexScalarImpl.of(res_re1.add(res_re2), res_im1.add(res_im2));
  }

  /** function is motivated by the expression c / (c^2 + d^2)
   * for c != 0 the term simplifies to 1 / (c + d^2 / c)
   * the function computes the denominator c + d^2 / c == c + d / c * d
   * 
   * @param c non-zero
   * @param d
   * @return c + d / c * d */
  static Scalar c_dcd(Scalar c, Scalar d) {
    return c.add(d.divide(c).multiply(d));
  }
}
