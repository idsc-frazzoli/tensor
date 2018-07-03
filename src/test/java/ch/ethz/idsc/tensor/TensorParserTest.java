// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.io.StringScalar;
import ch.ethz.idsc.tensor.io.StringScalarQ;
import junit.framework.TestCase;

public class TensorParserTest extends TestCase {
  public void testFromString() {
    assertEquals(Tensors.fromString("{   }"), Tensors.empty());
    assertEquals(Tensors.fromString("{ 2 ,-3   , 4}"), Tensors.vector(2, -3, 4));
    assertEquals(Tensors.fromString("{   {2, -3, 4  }, {2.3,-.2   }, {  }   }"), //
        Tensors.of(Tensors.vector(2, -3, 4), Tensors.vector(2.3, -.2), Tensors.empty()));
  }

  public void testFailBug() {
    assertTrue(Tensors.fromString("{2.5") instanceof StringScalar);
    assertTrue(Tensors.fromString("{2.5}}") instanceof StringScalar);
    assertTrue(Tensors.fromString("{2.5,") instanceof StringScalar);
    assertTrue(Tensors.fromString("{2.5,}}") instanceof StringScalar);
    assertTrue(Tensors.fromString("{2.5,},}") instanceof StringScalar);
  }

  public void testComma() {
    Tensor scalar = Tensors.fromString("3.12,");
    assertTrue(scalar instanceof StringScalar);
  }

  public void testEmptyPost() {
    Tensor vector = Tensors.fromString("{2.2,3,}");
    assertEquals(vector.length(), 3);
    assertTrue(vector.Get(2) instanceof StringScalar);
  }

  public void testEmptyAnte() {
    Tensor vector = Tensors.fromString("{,2.2,3}");
    assertEquals(vector.length(), 3);
    assertTrue(vector.Get(0) instanceof StringScalar);
  }

  public void testEmptyMid() {
    Tensor vector = Tensors.fromString("{2.2,,,3}");
    assertEquals(vector.length(), 4);
    assertTrue(vector.Get(1) instanceof StringScalar);
    assertTrue(vector.Get(2) instanceof StringScalar);
  }

  public void testFromStringFunction() {
    Tensor tensor = Tensors.fromString("{ 2 ,-3   , 4}", string -> RealScalar.of(3));
    assertEquals(tensor, Tensors.vector(3, 3, 3));
  }

  public void testFromStringFunctionNested() {
    Tensor tensor = Tensors.fromString("{ 2 ,{-3   , 4} }", string -> RealScalar.of(3));
    assertEquals(tensor, Tensors.fromString("{3, {3, 3}}"));
  }

  public void testEMatlab() {
    Tensor tensor = Tensors.fromString("3e-2");
    assertTrue(StringScalarQ.any(tensor));
  }

  public void testFailStringNull() {
    try {
      Tensors.fromString(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailFunctionNull() {
    try {
      new TensorParser(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
