#ifndef _GLOBAL_H_
#define _GLOBAL_H_

/*******************************************************************************
 * Make the page_size variable globally accessible.
 */
extern unsigned page_size;

/*******************************************************************************
 * Make the mem_size variable globally accessible.
 */
extern unsigned mem_size;

/*******************************************************************************
 * Make the tlb_size variable globally accessible.
 */
extern unsigned tlb_size;

/*******************************************************************************
 * Make the tlb_mode variable globally accessible.
 */
extern char *tlb_eviction_policy;

/*******************************************************************************
 * Make the current access timestamp variable globally accessible.
 */
extern unsigned current_timestamp;

#endif/*_GLOBAL_H_*/
