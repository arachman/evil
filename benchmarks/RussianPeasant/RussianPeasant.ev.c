#include<stdio.h>
#include<malloc.h>
struct EV_results
{
int EV_multiplies;
int EV_answer;
};
struct EV_results * binexp(int EV_base,int EV_pow)
{
struct EV_results * EV_sub;
EV_sub = (struct EV_results*)malloc(sizeof(struct EV_results));
if ((EV_pow==0))
{
EV_sub->EV_answer = 1;
EV_sub->EV_multiplies = 0;
return EV_sub;
}
else
{
if ((EV_pow==1))
{
EV_sub->EV_answer = EV_base;
EV_sub->EV_multiplies = 0;
return EV_sub;
}
}
EV_sub = binexp(EV_base, (EV_pow/2));
if ((((EV_pow/2)*2)!=EV_pow))
{
EV_sub->EV_answer = ((EV_base*EV_sub->EV_answer)*EV_sub->EV_answer);
EV_sub->EV_multiplies = (EV_sub->EV_multiplies+2);
}
else
{
EV_sub->EV_answer = (EV_sub->EV_answer*EV_sub->EV_answer);
EV_sub->EV_multiplies = (EV_sub->EV_multiplies+1);
}
return EV_sub;
}
struct EV_results * rpexp(int EV_base,int EV_pow)
{
int EV_p;
int EV_sub;
struct EV_results * EV_r;
EV_r = (struct EV_results*)malloc(sizeof(struct EV_results));
EV_r->EV_multiplies = 0;
EV_p = EV_pow;
EV_sub = EV_base;
if ((EV_pow==0))
{
EV_r->EV_answer = 1;
return EV_r;
}
while ((((EV_p/2)*2)==EV_p))
{
EV_sub = (EV_sub*EV_sub);
EV_p = (EV_p/2);
EV_r->EV_multiplies = (EV_r->EV_multiplies+1);
}
EV_r->EV_answer = EV_sub;
EV_p = (EV_p/2);
while ((EV_p>0))
{
EV_sub = (EV_sub*EV_sub);
EV_r->EV_multiplies = (EV_r->EV_multiplies+1);
if ((((EV_p/2)*2)!=EV_p))
{
EV_r->EV_answer = (EV_r->EV_answer*EV_sub);
EV_r->EV_multiplies = (EV_r->EV_multiplies+1);
}
EV_p = (EV_p/2);
}
return EV_r;
}
int checkexp(int EV_base,int EV_pow)
{
int EV_i;
int EV_result;
EV_i = 0;
EV_result = 1;
if ((EV_pow==0))
{
return 1;
}
while ((EV_i<EV_pow))
{
EV_result = (EV_result*EV_base);
EV_i = (EV_i+1);
}
return EV_result;
}
int main()
{
int EV_base;
int EV_pow;
int EV_c;
struct EV_results * EV_b;
struct EV_results * EV_rp;
scanf("%d", &EV_base);
scanf("%d", &EV_pow);
if ((EV_pow<0))
{
return 0;
}
EV_b = binexp(EV_base, EV_pow);
printf("%d\n",EV_b->EV_answer);
printf("%d\n",EV_b->EV_multiplies);
EV_rp = rpexp(EV_base, EV_pow);
printf("%d\n",EV_rp->EV_answer);
printf("%d\n",EV_rp->EV_multiplies);
EV_c = checkexp(EV_base, EV_pow);
if (((EV_b->EV_answer==EV_c)&&(EV_rp->EV_answer==EV_c)))
{
printf("%d\n",1);
}
else
{
printf("%d\n",0);
}
return 0;
}
