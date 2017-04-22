// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigInteger;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class ModTest extends TestCase {
  public void testSimple() {
    Mod mod = Mod.function(RealScalar.of(4), RealScalar.of(-2));
    assertEquals(mod.apply(RealScalar.ONE), RealScalar.ONE);
    assertEquals(mod.apply(RealScalar.ONE.negate()), RealScalar.ONE.negate());
    assertEquals(mod.apply(RealScalar.of(3)), RealScalar.ONE.negate());
    assertEquals(mod.apply(RealScalar.of(2)), RealScalar.of(-2));
    assertEquals(mod.apply(RealScalar.of(-2)), RealScalar.of(-2));
  }

  public void testPi() {
    Mod mod = Mod.function(RealScalar.of(2 * Math.PI), RealScalar.of(-Math.PI));
    assertEquals(mod.apply(RealScalar.ONE), RealScalar.ONE);
    assertEquals(mod.apply(RealScalar.ONE.negate()), RealScalar.ONE.negate());
    assertEquals(mod.apply(RealScalar.of(3)), RealScalar.of(3));
    assertEquals(mod.apply(RealScalar.of(2)), RealScalar.of(2));
    assertEquals(mod.apply(RealScalar.of(-2)), RealScalar.of(-2));
    assertEquals(mod.apply(RealScalar.of(-4)), RealScalar.of(-4 + 2 * Math.PI));
    assertEquals(mod.apply(RealScalar.of(5)), RealScalar.of(5 - 2 * Math.PI));
  }

  public void testRational1() {
    Scalar m = RationalScalar.of( //
        new BigInteger("816345827635482763548726354817635487162354876135284765"), //
        new BigInteger("89354817623548127345928347902837409827304897917"));
    Scalar n = RationalScalar.of( //
        new BigInteger("876215837615238675"), //
        new BigInteger("29386548765867571711"));
    Scalar d = RationalScalar.of( //
        new BigInteger("-9874698736"), //
        new BigInteger("230817253875"));
    String mathem = "-103027088435315211090616226024636019095132932417575467867214490110/2625829705559600168587308022574969971429853524559950547309532025987";
    Scalar expected = Scalars.fromString(mathem);
    {
      assertEquals(Mod.function(n, d).apply(m), expected);
    }
    {
      Scalar r = Mod.function(n, d).apply((Scalar) N.of(m));
      assertEquals(Chop.below(1e-9).apply(r.subtract(expected)), ZeroScalar.get());
    }
  }

  public void testRational2() {
    Scalar m = RationalScalar.of( //
        new BigInteger("816345827635482763548726354817635487162354876135284765"), //
        new BigInteger("89354817623548127345928347902837409827917"));
    Scalar n = RationalScalar.of( //
        new BigInteger("876215837615238675"), //
        new BigInteger("29386548765867571711332"));
    Scalar d = RationalScalar.of( //
        new BigInteger("-3453453453453459874698736"), //
        new BigInteger("230817253875123123"));
    String mathem = "-4365252651220514098051928821648538890491086296970254009125271304896855/291758856173288907624108202447331994414076589308384576368761716";
    Scalar expected = Scalars.fromString(mathem);
    {
      assertEquals(Mod.function(n, d).apply(m), expected);
    }
    {
      Scalar r = Mod.function(n, d).apply((Scalar) N.of(m));
      assertEquals(Chop.below(.01).apply(r.subtract(expected)), ZeroScalar.get());
    }
  }
}
