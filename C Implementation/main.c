#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <regex.h>
#include "./myUtils.h"

int main()
{
   char buf[51];
   char firstName[51];
   char lastName[51];
   char password[33];
   int num1, num2;
   int valid;
   
   printf("Enter first name (Up to 50 characters)\n");
   fgets(buf, sizeof(buf), stdin);
   strip(buf);
   printf("%s\n", buf);
   valid = isValidName(buf);
   if(valid == 0)
      strncpy(firstName, buf, sizeof(firstName));
   else
   {
      //report error to outfile/errorlog file
      return 0;
   }
   
   printf("Enter last name (Up to 50 characters)\n");
   fgets(buf, sizeof(buf), stdin);
   strip(buf);
   printf("%s\n", buf);
   valid = isValidName(buf);
   if(valid == 0)
      strncpy(lastName, buf, sizeof(lastName));
   else
   {
      //report error to outfile/error log file
      return 0;
   }
   
   printf("Enter an int (between -2147483647 and 2147483647)\n");
   fgets(buf, sizeof(buf), stdin);
   strip(buf);
   valid = isValidInt(buf);
   if(valid == 0)
      num1 = atoi(buf);
   else
   {
      //report error to outfile/error log file
      return 0;
   }
   
   printf("Enter another int (between -2147483647 and 2147483647)\n");
   fgets(buf, sizeof(buf), stdin);
   strip(buf);
   valid = isValidInt(buf);
   if(valid == 0)
      num2 = atoi(buf);
   else
   {
      //report error to outfile/error log file
      return 0;
   }
   
   //do file validation here ------------------
   
   printf("Enter a password (Must be less than 32 characters. May include numbers, uppercase, lowercase, and !,@,#,$,%)\n");
   fgets(buf, sizeof(buf), stdin);
   strip(buf);
   valid = isValidPassword(buf);
   if(valid == 0)
      strncpy(password, buf, sizeof(password));
   else
   {
      //report error to outfile/error log file
      return 0;
   }
   
   
   
   return 0;
}
