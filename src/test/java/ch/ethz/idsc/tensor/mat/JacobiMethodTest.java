// code by guedelmi
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class JacobiMethodTest extends TestCase {
  private static void checkEquation(Tensor matrix, Eigensystem eigensys) {
    Tensor Vi = Inverse.of(eigensys.vectors());
    Tensor res = Vi.dot(DiagonalMatrix.of(eigensys.values())).dot(eigensys.vectors());
    assertTrue(Chop.isZeros(res.subtract(matrix)));
  }

  public void testJacobiWithTensor1() {
    Tensor tensor = Tensors.fromString("{{2, 3, 0, 1}, {3, 1, 7, 5}, {0, 7, 10, 9}, {1, 5, 9, 13}}");
    Eigensystem eigsys = Eigensystem.ofSymmetric(tensor);
    Tensor expEigvl = Tensors.fromString("{ 23.853842147040694,  3.3039323944179757, 2.8422254585413294, -4}");
    Tensor expEigvc = Transpose.of(Tensors.fromString(
        "{{0.08008068980475883, -0.5948978891329353, 0.6877622539503787, -0.4082482904638631}, {0.35340348774478036, -0.45368409809391813, 0.05108862221538444, 0.8164965809277261}, {0.6267262856848018, -0.3124703070549013, -0.5855850095196097, -0.408248290463863}, {0.6898602907849903, 0.5853456652704466, 0.4259850130546227, -6.117111473932377E-17}}"));
    assertEquals(Chop.of(expEigvl.subtract(eigsys.values())), Array.zeros(4));
    assertEquals(Chop.of(expEigvc.subtract(eigsys.vectors())), Array.zeros(4, 4));
    checkEquation(tensor, eigsys);
  }

  public void testJacobiWithTensor2() {
    Tensor tensor = Tensors.fromString("{{0, 3, 0, 1}, {3, 0, 7, 5}, {0, 7, -2, 9}, {1, 5, 9, 0}}");
    Eigensystem eigsys = Eigensystem.ofSymmetric(tensor);
    Tensor expEigvl = Tensors.fromString("{13.741843166529974899, 0.42515310634896474734, -5.4100072556794011520, -10.756989017199538494}");
    Tensor expEigvc = Tensors.empty();
    expEigvc.append(Tensors.vector(-0.16135639309137209668, -0.54323075821807030942, -0.57753040539597776347, -0.58764197312438517024));
    expEigvc.append(Tensors.vector(-0.92624362790255729732, -0.20474246988585952709, 0.22707481664854011532, 0.22043205401887170741));
    expEigvc.append(Tensors.vector(0.31354840524937794336, -0.75632770019948717296, 0.041096194862756938126, 0.57268395319262161336));
    expEigvc.append(Tensors.vector(-0.13313246690429592645, 0.30157797376766923332, -0.78307519514730625756, 0.52737056301918462298));
    checkEquation(tensor, eigsys);
    assertEquals(Chop.of(expEigvl.subtract(eigsys.values())), Array.zeros(4));
    // assertEquals(Chop.of(expEigvc.subtract(eigsys.vectors())), Array.zeros(4, 4));
  }
}