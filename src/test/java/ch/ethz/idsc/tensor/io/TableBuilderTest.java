// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.IOException;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Range;
import junit.framework.TestCase;

public class TableBuilderTest extends TestCase {
  public void testEmpty() {
    TableBuilder tableBuilder = new TableBuilder();
    assertEquals(tableBuilder.getRowCount(), 0);
    assertEquals(tableBuilder.toTable(), Tensors.empty());
  }

  public void testAppendRow() throws IOException {
    TableBuilder tb = new TableBuilder();
    tb.appendRow(RealScalar.ONE, Tensors.vector(2, 3, 4), RealScalar.of(5));
    assertEquals(tb.getRowCount(), 1);
    tb.appendRow(RealScalar.of(-2), RealScalar.of(6), Tensors.vector(0, 7));
    assertEquals(tb.getRowCount(), 2);
    tb.appendRow();
    assertEquals(tb.getRowCount(), 3);
    tb.appendRow(RealScalar.of(100));
    assertEquals(tb.getRowCount(), 4);
    tb.appendRow(Tensors.vector(1, 2, 3));
    assertEquals(tb.stream().count(), 5);
    Tensor matrix = tb.toTable();
    assertEquals(matrix, Tensors.fromString("{{1,2,3,4,5},{-2,6,0,7},{},{100},{1,2,3}}"));
  }

  public void testModify() {
    TableBuilder tb = new TableBuilder();
    tb.appendRow(Tensors.vector(1, 2, 3, 4));
    Tensor t1 = tb.toTable();
    try {
      t1.set(RealScalar.of(99), 0, 2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    assertEquals(tb.toTable().get(0), Range.of(1, 5));
  }

  public void testFail() {
    TableBuilder tableBuilder = new TableBuilder();
    try {
      tableBuilder.appendRow((Tensor[]) null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
