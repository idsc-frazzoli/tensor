// code by jph
package ch.ethz.idsc.tensor.io.ext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.MatrixQ;
import junit.framework.TestCase;

public class WavefrontFormatTest extends TestCase {
  public void testSimple() throws IOException {
    String string = getClass().getResource("/io/ext/blender0.obj").getPath();
    Wavefront wavefront = WavefrontFormat.parse(Files.lines(Paths.get(string)));
    assertEquals(wavefront.objects().size(), 2);
    assertEquals(wavefront.objects().get(0).name(), "Cylinder");
    assertEquals(wavefront.objects().get(1).name(), "Cube");
    assertEquals(Dimensions.of(wavefront.vertices()), Arrays.asList(72, 3));
    assertEquals(Dimensions.of(wavefront.normals()), Arrays.asList(40, 3));
    {
      WavefrontObject wavefrontObject = wavefront.objects().get(0);
      List<Integer> list = wavefrontObject.faces().flatten(0) //
          .map(Tensor::length).distinct().collect(Collectors.toList());
      // contains quads and top/bottom polygon
      assertEquals(list, Arrays.asList(4, 32));
      // System.out.println(Pretty.of(wo.faces()));
      // System.out.println(Pretty.of(wo.normals()));
      assertTrue(MatrixQ.of(wavefront.normals()));
    }
  }
}
