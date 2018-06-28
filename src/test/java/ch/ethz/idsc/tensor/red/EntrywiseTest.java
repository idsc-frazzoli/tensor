// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import junit.framework.TestCase;

public class EntrywiseTest extends TestCase {
  public void testMax() {
    Entrywise entrywise = Entrywise.with(Max::of);
    Tensor result = entrywise.of( //
        Tensors.vector(3, 2, 3), Tensors.vector(-2, 1, 4), Tensors.vector(-3, 4, 0));
    assertEquals(result, Tensors.vector(3, 4, 4));
  }

  public void testHelpOf() {
    assertEquals(Entrywise.with(Max::of).of(Tensors.vector(1, 2, 3), Tensors.vector(5, 0, 4)), Tensors.vector(5, 2, 4));
    assertEquals(Entrywise.with(Min::of).of(Tensors.vector(1, 2, 3), Tensors.vector(5, 0, 4)), Tensors.vector(1, 0, 3));
  }

  public void testStreamReduce() {
    Tensor box = Tensors.fromString("{{0,7}, {0,8}, {1,5}, {2,7}}");
    Tensor max = box.stream().reduce(Entrywise.max()).get();
    Tensor min = box.stream().reduce(Entrywise.min()).get();
    assertEquals(max, Tensors.vector(2, 8));
    assertEquals(min, Tensors.vector(0, 5));
  }

  public void testMaxSimple() {
    Entrywise entrywise = Entrywise.max();
    Tensor result = entrywise.apply( //
        Tensors.vector(3, 2, 3), Tensors.vector(-2, 1, 4));
    assertEquals(result, Tensors.vector(3, 2, 4));
  }

  public void testMinSimple() {
    Entrywise entrywise = Entrywise.min();
    Tensor result = entrywise.apply( //
        Tensors.vector(3, 2, 3), Tensors.vector(-2, 1, 4));
    assertEquals(result, Tensors.vector(-2, 1, 3));
  }

  public void testMaxScalar() {
    Entrywise entrywise = Entrywise.max();
    Tensor result = entrywise.apply( //
        RealScalar.of(3), RealScalar.of(5));
    assertEquals(result, RealScalar.of(5));
  }

  public void testMinScalar() {
    Entrywise entrywise = Entrywise.min();
    Tensor result = entrywise.apply( //
        RealScalar.of(3), RealScalar.of(5));
    assertEquals(result, RealScalar.of(3));
  }

  public void testSingle() {
    Tensor single = Tensors.vector(3, 2, 3);
    Tensor copy = single.copy();
    Entrywise entrywise = Entrywise.with(Max::of);
    Tensor result = entrywise.of(single);
    result.set(RealScalar.ZERO, 0);
    assertEquals(single, copy);
  }

  public void testAdd() {
    Distribution distribution = UniformDistribution.unit();
    Tensor a = RandomVariate.of(distribution, 7, 9);
    Tensor b = RandomVariate.of(distribution, 7, 9);
    Tensor c = RandomVariate.of(distribution, 7, 9);
    Tensor res = Entrywise.with(Scalar::add).of(a, b, c);
    assertEquals(res, a.add(b).add(c));
  }

  public void testMultiply() {
    Distribution distribution = UniformDistribution.unit();
    Tensor a = RandomVariate.of(distribution, 7, 9);
    Tensor b = RandomVariate.of(distribution, 7, 9);
    Tensor c = RandomVariate.of(distribution, 7, 9);
    Tensor res = Entrywise.with(Scalar::multiply).of(a, b, c);
    assertEquals(res, a.pmul(b).pmul(c));
  }

  public void testEmpty() {
    Entrywise entrywise = Entrywise.with(Max::of);
    try {
      entrywise.of();
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    Entrywise entrywise = Entrywise.max();
    try {
      entrywise.apply(Tensors.vector(3, 2, 3), Tensors.vector(-2, 1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarTensorFail() {
    Entrywise entrywise = Entrywise.max();
    try {
      entrywise.apply(Tensors.vector(3, 2, 3), RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      entrywise.apply(RealScalar.ONE, Tensors.vector(3, 2, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail() {
    try {
      Entrywise.with(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
