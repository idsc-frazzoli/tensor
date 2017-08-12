// code by jph
package ch.ethz.idsc.tensor.utl;

public class Stopwatch {
  private long total;
  private long tic;

  public void start() {
    tic = System.nanoTime();
  }

  public long stop() {
    long toc = System.nanoTime() - tic;
    total += toc;
    return toc;
  }

  public long total() {
    return total;
  }
}
