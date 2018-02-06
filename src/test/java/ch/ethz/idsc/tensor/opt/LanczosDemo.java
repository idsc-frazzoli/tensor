// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.io.Import;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.utl.UserHome;

enum LanczosDemo {
  ;
  private static final int SEMI = 4;

  // ---
  static Tensor interp(Tensor row, Tensor sy) {
    Interpolation interpolation = LanczosInterpolation.of(row, SEMI);
    return Tensor.of(sy.stream().map(vy -> interpolation.get(Tensors.of(vy))));
  }

  public static void main(String[] args) throws ClassNotFoundException, DataFormatException, IOException {
    Tensor images = Import.of(UserHome.file("summary.png"));
    List<Integer> list = Dimensions.of(images);
    Interpolation interpolation = LanczosInterpolation.of(images, SEMI);
    float factor = 1.7f;
    int nx = Math.round(list.get(0) * factor);
    int ny = Math.round(list.get(1) * factor);
    Tensor sx = Subdivide.of(0, list.get(0) - 1, nx - 1);
    Tensor sy = Subdivide.of(0, list.get(1) - 1, ny - 1);
    Tensor result = Tensor.of(sx.stream().map(vx -> interpolation.get(Tensors.of(vx))));
    result = Tensor.of(result.stream().map(row -> interp(row, sy)));
    result = result.map(Clip.function(0, 255));
    Export.of(UserHome.file(String.format("castle%02d.png", Math.round(factor * 10))), result);
  }
}
