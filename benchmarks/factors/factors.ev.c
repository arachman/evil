#include<stdio.h>
#include<malloc.h>
int mymod(int EV_x,int EV_y)
{
while ((EV_x>=EV_y))
{
EV_x = (EV_x-EV_y);
}
return EV_x;
}
int main()
{
int EV_x;
int EV_y;
int EV_n;
EV_x = 0;
EV_y = 0;
EV_n = 0;
scanf("%d", &EV_x);
EV_n = EV_x;
EV_y = 2;
while ((EV_y<EV_n))
{
if ((mymod(EV_n, EV_y)==0))
{
printf("%d\n",EV_y);
EV_n = (EV_n/EV_y);
EV_y = 2;
}
EV_y = (EV_y+1);
}
printf("%d\n",EV_n);
return 0;
}
