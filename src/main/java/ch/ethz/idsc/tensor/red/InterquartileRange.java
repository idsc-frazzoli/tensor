// code by gjoel
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.*;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.InverseCDF;

/**
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/InterquartileRange.html">InterquartileRange</a> */
public enum InterquartileRange {
    ;
    private static final Scalar LO = RationalScalar.of(1,4);
    private static final Scalar HI = RationalScalar.of(3,4);

    /** <code>InterquartileRange[{0, 1, 2, 3, 10}] == {2}</code>
     * @param samples unsorted
     * @return interquartile range as scalar */
    public static Scalar of(Tensor samples){
        Tensor quartiles = Quantile.of(samples, Tensors.of(LO, HI));
        return quartiles.Get(1).subtract(quartiles.Get(0));
    }

    /** @param distribution
     * @return interquartile range of distribution as scalar */
    public static Scalar of(Distribution distribution) {
        InverseCDF invCDF = InverseCDF.of(distribution);
        return invCDF.quantile(HI).subtract(invCDF.quantile(LO));
    }
}
