package matmul

import chisel3._
import chisel3.iotesters.{Driver, TesterOptionsManager}
import utils.TesterRunner

object Launcher {
  val tests = Map(
    // "TreeAdder" -> { (manager: TesterOptionsManager) =>
    //    Driver.execute(() => new TreeAdder(w=32, n=128), manager) {
    //      (c) => new TreeAdderTest(c)
    //    }
    // }

    "VectorMulAdd" -> { (manager: TesterOptionsManager) =>
       Driver.execute(() => new VectorMulAdd(w=32, n=256), manager) {
         (c) => new VectorMulAddTest(c)
       }
    }
  )

  def main(args: Array[String]): Unit = {
    TesterRunner("matmul", tests, args)
  }
}
