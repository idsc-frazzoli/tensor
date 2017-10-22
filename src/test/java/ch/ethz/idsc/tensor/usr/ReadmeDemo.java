// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.io.Pretty;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.mat.Inverse;
import ch.ethz.idsc.tensor.mat.NullSpace;
import ch.ethz.idsc.tensor.mat.SingularValueDecomposition;
import ch.ethz.idsc.tensor.opt.LinearProgramming;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.HypergeometricDistribution;
import ch.ethz.idsc.tensor.pdf.PDF;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.Round;
import ch.ethz.idsc.tensor.sca.Sqrt;

enum ReadmeDemo {
  ;
  static void demoInverse() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { 2, -3, 2 }, { 4, 9, -3 }, { -1, 3, 2 } });
    System.out.println(Pretty.of(matrix));
    System.out.println(Pretty.of(Inverse.of(matrix)));
  }

  static void demoNullspace() {
    System.out.println(Pretty.of(NullSpace.usingRowReduce(Tensors.fromString("{{-1/3, 0, I}}"))));
  }

  static void demoSVD() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { 2, -3, 2 }, { 4, 9, -3 }, { -1, 3, 2 } });
    System.out.println(Pretty.of(SingularValueDecomposition.of(matrix).getU().map(Round._4)));
  }

  static void demoLP() {
    Tensor x = LinearProgramming.maxLessEquals( //
        Tensors.fromString("[1, 1]"), //
        Tensors.fromString("[[4, -1],[2, 1],[-5, 2]]"), //
        Tensors.fromString("[8, 7, 2]"));
    System.out.println(x);
  }

  static void demoSqrt() {
    Scalar fraction = RationalScalar.of(-9, 16);
    System.out.println(Sqrt.of(fraction));
  }

  static void demoPDF() {
    Distribution distribution = HypergeometricDistribution.of(10, 50, 100);
    System.out.println(RandomVariate.of(distribution, 20));
    // ---
    PDF pdf = PDF.of(distribution);
    System.out.println("P(X=3)=" + pdf.at(RealScalar.of(3)));
  }

  static void demoCross() {
    Tensor ad = LieAlgebras.so3();
    Tensor x = Tensors.vector(7, 2, -4);
    Tensor y = Tensors.vector(-3, 5, 2);
    System.out.println(ad);
    System.out.println(ad.dot(x).dot(y));
  }

  static void demoAssign() {
    Tensor matrix = Array.zeros(3, 4);
    matrix.set(Tensors.vector(9, 8, 4, 5), 2);
    matrix.set(Tensors.vector(6, 7, 8), Tensor.ALL, 1);
    System.out.println(Pretty.of(matrix));
    System.out.println(matrix.get(Tensor.ALL, 3));
  }

  static void demoDecimal() {
    System.out.println(Exp.of(DecimalScalar.of(10)));
    System.out.println(Sqrt.of(DecimalScalar.of(2)));
    Scalar a = N.in(100).of(RealScalar.of(2));
    System.out.println(Sqrt.of(a));
  }

  public static void main(String[] args) {
    // demoSqrt();
    // demoInverse();
    // demoPDF();
    // demoSVD();
    // demoAssign();
    // demoDecimal();
  }
}
