// code by jph
package ch.ethz.idsc.tensor.sca.win;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;

enum WindowFunction implements TensorUnaryOperator {
  Bartlett(BartlettWindow::of), //
  BlackmanHarris(BlackmanHarrisWindow::of), //
  BlackmanNuttall(BlackmanNuttallWindow::of), //
  Blackman(BlackmanWindow::of), //
  Dirichlet(DirichletWindow::of), //
  FlatTop(FlatTopWindow::of), //
  Gaussian(GaussianWindow::of), //
  Hamming(HammingWindow::of), //
  Hann(HannWindow::of), //
  Nuttall(NuttallWindow::of), //
  Parzen(ParzenWindow::of), //
  Tukey(TukeyWindow::of), //
  ;
  private final TensorUnaryOperator tensorUnaryOperator;

  private WindowFunction(TensorUnaryOperator tuo) {
    this.tensorUnaryOperator = tuo;
  }

  @Override
  public Tensor apply(Tensor tensor) {
    return tensorUnaryOperator.apply(tensor);
  }
}
