// See LICENSE.SiFive for license details.
// See LICENSE.Berkeley for license details.

package freechips.rocketchip.system

import Chisel._
// import freechips.rocketchip.config.Config
import freechips.rocketchip.subsystem._
// import freechips.rocketchip.devices.debug.{IncludeJtagDTM, JtagDTMKey}
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.system._

import freechips.rocketchip.config._
import freechips.rocketchip.devices.debug._
import freechips.rocketchip.devices.tilelink._
import freechips.rocketchip.rocket._
import freechips.rocketchip.tile._
import freechips.rocketchip.tilelink._
import freechips.rocketchip.util._

// import freechips.rocketchip.util.InOrderArbiter

import matmul._


class MatMulAccelConfig extends Config(new WithMatMulAccel ++ new DefaultConfig)

class WithMatMulAccel extends Config((site, here, up) => {
      case RocketTilesKey => up(RocketTilesKey, site).map { r =>
        r.copy(rocc = Seq(
          RoCCParams(
            opcodes = OpcodeSet.custom0,
            generator = (p: Parameters) => {
              val vecmuladd = LazyModule(new matmul.MatMulAccel()(p))
              vecmuladd})
          ))
      }
})


