// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.File;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Pretty;
import ch.ethz.idsc.tensor.mat.Inverse;
import ch.ethz.idsc.tensor.opt.LinearProgramming;
import junit.framework.TestCase;

public class DemoTest extends TestCase {
  public void testEmpty() {
    String asd = getClass().getResource("/io/test.csv").getFile();
    File csvf = new File(asd);
    assertTrue(csvf.isFile());
    File csv = new File("src/test/resources/io/test.csv");
    assertTrue(csv.isFile());
    // ---
  }

  public void _testReadme() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { 4, 3 }, { 8, 2 } });
    System.out.println(Pretty.of(matrix));
    System.out.println(Pretty.of(Inverse.of(matrix)));
  }

  public void _testLP() {
    Tensor x = LinearProgramming.maxLessEquals( //
        Tensors.fromString("[1, 1]"), //
        Tensors.fromString("[[4, -1],[2, 1],[-5, 2]]"), //
        Tensors.fromString("[8, 7, 2]"));
    System.out.println(x);
  }
}
