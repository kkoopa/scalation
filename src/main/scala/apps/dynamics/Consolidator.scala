
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
/** @author  Benjamin Byholm
 *  @version 1.0
 *  @date    Tue Oct 25 2016
 *  @see     LICENSE (MIT style license file).
 */

package apps.dynamics

import util.Sorting.quickSort
import scalation.random.{Randi0, RNGStream, Variate}

object Consolidator extends App {
	val pms = Array.tabulate(10)(_ => new PM(100))
	var vms = Array.tabulate(100)(_ => new VM(Randi0(10, RNGStream.ranStream).igen))

	for (v <- vms) {
		println(v)
	}

	for (p <- pms) {
		println(p)
	}

	quickSort(vms)(Ordering[Int].on((x: VM) => x.cpuUtil).reverse)

	for(v <- vms) {
		for(p <- pms) {
		}
	}
}

class PM (maxCpu: Int) {
	val id = PM.inc
	override def toString = "PM" + id.toString
}

object PM {
	private var current = 0
	private def inc = {current += 1; current}
}

class VM (var cpuUtil: Int) {
	val id = VM.inc

	def setUtil(util: Int): Unit = cpuUtil = util

	override def toString = "VM" + id.toString + ": " + cpuUtil
}

object VM {
	private var current = 0
	private def inc = {current += 1; current}
}
