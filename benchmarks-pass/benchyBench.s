	.common torf,4,4
	.common global1,4,4
	.common global2,4,4
	.section	".text"
	.align 4
	.global theFunc
	.type	theFunc, #function
theFunc:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L1:
	mov	%i0, %l1
	mov	%i1, %l2
	ldsw	[%fp-4], %l0
	mov	8, %o0
	call	malloc
	nop
	mov	%o0, %l0
	st	%l0, [%fp-4]
	ldsw	[%fp-4], %l0
	st	%l1, [%l0+0]
	ldsw	[%fp-4], %l0
	st	%l2, [%l0+4]
.L3:
	ldsw	[%fp-4], %l0
	ldsw	[%l0+4], %l0
.L4:
	ldsw	[%fp-4], %l0
	ldsw	[%l0+0], %l1
	ldsw	[%fp-4], %l0
	ldsw	[%l0+0], %l0
	mulx	%l1, %l0, %l0
	mov	4, %l1
	mulx	%l0, %l1, %l1
	mov	4, %l0
	mulx	%l1, %l0, %l1
	ldsw	[%fp-4], %l0
	ldsw	[%l0+0], %l0
	mulx	%l1, %l0, %l0
	sethi	%hi(.LLC0), %g1
	or	%g1, %lo(.LLC0), %o0
	mov	%l0, %o1
	call	printf
	nop
	ba	.L5
	nop
.L5:
	mov	3, %l0
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l0, %o1
	call	printf
	nop
	ldsw	[%fp-4], %l0
	mov	%l0, %o0
	call	free
	nop
	ba	.L2
	nop
.L2:
	ret
	restore
	.size	theFunc, .-theFunc
	.align 4
	.global fib
	.type	fib, #function
fib:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L6:
	mov	%i0, %l0
.L8:
	mov	2, %l2
	mov	1, %l1
	cmp	%l0, %l2
	bl	.L9
	nop
	ba	.L10
	nop
.L9:
	mov	%l0, %i0
	ba	.L7
	nop
	ba	.L11
	nop
.L10:
	mov	1, %l1
	sub	%l0, %l1, %l1
	mov	%l1, %o0
	call	fib
	nop
	mov	%o0, %l1
	mov	2, %l2
	sub	%l0, %l2, %l0
	mov	%l0, %o0
	call	fib
	nop
	mov	%o0, %l0
	add	%l1, %l0, %l0
	mov	%l0, %i0
	ba	.L7
	nop
	ba	.L11
	nop
.L11:
.L7:
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
.L12:
	sethi	%hi(global1), %g1
	or	%g1, %lo(global1), %l0
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l0, %o1
	call	scanf
	nop
	add	%fp, -8, %l0
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l0, %o1
	call	scanf
	nop
	sethi	%hi(global1), %g1
	or	%g1, %lo(global1), %g2
	ld	[%g2], %l0
	mov	1, %l1
	mov	%l0, %o0
	mov	%l1, %o1
	call	theFunc
	nop
	ldsw	[%fp-4], %l0
	ldsw	[%fp-8], %l0
	mov	%l0, %o0
	call	fib
	nop
	mov	%o0, %l0
	mov	%l0, %l0
	sethi	%hi(global1), %g1
	or	%g1, %lo(global1), %g2
	ld	[%g2], %l0
	sethi	%hi(global2), %g1
	or	%g1, %lo(global2), %g2
	ld	[%g2], %l1
	add	%l0, %l1, %l0
	mov	%l0, %i0
	ba	.L13
	nop
.L13:
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
