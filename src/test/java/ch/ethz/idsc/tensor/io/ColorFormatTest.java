// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.Color;
import java.util.Random;

import ch.ethz.idsc.tensor.Tensor;
import junit.framework.TestCase;

public class ColorFormatTest extends TestCase {
  public void testRandom() {
    Random random = new Random();
    for (int index = 0; index < 100; ++index) {
      int red = random.nextInt(255);
      int green = random.nextInt(255);
      int blue = random.nextInt(255);
      int alpha = random.nextInt(255);
      Color color = new Color(red, green, blue, alpha);
      Tensor vector = ColorFormat.toVector(color);
      assertEquals(red, vector.Get(0).number());
      assertEquals(green, vector.Get(1).number());
      assertEquals(blue, vector.Get(2).number());
      assertEquals(alpha, vector.Get(3).number());
      Color color2 = ColorFormat.toColor(vector);
      assertEquals(color, color2);
      Tensor vector2 = ColorFormat.toVector(color2);
      assertEquals(vector, vector2);
    }
  }
}
