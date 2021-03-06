#include <stdlib.h>

#include "types.h"
#include "pagetable.h"
#include "global.h"
#include "process.h"

/*******************************************************************************
 * Finds a free physical frame. If none are available, uses a clock sweep
 * algorithm to find a used frame for eviction.
 *
 * @return The physical frame number of a free (or evictable) frame.
 */
pfn_t get_free_frame(void) {
    int i;

    /* See if there are any free frames */
    for (i = 0; i < CPU_NUM_FRAMES; i++)
        if (rlt[i].pcb == NULL)
            return i;

    /* FIX ME : Problem 5 */
    /* IMPLEMENT A CLOCK SWEEP ALGORITHM HERE */

    for (i = 0; i < CPU_NUM_FRAMES; i++) {
        pte_t* iterate = rlt[i].pcb->pagetable;
        vpn_t* num = rlt[i].vpn;
        pte_t entry = iterate[num];
        if (entry.used) {
            iterate.used = 0;
        } else {
            return entry.pfn;
        }

    }
    return 0;
}