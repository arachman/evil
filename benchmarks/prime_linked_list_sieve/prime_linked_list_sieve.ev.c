#include<stdio.h>
#include<malloc.h>
struct EV_Node
{
struct EV_Node * EV_next;
struct EV_Node * EV_prev;
int EV_num;
};
struct EV_Node * initNode(int EV_num,struct EV_Node * EV_prev)
{
struct EV_Node * EV_n;
EV_n = (struct EV_Node*)malloc(sizeof(struct EV_Node));
EV_n->EV_prev = EV_prev;
EV_n->EV_num = EV_num;
EV_n->EV_next = NULL;
return EV_n;
}
void printNode(struct EV_Node * EV_n)
{
printf("%d\n",EV_n->EV_num);
}
int abs(int EV_val)
{
if ((EV_val>0))
{
return EV_val;
}
return (-EV_val);
}
int sqrt(int EV_n)
{
int EV_xn;
int EV_xn1;
EV_xn = 1;
EV_xn1 = ((EV_xn+(EV_n/EV_xn))/2);
while ((abs((EV_xn1-EV_xn))>1))
{
EV_xn = EV_xn1;
EV_xn1 = ((EV_xn+(EV_n/EV_xn))/2);
}
while (((EV_xn1*EV_xn1)>EV_n))
{
EV_xn1 = (EV_xn1-1);
}
return EV_xn1;
}
struct EV_Node * makeList(int EV_size)
{
struct EV_Node * EV_head;
struct EV_Node * EV_cur;
int EV_i;
EV_head = initNode(2, NULL);
EV_cur = EV_head;
EV_i = 3;
while ((EV_i<(EV_size+1)))
{
EV_cur->EV_next = initNode(EV_i, EV_cur);
EV_cur = EV_cur->EV_next;
EV_i = (EV_i+2);
}
return EV_head;
}
struct EV_Node * rem(struct EV_Node * EV_n)
{
struct EV_Node * EV_tmp;
EV_tmp = EV_n;
if ((EV_n->EV_prev!=NULL))
{
EV_n->EV_prev->EV_next = EV_n->EV_next;
}
if ((EV_n->EV_next!=NULL))
{
EV_n->EV_next->EV_prev = EV_n->EV_prev;
}
free(EV_n);
return EV_tmp;
}
void nuke(struct EV_Node * EV_n)
{
int EV_step;
int EV_nxt;
EV_step = EV_n->EV_num;
EV_nxt = (EV_step*2);
EV_n = EV_n->EV_next;
while ((EV_n!=NULL))
{
if ((EV_n->EV_num==EV_nxt))
{
EV_n = rem(EV_n);
EV_nxt = (EV_nxt+EV_step);
}
else
{
if ((EV_n->EV_num>EV_nxt))
{
EV_nxt = (((EV_n->EV_num+EV_step)/EV_step)*EV_step);
}
EV_n = EV_n->EV_next;
}
}
}
void reduce(struct EV_Node * EV_n,int EV_len)
{
int EV_lim;
EV_lim = sqrt(EV_len);
while ((EV_n->EV_num<EV_lim))
{
nuke(EV_n);
EV_n = EV_n->EV_next;
}
}
void printList(struct EV_Node * EV_n)
{
while ((EV_n!=NULL))
{
printNode(EV_n);
EV_n = EV_n->EV_next;
}
}
int main()
{
int EV_NUM;
struct EV_Node * EV_lst;
scanf("%d", &EV_NUM);
if ((EV_NUM<2))
{
return (-1);
}
EV_lst = makeList(EV_NUM);
reduce(EV_lst, EV_NUM);
printList(EV_lst);
return 0;
}
