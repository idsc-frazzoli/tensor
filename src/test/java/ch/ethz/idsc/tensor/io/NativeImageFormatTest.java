// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class NativeImageFormatTest extends TestCase {
  public void testRGBAFile() throws Exception {
    Tensor tensor = ImageFormatTest._readRGBA();
    File file = new File(getClass().getResource("/io/rgba15x33.png").getFile());
    BufferedImage bufferedImage = ImageIO.read(file);
    Tensor nif = NativeImageFormat.from(bufferedImage);
    assertEquals(Transpose.of(tensor), nif);
  }

  public void testSimpleGray() {
    Distribution distribution = DiscreteUniformDistribution.of(0, 256);
    Tensor image = RandomVariate.of(distribution, 100, 200);
    Tensor bimap = NativeImageFormat.fromGrayscale(NativeImageFormat.of(image));
    assertEquals(image, bimap);
  }

  public void testSimpleColor() {
    Distribution distribution = DiscreteUniformDistribution.of(0, 256);
    Tensor image = RandomVariate.of(distribution, 100, 200, 4);
    Tensor bimap = NativeImageFormat.from(NativeImageFormat.of(image));
    assertEquals(image, bimap);
  }

  public void testGrayFile() throws Exception {
    File file = new File(getClass().getResource("/io/gray15x9.png").getFile());
    BufferedImage bufferedImage = ImageIO.read(file);
    Tensor tensor = NativeImageFormat.fromGrayscale(bufferedImage);
    // confirmed with gimp
    assertEquals(tensor.Get(0, 2), RealScalar.of(175));
    assertEquals(tensor.Get(1, 2), RealScalar.of(109));
    assertEquals(tensor.Get(2, 2), RealScalar.of(94));
    assertEquals(Dimensions.of(tensor), Arrays.asList(9, 15));
  }
  // public void testSimple2() {
  // Distribution distribution = DiscreteUniformDistribution.of(0, 256);
  // int n = 1000;
  // Tensor image = RandomVariate.of(distribution, n, n);
  // {
  // NativeImageFormat.toTYPE_BYTE_GRAY(image, n, n);
  // NativeImageFormat.toTYPE_BYTE_GRAY2(image, n, n);
  // }
  // {
  // long tic = System.nanoTime();
  // NativeImageFormat.toTYPE_BYTE_GRAY(image, n, n);
  // System.out.println(" GR1 " + (System.nanoTime() - tic));
  // }
  // {
  // long tic = System.nanoTime();
  // NativeImageFormat.toTYPE_BYTE_GRAY2(image, n, n);
  // System.out.println(" GR2 " + (System.nanoTime() - tic));
  // }
  // // assertEquals(image, bimap);
  // }
  // public void testColorBimap2() {
  // Distribution distribution = DiscreteUniformDistribution.of(0, 256);
  // Tensor image = RandomVariate.of(distribution, 256, 256, 4);
  // {
  // ImageFormat.toTYPE_INT_ARGB(image, 256, 256);
  // NativeImageFormat.toTYPE_INT_ARGB(Transpose.of(image), 256, 256);
  // NativeImageFormat.toTYPE_INT_ARGB(image, 256, 256);
  // }
  // // ---
  // {
  // long tic = System.nanoTime();
  // ImageFormat.toTYPE_INT_ARGB(image, 256, 256);
  // System.out.println(" IF " + (System.nanoTime() - tic));
  // }
  // {
  // long tic = System.nanoTime();
  // NativeImageFormat.toTYPE_INT_ARGB(Transpose.of(image), 256, 256);
  // System.out.println("NIFT " + (System.nanoTime() - tic));
  // }
  // {
  // long tic = System.nanoTime();
  // NativeImageFormat.toTYPE_INT_ARGB(image, 256, 256);
  // System.out.println("NIF " + (System.nanoTime() - tic));
  // }
  // }
}
