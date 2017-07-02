// code by Robert Sedgewick and Kevin Wayne
// adapted by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.SignInterface;

/** Quote from Wikipedia:
 * 
 * Graham's scan is a method of finding the convex hull
 * of a finite set of points in the plane with time complexity O(n log n).
 * It is named after Ronald Graham, who published the original algorithm in 1972.
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
  private final Stack<Tensor> stack = new Stack<Tensor>();
  private final Tensor point0;

  GrahamScan(Tensor tensor) {
    // list is permuted during computation
    List<Tensor> list = tensor.flatten(0).collect(Collectors.toList());
    point0 = Collections.min(list, MINY_MINX);
    Collections.sort(list, new Comparator<Tensor>() {
      @Override
      public int compare(Tensor p1, Tensor p2) {
        int cmp = Scalars.compare(angle(p1), angle(p2));
        return cmp != 0 ? cmp
            : Scalars.compare( //
                Norm._2SQUARED.of(p1.subtract(point0)), //
                Norm._2SQUARED.of(p2.subtract(point0)));
      }
    });
    stack.push(point0);
    int k1 = 1;
    Tensor point1 = null;
    for (Tensor point : list.subList(k1, list.size())) {
      if (!point0.equals(point)) {
        point1 = point;
        break;
      }
      ++k1;
    }
    if (point1 == null)
      return;
    // System.out.println("seed1 " + k1);
    int k2 = k1 + 1;
    for (Tensor point : list.subList(k2, list.size()))
      if (Scalars.isZero(ccw(point0, point1, point))) {
        // System.out.println("co-lin");
        ++k2;
      } else
        break;
    // System.out.println("seed2 " + k2);
    // ---
    stack.push(list.get(k2 - 1));
    for (Tensor point : list.subList(k2, list.size())) {
      Tensor top = stack.pop();
      while (true) {
        Scalar ccw = ccw(stack.peek(), top, point);
        SignInterface signInterface = (SignInterface) ccw;
        if (signInterface.signInt() > 0) // if (!Scalars.lessEquals(ccw, RealScalar.ZERO))
          break;
        top = stack.pop();
      }
      stack.push(top);
      stack.push(point);
    }
  }

  private Scalar angle(Tensor p2) {
    return ArcTan.of( //
        getX(p2).subtract(getX(point0)), //
        getY(p2).subtract(getY(point0)));
  }

  /** Three points are a counter-clockwise turn if ccw > 0, clockwise if
   * ccw < 0, and collinear if ccw = 0 because ccw is a determinant that
   * gives twice the signed area of the triangle formed by p1, p2 and p3.
   * (from Wikipedia)
   * 
   * @param p1
   * @param p2
   * @param p3
   * @return */
  private static Scalar ccw(Tensor p1, Tensor p2, Tensor p3) {
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

  Tensor getConvexHull() {
    return Tensor.of(stack.stream());
  }
}
