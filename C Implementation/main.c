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
   long num1, num2;
   int valid = -1;
   char finName[51];
   char foutName[51];
   FILE * fin = NULL;
   FILE * fout = NULL;
   FILE * passOut = NULL;
   
   while(valid !=0)
   {
      printf("Enter first name (Up to 50 characters)\n");
      fgets(buf, sizeof(buf), stdin);
      strip(buf);
      stdin = freopen(NULL, "r", stdin);
      valid = isValidName(buf);
      if(valid == 0)
         strncpy(firstName, buf, sizeof(firstName));
   }
   valid = -1;
   
   while(valid != 0)
   {
      printf("Enter last name (Up to 50 characters)\n");
      fgets(buf, sizeof(buf), stdin);
      strip(buf);
      stdin = freopen(NULL, "r", stdin);
      valid = isValidName(buf);
      if(valid == 0)
         strncpy(lastName, buf, sizeof(lastName));
   }
   valid = -1;
   
   while(valid != 0)
   {
      printf("Enter an int (between -2147483647 and 2147483647)\n");
      fgets(buf, sizeof(buf), stdin);
      strip(buf);
      stdin = freopen(NULL, "r", stdin);
      valid = isValidInt(buf);
      if(valid == 0)
         num1 = atol(buf);
   }
   valid = -1;
   
   while(valid != 0)
   {
      printf("Enter another int (between -2147483647 and 2147483647)\n");
      fgets(buf, sizeof(buf), stdin);
      strip(buf);
      stdin = freopen(NULL, "r", stdin);
      valid = isValidInt(buf);
      if(valid == 0)
         num2 = atol(buf);
   }
   valid = -1;
   
   while(valid != 0)
   {
      printf("Enter input file (Must exist in the current directory. Must be different than output file, and cannot main.c, myUtils.c, or passwords.txt. Up to 50 characters, must be a .java, .c, or .txt file)\n");
      fgets(buf, sizeof(buf), stdin);
      strip(buf);
      stdin = freopen(NULL, "r", stdin);
      valid = isValidFile(buf);
      if(valid == 0)
      {
         if(strcmp(buf, "main.c") == 0 || strcmp(buf, "myUtils.c") == 0 || strcmp(buf, "passwords.txt") == 0)
         {
            printf("Input file cannot be main.c, myUtils.c, or passwords.txt\n");
            valid = -1;
         }
      }
      if(valid == 0)
      {
         strncpy(finName, buf, sizeof(finName));
         fin = fopen(finName, "r");
         if(fin == NULL)
         {
            printf("Invalid input, file does not exist\n");
            valid = -1;
         }
         else
            fclose(fin);
      }
   }
   valid = -1;
   
   while(valid != 0)
   {   
      printf("Enter output file (Must be in current directoy. Must be different than input file, and cannot main.c, myUtils.c, passwords.txt. Up to 50 characters, must be a .java, .c, or .txt file)\n");
      fgets(buf, sizeof(buf), stdin);
      strip(buf);
      stdin = freopen(NULL, "r", stdin);
      valid = isValidFile(buf);
      if(valid == 0)
      {
         if(strcmp(buf, "main.c") == 0 || strcmp(buf, "myUtils.c") == 0 || strcmp(buf, "passwords.txt") == 0)
         {
            printf("Output file cannot be main.c, myUtils.c, or passwords.txt\n");
            valid = -1;
         }
      }
      if(valid == 0)
         strncpy(foutName, buf, sizeof(foutName));
      if(strcmp(finName, foutName) == 0)
      {
         printf("Invalid input. Output file cannot be the same as the input file.\n");
         valid = -1;
      }
      
   }
   valid = -1;
   
   while(valid != 0)
   {
      printf("Enter a password (Must be between 8 and 32 characters. May include numbers, uppercase, lowercase, and !,@,#,$,%%)\n");
      fgets(buf, sizeof(buf), stdin);
      strip(buf);
      stdin = freopen(NULL, "r", stdin);
      valid = isValidPassword(buf);
      if(valid == 0)
         strncpy(password, buf, sizeof(password));
      printf("Please re-enter your password for verification:\n");
      fgets(buf, sizeof(buf), stdin);
      strip(buf);
      stdin = freopen(NULL, "r", stdin);
      if(strcmp(buf, password) != 0)
      {
         printf("Incorrect password, please try again\n");
         valid = -1;
      }
   }
   passOut = fopen("password.txt", "a");
   if(passOut != NULL)
   {
      fprintf(passOut, "%s", password);
      fclose(passOut);
   }
   fout = fopen(foutName, "w");
   fprintf(fout, "First Name: %s\nLast Name: %s\nnum1+num2: %li\nnum1*num2: %li\n", firstName, lastName, (num1+num2), (num1*num2));
   fclose(fout);
   writeToFile(finName, foutName);
   
   return 0;
}
