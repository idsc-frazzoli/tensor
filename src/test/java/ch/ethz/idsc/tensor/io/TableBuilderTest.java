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
    assertEquals(tableBuilder.getTable(), Tensors.empty());
  }

  public void testAppendRow() throws IOException {
    TableBuilder tableBuilder = new TableBuilder();
    tableBuilder.appendRow(RealScalar.ONE, Tensors.vector(2, 3, 4), RealScalar.of(5));
    assertEquals(tableBuilder.getRowCount(), 1);
    tableBuilder.appendRow(RealScalar.of(-2), RealScalar.of(6), Tensors.vector(0, 7));
    assertEquals(tableBuilder.getRowCount(), 2);
    tableBuilder.appendRow();
    assertEquals(tableBuilder.getRowCount(), 3);
    tableBuilder.appendRow(RealScalar.of(100));
    assertEquals(tableBuilder.getRowCount(), 4);
    tableBuilder.appendRow(Tensors.vector(1, 2, 3));
    assertEquals(tableBuilder.getTable().stream().count(), 5);
    Tensor table = tableBuilder.getTable();
    assertEquals(table, Tensors.fromString("{{1, 2, 3, 4, 5}, {-2, 6, 0, 7}, {}, {100}, {1, 2, 3}}"));
  }

  public void testModify() {
    TableBuilder tableBuilder = new TableBuilder();
    tableBuilder.appendRow(Tensors.vector(1, 2, 3, 4));
    Tensor table = tableBuilder.getTable();
    try {
      table.set(RealScalar.of(99), 0, 2);
      fail();
    } catch (Exception exception) {
      // ---
    }
    assertEquals(table.get(0), Range.of(1, 5));
  }

  public void testUnmodifiableWrapper() {
    TableBuilder tableBuilder = new TableBuilder();
    Tensor tensor = tableBuilder.getTable();
    assertEquals(tensor.length(), 0);
    tableBuilder.appendRow();
    assertEquals(tensor.length(), 1);
    tableBuilder.appendRow();
    assertEquals(tensor.length(), 2);
    assertEquals(tableBuilder.getRowCount(), 2);
  }

  public void testFail() {
    TableBuilder tableBuilder = new TableBuilder();
    assertEquals(tableBuilder.getRowCount(), 0);
    try {
      tableBuilder.appendRow((Tensor[]) null);
      fail();
    } catch (Exception exception) {
      // ---
    }
    assertEquals(tableBuilder.getRowCount(), 0);
  }
}
