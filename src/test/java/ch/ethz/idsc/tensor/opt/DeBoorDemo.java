// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.IOException;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.io.Put;
import ch.ethz.idsc.tensor.utl.UserHome;

enum DeBoorDemo {
  ;
  static void quadrA() throws IOException {
    Tensor domain = Subdivide.of(0, 1, 100);
    Tensor knots = Tensors.vector(0, 0, 1, 2).unmodifiable();
    {
      ScalarTensorFunction n12 = //
          s -> DeBoor.of(knots, Tensors.vector(1, 0, 0)).apply(s);
      Tensor r12 = domain.map(s -> Tensors.of(s, n12.apply(s)));
      Put.of(UserHome.file("n12a.put"), r12);
    }
    {
      ScalarTensorFunction n22 = //
          s -> DeBoor.of(knots, Tensors.vector(0, 1, 0)).apply(s);
      Tensor r22 = domain.map(s -> Tensors.of(s, n22.apply(s)));
      Put.of(UserHome.file("n22a.put"), r22);
    }
    {
      ScalarTensorFunction n32 = //
          s -> DeBoor.of(knots, Tensors.vector(0, 0, 1)).apply(s);
      Tensor r32 = domain.map(s -> Tensors.of(s, n32.apply(s)));
      Put.of(UserHome.file("n32a.put"), r32);
    }
  }

  static void quadrB() throws IOException {
    Tensor domain = Subdivide.of(1, 2, 100);
    Tensor knots = Tensors.vector(0, 1, 2, 3).unmodifiable();
    {
      ScalarTensorFunction n12 = //
          s -> DeBoor.of(knots, Tensors.vector(1, 0, 0)).apply(s);
      Tensor r12 = domain.map(s -> Tensors.of(s, n12.apply(s)));
      Put.of(UserHome.file("n12b.put"), r12);
    }
    {
      ScalarTensorFunction n22 = //
          s -> DeBoor.of(knots, Tensors.vector(0, 1, 0)).apply(s);
      Tensor r22 = domain.map(s -> Tensors.of(s, n22.apply(s)));
      Put.of(UserHome.file("n22b.put"), r22);
    }
    {
      ScalarTensorFunction n32 = //
          s -> DeBoor.of(knots, Tensors.vector(0, 0, 1)).apply(s);
      Tensor r32 = domain.map(s -> Tensors.of(s, n32.apply(s)));
      Put.of(UserHome.file("n32b.put"), r32);
    }
  }

  static void cubic() throws Exception {
    Tensor d = Tensors.vector(0, 0, 1, 3).unmodifiable();
    Tensor t = Tensors.vector(0, 0, 0, 1, 2, 3).unmodifiable();
    Tensor r = Tensors.empty();
    for (Tensor _x : Subdivide.of(0, 1, 10)) {
      Scalar x = _x.Get();
      Scalar y = (Scalar) DeBoor.of(t, d).apply(x);
      r.append(Tensors.of(x, y));
    }
    Put.of(UserHome.file("deboor.put"), r);
  }

  public static void main(String[] args) throws Exception {
    quadrA();
    quadrB();
    // cubic();
  }
}
