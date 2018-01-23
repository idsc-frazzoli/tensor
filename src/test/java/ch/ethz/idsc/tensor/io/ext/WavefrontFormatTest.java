// code by jph
package ch.ethz.idsc.tensor.io.ext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.MatrixQ;
import ch.ethz.idsc.tensor.red.Max;
import junit.framework.TestCase;

public class WavefrontFormatTest extends TestCase {
  public void testSimple() throws IOException {
    String string = getClass().getResource("/io/ext/blender0.obj").getPath();
    Wavefront wavefront = WavefrontFormat.parse(Files.lines(Paths.get(string)));
    assertEquals(wavefront.objects().size(), 2);
    assertEquals(wavefront.objects().get(0).name(), "Cylinder");
    assertEquals(wavefront.objects().get(1).name(), "Cube");
    assertEquals(Dimensions.of(wavefront.vertices()), Arrays.asList(72, 3));
    assertTrue(MatrixQ.of(wavefront.normals()));
    assertEquals(Dimensions.of(wavefront.normals()), Arrays.asList(40, 3));
    List<WavefrontObject> objects = wavefront.objects();
    assertEquals(objects.size(), 2);
    {
      WavefrontObject wavefrontObject = objects.get(0);
      List<Integer> list = wavefrontObject.faces().flatten(0) //
          .map(Tensor::length).distinct().collect(Collectors.toList());
      // contains quads and top/bottom polygon
      assertEquals(list, Arrays.asList(4, 32));
      Tensor normals = wavefrontObject.normals();
      Tensor faces = wavefrontObject.faces();
      normals.add(faces); // test if tensors have identical structure
      assertTrue(ExactScalarQ.all(faces));
      assertTrue(ExactScalarQ.all(normals));
    }
    {
      WavefrontObject wavefrontObject = objects.get(1);
      List<Integer> list = wavefrontObject.faces().flatten(0) //
          .map(Tensor::length).distinct().collect(Collectors.toList());
      assertEquals(list, Arrays.asList(4));
      assertTrue(MatrixQ.of(wavefront.normals()));
      Tensor normals = wavefrontObject.normals();
      Tensor faces = wavefrontObject.faces();
      normals.add(faces); // test if tensors have identical structure
      Scalar index_max = normals.flatten(-1).reduce(Max::of).get().Get();
      assertEquals(index_max.number().intValue() + 1, wavefront.normals().length());
    }
  }
}