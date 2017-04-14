// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.red.ArgMin;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.N;

/** result of optimization problem is typically
 * 1) approximate, and
 * 2) available only in numerical precision
 * 3) non-optimal for rare special inputs
 * 
 * implementation based on
 * "Weiszfeld’s Method: Old and New Results"
 * by [Amir Beck, Shoham Sabach] */
public class FermatWeberProblem {
  private final Tensor tensor;
  private Tensor point;
  private double tolerance = 1e-10;
  private int iteration = 0;

  /** @param tensor of anchor points */
  public FermatWeberProblem(Tensor tensor) {
    this.tensor = tensor.unmodifiable();
    point = N.of(Mean.of(tensor)); // initial value
  }

  /** @param tolerance below which iteration aborts */
  public void setTolerance(double tolerance) {
    this.tolerance = tolerance;
  }

  /** @param point initial value to start iteration
   * default is <code>N.of(Mean.of(tensor))</code> */
  public void setPoint(Tensor point) {
    this.point = point.copy();
  }

  /** iteration based on Endre Vaszonyi Weiszfeld
   * 
   * @return point that minimizes total distance to anchor points */
  public Tensor weiszfeld() {
    while (true) {
      Tensor next = weiszfeldStep();
      ++iteration;
      double delta = Norm._2.of(point.subtract(next)).number().doubleValue();
      point = next;
      if (delta <= tolerance)
        return point;
    }
  }

  private Tensor weiszfeldStep() {
    Tensor dist = Tensor.of(tensor.flatten(0).map(anchor -> Norm._2.of(anchor.subtract(point))));
    int index = ArgMin.of(dist);
    if (dist.Get(index).equals(ZeroScalar.get()))
      return point.copy();
    Tensor distinv = dist.map(Scalar::invert);
    return distinv.dot(tensor).multiply(Total.of(distinv).Get().invert());
  }

  /** @return number of iterations performed by weiszfeld method until tolerance was reached */
  public int getIterationCount() {
    return iteration;
  }
}
