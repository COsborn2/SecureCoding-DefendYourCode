#include "myUtils.h"

void strip(char *array)
{
	if(array == NULL)
	{
		perror("array is null");
		exit(-99);
	}// end if
	int len = strlen(array), x = 0;
   
	while(array[x] != '\0' && x < len)
	{
	  if(array[x] == '\r')
		 array[x] = '\0';

	  else if(array[x] == '\n')
		 array[x] = '\0';
	  x++;

}// end while
   
}// end strip

int isValidName(char * name)
{
   regex_t regex;
   int result;
   result = regcomp(&regex, "^([a-z]|[A-Z]|[\\-]){1,50}$", REG_EXTENDED);
   printf("%d\n", result);
   if(result)
   {
      return 1;
   }
   result = regexec(&regex, name, 0, NULL, 0);
   printf("%d\n", result);
   if(result == 0)
      return 0;
   else if(reti == REG_NOMATCH)
      return 1;
   else
      return -1;
}

int isValidInt(char * num)
{
   regex_t regex;
   int result;
   result = regcomp(&regex, "^([0-9]+)$", REG_EXTENDED);
   printf("%d\n", result);
   if(result)
   {
      return 1;
   }
   result = regexec(&regex, num, 0, NULL, 0);
   printf("%d\n", result);
   if(result == 0)
      return 0;
   else if(reti == REG_NOMATCH)
      return 1;
   else
      return -1;
}

int isValidPassword(char * password)
{

}