// code by jph
package ch.ethz.idsc.tensor.usr;

enum AutoCloseableDemo {
  ;
  private static void testNormal() {
    try (AutoCloseable autoCloseable = new AutoCloseable() {
      @Override
      public void close() throws Exception {
        System.out.println("close() called");
      }
    }) {
      return;
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private static void testFail() {
    try (AutoCloseable autoCloseable = new AutoCloseable() {
      @Override
      public void close() throws Exception {
        System.out.println("close() called");
      }
    }) {
      throw new RuntimeException();
    } catch (Exception exception) {
      // ---
    }
  }

  public static void main(String[] args) {
    testNormal();
    testFail();
  }
}
