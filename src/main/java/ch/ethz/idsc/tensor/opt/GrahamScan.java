// code by Robert Sedgewick and Kevin Wayne
// adapted by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.VectorQ;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Sign;

/** Quote from Wikipedia:
 * Graham's scan is a method of finding the convex hull of a finite set of points
 * in the plane with time complexity O(n log n).
 * 
 * <p>Ronald Graham published the original algorithm in 1972.
 * The algorithm finds all vertices of the convex hull ordered along its boundary.
 * It uses a stack to detect and remove concavities in the boundary efficiently.
 * 
 * <a href="https://en.wikipedia.org/wiki/Graham_scan">Graham scan</a> */
/* package */ enum GrahamScan {
  ;
  private static final Comparator<Tensor> MINY_MINX = new Comparator<Tensor>() {
    @Override
    public int compare(Tensor p1, Tensor p2) {
      int cmp = Scalars.compare(getY(p1), getY(p2));
      return cmp != 0 ? cmp : Scalars.compare(getX(p1), getX(p2));
    }
  };

  /* The Java API recommends to use ArrayDeque instead of Stack. However,
   * in the implementation of GrahamScan, we can't conveniently exchange Stack
   * and ArrayDeque because ArrayDeque#stream() reverses the order.
   * GrahamScan is used in several applications. No performance issues were
   * reported so far. */
  static Tensor of(Stream<Tensor> stream, Chop chop) {
    // list is permuted during computation of convex hull
    List<Tensor> list = stream.collect(Collectors.toList());
    if (list.isEmpty())
      return Tensors.empty();
    VectorQ.requireLength(list.get(0), 2);
    final Tensor point0 = Collections.min(list, MINY_MINX);
    Collections.sort(list, new Comparator<Tensor>() {
      @Override
      public int compare(Tensor p1, Tensor p2) {
        Tensor d10 = p1.subtract(point0);
        Tensor d20 = p2.subtract(point0);
        int cmp = Scalars.compare(arg(d10), arg(d20));
        return cmp != 0 ? cmp : MINY_MINX.compare(p1, p2);
      }
    });
    // ArrayDeque::stream is reverse of Stack::stream
    Stack<Tensor> stack = new Stack<>();
    stack.push(point0);
    int k1 = 1;
    Tensor point1 = null; // find point1 different from point0
    for (Tensor point : list.subList(k1, list.size())) {
      if (!point0.equals(point)) { // should Chop.08 be used for consistency with chop below ?
        point1 = point;
        break;
      }
      ++k1;
    }
    if (Objects.isNull(point1))
      return Tensors.of(point0);
    ++k1;
    // find point not co-linear with point0 and point1
    for (Tensor point : list.subList(k1, list.size()))
      if (Scalars.isZero(ccw(point0, point1, point))) // ... also here ?
        ++k1;
      else
        break;
    // ---
    stack.push(list.get(k1 - 1));
    for (Tensor point : list.subList(k1, list.size())) {
      Tensor top = stack.pop();
      while (!stack.isEmpty()) {
        Scalar ccw = ccw(stack.peek(), top, point);
        if (Sign.isPositive(chop.apply(ccw)))
          break;
        top = stack.pop();
      }
      stack.push(top);
      stack.push(point);
    }
    return Tensor.of(stack.stream());
  }

  /** @param point
   * @return argument of complex number with (re = pointX, im = pointY) */
  private static Scalar arg(Tensor point) {
    return ArcTan.of(point.Get(0), point.Get(1));
  }

  /** Three points are a counter-clockwise turn if ccw > 0, clockwise if
   * ccw < 0, and co-linear if ccw = 0 because ccw is a determinant that
   * gives twice the signed area of the triangle formed by p1, p2 and p3.
   * (from Wikipedia)
   * 
   * @param p1
   * @param p2
   * @param p3
   * @return */
  static Scalar ccw(Tensor p1, Tensor p2, Tensor p3) {
    Scalar a1 = getX(p2).subtract(getX(p1)).multiply(getY(p3).subtract(getY(p1)));
    Scalar a2 = getY(p2).subtract(getY(p1)).multiply(getX(p3).subtract(getX(p1)));
    return a1.subtract(a2);
  }

  private static Scalar getX(Tensor point) {
    return point.Get(0);
  }

  private static Scalar getY(Tensor point) {
    return point.Get(1);
  }
}
