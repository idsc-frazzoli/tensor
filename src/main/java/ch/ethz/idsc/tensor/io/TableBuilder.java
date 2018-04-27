// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Flatten;

/** Several applications that use the tensor library export tables as
 * CSV files for analysis, and visualization in Mathematica, or Excel.
 * 
 * <p>The applications typically concatenate scalars, vectors, or even
 * matrices into a single row. Careful tracking of indices allows to
 * reconstruct the information in Mathematica, or Excel.
 * 
 * <p>The purpose of {@link TableBuilder} is to facilitate the process.
 * The function {@link #appendRow(Tensor...)} flattens the given tensors
 * into a vector. The vector is appended as a new row.
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
 * {@link Tensors#empty()} with subsequent {@link Tensor#append(Tensor)} */
public final class TableBuilder {
  /** LinkedList was found to be the faster than ArrayDeque */
  private final Deque<Tensor> deque = new LinkedList<>();

  /** entries of given tensors are flattened into a vector,
   * which is appended as a row to the table.
   * 
   * @param tensors */
  public void appendRow(Tensor... tensors) {
    deque.add(Flatten.of(tensors));
  }

  /** function name inspired by TableModel::getRowCount
   * 
   * @return number of rows */
  public int getRowCount() {
    return deque.size();
  }

  /** @return unmodifiable tensor with rows as entries */
  public Tensor toTable() {
    return Tensor.of(deque.stream()).unmodifiable();
  }

  /** @return stream of references to rows */
  public Stream<Tensor> stream() {
    return deque.stream().map(Tensor::unmodifiable);
  }
}
