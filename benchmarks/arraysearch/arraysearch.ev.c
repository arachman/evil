#include<stdio.h>
#include<malloc.h>
struct EV_Node
{
int EV_value;
struct EV_Node * EV_next;
};
struct EV_Node * LinkedListnewNode(int EV_value)
{
struct EV_Node * EV_node;
EV_node = (struct EV_Node*)malloc(sizeof(struct EV_Node));
EV_node->EV_value = EV_value;
EV_node->EV_next = NULL;
return EV_node;
}
struct EV_Node * LinkedListcreate(int EV_size)
{
struct EV_Node * EV_head;
struct EV_Node * EV_cur;
int EV_idx;
EV_head = LinkedListnewNode(0);
EV_cur = EV_head;
EV_idx = 1;
while ((EV_idx<EV_size))
{
EV_cur->EV_next = LinkedListnewNode(EV_idx);
EV_cur = EV_cur->EV_next;
EV_idx = (EV_idx+1);
}
return EV_head;
}
void LinkedListdestroy(struct EV_Node * EV_head)
{
struct EV_Node * EV_cur;
struct EV_Node * EV_next;
EV_cur = EV_head;
while ((EV_cur!=NULL))
{
EV_next = EV_cur->EV_next;
free(EV_cur);
EV_cur = EV_next;
}
}
struct EV_Node * LinkedListfind(struct EV_Node * EV_node,int EV_needle)
{
if ((EV_node==NULL))
{
return NULL;
}
if ((EV_node->EV_value==EV_needle))
{
return EV_node;
}
return LinkedListfind(EV_node->EV_next, EV_needle);
}
int main()
{
int EV_size;
int EV_needle;
int EV_repeats;
struct EV_Node * EV_head;
struct EV_Node * EV_cur;
scanf("%d", &EV_size);
scanf("%d", &EV_needle);
scanf("%d", &EV_repeats);
EV_cur = NULL;
EV_head = LinkedListcreate(EV_size);
while ((EV_repeats>0))
{
EV_cur = LinkedListfind(EV_head, EV_needle);
EV_repeats = (EV_repeats-1);
}
if ((EV_cur==NULL))
{
printf("%d\n",(-1));
LinkedListdestroy(EV_head);
return (-1);
}
printf("%d\n",EV_cur->EV_value);
LinkedListdestroy(EV_head);
return 0;
}
