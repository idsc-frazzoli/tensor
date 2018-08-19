// code by jph
package ch.ethz.idsc.tensor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.function.Function;

import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.alg.VectorQ;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class TensorsTest extends TestCase {
  public void testEmpty() {
    assertEquals(Tensors.empty(), Tensors.empty());
    assertEquals(Tensors.empty(), Tensors.vector());
    assertEquals(Tensors.empty(), Tensors.of());
  }

  public void testNorm() {
    Tensor vector = Tensors.vectorLong(2, 3, 4, 5);
    assertTrue(ExactScalarQ.all(vector));
    Scalar scalar = (Scalar) vector.dot(vector);
    assertEquals(scalar, RationalScalar.of(4 + 9 + 16 + 25, 1));
    assertTrue(ExactScalarQ.of(scalar));
  }

  public void testNorm2() {
    Tensor a = Tensors.of(RationalScalar.of(2, 3), RationalScalar.of(4, 5));
    Scalar s = (Scalar) a.dot(a);
    assertEquals(s, RationalScalar.of(244, 225));
  }

  public void testNorm5() {
    int n = 6;
    int m = 12;
    Random random = new Random();
    Tensor A = Tensors.matrix((i, j) -> //
    RationalScalar.of( //
        random.nextInt(100) - 50, //
        random.nextInt(100) + 1), n, m);
    Tensor c = Tensors.vector(i -> RationalScalar.of(1, 1), n);
    assertEquals(Total.of(A), c.dot(A));
  }

  public void testInteger() {
    Tensor p = Tensors.vector(Arrays.asList(3, 4, -5, 6));
    Tensor q = Tensors.vector(3, 4, -5, 6);
    Tensor r = Tensors.vector(3.0, 4.0, -5.0, 6.0);
    assertTrue(p.Get(0) instanceof RationalScalar);
    assertTrue(p.Get(1) instanceof RationalScalar);
    assertTrue(p.Get(2) instanceof RationalScalar);
    assertTrue(p.Get(3) instanceof RationalScalar);
    assertTrue(q.Get(0) instanceof RationalScalar);
    assertTrue(q.Get(1) instanceof RationalScalar);
    assertTrue(q.Get(2) instanceof RationalScalar);
    assertTrue(q.Get(3) instanceof RationalScalar);
    assertEquals(p, q);
    assertEquals(p, r);
  }

  public void testDoubleArray() {
    double[] asd = new double[] { 3.2, -0.3, 1.0 };
    Tensors.vectorDouble(asd);
  }

  public void testDouble() {
    Tensor p = Tensors.vector(Arrays.asList(3.3, 4., 5.3, 6.));
    Tensor q = Tensors.vector(3.3, 4., 5.3, 6.);
    assertEquals(p, q);
  }

  public void testNumber() {
    Tensor p = Tensors.vector(Arrays.asList(3, 4, 5.3, 6));
    Tensor q = Tensors.vector(3, 4, 5.3, 6);
    assertTrue(p.Get(0) instanceof RationalScalar);
    assertEquals(p, q);
  }

  public void testIntArrays() {
    int[][] data = new int[][] { { 1, -2, 3 }, { 4, 9 } };
    Tensor actual = Tensors.matrixInt(data);
    Tensor expected = Tensors.fromString("{{1, -2, 3}, {4, 9}}");
    assertEquals(expected, actual);
  }

  public void testLongArrays() {
    long[][] data = new long[][] { { 1, -2, 3 }, { 4, 9 }, { 0, 0, 0, 0, 0 }, {} };
    Tensor actual = Tensors.matrixLong(data);
    Tensor expected = Tensors.fromString("{{1, -2, 3}, {4, 9},{0,0,0,0,0},{}}");
    assertEquals(expected, actual);
  }

  public void testVectorFloat() {
    float[] fvalues = { 3.1f, 4.3f, -1.89f };
    double[] dvalues = { 3.1, 4.3, -1.89 };
    assertTrue(Chop._06.close( //
        Tensors.vectorFloat(fvalues), //
        Tensors.vectorDouble(dvalues)));
  }

  public void testMatrixFloat() {
    float[][] values = { { 3.1f, 4.3f, -1.89f }, { -3.6f, 9.3f } };
    Tensor tensor = Tensors.matrixFloat(values);
    assertEquals(tensor.length(), 2);
    assertEquals(tensor.get(0).length(), 3);
    assertEquals(tensor.get(1).length(), 2);
  }

  public void testDoubleArrays() {
    double[][] data = new double[][] { { 1, -2, 3 }, { 4, 9 }, { 0, 0, 0, 0, 0 }, {} };
    Tensor actual = Tensors.matrixDouble(data);
    Tensor expected = Tensors.fromString("{{1, -2, 3}, {4, 9},{0,0,0,0,0},{}}");
    assertEquals(expected, actual);
  }

  public void testNumberArrays() {
    Number[][] data = new Number[][] { { 1, -2, 3 }, { 4, 9 }, { 0, 0, 0, 0, 0 }, {} };
    Tensor actual = Tensors.matrix(data);
    Tensor expected = Tensors.fromString("{{1, -2, 3}, {4, 9},{0,0,0,0,0},{}}");
    assertEquals(expected, actual);
  }

  public void testScalarArrays() {
    Scalar[][] data = new Scalar[][] { { RealScalar.ZERO, RealScalar.ONE }, {}, { ComplexScalar.of(2, 3) } };
    Tensor actual = Tensors.matrix(data);
    Tensor expected = Tensors.fromString("{{0, 1}, {}, {2+3*I}}");
    assertEquals(expected, actual);
  }

  public void testNCopies() {
    Tensor ncopies = Tensor.of(Collections.nCopies(6, RealScalar.of(3)).stream().map(Tensor.class::cast));
    ncopies.set(RealScalar.ZERO, 2);
    assertEquals(ncopies, Tensors.vector(3, 3, 0, 3, 3, 3));
  }

  public void testOfReferences() {
    Tensor a = Tensors.vector(1, 2, 3);
    Tensor b = Tensors.of(a);
    a.set(RealScalar.of(4), 0);
    assertEquals(b.get(0), Tensors.vector(1, 2, 3));
  }

  public void testOfComparison() {
    Tensor row = Tensors.vector(1, 2, 3);
    Tensor tensor = Tensors.of(RealScalar.of(1), row);
    tensor.set(RealScalar.of(4), 1, 1);
    assertEquals(tensor, Tensors.fromString("{1, {1, 4, 3}}"));
    assertEquals(row, Range.of(1, 4));
    Tensor vector = Tensors.of(RealScalar.of(1), Quantity.of(2, "V"));
    assertTrue(VectorQ.ofLength(vector, 2));
  }

  public void testOfTensors() {
    Function<Tensor[], Tensor> ftensors = Tensors::of;
    assertEquals(ftensors.apply(new Tensor[] {}), Tensors.empty());
    assertEquals(ftensors.apply(new Scalar[] {}), Tensors.empty());
  }

  public void testOfScalars() {
    Function<Scalar[], Tensor> fscalars = Tensors::of;
    assertEquals(fscalars.apply(new Scalar[] {}), Tensors.empty());
  }
}
