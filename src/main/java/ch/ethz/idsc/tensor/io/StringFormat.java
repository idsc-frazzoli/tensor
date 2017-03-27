package ch.ethz.idsc.tensor.io;

import java.util.Stack;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

// WIP
/* package */ class StringFormat {
  public static Tensor of(String string) {
    return null;
  }

  Stack<Tensor> stack = new Stack<>();

  private StringFormat(String string) {
    process(string);
  }

  private void process(String string) {
    if (string.startsWith("[")) {
      stack.add(Tensors.empty());
      // Tensor list = stack.peek();
      // List<Tensor> list = new ArrayList<>();
      int level = 0;
      int beg = -1;
      for (int index = 0; index < string.length(); ++index) {
        final char chr = string.charAt(index);
        if (chr == '[') {
          ++level;
          if (level == 1)
            beg = index + 1;
          System.out.println(beg); // to suppress warning
        }
        if (level == 1 && (chr == ',' || chr == ']')) {
          // String entry = string.substring(beg, index).trim(); // <- TODO not sure if trim is good
          // if (!entry.isEmpty())
          // list.add(process(entry));
          beg = index + 1;
        }
        if (chr == ']')
          --level;
      }
      // return Tensor.of(list.stream());
    }
    // return Scalars.fromString(string);
  }
}
