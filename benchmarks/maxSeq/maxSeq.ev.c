#include<stdio.h>
#include<malloc.h>
int EV_seed;
int mod(int EV_a,int EV_b)
{
return (EV_a-((EV_a/EV_b)*EV_b));
}
int rand()
{
EV_seed = ((mod(EV_seed, 532)*24)+6);
return EV_seed;
}
int main()
{
int EV_num;
int EV_maxBest;
int EV_maxCur;
int EV_count;
int EV_rTemp;
scanf("%d", &EV_count);
scanf("%d", &EV_seed);
EV_rTemp = rand();
EV_num = (mod(EV_rTemp, 201)-100);
EV_maxBest = EV_num;
EV_maxCur = EV_num;
while ((EV_count>0))
{
EV_count = (EV_count-1);
EV_rTemp = rand();
EV_num = (mod(EV_rTemp, 201)-100);
if ((EV_maxCur>0))
{
EV_maxCur = (EV_maxCur+EV_num);
}
else
{
EV_maxCur = EV_num;
}
if ((EV_maxCur>EV_maxBest))
{
EV_maxBest = EV_maxCur;
}
}
printf("%d\n",EV_maxBest);
return 0;
}
