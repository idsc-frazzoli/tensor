// code by jph
package ch.ethz.idsc.tensor.io;

/** file extensions used by the tensor library */
/* package */ enum Extension {
  BMP, //
  CSV, //
  CSV_GZ, //
  GIF, //
  JPG, //
  M, //
  PNG, //
  VECTOR, //
  ;
  final String suffix = "." + name().toLowerCase().replace('_', '.');
}
