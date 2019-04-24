// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;

/** recursive file/directory deletion
 * 
 * safety from erroneous use is enhanced by several criteria
 * 1) checking the depth of the directory tree T to be deleted
 * against a permitted upper bound "max_nested"
 * 2) checking the number of files to be deleted #F
 * against a permitted upper bound "max_delete"
 * 3) all files are checked for write permission using {@link File#canWrite()}
 * 4) if deletion of a file or directory fails, the process aborts
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/DeleteDirectory.html">DeleteDirectory</a> */
public class DeleteDirectory {
  /** Example: The command
   * DeleteDirectory.of(new File("/user/name/myapp/recordings/log20171024"), 2, 1000);
   * deletes given directory with sub directories of depth of at most 2,
   * and max number of total files and directories less equals than 1000.
   * No files are deleted if the directory tree exceeds 2, or total of files exceeds 1000.
   * 
   * The abort criteria are described at top of class
   * 
   * @param directory
   * @param max_nested
   * @param max_delete
   * @return
   * @throws Exception if given directory does not exist, or criteria are not met */
  public static DeleteDirectory of(File directory, int max_nested, long max_delete) throws IOException {
    return new DeleteDirectory(directory, max_nested, max_delete);
  }

  // ---
  private final int max_depth;
  private long deleted = 0;

  private DeleteDirectory(File root, int max_depth, long max_count) throws IOException {
    this.max_depth = max_depth;
    // ---
    int count = visitRecursively(root, 0, false);
    if (count <= max_count) // abort criteria 2)
      visitRecursively(root, 0, true);
    else
      throw new IOException("more files to be deleted than allowed (" + max_count + "<=" + count + ") in " + root);
  }

  private int visitRecursively(File file, int depth, boolean delete) throws IOException {
    if (max_depth < depth) // enforce depth limit, abort criteria 1)
      throw new IOException("directory tree exceeds permitted depth");
    // ---
    int count = 0;
    if (file.isDirectory()) // if file is a directory, recur
      for (File entry : file.listFiles())
        count += visitRecursively(entry, depth + 1, delete);
    ++count; // count file as visited
    if (delete) {
      boolean file_delete = file.delete();
      if (!file_delete) // abort criteria 4)
        throw new IOException("cannot delete " + file.getAbsolutePath());
      ++deleted;
    } else //
    if (!file.canWrite()) // abort criteria 3)
      throw new IOException("cannot write " + file.getAbsolutePath());
    return count;
  }

  public long deletedCount() {
    return deleted;
  }
}
