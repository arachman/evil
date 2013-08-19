#include<stdio.h>
#include<malloc.h>
struct EV_ego
{
int EV_a;
int EV_b;
};
struct EV_superego
{
int EV_first;
int EV_sec;
int EV_third;
};
int EV_global1;
int EV_global2;
int EV_torf;
void theFunc(int EV_jerky,int EV_twerky)
{
struct EV_ego * EV_helper;
EV_helper = (struct EV_ego*)malloc(sizeof(struct EV_ego));
EV_helper->EV_a = EV_jerky;
EV_helper->EV_b = EV_twerky;
if (EV_helper->EV_b)
{
printf("%d ",((((EV_helper->EV_a*EV_helper->EV_a)*4)*4)*EV_helper->EV_a));
}
printf("%d\n",3);
free(EV_helper);
return ;
}
int fib(int EV_i)
{
if ((EV_i<2))
{
return EV_i;
}
else
{
return (fib((EV_i-1))+fib((EV_i-2)));
}
}
int main()
{
int EV_result;
int EV_six;
scanf("%d", &EV_global1);
scanf("%d", &EV_six);
theFunc(EV_global1, 1);
EV_result = fib(EV_six);
return (EV_global1+EV_global2);
}
