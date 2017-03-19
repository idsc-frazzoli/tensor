// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.MathematicaFormat;

class MathematicaTest {
  public static void main(String[] args) {
    {
      int n = 20;
      Random random = new Random();
      Tensor a = Tensors.matrix((i, j) -> //
      random.nextInt(3) == 0 ? //
          DoubleScalar.of(random.nextDouble()) : //
          RationalScalar.of(random.nextLong(), random.nextLong()), n, n);
      // System.out.println(toMathematica(a).count());
      // toMathematica(a).forEach(System.out::println);
      try {
        Files.write(Paths.get("/home/datahaki/fromjava"), (Iterable<String>) MathematicaFormat.of(a)::iterator);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    {
      try {
        Stream<String> stream = Files.lines(Paths.get("/home/datahaki/fromjava"));
        Tensor a = MathematicaFormat.parse(stream);
        System.out.println(a);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
