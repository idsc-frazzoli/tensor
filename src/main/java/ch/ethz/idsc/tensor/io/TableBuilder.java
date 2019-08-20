// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.Serializable;
import java.util.LinkedList;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.Flatten;

/** Several applications require to export tables as CSV files for analysis,
 * and visualization in Mathematica, or Excel. The applications typically
 * concatenate scalars, vectors, or even matrices into a single row. Careful
 * tracking of indices allows to reconstruct the information in Mathematica,
 * or Excel.
 * 
 * <p>The purpose of {@link TableBuilder} is to facilitate the building of a
 * table, i.e. a tensor of rank 2. The function {@link #appendRow(Tensor...)}
 * flattens the given tensors into a vector. The vector is appended as a new
 * row. The number of elements in each row are not required to be the same.
 * 
 * <p>Example:
 * <pre>
 * TableBuilder tb = new TableBuilder();
 * tb.appendRow(1, {4, 3}, 7);
 * tb.appendRow(3, {9, 8}, {0});
 * tb.appendRow({2, 5}, 6);
 * tb.toTable() == {{1, 4, 3, 7}, {3, 9, 8, 0}, {2, 5, 6}}
 * </pre>
 * 
 * <p>For export of the table to a CSV file use
 * <pre>
 * Export.of(new File("file.csv"), tb.toTable().map(CsvFormat.strict()));
 * </pre>
 * 
 * <p>The name TableBuilder was inspired by {@link StringBuilder}.
 * 
 * <p>Due to the use of a LinkedList, TableBuilder is typically faster than
 * {@link Tensors#empty()} with subsequent {@link Tensor#append(Tensor)}.
 * 
 * <p>The resulting table is <em>not</em> required to be regular, i.e.
 * a matrix. The rows may differ in length. */
public final class TableBuilder implements Serializable {
  /** The type LinkedList was found to be the faster than ArrayDeque */
  private final Tensor tensor = Unprotect.using(new LinkedList<>());

  /** entries of given tensors are flattened into a vector,
   * which is appended as a row to the table.
   * 
   * @param tensors */
  public void appendRow(Tensor... tensors) {
    tensor.append(Flatten.of(tensors));
  }

  /** function name inspired by TableModel::getRowCount
   * 
   * @return number of rows */
  public int getRowCount() {
    return tensor.length();
  }

  /** Hint: Since the tensor is backed by a linked list, extractions
   * with {@link Tensor#get(Integer...)} are inefficient!
   * 
   * Hint: Although the return value is unmodifiable, subsequent changes
   * to the table builder affect the return value!
   *
   * <pre>
   * TableBuilder tableBuilder = new TableBuilder();
   * Tensor tensor = tableBuilder.getTable();
   * assertEquals(tensor.length(), 0);
   * tableBuilder.appendRow();
   * assertEquals(tensor.length(), 1);
   * </pre>
   * 
   * @return unmodifiable tensor with rows of table builder as entries. */
  public Tensor getTable() {
    return tensor.unmodifiable();
  }
}
