#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <regex.h>
#include "./myUtils.h"

int main()
{
   char firstName[51];
   char lastName[51];
   long num1;
   long num2;
   char finName[51];
   char foutName[51];
   FILE * fout = NULL;
   getFname(firstName);

   getLname(lastName);

   num1 = getNum1();

   num2 = getNum2();

   getFin(finName);

   getFout(foutName, finName);

   verifyHashes();
   fout = fopen(foutName, "w");
   fprintf(fout, "First Name: %s\nLast Name: %s\nnum1+num2: %li\nnum1*num2: %li\n", firstName, lastName, (num1+num2), (num1*num2));
   fclose(fout);
   writeToFile(finName, foutName);
   
   return 0;
}
