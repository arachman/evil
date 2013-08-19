	.common largeprime1,4,4
	.common M,4,4
	.common largeprime2,4,4
	.section	".text"
	.align 4
	.global abs
	.type	abs, #function
abs:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L1:
	mov	%i0, %l0
.L3:
	mov	%g0, %l1
	mov	1, %l2
	cmp	%l0, %l1
	bl	.L4
	nop
	ba	.L5
	nop
.L4:
	mov	2, %l1
	mulx	%l1, %l0, %l1
	sub	%l0, %l1, %l0
	mov	%l0, %l0
	ba	.L5
	nop
.L5:
	mov	%l0, %i0
	ba	.L2
	nop
.L2:
	ret
	restore
	.size	abs, .-abs
	.align 4
	.global mod
	.type	mod, #function
mod:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L6:
	mov	%i0, %l2
	mov	%i1, %l0
	mov	%l2, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l1
	mulx	%l0, %l1, %l0
	sub	%l2, %l0, %l0
	mov	%l0, %i0
	ba	.L7
	nop
.L7:
	ret
	restore
	.size	mod, .-mod
	.align 4
	.global gcd
	.type	gcd, #function
gcd:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L8:
	mov	%i0, %l2
	mov	%i1, %l0
.L10:
	mov	%g0, %l1
	mov	1, %l3
	cmp	%l0, %l1
	be	.L11
	nop
	ba	.L12
	nop
.L11:
	mov	%l2, %i0
	ba	.L9
	nop
	ba	.L13
	nop
.L12:
	mov	%l2, %o0
	mov	%l0, %o1
	call	mod
	nop
	mov	%o0, %l1
	mov	%l0, %o0
	mov	%l1, %o1
	call	gcd
	nop
	mov	%o0, %l0
	mov	%l0, %i0
	ba	.L9
	nop
	ba	.L13
	nop
.L13:
.L9:
	ret
	restore
	.size	gcd, .-gcd
	.align 4
	.global blum
	.type	blum, #function
blum:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L14:
	mov	%i0, %l2
	ldsw	[%fp-4], %l1
	sethi	%hi(largeprime1), %g1
	or	%g1, %lo(largeprime1), %g2
	ld	[%g2], %l0
	sethi	%hi(largeprime2), %g1
	or	%g1, %lo(largeprime2), %g2
	ld	[%g2], %l1
	mulx	%l0, %l1, %l0
	st	%l0, [%fp-4]
	mulx	%l2, %l2, %l0
	ldsw	[%fp-4], %l1
	mov	%l0, %o0
	mov	%l1, %o1
	call	mod
	nop
	mov	%o0, %l0
	mov	%l0, %o0
	call	abs
	nop
	mov	%o0, %l1
	mov	1, %l0
	add	%l1, %l0, %l0
	mov	%l0, %i0
	ba	.L15
	nop
.L15:
	ret
	restore
	.size	blum, .-blum
	.align 4
	.global rho
	.type	rho, #function
rho:
	!#PROLOGUE# 0
	save	%sp, -112, %sp
	!#PROLOGUE 1
.L16:
	mov	%i0, %l3
	ldsw	[%fp-4], %l1
	mov	2, %l0
	mov	%l0, %l1
	ldsw	[%fp-8], %l4
	mov	2, %l0
	mov	%l0, %l4
	ldsw	[%fp-12], %l5
	mov	1, %l0
	mov	%l0, %l5
.L18:
	mov	1, %l2
	mov	1, %l0
	cmp	%l5, %l2
	be	.L19
	nop
	ba	.L20
	nop
.L19:
	mov	%l1, %o0
	call	blum
	nop
	mov	%o0, %l0
	mov	%l0, %l1
	mov	%l4, %o0
	call	blum
	nop
	mov	%o0, %l0
	mov	%l0, %o0
	call	blum
	nop
	mov	%o0, %l0
	mov	%l0, %l4
	ldsw	[%fp-16], %l0
	sub	%l1, %l4, %l0
	mov	%l0, %o0
	call	abs
	nop
	mov	%o0, %l0
	mov	%l0, %l0
	mov	%l0, %o0
	mov	%l3, %o1
	call	gcd
	nop
	mov	%o0, %l0
	mov	%l0, %l5
	mov	1, %l2
	mov	1, %l0
	cmp	%l5, %l2
	be	.L19
	nop
	ba	.L20
	nop
.L20:
.L21:
	mov	1, %l0
	cmp	%l5, %l3
	be	.L22
	nop
	ba	.L23
	nop
.L22:
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L17
	nop
	ba	.L24
	nop
.L23:
	mov	%l5, %i0
	ba	.L17
	nop
	ba	.L24
	nop
.L24:
.L17:
	ret
	restore
	.size	rho, .-rho
	.align 4
	.global main
	.type	main, #function
main:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L25:
	sethi	%hi(largeprime1), %g1
	or	%g1, %lo(largeprime1), %l1
	sethi 	%hi(30211), %l0
	or	%l0, %lo(30211), %l0
	st	%l0, [%l1]
	sethi	%hi(largeprime2), %g1
	or	%g1, %lo(largeprime2), %l1
	sethi 	%hi(30223), %l0
	or	%l0, %lo(30223), %l0
	st	%l0, [%l1]
	add	%fp, -4, %l2
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l2, %o1
	call	scanf
	nop
.L27:
	ldsw	[%fp-4], %l2
	mov	%g0, %l0
	mov	1, %l1
	cmp	%l2, %l0
	bne	.L28
	nop
	ba	.L29
	nop
.L28:
	mov	%l2, %o0
	call	rho
	nop
	mov	%o0, %l0
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l0, %o1
	call	printf
	nop
	add	%fp, -4, %l2
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l2, %o1
	call	scanf
	nop
	ldsw	[%fp-4], %l2
	mov	%g0, %l1
	mov	1, %l0
	cmp	%l2, %l1
	bne	.L28
	nop
	ba	.L29
	nop
.L29:
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L26
	nop
.L26:
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
