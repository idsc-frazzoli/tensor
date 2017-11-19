// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class StringFormatTest extends TestCase {
  public void testScalar() {
    assertEquals(StringFormat.parse("3"), RealScalar.of(3));
    assertEquals(StringFormat.parse("3/19"), RationalScalar.of(3, 19));
  }

  public void testFromString() {
    assertEquals(StringFormat.parse("{   }"), Tensors.empty());
    assertEquals(StringFormat.parse("{ 2 ,-3   , 4}"), Tensors.vector(2, -3, 4));
    assertEquals(StringFormat.parse("{   {2, -3, 4  }, {2.3,-.2   }, {  }   }"), //
        Tensors.of(Tensors.vector(2, -3, 4), Tensors.vector(2.3, -.2), Tensors.empty()));
  }

  public void testFailBug() {
    assertTrue(StringFormat.parse("{2.5") instanceof StringScalar);
    assertTrue(StringFormat.parse("{2.5}}") instanceof StringScalar);
    assertTrue(StringFormat.parse("{2.5,") instanceof StringScalar);
    assertTrue(StringFormat.parse("{2.5,}}") instanceof StringScalar);
    assertTrue(StringFormat.parse("{2.5,},}") instanceof StringScalar);
  }

  // public void testComma() {
  // Tensor scalar = StringFormat.parse("3.12,");
  // // System.out.println(scalar);
  // assertTrue(scalar instanceof StringScalar);
  // }
  // public void testEmptyPost() {
  // Tensor vector = StringFormat.parse("{2.2,3,}");
  // assertEquals(vector.length(), 3);
  // assertTrue(vector.Get(2) instanceof StringScalar);
  // }
  // public void testEmptyAnte() {
  // Tensor vector = StringFormat.parse("{,2.2,3}");
  // assertEquals(vector.length(), 3);
  // assertTrue(vector.Get(0) instanceof StringScalar);
  // }
  // public void testEmptyMid() {
  // Tensor vector = StringFormat.parse("{2.2,,,3}");
  // assertEquals(vector.length(), 4);
  // assertTrue(vector.Get(1) instanceof StringScalar);
  // assertTrue(vector.Get(2) instanceof StringScalar);
  // }
  public void testFromStringFunction() {
    Tensor tensor = StringFormat.parse("{ 2 ,-3   , 4}", string -> RealScalar.of(3));
    assertEquals(tensor, Tensors.vector(3, 3, 3));
  }

  public void testFromStringFunctionNested() {
    Tensor tensor = StringFormat.parse("{ 2 ,{-3   , 4} }", string -> RealScalar.of(3));
    assertEquals(tensor, StringFormat.parse("{3, {3, 3}}"));
  }
}
