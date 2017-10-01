// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.IOException;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.utl.UserHome;

enum GaussianMatrixDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor tensor = GaussianMatrix.of(255);
    Export.of(UserHome.Pictures(GaussianMatrix.class.getSimpleName() + ".png"), //
        ArrayPlot.of(tensor, ColorDataGradients.PARULA));
  }
}
