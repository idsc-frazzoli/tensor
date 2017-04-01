//// code by jph
// package ch.ethz.idsc.tensor;
//
// import java.math.BigInteger;
//
//// test
// @Deprecated
// class IntegerScalar extends RealScalar {
// public static RealScalar of(BigInteger value) {
// return value.equals(BigInteger.ZERO) ? ZeroScalar.get() : new IntegerScalar(value);
// }
//
// private final BigInteger value;
//
// private IntegerScalar(BigInteger value) {
// this.value = value;
// }
//
// @Override
// public int compareTo(RealScalar o) {
// return 0;
// }
//
// @Override
// protected boolean isPositive() {
// return 0 < value.signum();
// }
//
// @Override
// public RealScalar negate() {
// return of(value.negate());
// }
//
// @Override
// public Number number() {
// throw new UnsupportedOperationException();
// }
//
// @Override
// protected Scalar plus(Scalar scalar) {
// throw new UnsupportedOperationException();
// }
//
// @Override
// public Scalar multiply(Scalar scalar) {
// throw new UnsupportedOperationException();
// }
//
// @Override
// public Scalar invert() {
// throw new UnsupportedOperationException();
// }
//
// @Override
// public int hashCode() {
// return value.hashCode();
// }
//
// @Override
// public boolean equals(Object object) {
// throw new UnsupportedOperationException();
// }
//
// @Override
// public String toString() {
// return value.toString();
// }
// }
