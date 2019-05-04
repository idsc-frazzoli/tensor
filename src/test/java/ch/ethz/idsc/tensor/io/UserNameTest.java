// code by jph
package ch.ethz.idsc.tensor.io;

import junit.framework.TestCase;

public class UserNameTest extends TestCase {
  public void testGetProperty() {
    String name = System.getProperty("user.name");
    assertFalse(name.isEmpty());
  }

  public void testUsername() {
    assertNotNull(UserName.get());
    assertFalse(UserName.get().isEmpty());
  }

  public void testIs() {
    assertTrue(UserName.is(UserName.get()));
    assertFalse(UserName.is(""));
    assertFalse(UserName.is("&E T H!"));
  }

  public void testNullFail() {
    try {
      assertFalse(UserName.is(null));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
