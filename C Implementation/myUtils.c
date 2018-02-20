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
   if(result)
   {
      regfree(&regex);
      printf("Regex failed to compile\n");
      return -1;
   }
   result = regexec(&regex, name, 0, NULL, 0);
   if(result == 0)
   {
      regfree(&regex);
      return 0;
   }
   else if(result == REG_NOMATCH)
   {
      regfree(&regex);
      printf("Invalid input\n");
      return -1;
   }
   else
   {
      regfree(&regex);
      printf("Regex execution returned invalid result\n");
      return -1;
   }
}

int isValidInt(char * num)
{
   regex_t regex;
   int result = 0;
   long theNum = 0;
   result = regcomp(&regex, "^[\\-]?([0-9]{1,10})$", REG_EXTENDED);
   if(result)
   {
      regfree(&regex);
      printf("Regex failed to compile\n");
      return -1;
   }
   result = regexec(&regex, num, 0, NULL, 0);
   if(result == 0)
   {
      theNum = atol(num);
      long max = 2147483648;
      long min = -2147483648;
      if(theNum > min && theNum < max)
      {
         regfree(&regex);
         return 0;
      }
      else
      {
         regfree(&regex);
         printf("Invalid input\n");
         return -1;
      }
   }
   else if(result == REG_NOMATCH)
   {
      regfree(&regex);
      printf("Invalid input\n");
      return -1;
   }
   else
   {
      regfree(&regex);
      printf("Regex execution returned invalid result\n");
      return -1;
   }
}

int isValidPassword(char * password)
{
   regex_t regex;
   int result;
   result = regcomp(&regex, "^([0-9]|[a-z]|[A-Z]|[!]|[@]|[#]|[$]|[%%]){8,32}$", REG_EXTENDED);
   if(result)
   {
      regfree(&regex);
      printf("Regex failed to compile\n");
      return -1;
   }
   result = regexec(&regex, password, 0, NULL, 0);
   if(result == 0)
   {
      regfree(&regex);
      return 0;
   }
   else if(result == REG_NOMATCH)
   {
      regfree(&regex);
      printf("Invalid input\n");
      return -1;
   }
   else
   {
      regfree(&regex);
      printf("Regex execution returned invalid result\n");
      return -1;
   }
}

int isValidFile(char * file)
{
   regex_t regex;
   int result;
   result = regcomp(&regex, "^(([a-z]|[A-Z]|[0-9]){1,47}.txt|([a-z]|[A-Z]|[0-9]){1,49}.c|([a-z]|[A-Z]|[0-9]){1,46}.java)$", REG_EXTENDED);
   if(result)
   {
      regfree(&regex);
      printf("Regex failed to compile\n");
      return -1;
   }
   result = regexec(&regex, file, 0, NULL, 0);
   if(result == 0)
   {
      regfree(&regex);
      return 0;
   }
   else if(result == REG_NOMATCH)
   {
      regfree(&regex);
      printf("Invalid input\n");
      return -1;
   }
   else
   {
      regfree(&regex);
      printf("Regex execution returned invalid result\n");
      return -1;
   }
}

void writeToFile(char * finName, char * foutName)
{
   FILE * fout = fopen(foutName, "a");
   if(fout != NULL)
   {
      int myfd = fileno(fout);
      close(1);
      dup2(myfd, STDOUT_FILENO);
      fclose(fout);
      char * argv[3];
      argv[0] = "cat";
      argv[1] = finName;
      argv[2] = NULL;
      execvp(argv[0], argv);
   }
   else
   {
      printf("Unable to write to file\n");
   }
}

char * hashedPass(char * password)
{
   unsigned long seed[2];
   char salt[] = "$1$........";
   const char *const seedchars = 
    "./0123456789ABCDEFGHIJKLMNOPQRST"
    "UVWXYZabcdefghijklmnopqrstuvwxyz";
   int i;
   seed[0] = time(NULL);
   seed[1] = getpid() ^ (seed[0] >> 14 && 0x30000);
   for(i = 0; i < 8; i++)
      salt[3+i] = seedchars[(seed[i/5] >> (i%5)*6) & 0x3f];
   char * coded = crypt(password, salt);
   return coded;
}