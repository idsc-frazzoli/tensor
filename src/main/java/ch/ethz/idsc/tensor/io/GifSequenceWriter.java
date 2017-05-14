// code adapted by jph inspired by elliot kroo
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

/** in Mathematica, animated gif sequences are created by Mathematica::Export */
public class GifSequenceWriter implements AutoCloseable {
  /** @param file
   * @param period between frames in milliseconds
   * @return
   * @throws Exception */
  public static GifSequenceWriter of(File file, int period) throws Exception {
    return new GifSequenceWriter(new FileImageOutputStream(file), period, true);
  }

  private final ImageOutputStream imageOutputStream;
  private final int period;
  private final boolean loop;
  private final ImageWriter imageWriter;
  private final ImageWriteParam imageWriteParam;
  private IIOMetadata iIOMetadata = null;

  private GifSequenceWriter(ImageOutputStream imageOutputStream, int period, boolean loop) {
    this.imageOutputStream = imageOutputStream;
    this.period = period;
    this.loop = loop;
    imageWriter = ImageIO.getImageWritersBySuffix("gif").next();
    imageWriteParam = imageWriter.getDefaultWriteParam();
  }

  private void _initialize(int type) throws Exception {
    ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(type);
    iIOMetadata = imageWriter.getDefaultImageMetadata(imageTypeSpecifier, imageWriteParam);
    String metadataFormatName = iIOMetadata.getNativeMetadataFormatName();
    IIOMetadataNode root = (IIOMetadataNode) iIOMetadata.getAsTree(metadataFormatName);
    IIOMetadataNode graphicsControlExtension = _node(root, "GraphicControlExtension");
    graphicsControlExtension.setAttribute("delayTime", Integer.toString(period / 10));
    IIOMetadataNode applicationExtensions = _node(root, "ApplicationExtensions");
    IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
    child.setAttribute("applicationID", "NETSCAPE");
    child.setAttribute("authenticationCode", "2.0");
    child.setUserObject(new byte[] { 1, (byte) (loop ? 0 : 1), 0 });
    applicationExtensions.appendChild(child);
    iIOMetadata.setFromTree(metadataFormatName, root);
    imageWriter.setOutput(imageOutputStream);
    imageWriter.prepareWriteSequence(null);
  }

  private static IIOMetadataNode _node(IIOMetadataNode root, String nodeName) {
    for (int i = 0; i < root.getLength(); ++i)
      if (root.item(i).getNodeName().equalsIgnoreCase(nodeName))
        return (IIOMetadataNode) root.item(i);
    IIOMetadataNode node = new IIOMetadataNode(nodeName);
    root.appendChild(node);
    return node;
  }

  /** @param bufferedImage to append to the sequence
   * @throws Exception */
  public void append(BufferedImage bufferedImage) throws Exception {
    if (iIOMetadata == null)
      _initialize(bufferedImage.getType());
    imageWriter.writeToSequence(new IIOImage(bufferedImage, null, iIOMetadata), imageWriteParam);
  }

  @Override
  public void close() throws Exception {
    imageWriter.endWriteSequence();
    imageOutputStream.close();
  }
}