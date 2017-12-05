// code by jph
package ch.ethz.idsc.tensor.pdf;

public abstract class AbstractContinuousDistribution implements Distribution, //
    CDF, PDF, RandomVariateInterface {
  @Override // from Object
  public int hashCode() {
    return StaticHelper.hashCode(this);
  }

  @Override // from Object
  public boolean equals(Object object) {
    return object instanceof Distribution ? StaticHelper.equals(this, (Distribution) object) : false;
  }
}
