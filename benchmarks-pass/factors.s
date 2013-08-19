	.section	".text"
	.align 4
	.global mymod
	.type	mymod, #function
mymod:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L1:
	mov	%i0, %l1
	mov	%i1, %l2
.L3:
	mov	1, %l0
	cmp	%l1, %l2
	bge	.L4
	nop
	ba	.L5
	nop
.L4:
	sub	%l1, %l2, %l0
	mov	%l0, %l1
	mov	1, %l0
	cmp	%l1, %l2
	bge	.L4
	nop
	ba	.L5
	nop
.L5:
	mov	%l1, %i0
	ba	.L2
	nop
.L2:
	ret
	restore
	.size	mymod, .-mymod
	.align 4
	.global main
	.type	main, #function
main:
	!#PROLOGUE# 0
	save	%sp, -112, %sp
	!#PROLOGUE 1
.L6:
	mov	%g0, %l0
	mov	%l0, %l1
	mov	%g0, %l0
	mov	%l0, %l2
	ldsw	[%fp-12], %l3
	mov	%g0, %l0
	mov	%l0, %l3
	add	%fp, -4, %l1
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l1, %o1
	call	scanf
	nop
	ldsw	[%fp-4], %l1
	mov	%l1, %l3
	mov	2, %l0
	mov	%l0, %l2
.L8:
	mov	1, %l0
	cmp	%l2, %l3
	bl	.L9
	nop
	ba	.L10
	nop
.L9:
.L11:
	mov	%l3, %o0
	mov	%l2, %o1
	call	mymod
	nop
	mov	%o0, %l4
	mov	%g0, %l0
	mov	1, %l1
	cmp	%l4, %l0
	be	.L12
	nop
	ba	.L13
	nop
.L12:
	mov	1, %l1
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l2, %o1
	call	printf
	nop
	mov	%l3, %o0
	mov	%l2, %o1
	call	.div
	nop
	mov	%o0, %l0
	mov	%l0, %l3
	mov	2, %l0
	mov	%l0, %l2
	ba	.L13
	nop
.L13:
	mov	%g0, %l1
	mov	1, %l0
	add	%l2, %l0, %l0
	mov	%l0, %l2
	mov	1, %l0
	cmp	%l2, %l3
	bl	.L9
	nop
	ba	.L10
	nop
.L10:
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l3, %o1
	call	printf
	nop
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L7
	nop
.L7:
	ret
	restore
	.size	main, .-main


	.section	".rodata"
	.align 8
	.LLC0:
	.asciz "%d "
	.align 8
	.LLC1:
	.asciz "%d\n"
	.align 8
	.LLC2:
	.asciz "%d"
