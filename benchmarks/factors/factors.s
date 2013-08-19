	.section	".text"
	.align 4
	.global mymod
	.type	mymod, #function
mymod:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L1:
	mov	%i0, %l0
	mov	%i1, %l1
.L3:
	mov	1, %l2
	cmp	%l0, %l1
	bge	.L4
	nop
	ba	.L5
	nop
.L4:
	sub	%l0, %l1, %l0
	mov	%l0, %l0
.L6:
	mov	1, %l2
	cmp	%l0, %l1
	bge	.L4
	nop
	ba	.L5
	nop
.L5:
	mov	%l0, %i0
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
.L7:
	ldsw	[%fp-4], %l0
	mov	%g0, %l0
	mov	%l0, %l0
	ldsw	[%fp-8], %l2
	mov	%g0, %l0
	mov	%l0, %l2
	ldsw	[%fp-12], %l0
	mov	%g0, %l0
	mov	%l0, %l0
	add	%fp, -4, %l0
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l0, %o1
	call	scanf
	nop
	ldsw	[%fp-4], %l0
	mov	%l0, %l0
	mov	2, %l1
	mov	%l1, %l2
.L9:
	mov	1, %l1
	cmp	%l2, %l0
	bl	.L10
	nop
	ba	.L11
	nop
.L10:
.L12:
	mov	%l0, %o0
	mov	%l2, %o1
	call	mymod
	nop
	mov	%o0, %l1
	mov	%g0, %l4
	mov	1, %l3
	cmp	%l1, %l4
	be	.L13
	nop
	ba	.L14
	nop
.L13:
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l2, %o1
	call	printf
	nop
	mov	%l0, %o0
	mov	%l2, %o1
	call	.div
	nop
	mov	%o0, %l0
	mov	%l0, %l0
	mov	2, %l1
	mov	%l1, %l2
	ba	.L14
	nop
.L14:
	mov	1, %l1
	add	%l2, %l1, %l1
	mov	%l1, %l2
.L15:
	mov	1, %l1
	cmp	%l2, %l0
	bl	.L10
	nop
	ba	.L11
	nop
.L11:
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l0, %o1
	call	printf
	nop
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L8
	nop
.L8:
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
