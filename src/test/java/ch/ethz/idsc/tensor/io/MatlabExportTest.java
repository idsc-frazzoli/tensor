// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class MatlabExportTest extends TestCase {
  public void testScalar() {
    Stream<String> stream = MatlabExport.of(ComplexScalar.of(2, 3));
    List<String> list = stream.collect(Collectors.toList());
    assertTrue(list.contains("a=2+3*I;"));
    // list.forEach(System.out::println);
  }

  public void testScalar2() {
    Scalar s = Scalars.fromString("-1/41+73333/12*I");
    Stream<String> stream = MatlabExport.of(s);
    List<String> list = stream.collect(Collectors.toList());
    assertTrue(list.contains("a=-1/41+73333/12*I;"));
  }

  public void testVector() {
    Stream<String> stream = MatlabExport.of(Tensors.vector(1, 2, 3));
    List<String> list = stream.collect(Collectors.toList());
    assertTrue(list.contains("a=zeros([3, 1]);"));
    // list.forEach(System.out::println);
  }

  public void testMatrix() {
    Tensor m = HilbertMatrix.of(3, 4);
    Stream<String> stream = MatlabExport.of(m.add(m.multiply(ComplexScalar.I)));
    List<String> list = stream.collect(Collectors.toList());
    assertTrue(list.contains("a=zeros([3, 4]);"));
  }

  public void testLieAlgebras() {
    Tensor m = LieAlgebras.so3();
    Stream<String> stream = MatlabExport.of(m);
    List<String> list = stream.collect(Collectors.toList());
    assertTrue(list.contains("a=zeros([3, 3, 3]);"));
    assertTrue(list.size() < 12);
    // list.forEach(System.out::println);
  }

  public void testUnits() {
    ScalarUnaryOperator scalarUnaryOperator = //
        scalar -> scalar instanceof Quantity //
            ? ((Quantity) scalar).value()
            : scalar;
    Tensor m = Tensors.fromString("{{1[m],2[s^-1],0[r]},{3,4[N],7[rad]}}");
    Stream<String> stream = MatlabExport.of(m, sc -> scalarUnaryOperator.apply(sc).toString());
    List<String> list = stream.collect(Collectors.toList());
    assertTrue(list.contains("a=zeros([2, 3]);"));
    // list.forEach(System.out::println);
    assertTrue(list.contains("a(1)=1;"));
    assertTrue(list.contains("a(2)=3;"));
    assertTrue(list.contains("a(3)=2;"));
    assertTrue(list.contains("a(4)=4;"));
    assertTrue(list.contains("a(6)=7;"));
    assertFalse(list.stream().anyMatch(s -> s.startsWith("a(5)=")));
  }

  public void testFail() {
    Tensor tensor = Tensors.fromString("{{1,2},{3,4,5}}");
    try {
      MatlabExport.of(tensor);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
