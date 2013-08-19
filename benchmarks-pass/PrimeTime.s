	.section	".text"
	.align 4
	.global PrimeTime
	.type	PrimeTime, #function
PrimeTime:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L1:
	mov	%i0, %l0
	ldsw	[%fp-24], %l1
	mov	1, %l1
	mov	%l1, %l1
	ldsw	[%fp-20], %l2
	mov	2, %l2
	mov	%l2, %l2
.L3:
	mov	2, %l3
	mov	%l0, %o0
	mov	%l3, %o1
	call	.div
	nop
	mov	%o0, %l4
	mov	1, %l3
	cmp	%l2, %l4
	ble	.L4
	nop
	ba	.L5
	nop
.L4:
.L6:
	mov	%l0, %o0
	mov	%l2, %o1
	call	.div
	nop
	mov	%o0, %l3
	mulx	%l3, %l2, %l4
	mov	1, %l3
	cmp	%l4, %l0
	be	.L7
	nop
	ba	.L8
	nop
.L7:
	mov	1, %l3
	mov	%g0, %l1
	mov	%l1, %l1
	mov	%l0, %l2
	ba	.L8
	nop
.L8:
	mov	%g0, %l3
	mov	1, %l3
	add	%l2, %l3, %l3
	mov	%l3, %l2
	mov	2, %l3
	mov	%l0, %o0
	mov	%l3, %o1
	call	.div
	nop
	mov	%o0, %l4
	mov	1, %l3
	cmp	%l2, %l4
	ble	.L4
	nop
	ba	.L5
	nop
.L5:
.L9:
	mov	1, %l2
	cmp	%l1, 1
	be	.L10
	nop
	ba	.L11
	nop
.L10:
	mov	1, %l2
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l0, %o1
	call	printf
	nop
	ba	.L11
	nop
.L11:
	mov	%g0, %l2
.L12:
	mov	2, %l2
	mov	1, %l1
	cmp	%l0, %l2
	bg	.L13
	nop
	ba	.L14
	nop
.L13:
	mov	1, %l1
	mov	1, %l1
	sub	%l0, %l1, %l0
	mov	%l0, %o0
	call	PrimeTime
	nop
	ba	.L14
	nop
.L14:
	mov	%g0, %l1
.L2:
	ret
	restore
	.size	PrimeTime, .-PrimeTime
	.align 4
	.global main
	.type	main, #function
main:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L15:
	ldsw	[%fp-20], %l0
	mov	%g0, %l0
	mov	%l0, %l0
	add	%fp, -20, %l0
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l0, %o1
	call	scanf
	nop
	ldsw	[%fp-20], %l0
	mov	%l0, %o0
	call	PrimeTime
	nop
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L16
	nop
.L16:
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
