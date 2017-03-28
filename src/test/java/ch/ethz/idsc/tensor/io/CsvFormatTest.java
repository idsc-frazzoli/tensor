// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.StringScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class CsvFormatTest extends TestCase {
  private static void convertCheck(Tensor A) {
    assertEquals(A, CsvFormat.parse(CsvFormat.of(A)));
  }

  public void testCsvR() {
    Random r = new Random();
    convertCheck( //
        Tensors.matrix((i, j) -> RationalScalar.of(r.nextInt(100) - 50, r.nextInt(100) + 1), 20, 4));
    convertCheck(Tensors.matrix((i, j) -> DoubleScalar.of(r.nextGaussian() * 1e-50), 20, 10));
    convertCheck(Tensors.matrix((i, j) -> DoubleScalar.of(r.nextGaussian() * 1e+50), 20, 10));
  }

  public void testNonRect() {
    Tensor s = Tensors.empty();
    s.append(Tensors.of(StringScalar.of("ksah   g d fkhjg")));
    s.append(Tensors.vectorInt(1, 2, 3));
    s.append(Tensors.vectorInt(7));
    s.append(Tensors.of(StringScalar.of("kddd")));
    s.append(Tensors.vectorInt(5, 6));
    Stream<String> stream = CsvFormat.of(s);
    Tensor r = CsvFormat.parse(stream);
    assertEquals(s, r);
    Tensor p = Tensors.fromString(s.toString());
    assertEquals(r, p);
    Tensor ten = ObjectFormat.from(ObjectFormat.of(s));
    assertEquals(s, ten);
  }

  public void testParse() {
    Tensor t = CsvFormat.parse(Arrays.asList("10, 200, 3","", "78", "-3, 2.3").stream());
    Tensor r = Tensors.fromString("[[10, 200, 3], [], [78], [-3, 2.3]]");
    assertEquals(t, r);
    assertEquals(t.toString(), r.toString());
  }

  public void testParse2() {
    Tensor t = CsvFormat.parse(Arrays.asList("10, [200, 3]", "78", "-3, 2.3").stream());
    Tensor r = Tensors.fromString("[[10, [200, 3]], [78], [-3, 2.3]]");
    assertEquals(t, r);
    assertEquals(t.toString(), r.toString());
  }
}
