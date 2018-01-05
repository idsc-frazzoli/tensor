// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.Parallelize;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.sca.ArcCosh;
import ch.ethz.idsc.tensor.sca.ArcSinh;
import ch.ethz.idsc.tensor.sca.ArcTanh;
import ch.ethz.idsc.tensor.sca.Imag;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import ch.ethz.idsc.tensor.utl.UserHome;

/** inspired by Mathematica's documentation of DensityPlot */
class InverseTrigDemo {
  private static final int RES = StaticHelper.GALLERY_RES;
  private static final int EXPONENT = 3;
  private static final Tensor RE = Subdivide.of(-2.0, +2.0, RES - 1);
  private static final Tensor IM = Subdivide.of(-2.0, +2.0, RES - 1);
  // ---
  final ScalarUnaryOperator scalarUnaryOperator;

  InverseTrigDemo(ScalarUnaryOperator scalarUnaryOperator) {
    this.scalarUnaryOperator = scalarUnaryOperator;
  }

  Scalar function(int y, int x) {
    return Imag.of(scalarUnaryOperator.apply(Power.of(ComplexScalar.of(RE.Get(x), IM.Get(y)), EXPONENT)));
  }

  public static void main(String[] args) throws Exception {
    Tensor collection = Tensors.empty();
    {
      InverseTrigDemo itd = new InverseTrigDemo(ArcSinh.FUNCTION);
      collection.append(Parallelize.matrix(itd::function, RES, RES));
    }
    {
      InverseTrigDemo itd = new InverseTrigDemo(ArcTanh.FUNCTION);
      collection.append(Parallelize.matrix(itd::function, RES, RES));
    }
    {
      InverseTrigDemo itd = new InverseTrigDemo(ArcCosh.FUNCTION);
      collection.append(Parallelize.matrix(itd::function, RES, RES));
    }
    Export.of(UserHome.Pictures(InverseTrigDemo.class.getSimpleName() + ".png"), //
        ArrayPlot.of(Mean.of(collection), ColorDataGradients.THERMOMETER));
  }
}
