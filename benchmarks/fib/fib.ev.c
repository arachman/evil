#include<stdio.h>
#include<malloc.h>
int fib(int EV_n)
{
if ((EV_n==0))
{
return 0;
}
if ((EV_n<=1))
{
return 1;
}
else
{
return (fib((EV_n-1))+fib((EV_n-2)));
}
}
int main()
{
int EV_val;
scanf("%d", &EV_val);
if ((EV_val<0))
{
return (-1);
}
printf("%d\n",fib(EV_val));
return 0;
}
