	.section	".text"
	.align 4
	.global fib
	.type	fib, #function
fib:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L1:
	mov	%i0, %l2
.L3:
	mov	%g0, %l0
	mov	1, %l1
	cmp	%l2, %l0
	be	.L4
	nop
	ba	.L5
	nop
.L4:
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L2
	nop
	ba	.L5
	nop
.L5:
.L6:
	mov	1, %l1
	mov	1, %l0
	cmp	%l2, %l1
	ble	.L7
	nop
	ba	.L8
	nop
.L7:
	mov	1, %l0
	mov	%l0, %i0
	ba	.L2
	nop
	ba	.L9
	nop
.L8:
	mov	1, %l0
	sub	%l2, %l0, %l0
	mov	%l0, %o0
	call	fib
	nop
	mov	%o0, %l1
	mov	2, %l0
	sub	%l2, %l0, %l0
	mov	%l0, %o0
	call	fib
	nop
	mov	%o0, %l0
	add	%l1, %l0, %l0
	mov	%l0, %i0
	ba	.L2
	nop
	ba	.L9
	nop
.L9:
.L2:
	ret
	restore
	.size	fib, .-fib
	.align 4
	.global main
	.type	main, #function
main:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L10:
	add	%fp, -4, %l2
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l2, %o1
	call	scanf
	nop
.L12:
	ldsw	[%fp-4], %l2
	mov	%g0, %l0
	mov	1, %l1
	cmp	%l2, %l0
	bl	.L13
	nop
	ba	.L14
	nop
.L13:
	mov	1, %l0
	sub	%g0, %l0, %l0
	mov	%l0, %i0
	ba	.L11
	nop
	ba	.L14
	nop
.L14:
	mov	%l2, %o0
	call	fib
	nop
	mov	%o0, %l0
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l0, %o1
	call	printf
	nop
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L11
	nop
.L11:
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
