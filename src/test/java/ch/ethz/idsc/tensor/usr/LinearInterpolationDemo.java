// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.io.Timing;
import ch.ethz.idsc.tensor.opt.Interpolation;
import ch.ethz.idsc.tensor.opt.LinearInterpolation;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;

/** DEMO IS OBSOLETE SINCE LinearInterpolation
 * INTERNALLY USES UNPROTECT */
/* package */ enum LinearInterpolationDemo {
  ;
  private static final Distribution DISTRIBUTION = UniformDistribution.unit();

  private static long time(Tensor tensor) {
    Interpolation interpolation = LinearInterpolation.of(tensor);
    Timing timing = Timing.started();
    for (int count = 1; count < 20000; ++count) {
      interpolation.get(RandomVariate.of(DISTRIBUTION, 3));
    }
    timing.stop();
    return timing.nanoSeconds();
  }

  public static void main(String[] args) {
    Tensor tensor = RandomVariate.of(DISTRIBUTION, 10, 10, 10);
    {
      System.out.println("unprot " + time(Unprotect.references(tensor)));
      System.out.println("normal " + time(tensor));
      System.out.println("unprot " + time(Unprotect.references(tensor)));
      System.out.println("normal " + time(tensor));
      System.out.println("unprot " + time(Unprotect.references(tensor)));
      System.out.println("normal " + time(tensor));
      System.out.println("unprot " + time(Unprotect.references(tensor)));
      System.out.println("normal " + time(tensor));
    }
  }
}
