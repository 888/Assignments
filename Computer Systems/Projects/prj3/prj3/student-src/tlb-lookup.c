#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "tlb.h"
#include "pagetable.h"
#include "global.h" /* for tlb_size */
#include "statistics.h"

/*******************************************************************************
 * This function selects which TLB replacement algorithm we are using.
 * The default is LRU, but FIFO can be specified in the command line.
 *
 * IMPORTANT: DO NOT MODIFY THIS FUNCTION.
 *
 * @param vpn The virtual page number to lookup.
 * @param write If the access is a write, this is 1. Otherwise, it is 0.
 * @return The physical frame number of the page we are accessing.
 */
pfn_t tlb_lookup(vpn_t vpn, int write) {
    return !strcmp(tlb_eviction_policy, "FIFO") ? tlb_lookup_fifo(vpn, write) : tlb_lookup_lru(vpn, write);
}

/*******************************************************************************
 * Looks up an address in the TLB. If no entry is found, attempts to access the
 * current page table via cpu_pagetable_lookup().
 *
 * @param vpn The virtual page number to lookup.
 * @param write If the access is a write, this is 1. Otherwise, it is 0.
 * @return The physical frame number of the page we are accessing.
 */
pfn_t tlb_lookup_lru(vpn_t vpn, int write) {
    pfn_t pfn;

    /*
    * FIX ME : Step 6
    */

    /*
    * Search the TLB for the given VPN. Make sure to increment count_tlbhits if
    * it was a hit!
    */

    /* If it does not exist (it was not a hit), call the page table reader */

    for (i = 0; i < tlb_size; i++) {
        tlbe_t entry = tlb[i];
        if (entry.vpn == vpn){
            entry.access = current_timestamp;
            output = entry.pfn;
            return output;
        }
    }

    pfn = pagetable_lookup(vpn, write);

    /*
    * Replace an entry in the TLB if we missed. Pick invalid entries first,
    * then use LRU to find a victim.
    */

    int replaced = 0;
    int lru = 1000000;
    int replace = 0;
    for (i = 0; i < tlb.size; i++) {
        tlbe_t entry = tlb[i];
        if (!(entry.valid) {
            entry.vpn = vpn;
            entry.pfn = pfn;
            entry.valid = 1;
            replaced = 1;
            break;
        } else {
            if (entry.access < lru) {
                lru = entry.access;
                replace = i;
            }
        }
    }

    if (!(replaced)) {
        tlbe_t entry = tlb[replace];
        entry.vpn = vpn;
        entry.pfn = pfn;
        entry.valid = 1;
    }

    /*
    * Perform TLB house keeping. This means updating the found TLB entry's last
    * access timestamp and if we had a write, dirty. We also need to update the
    * page table in memory with the same data.
    *
    * We'll assume that this write is scheduled and the CPU doesn't actually
    * have to wait for it to finish (there wouldn't be much point to a TLB if
    * we didn't!).
    */

    entry.dirty = write;
    current_pagetable[vpn].dirty = write;
    entry.access = current_timestamp;
    return pfn;
}

/*******************************************************************************
 * Looks up an address in the TLB. If no entry is found, attempts to access the
 * current page table via cpu_pagetable_lookup().
 *
 * @param vpn The virtual page number to lookup.
 * @param write If the access is a write, this is 1. Otherwise, it is 0.
 * @return The physical frame number of the page we are accessing.
 */
pfn_t tlb_lookup_fifo(vpn_t vpn, int write) {
    pfn_t pfn;

    /*
    * FIX ME : Step 6
    */

    /*
    * Search the TLB for the given VPN. Make sure to increment count_tlbhits if
    * it was a hit!
    */

    for (i = 0; i < tlb_size; i++) {
        tlbe_t entry = tlb[i];
        if (entry.vpn == vpn){
            output = entry.pfn;
            return output;
        }
    }

    /* If it does not exist (it was not a hit), call the page table reader */
    pfn = pagetable_lookup(vpn, write);

    /*
    * Replace an entry in the TLB if we missed. Pick invalid entries first,
    * then use FIFO to find a victim.
    */

    int replaced = 0;
    int lru = 1000000;
    int replace = 0;
    for (i = 0; i < tlb.size; i++) {
        tlbe_t entry = tlb[i];
        if (!(entry.valid) {
            entry.vpn = vpn;
            entry.pfn = pfn;
            entry.valid = 1;
            replaced = 1;
            break;
        } else {
            if (entry.access < lru) {
                lru = entry.access;
                replace = i;
            }
        }
    }

    if (!(replaced)) {
        tlbe_t entry = tlb[replace];
        entry.vpn = vpn;
        entry.pfn = pfn;
        entry.valid = 1;
    }

    /*
    * Perform TLB house keeping. This means updating the found TLB entry's last
    * access timestamp and if we had a write, dirty. We also need to update the
    * page table in memory with the same data.
    *
    * We'll assume that this write is scheduled and the CPU doesn't actually
    * have to wait for it to finish (there wouldn't be much point to a TLB if
    * we didn't!).
    */

    entry.dirty = write;
    current_pagetable[vpn].dirty = write;
    entry.access = current_timestamp;
    return pfn;
}
