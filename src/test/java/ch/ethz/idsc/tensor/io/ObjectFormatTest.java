// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.usr.TestFile;
import junit.framework.TestCase;

public class ObjectFormatTest extends TestCase {
  public void testSome() throws Exception {
    Tensor inp = Tensors.fromString("{1, {2, 3, {4.3}}, 1}");
    byte[] bytes = ObjectFormat.of(inp);
    Tensor ten = ObjectFormat.parse(bytes);
    assertEquals(inp, ten);
  }

  public void testNull() throws Exception {
    final Object put = null;
    byte[] bytes = ObjectFormat.of(put);
    Object get = ObjectFormat.parse(bytes);
    assertEquals(put, get);
    assertNull(get);
  }

  public void testUnderClear() throws ClassNotFoundException, IOException, DataFormatException {
    Scalar q1 = Quantity.of(ComplexScalar.of(RationalScalar.of(2, 7), RationalScalar.HALF.negate()), "m");
    Scalar q2 = Quantity.of(ComplexScalar.of(-1, 7), "m");
    Scalar quc = q1.under(q2);
    Scalar expected = Scalars.fromString("-742/65+294/65*I");
    assertEquals(quc, expected);
    assertEquals(Serialization.copy(quc), expected);
    assertTrue(quc instanceof ComplexScalar);
    byte[] bytes = ObjectFormat.of(quc);
    Scalar copy = ObjectFormat.parse(bytes);
    assertEquals(copy, expected);
    Scalar cdq = q2.divide(q1);
    assertTrue(cdq instanceof ComplexScalar);
    Scalar qrc = q1.reciprocal().multiply(q2);
    assertTrue(qrc instanceof ComplexScalar);
    assertEquals(quc, qrc);
    assertEquals(quc, cdq);
    ExactScalarQ.require(quc);
  }

  public void testUnderMix() throws ClassNotFoundException, IOException, DataFormatException {
    Scalar q1 = Quantity.of(ComplexScalar.of(RationalScalar.of(2, 7), RationalScalar.HALF.negate()), "CHF");
    Scalar q2 = Quantity.of(ComplexScalar.of(-1, 7), "m");
    Scalar quc = q1.under(q2);
    Scalar expected = Scalars.fromString("-742/65+294/65*I[m*CHF^-1]");
    assertEquals(quc, expected);
    assertEquals(Serialization.copy(quc), expected);
    assertTrue(quc instanceof Quantity);
    byte[] bytes = ObjectFormat.of(quc);
    Scalar copy = ObjectFormat.parse(bytes);
    assertEquals(copy, expected);
    Scalar cdq = q2.divide(q1);
    assertTrue(cdq instanceof Quantity);
    Scalar qrc = q1.reciprocal().multiply(q2);
    assertTrue(qrc instanceof Quantity);
    assertEquals(quc, qrc);
    assertEquals(quc, cdq);
    ExactScalarQ.require(quc);
  }

  public void testExportImportObject() throws IOException, ClassNotFoundException, DataFormatException {
    Tensor tensor = HilbertMatrix.of(3, 4);
    File file = TestFile.withExtension("random");
    Export.object(file, tensor);
    assertTrue(file.isFile());
    assertEquals(Import.object(file), tensor);
    assertTrue(file.delete());
  }
}
