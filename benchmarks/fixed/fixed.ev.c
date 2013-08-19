#include<stdio.h>
#include<malloc.h>
int int2fix(int EV_arg)
{
return (EV_arg*65536);
}
void printfixhelper(int EV_arg)
{
int EV_digit;
EV_digit = (EV_arg-((EV_arg/10)*10));
}
void printfix(int EV_arg)
{
int EV_whole;
int EV_fract;
EV_whole = (EV_arg/65536);
EV_fract = (EV_arg-(EV_whole*65536));
printf("%d ",EV_whole);
printf("%d\n",EV_fract);
}
int fixmul(int EV_arg1,int EV_arg2)
{
int EV_x;
int EV_y;
int EV_z;
EV_x = (EV_arg1/256);
EV_y = (EV_arg2/256);
EV_z = (EV_x*EV_y);
return EV_z;
}
int xcubed(int EV_x)
{
return fixmul(EV_x, fixmul(EV_x, EV_x));
}
int integrate(int EV_start,int EV_end,int EV_n)
{
int EV_acc;
int EV_delta;
int EV_x;
EV_acc = int2fix(0);
EV_start = int2fix(EV_start);
EV_end = int2fix(EV_end);
EV_delta = ((EV_end-EV_start)/EV_n);
if ((EV_delta==0))
{
EV_delta = 1;
}
EV_x = EV_start;
while ((EV_x<=EV_end))
{
EV_acc = (EV_acc+(xcubed(EV_x)/EV_n));
EV_x = (EV_x+EV_delta);
}
return EV_acc;
}
int main()
{
int EV_a;
int EV_b;
int EV_n;
int EV_i;
int EV_cases;
scanf("%d", &EV_cases);
while ((EV_cases>0))
{
scanf("%d", &EV_a);
scanf("%d", &EV_b);
scanf("%d", &EV_n);
EV_i = 0;
while ((EV_i<32768))
{
integrate(EV_a, EV_b, EV_n);
EV_i = (EV_i+1);
}
printfix(integrate(EV_a, EV_b, EV_n));
EV_cases = (EV_cases-1);
}
return 0;
}
