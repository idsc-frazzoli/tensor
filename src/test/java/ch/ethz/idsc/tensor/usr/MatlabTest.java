// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Partition;
import ch.ethz.idsc.tensor.io.Pretty;
import junit.framework.TestCase;

public class MatlabTest extends TestCase {
  public void testExportVector() {
    Tensor tensor = Tensors.of(ZeroScalar.get(), ComplexScalar.of(3, 4));
    Pretty.of(tensor);
    // System.out.println(Pretty.of(tensor));
    // System.out.println(Dimensions.of(tensor));
    try {
      // Files.write(Paths.get("/home/datahaki/exported1d.m"), (Iterable<String>) MatlabExport.of(tensor)::iterator);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public void testExportMatrix() {
    Tensor tensor = Tensors.matrix((i, j) -> RationalScalar.of(i * 5 + j, 1), 6, 5);
    Pretty.of(tensor);
    // System.out.println(Pretty.of(tensor));
    // System.out.println(Dimensions.of(tensor));
    try {
      // Files.write(Paths.get("/home/datahaki/matexported.m"), (Iterable<String>) MatlabExport.of(tensor)::iterator);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public void testExport3D() {
    Tensor tensor = Partition.of(Tensors.matrix((i, j) -> RationalScalar.of(i * 5 + j, 1), 6, 5), 3);
    Pretty.of(tensor);
    // System.out.println(Pretty.of(tensor));
    // System.out.println(Dimensions.of(tensor));
    try {
      // Files.write(Paths.get("/home/datahaki/exported.m"), (Iterable<String>) MatlabExport.of(tensor)::iterator);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    // in matlab this is imported as 2x3x5 array
    // with
    // reshape(a(1,:,:),[3 5])
    // gives
    // 0 1 2 3 4
    // 5 6 7 8 9
    // 10 11 12 13 14
  }

  public void testCsvMatrix() {
    Tensor tensor = Tensors.matrix((i, j) -> RationalScalar.of(i * 5 + j, 1), 6, 5);
    Pretty.of(tensor);
    // System.out.println(Pretty.of(tensor));
    // System.out.println(Dimensions.of(tensor));
    try {
      // Files.write(Paths.get("/home/datahaki/exported.csv"), (Iterable<String>) CsvFormat.of(tensor)::iterator);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public void testCsvVector() {
    Tensor tensor = Tensors.vectorDouble(3.2, -3, .234, 3, Double.POSITIVE_INFINITY, 0);
    Pretty.of(tensor);
    // System.out.println(Pretty.of(tensor));
    // System.out.println(Dimensions.of(tensor));
    try {
      // Files.write(Paths.get("/home/datahaki/vecexported.csv"), (Iterable<String>) CsvFormat.of(tensor)::iterator);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
