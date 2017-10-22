// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.Serializable;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.ArgMin;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.N;

/** iterative method to find solution to Fermat-Weber Problem
 * 
 * result of optimization is typically
 * 1) approximate, and
 * 2) available only in numerical precision
 * 3) non-optimal for rare special inputs
 * 
 * implementation based on
 * "Weiszfeld’s Method: Old and New Results"
 * by [Amir Beck, Shoham Sabach] */
public class FermatWeberProblem implements Serializable {
  private final Tensor tensor;
  private Tensor point;
  private Tensor weights;
  private double tolerance = 1e-10;
  private int iteration = 0;
  private int iteration_max = Integer.MAX_VALUE;

  /** @param tensor of anchor points */
  public FermatWeberProblem(Tensor tensor) {
    this.tensor = tensor.unmodifiable();
    setWeights(Tensors.vector(i -> RealScalar.ONE, tensor.length()));
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

  /** function defines weight corresponding to each anchor point.
   * function defines starting point of iteration as weighted average of anchor points.
   * 
   * @param weights */
  public void setWeights(Tensor weights) {
    this.weights = weights;
    point = N.DOUBLE.of(Mean.of(weights.pmul(tensor))); // initial value
  }

  /** iteration based on Endre Vaszonyi Weiszfeld
   * 
   * @return point that minimizes total distance to anchor points */
  public Tensor weiszfeld() {
    while (iteration < iteration_max) {
      Tensor next = weiszfeldStep();
      ++iteration;
      double delta = Norm._2.between(point, next).number().doubleValue();
      point = next;
      if (delta <= tolerance)
        break;
    }
    return point;
  }

  private Tensor weiszfeldStep() {
    Tensor dist = Tensor.of(tensor.stream().map(anchor -> Norm._2.between(anchor, point)));
    int index = ArgMin.of(dist);
    if (Scalars.isZero(dist.Get(index)))
      return point.copy();
    Tensor distinv = weights.pmul(dist.map(Scalar::reciprocal));
    return distinv.dot(tensor).divide(Total.of(distinv).Get());
  }

  /** @param max_iterations beyond which optimization method stops
   * regardless of tolerance */
  public void setMaxIterations(int max_iterations) {
    this.iteration_max = max_iterations;
  }

  /** @return number of iterations performed by weiszfeld method until tolerance was reached */
  public int getIterationCount() {
    return iteration;
  }
}
