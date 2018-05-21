// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.sca.Increment;
import ch.ethz.idsc.tensor.utl.Stopwatch;

enum FastInterpolationDemo {
  ;
  private static final Distribution DISTRIBUTION = UniformDistribution.unit();

  public static void main(String[] args) {
    Tensor tensor = RandomVariate.of(DISTRIBUTION, 30, 3);
    LinearInterpolation linearInterpolation = //
        (LinearInterpolation) LinearInterpolation.of(tensor);
    {
      Tensor rep1 = linearInterpolation.at(RealScalar.of(3));
      System.out.println(rep1);
      rep1.set(Increment.ONE, 1);
      Tensor rep2 = linearInterpolation.at(RealScalar.of(3));
      System.out.println(rep2);
      // System.exit(0);
    }
    System.out.println("=== DOUBLE");
    for (int count = 0; count < 10; ++count) {
      {
        Scalar a = DoubleScalar.of(4.123);
        Stopwatch stopwatch = Stopwatch.started();
        for (int index = 0; index < 50000; ++index)
          linearInterpolation.at(a);
        System.out.println("at  " + stopwatch.display_nanoSeconds());
      }
      {
        Tensor b = Tensors.vector(4.123);
        Stopwatch stopwatch = Stopwatch.started();
        for (int index = 0; index < 50000; ++index)
          linearInterpolation.get(b);
        System.out.println("get " + stopwatch.display_nanoSeconds());
      }
      {
        Scalar a = DoubleScalar.of(4.123);
        Tensor b = Tensors.vector(4.123);
        for (int index = 0; index < 50000; ++index) {
          Tensor r1 = linearInterpolation.at(a);
          Tensor r2 = linearInterpolation.get(b);
          if (!r1.equals(r2))
            System.err.println("wrong");
        }
      }
    }
    System.out.println("=== EXACT");
    for (int count = 0; count < 10; ++count) {
      {
        Scalar a = RationalScalar.of(20, 7);
        Stopwatch stopwatch = Stopwatch.started();
        for (int index = 0; index < 50000; ++index)
          linearInterpolation.at(a);
        System.out.println("at  " + stopwatch.display_nanoSeconds());
      }
      {
        Tensor b = Tensors.vector(4.123);
        Stopwatch stopwatch = Stopwatch.started();
        for (int index = 0; index < 50000; ++index)
          linearInterpolation.get(b);
        System.out.println("get " + stopwatch.display_nanoSeconds());
      }
    }
  }
}
