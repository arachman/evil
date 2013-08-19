#include<stdio.h>
#include<malloc.h>
void PrimeTime(int EV_val)
{
int EV_i;
int EV_isPrime;
EV_isPrime = 1;
EV_i = 2;
while ((EV_i<=(EV_val/2)))
{
if ((((EV_val/EV_i)*EV_i)==EV_val))
{
EV_isPrime = 0;
EV_i = EV_val;
}
EV_i = (EV_i+1);
}
if (EV_isPrime)
{
printf("%d\n",EV_val);
}
if ((EV_val>2))
{
PrimeTime((EV_val-1));
}
}
int main()
{
int EV_in;
EV_in = 0;
scanf("%d", &EV_in);
PrimeTime(EV_in);
return 0;
}
