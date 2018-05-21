// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.utl.Stopwatch;

/** DEMO IS OBSOLETE SINCE LinearInterpolation
 * INTERNALLY USES UNPROTECT */
enum LinearInterpolationDemo {
  ;
  private static final Distribution DISTRIBUTION = UniformDistribution.unit();

  private static long time(Tensor tensor) {
    Interpolation interpolation = LinearInterpolation.of(tensor);
    Stopwatch stopwatch = Stopwatch.started();
    for (int c = 1; c < 20000; ++c) {
      interpolation.get(RandomVariate.of(DISTRIBUTION, 3));
    }
    stopwatch.stop();
    return stopwatch.display_nanoSeconds();
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
