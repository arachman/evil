	.section	".text"
	.align 4
	.global PrimeTime
	.type	PrimeTime, #function
PrimeTime:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L1:
	mov	%i0, %l2
	ldsw	[%fp-8], %l3
	mov	1, %l0
	mov	%l0, %l3
	ldsw	[%fp-4], %l4
	mov	2, %l0
	mov	%l0, %l4
.L3:
	mov	2, %l0
	mov	%l2, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l1
	mov	1, %l0
	cmp	%l4, %l1
	ble	.L4
	nop
	ba	.L5
	nop
.L4:
.L6:
	mov	%l2, %o0
	mov	%l4, %o1
	call	.div
	nop
	mov	%o0, %l0
	mulx	%l0, %l4, %l0
	mov	1, %l1
	cmp	%l0, %l2
	be	.L7
	nop
	ba	.L8
	nop
.L7:
	mov	%g0, %l0
	mov	%l0, %l3
	mov	%l2, %l4
	ba	.L8
	nop
.L8:
	mov	1, %l0
	add	%l4, %l0, %l0
	mov	%l0, %l4
.L9:
	mov	2, %l0
	mov	%l2, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l1
	mov	1, %l0
	cmp	%l4, %l1
	ble	.L4
	nop
	ba	.L5
	nop
.L5:
.L10:
	mov	1, %l0
	cmp	%l3, 1
	be	.L11
	nop
	ba	.L12
	nop
.L11:
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l2, %o1
	call	printf
	nop
	ba	.L12
	nop
.L12:
.L13:
	mov	2, %l1
	mov	1, %l0
	cmp	%l2, %l1
	bg	.L14
	nop
	ba	.L15
	nop
.L14:
	mov	1, %l0
	sub	%l2, %l0, %l0
	mov	%l0, %o0
	call	PrimeTime
	nop
	ba	.L15
	nop
.L15:
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
.L16:
	ldsw	[%fp-4], %l0
	mov	%g0, %l0
	mov	%l0, %l0
	add	%fp, -4, %l0
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l0, %o1
	call	scanf
	nop
	ldsw	[%fp-4], %l0
	mov	%l0, %o0
	call	PrimeTime
	nop
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L17
	nop
.L17:
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
