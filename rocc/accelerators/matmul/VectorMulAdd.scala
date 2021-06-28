package matmul

import chisel3._

class VectorMulAdd(val w: Int, val n: Int) extends Module{
    val io = IO(new Bundle{
        val vec1    =  Input(Vec(n,UInt(w.W)))
        val vec2    =  Input(Vec(n,UInt(w.W)))
        val mul_out =  Output(Vec(n,UInt(w.W)))
        val out     =  Output(UInt(w.W))
    })

    val muls     = Array.fill(n)(Module(new Mul(n=w)).io)
    val products = RegInit(Vec(Seq.fill(n)(0.asUInt(w.W))))

    for (i <- 0 until n) {
        muls(i).in0 := io.vec1(i)
        muls(i).in1 := io.vec2(i)
        products(i) := muls(i).out
    }

    val treeAdder = Module(new TreeAdder(w=w, n=n)).io
    treeAdder.vec := products
    io.mul_out := products
    io.out := treeAdder.out
}

object VectorMulAdd extends App {
    chisel3.Driver.execute(args, () => new VectorMulAdd(2, 9))
}
