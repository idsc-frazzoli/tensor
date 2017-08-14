// code by jph
package ch.ethz.idsc.tensor.utl;

public class Stopwatch {
  public static Stopwatch started() {
    Stopwatch stopwatch = new Stopwatch();
    stopwatch.start();
    return stopwatch;
  }

  private long total;
  private Long tic;

  public void start() {
    tic = System.nanoTime();
  }

  /** @return
   * @throws Exception if stopwatch was never started */
  public long stop() {
    long toc = System.nanoTime() - tic;
    total += toc;
    return toc;
  }

  public long total() {
    return total;
  }
}
