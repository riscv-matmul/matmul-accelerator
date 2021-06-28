# MatMul RoCC Accelerator

## 单元测试
```
cd rocc/sbt
sbt "test:runMain matmul.Launcher VectorMul"
```

## 集成到 Rocket-chip (需要装好RISCV工具链)
```
git submodule update --init --recursive --depth 1
./install-symlinks.sh

# 编译仿真程序
cd rocket-chip/emulator
make CONFIG=MatMulAccelConfig
```

## C测试样例
```
cd verilator-tests/matmul
riscv64-unknown-elf-gcc -static filename.c -o test.elf

# use proxy kernel to simulate
../rocket-chip/emulator/emulator-* pk test.elf
```

