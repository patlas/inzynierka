#ifndef __MD5HASH_H__
#define __MD5HASH_H__

#include <stdio.h>
#include <ctype.h>

#define STR_VALUE(val) #val
#define STR(name) STR_VALUE(name)

#define PATH_LEN 256
#define MD5_LEN 32

int md5(char *file_name, char *md5_sum);




#endif
