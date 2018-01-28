// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.IOException;
import java.util.Random;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

class NonSerializable {
  int value;
}

public class SerializationTest extends TestCase {
  public void testCopy() throws ClassNotFoundException, IOException {
    String s1 = "abc";
    String s2 = Serialization.copy(s1);
    assertEquals(s1, s2);
    assertFalse(s1 == s2);
  }

  public void testCopy2() throws ClassNotFoundException, IOException {
    Tensor t1 = Tensors.vector(2, 3, 4, 5);
    Tensor t2 = Serialization.copy(t1);
    assertEquals(t1, t2);
    assertFalse(t1 == t2);
  }

  public void testCopyNull() throws ClassNotFoundException, IOException {
    Serialization.parse(Serialization.of(null));
  }

  public void testOfFail() {
    NonSerializable nonSerializable = new NonSerializable();
    try {
      Serialization.of(nonSerializable);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testParseFail() {
    try {
      Serialization.parse(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testParseFail2() {
    byte[] bytes = new byte[100];
    new Random().nextBytes(bytes);
    try {
      Serialization.parse(bytes);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
