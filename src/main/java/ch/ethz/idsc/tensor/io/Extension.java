// code by jph
package ch.ethz.idsc.tensor.io;

/** file extensions used by the tensor library
 * 
 * @see ImportHelper
 * @see ExportHelper */
/* package */ enum Extension {
  /** uncompressed loss-less image format, no alpha channel */
  BMP, //
  /** table */
  CSV, //
  /** image and animation format
   * when exporting a tensor to an image, any alpha value != 255
   * results in the pixel to be transparent */
  GIF, //
  /** compressed version of another format, for instance csv.gz */
  GZ, //
  /** compressed, lossy image format */
  JPG, //
  /** MATLAB m file, export only */
  M, //
  /** compressed image format with alpha channel */
  PNG, //
  /** ".vector" is an extension specific to the tensor library
   * @see VectorFormat */
  VECTOR, //
  ;
}
