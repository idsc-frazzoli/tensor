// code by jph
// adapted from the Internet
package ch.ethz.idsc.tensor;

/** @see GaussScalar */
/* package */ class ExtendedGcd {
  private final long a;
  private final long b;
  public final long x;
  public final long y;
  public final long gcd;

  public ExtendedGcd(long a, long b) {
    this.a = a;
    this.b = b;
    if (a == 0) {
      x = 0;
      y = 1;
      gcd = b;
    } else {
      ExtendedGcd extendedGcd = new ExtendedGcd(b % a, a);
      x = extendedGcd.y - (b / a) * extendedGcd.x;
      y = extendedGcd.x;
      gcd = extendedGcd.gcd;
    }
  }

  /* package */ boolean isConsistent() {
    return a * x + b * y == gcd;
  }
}
