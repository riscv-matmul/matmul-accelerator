package matmul

import chisel3._

class TreeAdder(val w: Int = 32, val n: Int = 256) extends Module{
    val io = IO(new Bundle {
        val vec = Input(Vec(n,UInt(w.W)))
        val out = Output(UInt(w.W))
    })

    val adder0 = Array.fill(128)(Module(new Adder(w=w)).io)
    val adder1 = Array.fill(64)(Module(new Adder(w=w)).io)
    val adder2 = Array.fill(32)(Module(new Adder(w=w)).io)
    val adder3 = Array.fill(16)(Module(new Adder(w=w)).io)
    val adder4 = Array.fill(8)(Module(new Adder(w=w)).io)
    val adder5 = Array.fill(4)(Module(new Adder(w=w)).io)
    val adder6 = Array.fill(2)(Module(new Adder(w=w)).io)
    val adder7 = Module(new Adder(w=w)).io

    val sum = RegInit(0.U(w.W))


    for(i <- 0 until 128) {
        adder0(i).in0 := io.vec(2*i)
        adder0(i).in1 := io.vec(2*i+1)
    }

    for(i <- 0 until 64) {
        adder1(i).in0 := adder0(2*i).out
        adder1(i).in1 := adder0(2*i+1).out
    }

    for(i <- 0 until 32) {
        adder2(i).in0 := adder1(2*i).out
        adder2(i).in1 := adder1(2*i+1).out
    }

    for(i <- 0 until 16) {
        adder3(i).in0 := adder2(2*i).out
        adder3(i).in1 := adder2(2*i+1).out
    }

    for(i <- 0 until 8) {
        adder4(i).in0 := adder3(2*i).out
        adder4(i).in1 := adder3(2*i+1).out
    }

    for(i <- 0 until 4) {
        adder5(i).in0 := adder4(2*i).out
        adder5(i).in1 := adder4(2*i+1).out
    }

    for(i <- 0 until 2) {
        adder6(i).in0 := adder5(2*i).out
        adder6(i).in1 := adder5(2*i+1).out
    }

    adder7.in0 := adder6(0).out
    adder7.in1 := adder6(1).out

    sum := adder7.out

    io.out := sum

}
