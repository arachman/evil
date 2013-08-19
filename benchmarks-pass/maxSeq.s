	.common seed,4,4
	.section	".text"
	.align 4
	.global mod
	.type	mod, #function
mod:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L1:
	mov	%i0, %l1
	mov	%i1, %l2
	mov	%l1, %o0
	mov	%l2, %o1
	call	.div
	nop
	mov	%o0, %l0
	mulx	%l0, %l2, %l0
	sub	%l1, %l0, %l0
	mov	%l0, %i0
	ba	.L2
	nop
.L2:
	ret
	restore
	.size	mod, .-mod
	.align 4
	.global rand
	.type	rand, #function
rand:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L3:
	sethi	%hi(seed), %g1
	or	%g1, %lo(seed), %l1
	sethi	%hi(seed), %g1
	or	%g1, %lo(seed), %g2
	ld	[%g2], %l1
	mov	532, %l0
	mov	%l1, %o0
	mov	%l0, %o1
	call	mod
	nop
	mov	%o0, %l1
	mov	24, %l0
	mulx	%l1, %l0, %l0
	mov	6, %l1
	add	%l0, %l1, %l0
	sethi	%hi(seed), %g1
	or	%g1, %lo(seed), %l1
	st	%l0, [%l1]
	sethi	%hi(seed), %g1
	or	%g1, %lo(seed), %g2
	ld	[%g2], %l1
	mov	%l1, %i0
	ba	.L4
	nop
.L4:
	ret
	restore
	.size	rand, .-rand
	.align 4
	.global main
	.type	main, #function
main:
	!#PROLOGUE# 0
	save	%sp, -120, %sp
	!#PROLOGUE 1
.L5:
	add	%fp, -16, %l1
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l1, %o1
	call	scanf
	nop
	sethi	%hi(seed), %g1
	or	%g1, %lo(seed), %l0
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l0, %o1
	call	scanf
	nop
	ldsw	[%fp-20], %l5
	call	rand
	nop
	mov	%o0, %l0
	mov	%l0, %l5
	ldsw	[%fp-4], %l4
	mov	201, %l0
	mov	%l5, %o0
	mov	%l0, %o1
	call	mod
	nop
	mov	%o0, %l1
	mov	100, %l0
	sub	%l1, %l0, %l0
	mov	%l0, %l4
	ldsw	[%fp-8], %l2
	mov	%l4, %l2
	ldsw	[%fp-12], %l3
	mov	%l4, %l3
.L7:
	ldsw	[%fp-16], %l1
	mov	%g0, %l4
	mov	1, %l0
	cmp	%l1, %l4
	bg	.L8
	nop
	ba	.L9
	nop
.L8:
	mov	1, %l0
	sub	%l1, %l0, %l0
	mov	%l0, %l1
	call	rand
	nop
	mov	%o0, %l0
	mov	%l0, %l5
	mov	201, %l0
	mov	%l5, %o0
	mov	%l0, %o1
	call	mod
	nop
	mov	%o0, %l0
	mov	100, %l4
	sub	%l0, %l4, %l0
	mov	%l0, %l4
.L10:
	mov	%g0, %l0
	mov	1, %l5
	cmp	%l3, %l0
	bg	.L11
	nop
	ba	.L12
	nop
.L11:
	add	%l3, %l4, %l0
	mov	%l0, %l3
	ba	.L13
	nop
.L13:
.L14:
	mov	1, %l0
	cmp	%l3, %l2
	bg	.L15
	nop
	ba	.L16
	nop
.L15:
	mov	%l3, %l2
	ba	.L16
	nop
.L16:
.L17:
	mov	%g0, %l4
	mov	1, %l0
	cmp	%l1, %l4
	bg	.L8
	nop
	ba	.L9
	nop
.L12:
	mov	%l4, %l3
	ba	.L13
	nop
.L9:
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l2, %o1
	call	printf
	nop
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L6
	nop
.L6:
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
