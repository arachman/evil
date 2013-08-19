	.section	".text"
	.align 4
	.global binexp
	.type	binexp, #function
binexp:
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
.L3:
	mov	%g0, %l3
	mov	1, %l0
	cmp	%l2, %l3
	be	.L4
	nop
	ba	.L5
	nop
.L4:
	ldsw	[%fp-4], %l3
	mov	1, %l0
	st	%l0, [%l3+4]
	ldsw	[%fp-4], %l0
	mov	%g0, %l3
	st	%l3, [%l0+0]
	ldsw	[%fp-4], %l0
	mov	%l0, %i0
	ba	.L2
	nop
	ba	.L6
	nop
.L6:
	mov	2, %l0
	mov	%l2, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l0
	mov	%l1, %o0
	mov	%l0, %o1
	call	binexp
	nop
	mov	%o0, %l0
	st	%l0, [%fp-4]
.L10:
	mov	2, %l0
	mov	%l2, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l0
	mov	2, %l3
	mulx	%l0, %l3, %l3
	mov	1, %l0
	cmp	%l3, %l2
	bne	.L11
	nop
	ba	.L12
	nop
.L11:
	ldsw	[%fp-4], %l2
	ldsw	[%fp-4], %l0
	ldsw	[%l0+4], %l0
	mulx	%l1, %l0, %l1
	ldsw	[%fp-4], %l0
	ldsw	[%l0+4], %l0
	mulx	%l1, %l0, %l0
	st	%l0, [%l2+4]
	ldsw	[%fp-4], %l1
	ldsw	[%fp-4], %l0
	ldsw	[%l0+0], %l0
	mov	2, %l2
	add	%l0, %l2, %l0
	st	%l0, [%l1+0]
	ba	.L13
	nop
.L12:
	ldsw	[%fp-4], %l0
	ldsw	[%fp-4], %l1
	ldsw	[%l1+4], %l2
	ldsw	[%fp-4], %l1
	ldsw	[%l1+4], %l1
	mulx	%l2, %l1, %l1
	st	%l1, [%l0+4]
	ldsw	[%fp-4], %l0
	ldsw	[%fp-4], %l1
	ldsw	[%l1+0], %l2
	mov	1, %l1
	add	%l2, %l1, %l1
	st	%l1, [%l0+0]
	ba	.L13
	nop
.L5:
.L7:
	mov	1, %l0
	mov	1, %l3
	cmp	%l2, %l0
	be	.L8
	nop
	ba	.L9
	nop
.L8:
	ldsw	[%fp-4], %l0
	st	%l1, [%l0+4]
	ldsw	[%fp-4], %l0
	mov	%g0, %l3
	st	%l3, [%l0+0]
	ldsw	[%fp-4], %l0
	mov	%l0, %i0
	ba	.L2
	nop
	ba	.L9
	nop
.L9:
	ba	.L6
	nop
.L13:
	ldsw	[%fp-4], %l0
	mov	%l0, %i0
	ba	.L2
	nop
.L2:
	ret
	restore
	.size	binexp, .-binexp
	.align 4
	.global rpexp
	.type	rpexp, #function
rpexp:
	!#PROLOGUE# 0
	save	%sp, -112, %sp
	!#PROLOGUE 1
.L14:
	mov	%i0, %l1
	mov	%i1, %l4
	ldsw	[%fp-12], %l0
	mov	8, %o0
	call	malloc
	nop
	mov	%o0, %l0
	st	%l0, [%fp-12]
	ldsw	[%fp-12], %l0
	mov	%g0, %l2
	st	%l2, [%l0+0]
	ldsw	[%fp-4], %l2
	mov	%l4, %l2
	ldsw	[%fp-8], %l3
	mov	%l1, %l3
.L16:
	mov	%g0, %l1
	mov	1, %l0
	cmp	%l4, %l1
	be	.L17
	nop
	ba	.L18
	nop
.L17:
	ldsw	[%fp-12], %l1
	mov	1, %l0
	st	%l0, [%l1+4]
	ldsw	[%fp-12], %l0
	mov	%l0, %i0
	ba	.L15
	nop
	ba	.L18
	nop
.L18:
.L19:
	mov	2, %l0
	mov	%l2, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l1
	mov	2, %l0
	mulx	%l1, %l0, %l1
	mov	1, %l0
	cmp	%l1, %l2
	be	.L20
	nop
	ba	.L21
	nop
.L20:
	mulx	%l3, %l3, %l0
	mov	%l0, %l3
	mov	2, %l0
	mov	%l2, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l0
	mov	%l0, %l2
	ldsw	[%fp-12], %l0
	ldsw	[%fp-12], %l1
	ldsw	[%l1+0], %l1
	mov	1, %l4
	add	%l1, %l4, %l1
	st	%l1, [%l0+0]
.L22:
	mov	2, %l0
	mov	%l2, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l0
	mov	2, %l1
	mulx	%l0, %l1, %l1
	mov	1, %l0
	cmp	%l1, %l2
	be	.L20
	nop
	ba	.L21
	nop
.L21:
	ldsw	[%fp-12], %l0
	st	%l3, [%l0+4]
	mov	2, %l0
	mov	%l2, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l0
	mov	%l0, %l2
.L23:
	mov	%g0, %l1
	mov	1, %l0
	cmp	%l2, %l1
	bg	.L24
	nop
	ba	.L25
	nop
.L24:
	mulx	%l3, %l3, %l0
	mov	%l0, %l3
	ldsw	[%fp-12], %l0
	ldsw	[%fp-12], %l1
	ldsw	[%l1+0], %l4
	mov	1, %l1
	add	%l4, %l1, %l1
	st	%l1, [%l0+0]
.L26:
	mov	2, %l0
	mov	%l2, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l1
	mov	2, %l0
	mulx	%l1, %l0, %l0
	mov	1, %l1
	cmp	%l0, %l2
	bne	.L27
	nop
	ba	.L28
	nop
.L27:
	ldsw	[%fp-12], %l0
	ldsw	[%fp-12], %l1
	ldsw	[%l1+4], %l1
	mulx	%l1, %l3, %l1
	st	%l1, [%l0+4]
	ldsw	[%fp-12], %l1
	ldsw	[%fp-12], %l0
	ldsw	[%l0+0], %l4
	mov	1, %l0
	add	%l4, %l0, %l0
	st	%l0, [%l1+0]
	ba	.L28
	nop
.L28:
	mov	2, %l0
	mov	%l2, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l0
	mov	%l0, %l2
.L29:
	mov	%g0, %l1
	mov	1, %l0
	cmp	%l2, %l1
	bg	.L24
	nop
	ba	.L25
	nop
.L25:
	ldsw	[%fp-12], %l0
	mov	%l0, %i0
	ba	.L15
	nop
.L15:
	ret
	restore
	.size	rpexp, .-rpexp
	.align 4
	.global checkexp
	.type	checkexp, #function
checkexp:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L30:
	mov	%i0, %l1
	mov	%i1, %l3
	ldsw	[%fp-4], %l5
	mov	%g0, %l0
	mov	%l0, %l5
	ldsw	[%fp-8], %l2
	mov	1, %l0
	mov	%l0, %l2
.L32:
	mov	%g0, %l0
	mov	1, %l4
	cmp	%l3, %l0
	be	.L33
	nop
	ba	.L34
	nop
.L33:
	mov	1, %l0
	mov	%l0, %i0
	ba	.L31
	nop
	ba	.L34
	nop
.L34:
.L35:
	mov	1, %l0
	cmp	%l5, %l3
	bl	.L36
	nop
	ba	.L37
	nop
.L36:
	mulx	%l2, %l1, %l0
	mov	%l0, %l2
	mov	1, %l0
	add	%l5, %l0, %l0
	mov	%l0, %l5
.L38:
	mov	1, %l0
	cmp	%l5, %l3
	bl	.L36
	nop
	ba	.L37
	nop
.L37:
	mov	%l2, %i0
	ba	.L31
	nop
.L31:
	ret
	restore
	.size	checkexp, .-checkexp
	.align 4
	.global main
	.type	main, #function
main:
	!#PROLOGUE# 0
	save	%sp, -120, %sp
	!#PROLOGUE 1
.L39:
	add	%fp, -4, %l1
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l1, %o1
	call	scanf
	nop
	add	%fp, -8, %l2
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l2, %o1
	call	scanf
	nop
.L41:
	ldsw	[%fp-8], %l2
	mov	%g0, %l0
	mov	1, %l1
	cmp	%l2, %l0
	bl	.L42
	nop
	ba	.L43
	nop
.L42:
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L40
	nop
	ba	.L43
	nop
.L43:
	ldsw	[%fp-16], %l0
	ldsw	[%fp-4], %l1
	mov	%l1, %o0
	mov	%l2, %o1
	call	binexp
	nop
	mov	%o0, %l0
	st	%l0, [%fp-16]
	ldsw	[%fp-16], %l0
	ldsw	[%l0+4], %l0
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l0, %o1
	call	printf
	nop
	ldsw	[%fp-16], %l0
	ldsw	[%l0+0], %l0
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l0, %o1
	call	printf
	nop
	ldsw	[%fp-20], %l0
	mov	%l1, %o0
	mov	%l2, %o1
	call	rpexp
	nop
	mov	%o0, %l0
	st	%l0, [%fp-20]
	ldsw	[%fp-20], %l0
	ldsw	[%l0+4], %l0
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l0, %o1
	call	printf
	nop
	ldsw	[%fp-20], %l0
	ldsw	[%l0+0], %l0
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l0, %o1
	call	printf
	nop
	ldsw	[%fp-12], %l3
	mov	%l1, %o0
	mov	%l2, %o1
	call	checkexp
	nop
	mov	%o0, %l0
	mov	%l0, %l3
.L44:
	ldsw	[%fp-16], %l0
	ldsw	[%l0+4], %l1
	mov	1, %l0
	cmp	%l1, %l3
	be	.L48
	nop
	ba	.L49
	nop
.L49:
	mov	%g0, %l0
.L48:
	ldsw	[%fp-20], %l1
	ldsw	[%l1+4], %l2
	mov	1, %l1
	cmp	%l2, %l3
	be	.L50
	nop
	ba	.L51
	nop
.L51:
	mov	%g0, %l1
.L50:
	and	%l0, %l1, %l0
	cmp	%l0, 1
	be	.L45
	nop
	ba	.L46
	nop
.L45:
	mov	1, %l0
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l0, %o1
	call	printf
	nop
	ba	.L47
	nop
.L46:
	mov	%g0, %l0
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l0, %o1
	call	printf
	nop
	ba	.L47
	nop
.L47:
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L40
	nop
.L40:
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
