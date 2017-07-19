// code by jph
package ch.ethz.idsc.tensor.io.ext;

import java.util.stream.Stream;

public enum WavefrontFormat {
  ;
  /** parse Wavefront .obj file
   * 
   * @param stream
   * @return */
  public static Wavefront parse(Stream<String> stream) {
    WavefrontImpl wavefrontObjImpl = new WavefrontImpl();
    stream.forEach(line -> {
      if (line.startsWith("v ")) {
        wavefrontObjImpl.append_v(line.substring(2));
      } else //
      if (line.startsWith("vn ")) {
        wavefrontObjImpl.append_vn(line.substring(3));
      } else //
      if (line.startsWith("f ")) {
        wavefrontObjImpl.append_f(line.substring(2));
      }
      if (line.startsWith("o ")) {
        wavefrontObjImpl.append_o(line.substring(2));
      }
    });
    return wavefrontObjImpl;
  }
}
