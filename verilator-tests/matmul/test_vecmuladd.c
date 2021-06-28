#include <assert.h>
#include <stdio.h>
#include "rocc.h"

int main()
{
    uint32_t a[4] = {1, 2, 3, 4};
    uint32_t b[4] = {3, 6, 2, 1};
    uint32_t result;
    printf("[INFO] a addr: %x, b_addr: %x\n", a, b);
    asm volatile ("fence");
    ROCC_INSTRUCTION_SS(0, a, b, 0); /* a => rs1, b => rs2 */
    ROCC_INSTRUCTION_DS(0, result, 4, 1); /* result, len */
    asm volatile ("fence");

    assert(result == 25);
    printf("[INFO] ***** result %d ****** success! \n", result);
}