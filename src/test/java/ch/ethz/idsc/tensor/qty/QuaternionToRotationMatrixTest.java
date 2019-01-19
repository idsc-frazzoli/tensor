// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Random;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.mat.Det;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.mat.OrthogonalMatrixQ;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Conjugate;
import junit.framework.TestCase;

public class QuaternionToRotationMatrixTest extends TestCase {
  private static final Tensor ID3 = IdentityMatrix.of(3);

  public void testSimple() {
    Quaternion quaternion = Quaternion.of(0.240810, -0.761102, -0.355923, -0.485854);
    Tensor matrix = QuaternionToRotationMatrix.of(quaternion);
    assertTrue(OrthogonalMatrixQ.of(matrix));
    Chop._12.requireClose(Det.of(matrix), RealScalar.ONE);
    Tensor altern = QuaternionToRotationMatrix.of(quaternion.multiply(RealScalar.of(3)));
    Chop._12.requireClose(matrix, altern);
  }

  public void testRandom() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < 100; ++index) {
      Tensor wxyz = RandomVariate.of(distribution, 4);
      Tensor matrix = QuaternionToRotationMatrix.of(Quaternion.of(wxyz.Get(0), wxyz.extract(1, 4)));
      assertTrue(OrthogonalMatrixQ.of(matrix, Chop._12));
      Chop._12.requireClose(Det.of(matrix), RealScalar.ONE);
      Quaternion quaternion = Quaternion.of(wxyz.Get(0), wxyz.Get(1), wxyz.Get(2), wxyz.Get(3));
      Quaternion reciprocal = quaternion.reciprocal();
      Tensor invmat = QuaternionToRotationMatrix.of(reciprocal);
      assertTrue(Chop._12.close(matrix.dot(invmat), ID3));
    }
  }

  public void testQuaternionVector() {
    Random random = new Random();
    for (int index = 0; index < 100; ++index) {
      Quaternion quaternion = Quaternion.of(random.nextGaussian(), random.nextGaussian(), random.nextGaussian(), random.nextGaussian());
      quaternion = quaternion.divide(quaternion.abs()); // normalize
      Tensor vector = RandomVariate.of(NormalDistribution.standard(), 3);
      Scalar v = Quaternion.of(RealScalar.ZERO, vector);
      Quaternion qvq = (Quaternion) quaternion.multiply(v).multiply(Conjugate.FUNCTION.apply(quaternion));
      Quaternion qq = (Quaternion) quaternion;
      Tensor matrix = QuaternionToRotationMatrix.of(qq);
      assertTrue(OrthogonalMatrixQ.of(matrix, Chop._12));
      Chop._12.requireClose(Det.of(matrix), RealScalar.ONE);
      Tensor result = matrix.dot(vector);
      assertTrue(Chop._12.close(result, qvq.xyz()));
    }
  }

  public void testFail() {
    try {
      QuaternionToRotationMatrix.of(Quaternion.of(0, 0, 0, 0));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      QuaternionToRotationMatrix.of(Quaternion.of(0.0, 0.0, 0.0, 0.0));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
