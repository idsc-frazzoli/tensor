// code by Robert Sedgewick and Kevin Wayne
// adapted by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.VectorQ;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Sign;

/** Quote from Wikipedia:
 * Graham's scan is a method of finding the convex hull of a finite set of points
 * in the plane with time complexity O(n log n).
 * 
 * Ronald Graham published the original algorithm in 1972.
 * The algorithm finds all vertices of the convex hull ordered along its boundary.
 * It uses a stack to detect and remove concavities in the boundary efficiently.
 * 
 * https://en.wikipedia.org/wiki/Graham_scan */
/* package */ class GrahamScan {
  private static final Comparator<Tensor> MINY_MINX = new Comparator<Tensor>() {
    @Override
    public int compare(Tensor p1, Tensor p2) {
      int cmp = Scalars.compare(getY(p1), getY(p2));
      return cmp != 0 ? cmp : Scalars.compare(getX(p1), getX(p2));
    }
  };
  // ---
  // TODO we can't easily switch to ArrayDeque since the stream()
  // ... reverses the order when compared to Stack
  // private final Deque<Tensor> deque = new ArrayDeque<Tensor>();
  private final Stack<Tensor> stack = new Stack<>();

  GrahamScan(Tensor tensor) {
    VectorQ.elseThrow(tensor.get(0));
    // list is permuted during computation
    final List<Tensor> list = tensor.stream().collect(Collectors.toList());
    final Tensor point0 = Collections.min(list, MINY_MINX);
    Collections.sort(list, new Comparator<Tensor>() {
      @Override
      public int compare(Tensor p1, Tensor p2) {
        Tensor d10 = p1.subtract(point0);
        Tensor d20 = p2.subtract(point0);
        int cmp = Scalars.compare(arg(d10), arg(d20));
        return cmp != 0 //
            ? cmp //
            : MINY_MINX.compare(p1, p2);
        // : Scalars.compare(Norm._2.ofVector(d10), Norm._2.ofVector(d20));
      }
    });
    stack.push(point0);
    int k1 = 1;
    Tensor point1 = null;
    for (Tensor point : list.subList(k1, list.size())) {
      if (!point0.equals(point)) { // find point1 different from point0
        point1 = point;
        break;
      }
      ++k1;
    }
    if (Objects.isNull(point1))
      return;
    ++k1;
    for (Tensor point : list.subList(k1, list.size())) // find point not co-linear with point0 and point1
      if (Scalars.isZero(ccw(point0, point1, point)))
        ++k1;
      else
        break;
    // ---
    stack.push(list.get(k1 - 1));
    for (Tensor point : list.subList(k1, list.size())) {
      Tensor top = stack.pop();
      while (true) {
        Scalar ccw = ccw(stack.peek(), top, point);
        if (Sign.isPositive(Chop._15.apply(ccw))) // magic const
          break;
        top = stack.pop();
      }
      stack.push(top);
      stack.push(point);
    }
  }

  /* package */ Tensor getConvexHull() {
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
