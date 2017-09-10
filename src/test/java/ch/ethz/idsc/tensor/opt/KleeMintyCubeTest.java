// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

public class KleeMintyCubeTest extends TestCase {
  public void callKlee(int n) {
    KleeMintyCube kmc = new KleeMintyCube(n);
    Tensor x = LinearProgramming.maxLessEquals(kmc.c, kmc.m, kmc.b);
    // System.out.println("---");
    // kmc.show();
    assertEquals(x, kmc.x);
  }

  // numeric test
  public void callKleeN(int n) {
    KleeMintyCube kmc = new KleeMintyCube(n);
    Tensor x = LinearProgramming.maxLessEquals(N.DOUBLE.of(kmc.c), N.DOUBLE.of(kmc.m), N.DOUBLE.of(kmc.b));
    assertEquals(x, kmc.x);
  }

  public void testKleeMinty() {
    for (int n = 1; n <= 10; ++n) {
      callKlee(n);
      callKleeN(n);
    }
  }

  public static void main(String[] args) {
    KleeMintyCube kmc = new KleeMintyCube(3);
    kmc.show();
    Tensor x = LinearProgramming.maxLessEquals(kmc.c, kmc.m, kmc.b);
    System.out.println("LP" + x + " <- solution");
  }
}
