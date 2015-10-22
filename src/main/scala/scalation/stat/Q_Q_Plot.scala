
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
/** @author  John Miller
 *  @version 1.2
 *  @date    Sat Mar  8 14:24:11 EST 2014
 *  @see     LICENSE (MIT style license file).
 */

package scalation.stat

import scalation.linalgebra.VectorD
import scalation.plot.{FramelessPlot, Plot}
import scalation.random.Distribution
import scalation.util.Error

//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
/** The `Q_Q_Plot` object produces Quantile-Quantile plots that are used to
 *  compare probability distributions.
 */
object Q_Q_Plot
       extends Error
{
    /** Debug flag
     */
    private val DEBUG = false

    /** Whether the plot is to be embedded or has its own frame
     *  To change, set to true before calling plot
     */
    var frameless = false

    /** Whether to transform the data to zero mean and unit standard deviation
     *  To change, set to true before calling plot
     */
    var makeStandard = false             

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /** Produce a Q-Q plot for the two data vectors.
     *  @param fv         the first data vector
     *  @param gv         the second data vector
     */
    def plot (fv: VectorD, gv: VectorD): FramelessPlot =
    {
        val n = fv.dim
        if (gv.dim != n) flaw ("plot", "vectors must have the same size")
        val fv_ = if (makeStandard) fv.standardize else fv
        val pv = new VectorD (n)
        for (i <- 1 until n) {
            val p   = i / n.toDouble
            pv(i-1) = p
            if (DEBUG) println ("pv = " + pv + ", fv = " + fv_(i-1) + ", gv = " + gv(i-1))
        } // for

        if (frameless) new FramelessPlot (pv, fv_, gv)
        else { new Plot (pv, fv_, gv); null }
    } // plot

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /** Produce a Q-Q plot for the data vector and the distribution.
     *  @param fv         the data vector
     *  @param gInv       the inverse CDF
     *  @param g_df       the degrees of freedom for the distribution
     */
    def plot (fv: VectorD, gInv: Distribution, g_df: Array [Int]): FramelessPlot =
    {
        val n = fv.dim
        val gv = new VectorD (n)          // to hold vector of values for gInv
        for (i <- 1 until n) {
            val p   = i / n.toDouble
            gv(i-1) = gInv (p, g_df)
        } // for
        plot (fv, gv)
    } // plot

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /** Produce a Q-Q plot for the two distribution.
     *  @param fInv       the first inverse CDF
     *  @param f_df       the degrees of freedom for the first distribution
     *  @param gInv       the second inverse CDF
     *  @param g_df       the degrees of freedom for the second distribution
     *  @param n          the number of intervals
     */
    def plot (fInv: Distribution, f_df: Array [Int], gInv: Distribution, g_df: Array [Int],
              n: Int): FramelessPlot =
    {
        val fv = new VectorD (n)          // to hold vector of values for fInv
        val gv = new VectorD (n)          // to hold vector of values for gInv
        for (i <- 1 until n) {
            val p   = i / n.toDouble
            fv(i-1) = fInv (p, f_df)
            gv(i-1) = gInv (p, g_df)
        } // for
        plot (fv, gv)
    } // plot

} // Q_Q_Plot object


//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
/** The `Q_Q_PlotTest` object is used to test the `Q_Q_Plot` object.
 *  > run-main scalation.stat.Q_Q_PlotTest
 */
object Q_Q_PlotTest extends App
{
     import scalation.random.Quantile.{normalInv, studentTInv}

     Q_Q_Plot.plot (normalInv, Array (), studentTInv, Array (10), 100)

} // Q_Q_PlotTest object

