#include<stdio.h>
#include<malloc.h>
struct EV_node
{
struct EV_node * EV_n;
int EV_val;
};
struct EV_node * newNode(int EV_val)
{
struct EV_node * EV_ret;
EV_ret = (struct EV_node*)malloc(sizeof(struct EV_node));
EV_ret->EV_val = EV_val;
EV_ret->EV_n = NULL;
return EV_ret;
}
void freeNode(struct EV_node * EV_n)
{
if ((EV_n->EV_n!=NULL))
{
freeNode(EV_n->EV_n);
}
free(EV_n);
}
struct EV_node * append(struct EV_node * EV_list,int EV_newval)
{
if ((EV_list==NULL))
{
return newNode(EV_newval);
}
else
{
EV_list->EV_n = append(EV_list->EV_n, EV_newval);
}
return EV_list;
}
int readVal(struct EV_node * EV_list)
{
struct EV_node * EV_prev;
struct EV_node * EV_cur;
int EV_digit;
int EV_val;
EV_digit = 1;
EV_val = 0;
if ((EV_list==NULL))
{
return 0;
}
EV_cur = EV_list;
while ((EV_cur->EV_n!=NULL))
{
EV_cur = EV_cur->EV_n;
}
while ((EV_cur!=EV_list))
{
EV_val = (EV_val+(EV_cur->EV_val*EV_digit));
EV_prev = EV_list;
while ((EV_prev->EV_n!=EV_cur))
{
EV_prev = EV_prev->EV_n;
}
EV_cur = EV_prev;
EV_digit = (EV_digit*10);
}
EV_val = (EV_val+(EV_cur->EV_val*EV_digit));
return EV_val;
}
int nextFib(int EV_nminusone,int EV_n)
{
return (EV_n+EV_nminusone);
}
int isFib(int EV_num)
{
int EV_n;
int EV_n1;
int EV_n2;
EV_n = 1;
EV_n1 = 1;
while ((EV_n<EV_num))
{
EV_n2 = EV_n1;
EV_n1 = EV_n;
EV_n = nextFib(EV_n2, EV_n1);
}
if ((EV_num==EV_n))
{
return 1;
}
return 0;
}
int main()
{
struct EV_node * EV_list;
int EV_c;
int EV_v;
EV_list = NULL;
scanf("%d", &EV_c);
while ((EV_c!=(-1)))
{
if ((EV_c<10))
{
EV_list = append(EV_list, EV_c);
}
else
{
EV_v = readVal(EV_list);
printf("%d\n",EV_v);
while ((!isFib(EV_v)))
{
EV_v = (EV_v-1);
}
printf("%d\n",EV_v);
freeNode(EV_list);
EV_list = NULL;
}
scanf("%d", &EV_c);
}
return 0;
}
