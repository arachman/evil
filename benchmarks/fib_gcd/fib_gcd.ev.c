#include<stdio.h>
#include<malloc.h>
struct EV_B
{
int EV_fa;
int EV_fb;
};
struct EV_A
{
int EV_dividend;
int EV_divisor;
struct EV_B * EV_b;
};
int mod(int EV_a,int EV_b)
{
if ((EV_a<EV_b))
{
return EV_a;
}
else
{
return mod((EV_a-EV_b), EV_b);
}
}
int gcd(struct EV_A * EV_a)
{
int EV_temp;
while ((EV_a->EV_divisor!=0))
{
EV_temp = EV_a->EV_divisor;
EV_a->EV_divisor = mod(EV_a->EV_dividend, EV_a->EV_divisor);
EV_a->EV_dividend = EV_temp;
}
return EV_a->EV_dividend;
}
int fib(int EV_x)
{
if ((EV_x==0))
{
return 0;
}
if ((EV_x==1))
{
return 1;
}
else
{
return (fib((EV_x-1))+fib((EV_x-2)));
}
}
int main()
{
int EV_x;
int EV_y;
int EV_result;
struct EV_A * EV_a;
EV_a = (struct EV_A*)malloc(sizeof(struct EV_A));
EV_a->EV_b = (struct EV_B*)malloc(sizeof(struct EV_B));
scanf("%d", &EV_x);
scanf("%d", &EV_y);
while (((EV_x!=0)&&(EV_y!=0)))
{
EV_a->EV_b->EV_fa = fib(EV_x);
EV_a->EV_b->EV_fb = fib(EV_y);
EV_a->EV_divisor = EV_a->EV_b->EV_fa;
EV_a->EV_dividend = EV_a->EV_b->EV_fb;
EV_result = gcd(EV_a);
printf("%d\n",EV_result);
scanf("%d", &EV_x);
scanf("%d", &EV_y);
}
return 0;
}
