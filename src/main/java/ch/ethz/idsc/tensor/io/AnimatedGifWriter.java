// code template by elliot kroo
// adapted by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

/** AnimatedGifWriter is a standalone implementation to generate animated .gif image files */
public class AnimatedGifWriter implements AutoCloseable {
  /** initializes animation to loop indefinitely
   * 
   * @param file with extension "gif"
   * @param period between frames in milliseconds
   * @return
   * @throws Exception */
  public static AnimatedGifWriter of(File file, int period) throws IOException {
    // deletion of existing file is mandatory:
    // if the gif output is smaller than the existing file
    // trailing bytes of the existing file are not removed
    if (file.isFile())
      file.delete();
    return new AnimatedGifWriter(new FileImageOutputStream(file), period, true);
  }
  // ---

  private final ImageOutputStream imageOutputStream;
  private final int period;
  private final boolean loop;
  private final ImageWriter imageWriter;
  private final ImageWriteParam imageWriteParam;
  /** assigned when first image is appended */
  private IIOMetadata iIOMetadata = null;

  private AnimatedGifWriter(ImageOutputStream imageOutputStream, int period, boolean loop) {
    this.imageOutputStream = imageOutputStream;
    this.period = period;
    this.loop = loop;
    imageWriter = ImageIO.getImageWritersBySuffix("gif").next();
    imageWriteParam = imageWriter.getDefaultWriteParam();
  }

  // invoked when first image is appended
  private void initialize(int type) throws IOException {
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

  // helper function
  private static IIOMetadataNode _node(IIOMetadataNode root, String nodeName) {
    for (int i = 0; i < root.getLength(); ++i)
      if (root.item(i).getNodeName().equalsIgnoreCase(nodeName))
        return (IIOMetadataNode) root.item(i);
    IIOMetadataNode node = new IIOMetadataNode(nodeName);
    root.appendChild(node);
    return node;
  }

  private boolean isEmpty() {
    return Objects.isNull(iIOMetadata);
  }

  public void append(BufferedImage bufferedImage) throws IOException {
    if (isEmpty())
      initialize(bufferedImage.getType());
    imageWriter.writeToSequence(new IIOImage(bufferedImage, null, iIOMetadata), imageWriteParam);
  }

  @Override // from AutoCloseable
  public void close() throws IOException {
    if (!isEmpty())
      imageWriter.endWriteSequence(); // operation invalid if no image was appended
    imageOutputStream.close();
  }
}