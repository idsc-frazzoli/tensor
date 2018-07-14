// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Partition;
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

  public void testNonRect() throws Exception {
    Tensor s = Tensors.empty();
    s.append(Tensors.of(StringScalar.of("ksah   g d fkhjg")));
    s.append(Tensors.vector(1, 2, 3));
    s.append(Tensors.vector(7));
    s.append(Tensors.of(StringScalar.of("kddd")));
    s.append(Tensors.vector(5, 6));
    Stream<String> stream = CsvFormat.of(s);
    Tensor r = CsvFormat.parse(stream);
    assertEquals(s, r);
    Tensor p = Tensors.fromString(s.toString());
    assertEquals(r, p);
    Tensor ten = Serialization.parse(Serialization.of(s));
    assertEquals(s, ten);
    assertEquals(s, Serialization.copy(s));
  }

  public void testParse() {
    Tensor t = CsvFormat.parse(Arrays.asList("10, 200, 3", "", "78", "-3, 2.3").stream());
    Tensor r = Tensors.fromString("{{10, 200, 3}, {}, {78}, {-3, 2.3}}");
    assertEquals(t, r);
    assertEquals(t.toString(), r.toString());
  }

  public void testParse2() {
    Tensor t = CsvFormat.parse(Arrays.asList("10, {200, 3}", "78", "-3, 2.3").stream());
    Tensor r = Tensors.fromString("{{10, {200, 3}}, {78}, {-3, 2.3}}");
    assertEquals(t, r);
    assertEquals(t.toString(), r.toString());
  }

  public void testSpacing() {
    Tensor r = Tensors.fromString("{{10, {200, 3}}, {},  {78}, {-3, 2.3, 1-3*I}}");
    List<String> list = CsvFormat.of(r).collect(Collectors.toList());
    assertEquals(list.get(0), "10,{200, 3}");
    assertEquals(list.get(1), "");
    assertEquals(list.get(2), "78");
    assertEquals(list.get(3), "-3,2.3,1-3*I");
  }

  public void testVector() {
    Tensor r = Tensors.fromString("{123,456}");
    List<String> list = CsvFormat.of(r).collect(Collectors.toList());
    Tensor s = CsvFormat.parse(list.stream()); // [[123], [456]]
    assertEquals(Partition.of(r, 1), s);
  }

  public void testScalar() {
    Tensor r = Scalars.fromString("123");
    List<String> list = CsvFormat.of(r).collect(Collectors.toList());
    Tensor s = CsvFormat.parse(list.stream());
    assertEquals(Tensors.of(Tensors.of(r)), s);
  }

  public void testLibreofficeCalcFile() throws Exception {
    InputStream inputStream = getClass().getResource("/io/libreoffice_calc.csv").openStream();
    Stream<String> stream = ImportHelper.lines(inputStream);
    Tensor table = CsvFormat.parse(stream);
    assertEquals(Dimensions.of(table), Arrays.asList(4, 2));
  }

  public void testMatlabFile() throws Exception {
    InputStream inputStream = getClass().getResource("/io/matlab_3x5.csv").openStream();
    Stream<String> stream = ImportHelper.lines(inputStream);
    Tensor table = CsvFormat.parse(stream);
    assertEquals(Dimensions.of(table), Arrays.asList(3, 5));
  }

  public void testGeditFile() throws Exception {
    InputStream inputStream = getClass().getResource("/io/gedit_mixed.csv").openStream();
    Stream<String> stream = ImportHelper.lines(inputStream);
    Tensor table = CsvFormat.parse(stream);
    assertEquals(table, Tensors.fromString("{{hello, blub}, {1, 4.22}, {-3, 0.323, asdf}, {}, {2, 1.223}, {3+8*I, 12, 33}}"));
  }

  public void testStrict() {
    Tensor matrix = Tensors.of(Tensors.of( //
        StringScalar.of("PUT"), //
        RationalScalar.of(1, 2), //
        RationalScalar.of(5, 1), //
        DoubleScalar.of(1.25)));
    Tensor strict = matrix.map(CsvFormat.strict());
    assertEquals(strict.toString(), "{{\"PUT\", 0.5, 5, 1.25}}");
  }

  public void testStringWithComma() {
    Tensor row = Tensors.of(StringTensor.vector("123", "[ , ]", "a"));
    Stream<String> stream = CsvFormat.of(row);
    List<String> list = stream.collect(Collectors.toList());
    assertEquals(list.size(), 1); // only 1 row
    assertEquals(list.get(0), "123,[ , ],a");
  }

  public void testStringStrict() {
    Tensor row = Tensors.of(StringTensor.vector("123", "[ , ]", "a"));
    Stream<String> stream = CsvFormat.of(row.map(CsvFormat.strict()));
    List<String> list = stream.collect(Collectors.toList());
    assertEquals(list.size(), 1); // only 1 row
    assertEquals(list.get(0), "\"123\",\"[ , ]\",\"a\"");
  }

  public void testVectorWithComma() {
    Tensor row = StringTensor.vector(" 2  ,  3 ", "[ , ]", "` ;  ;  ,   ;`");
    Stream<String> stream = CsvFormat.of(row);
    List<String> list = stream.collect(Collectors.toList());
    assertEquals(list.size(), 3); // 3 rows
    assertEquals(list.get(0), " 2  ,  3 ");
    assertEquals(list.get(1), "[ , ]");
    assertEquals(list.get(2), "` ;  ;  ,   ;`");
  }
}
