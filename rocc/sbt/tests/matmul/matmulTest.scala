package matmul
import chisel3._
//import Chisel._
import chisel3.iotesters.{PeekPokeTester, Driver, TesterOptionsManager, ChiselFlatSpec, SteppedHWIOTester}
import utils.TesterRunner

class TreeAdderTest(c: TreeAdder) extends PeekPokeTester(c) {
  for (i <- 0 until c.n) {
      poke(c.io.vec(i), 2)
  }
  step(1)
  expect(c.io.out, 2 * c.n)
}

class VectorMulAddTest(c: VectorMulAdd) extends PeekPokeTester(c) {
  for (i <- 0 until c.n) {
      poke(c.io.vec1(i), 2)
      poke(c.io.vec2(i), 3)
  }
  step(2)
  expect(c.io.out, (2*3) * c.n)
}