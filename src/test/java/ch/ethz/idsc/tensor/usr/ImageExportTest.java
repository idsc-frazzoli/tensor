// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.File;

import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Export;
import junit.framework.TestCase;

public class ImageExportTest extends TestCase {
  private static final String ROOT = "/home/datahaki/";

  public void testDummy() {
    // ---
  }

  public static void _im1() throws Exception {
    int n = 251;
    Export.of(new File(ROOT, "image.png"), Tensors.matrix((i, j) -> GaussScalar.of(i * j, n), n, n));
  }

  public static void _im2() throws Exception {
    int n = 251;
    Export.of(new File(ROOT, "image2.png"), Tensors.matrix((i, j) -> //
    Tensors.of(RealScalar.of(i), RealScalar.of(j), GaussScalar.of(i + 2 * j, n), GaussScalar.of(i * j, n)), n, n));
  }

  public static void main(String[] args) throws Exception {
    _im1();
    _im2();
  }
}
