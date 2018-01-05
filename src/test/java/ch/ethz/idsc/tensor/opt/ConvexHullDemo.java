// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

enum ConvexHullDemo {
  ;
  public static void main(String[] args) {
    {
      Stack<Tensor> deque = new Stack<>();
      deque.push(Tensors.vector(0, 0));
      deque.push(Tensors.vector(1, 0));
      deque.push(Tensors.vector(1, 1));
      System.out.println(Tensor.of(deque.stream()));
    }
    {
      Deque<Tensor> deque = new ArrayDeque<>();
      deque.push(Tensors.vector(0, 0));
      deque.push(Tensors.vector(1, 0));
      deque.push(Tensors.vector(1, 1));
      System.out.println(Tensor.of(deque.stream()));
    }
  }
}
