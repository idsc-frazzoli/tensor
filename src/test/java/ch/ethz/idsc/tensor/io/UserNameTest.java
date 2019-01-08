// code by jph
package ch.ethz.idsc.tensor.io;

import junit.framework.TestCase;

public class UserNameTest extends TestCase {
  public void testUsername() {
    assertFalse(UserName.get().isEmpty());
  }
}
