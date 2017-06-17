// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import junit.framework.TestCase;

public class PDFTest extends TestCase {
  public void testSimple() {
    PDF.of(ExponentialDistribution.of(RationalScalar.of(3, 2)));
  }
}
