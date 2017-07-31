// code by jph
package ch.ethz.idsc.tensor.usr;

import junit.framework.TestCase;

//The value of 1.0 / (1.0 / x) will differ from x by no more than 1 unit in the last place.
class DoubleInversion extends TestCase {
  public static double invertTwice(double value) {
    double inverse = 1.0 / value;
    return 1.0 / inverse;
  }

  private static void assertClosed(double value) {
    assertEquals(value, invertTwice(value));
  }

  public static void main(String[] args) {
    // double eps = Math.nextUp(0.0);
    // System.out.println(eps);
    System.out.println("max exponent = " + Double.MAX_EXPONENT);
    System.out.println("min exponent = " + Double.MIN_EXPONENT);
    System.out.println("max value  = " + Double.MAX_VALUE);
    System.out.println("min value  = " + Double.MIN_VALUE);
    System.out.println("min normal = " + Double.MIN_NORMAL);
    // ---
    System.out.println("closed under inversion");
    System.out.println(1 / Double.MIN_NORMAL);
    System.out.println(1 / Double.MAX_VALUE);
    // ---
    // double eps =
    {
      double value = Double.MIN_NORMAL;
      for (int c = 0; c < 1000; ++c) {
        assertClosed(value);
        //
        value = Math.nextDown(value);
        // System.out.println(value);
      }
    }
    {
      double value = 1.0 / Double.MAX_VALUE;
      System.out.println("truemin=" + value);
      // assertClosed(value);
      double inv = 1.0 / value;
      System.out.println(inv);
    }
    {
      double inf = Math.nextUp(Double.MAX_VALUE);
      assertEquals(inf, Double.POSITIVE_INFINITY);
      // gives infinity
      // System.out.println(max);
    }
    {
      // double asd = invertTwice(1E-323);
      System.out.println("HERE" + (1.0 / 1E-310));
    }
    // double below = Math.nextDown(Double.MIN_NORMAL);
    // System.out.println(1/below);
  }
}
