#include<stdio.h>
#include<malloc.h>
int EV_largeprime1;
int EV_largeprime2;
int EV_M;
int abs(int EV_n)
{
if ((EV_n<0))
{
EV_n = (EV_n-(2*EV_n));
}
return EV_n;
}
int mod(int EV_a,int EV_n)
{
return (EV_a-(EV_n*(EV_a/EV_n)));
}
int gcd(int EV_a,int EV_b)
{
if ((EV_b==0))
{
return EV_a;
}
else
{
return gcd(EV_b, mod(EV_a, EV_b));
}
}
int blum(int EV_in)
{
int EV_M;
EV_M = (EV_largeprime1*EV_largeprime2);
return (abs(mod((EV_in*EV_in), EV_M))+1);
}
int rho(int EV_n)
{
int EV_x;
int EV_y;
int EV_d;
int EV_t;
EV_x = 2;
EV_y = 2;
EV_d = 1;
while ((EV_d==1))
{
EV_x = blum(EV_x);
EV_y = blum(blum(EV_y));
EV_t = abs((EV_x-EV_y));
EV_d = gcd(EV_t, EV_n);
}
if ((EV_d==EV_n))
{
return 0;
}
else
{
return EV_d;
}
}
int main()
{
int EV_composite;
EV_largeprime1 = 30211;
EV_largeprime2 = 30223;
scanf("%d", &EV_composite);
while ((EV_composite!=0))
{
printf("%d\n",rho(EV_composite));
scanf("%d", &EV_composite);
}
return 0;
}
