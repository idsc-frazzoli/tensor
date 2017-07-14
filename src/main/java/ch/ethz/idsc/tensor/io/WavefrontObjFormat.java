// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.stream.Stream;

public enum WavefrontObjFormat {
  ;
  public static WavefrontObj parse(Stream<String> stream) {
    WavefrontObjImpl wavefrontObjImpl = new WavefrontObjImpl();
    stream.forEach(line -> {
      if (line.startsWith("v ")) {
        wavefrontObjImpl.append_v(line.substring(2));
      } else //
      if (line.startsWith("vn ")) {
        wavefrontObjImpl.append_vn(line.substring(3));
      } else //
      if (line.startsWith("f ")) {
        // TODO implement and test
      }
    });
    return wavefrontObjImpl;
  }
}
