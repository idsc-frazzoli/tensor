// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.File;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.red.Nest;
import ch.ethz.idsc.tensor.sca.Arg;
import ch.ethz.idsc.tensor.sca.Gamma;

enum GammaDemo {
  ;
  private static final int RES = 100;
  // {x, -1.25, -0.6}, {y, -0.25, 0.25}
  private static final Tensor re = Subdivide.of(-1.25, -0.6, RES);
  private static final Tensor im = Subdivide.of(-0.25, +0.25, RES);

  public static Tensor fun(int x, int y) {
    Scalar ofs = DoubleScalar.of(0.00001 / 7);
    Scalar seed = ComplexScalar.of(re.Get(x).add(ofs), im.Get(y).add(ofs));
    try {
      Scalar nest = Nest.of(Gamma.FUNCTION, seed, 3);
      if (NumberQ.of(nest)) {
        Scalar arg = Arg.of(nest);
        // System.out.println(seed + " " + nest + " " + arg);
        Scalar value = arg.add(DoubleScalar.of(Math.PI)); // <- obsolete
        if (Scalars.lessThan(value, RealScalar.ZERO))
          throw TensorRuntimeException.of(value);
        if (Scalars.lessThan(RealScalar.of(2 * Math.PI), value))
          throw TensorRuntimeException.of(value);
        return value;
      }
    } catch (Exception exception) {
      // System.out.println("seed " + seed);
      // ---
    }
    return DoubleScalar.INDETERMINATE;
  }

  public static void main(String[] args) throws Exception {
    Tensor matrix = Tensors.matrix(GammaDemo::fun, RES + 1, RES + 1);
    System.out.println(Dimensions.of(matrix));
    Export.of(new File("/home/datahaki/Pictures/image.png"), //
        ArrayPlot.of(matrix, ColorDataGradients.HUE));
  }
}
