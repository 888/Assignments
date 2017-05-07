#include "global.h"

unsigned page_size = 2;
unsigned mem_size  = 16;
unsigned tlb_size  = 4;
char* tlb_eviction_policy = "LRU";
unsigned current_timestamp = 0;
