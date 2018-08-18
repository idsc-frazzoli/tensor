// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/* package */ enum CsvHelper implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static final Scalar EMPTY = StringScalar.of("\"\"");

  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof StringScalar)
      return wrap(scalar);
    if (scalar instanceof RationalScalar)
      return IntegerQ.of(scalar) ? scalar : N.DOUBLE.apply(scalar);
    if (scalar instanceof DecimalScalar)
      return N.DOUBLE.apply(scalar);
    if (scalar instanceof ComplexScalar)
      throw TensorRuntimeException.of(scalar);
    if (scalar instanceof Quantity)
      throw TensorRuntimeException.of(scalar);
    return scalar;
  }

  /* package */ static Scalar wrap(Scalar scalar) {
    String string = scalar.toString();
    if (string.isEmpty())
      return EMPTY;
    int e = string.length() - 1;
    if (string.charAt(0) == '\"' && string.charAt(e) == '\"') {
      requireQuotesFree(string.substring(1, e));
      return scalar;
    }
    return StringScalar.of("\"" + requireQuotesFree(string) + "\"");
  }

  /* package */ static String requireQuotesFree(String string) {
    int index = string.indexOf('\"');
    if (0 <= index)
      throw new RuntimeException(string);
    return string;
  }
}
