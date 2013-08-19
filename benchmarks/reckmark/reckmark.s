	.section	".text"
	.align 4
	.global newNode
	.type	newNode, #function
newNode:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L1:
	mov	%i0, %l1
	ldsw	[%fp-4], %l0
	mov	8, %o0
	call	malloc
	nop
	mov	%o0, %l0
	st	%l0, [%fp-4]
	ldsw	[%fp-4], %l0
	st	%l1, [%l0+4]
	ldsw	[%fp-4], %l1
	mov	%g0, %l0
	st	%l0, [%l1+0]
	ldsw	[%fp-4], %l0
	mov	%l0, %i0
	ba	.L2
	nop
.L2:
	ret
	restore
	.size	newNode, .-newNode
	.align 4
	.global freeNode
	.type	freeNode, #function
freeNode:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L3:
	mov	%i0, %l3
.L5:
	mov	%l3, %l0
	ldsw	[%l0+0], %l0
	mov	%g0, %l1
	mov	1, %l2
	cmp	%l0, %l1
	bne	.L6
	nop
	ba	.L7
	nop
.L6:
	mov	%l3, %l0
	ldsw	[%l0+0], %l0
	mov	%l0, %o0
	call	freeNode
	nop
	ba	.L7
	nop
.L7:
	mov	%l3, %l3
	mov	%l3, %o0
	call	free
	nop
.L4:
	ret
	restore
	.size	freeNode, .-freeNode
	.align 4
	.global append
	.type	append, #function
append:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L8:
	mov	%i0, %l2
	mov	%i1, %l3
.L10:
	mov	%l2, %l2
	mov	%g0, %l0
	mov	1, %l1
	cmp	%l2, %l0
	be	.L11
	nop
	ba	.L12
	nop
.L11:
	mov	%l3, %o0
	call	newNode
	nop
	mov	%o0, %l0
	mov	%l0, %i0
	ba	.L9
	nop
	ba	.L13
	nop
.L12:
	mov	%l2, %l1
	mov	%l2, %l0
	ldsw	[%l0+0], %l0
	mov	%l0, %o0
	mov	%l3, %o1
	call	append
	nop
	mov	%o0, %l0
	st	%l0, [%l1+0]
	ba	.L13
	nop
.L13:
	mov	%l2, %l2
	mov	%l2, %i0
	ba	.L9
	nop
.L9:
	ret
	restore
	.size	append, .-append
	.align 4
	.global readVal
	.type	readVal, #function
readVal:
	!#PROLOGUE# 0
	save	%sp, -112, %sp
	!#PROLOGUE 1
.L14:
	mov	%i0, %l1
	ldsw	[%fp-12], %l2
	mov	1, %l0
	mov	%l0, %l2
	ldsw	[%fp-16], %l3
	mov	%g0, %l0
	mov	%l0, %l3
.L16:
	mov	%l1, %l1
	mov	%g0, %l0
	mov	1, %l4
	cmp	%l1, %l0
	be	.L17
	nop
	ba	.L18
	nop
.L17:
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L15
	nop
	ba	.L18
	nop
.L18:
	ldsw	[%fp-8], %l5
	mov	%l1, %l1
	st	%l1, [%fp-8]
.L19:
	ldsw	[%fp-8], %l0
	ldsw	[%l0+0], %l4
	mov	%g0, %l5
	mov	1, %l0
	cmp	%l4, %l5
	bne	.L20
	nop
	ba	.L21
	nop
.L20:
	ldsw	[%fp-8], %l0
	ldsw	[%l0+0], %l0
	st	%l0, [%fp-8]
.L22:
	ldsw	[%fp-8], %l0
	ldsw	[%l0+0], %l4
	mov	%g0, %l5
	mov	1, %l0
	cmp	%l4, %l5
	bne	.L20
	nop
	ba	.L21
	nop
.L21:
.L23:
	ldsw	[%fp-8], %l5
	mov	%l1, %l1
	mov	1, %l0
	cmp	%l5, %l1
	bne	.L24
	nop
	ba	.L25
	nop
.L24:
	ldsw	[%fp-8], %l0
	ldsw	[%l0+4], %l0
	mulx	%l0, %l2, %l0
	add	%l3, %l0, %l0
	mov	%l0, %l3
	ldsw	[%fp-4], %l0
	mov	%l1, %l1
	st	%l1, [%fp-4]
.L26:
	ldsw	[%fp-4], %l0
	ldsw	[%l0+0], %l4
	ldsw	[%fp-8], %l5
	mov	1, %l0
	cmp	%l4, %l5
	bne	.L27
	nop
	ba	.L28
	nop
.L27:
	ldsw	[%fp-4], %l0
	ldsw	[%l0+0], %l0
	st	%l0, [%fp-4]
.L29:
	ldsw	[%fp-4], %l0
	ldsw	[%l0+0], %l4
	ldsw	[%fp-8], %l5
	mov	1, %l0
	cmp	%l4, %l5
	bne	.L27
	nop
	ba	.L28
	nop
.L28:
	ldsw	[%fp-4], %l0
	st	%l0, [%fp-8]
	mov	10, %l0
	mulx	%l2, %l0, %l0
	mov	%l0, %l2
.L30:
	ldsw	[%fp-8], %l5
	mov	%l1, %l1
	mov	1, %l0
	cmp	%l5, %l1
	bne	.L24
	nop
	ba	.L25
	nop
.L25:
	ldsw	[%fp-8], %l0
	ldsw	[%l0+4], %l0
	mulx	%l0, %l2, %l0
	add	%l3, %l0, %l0
	mov	%l0, %l3
	mov	%l3, %i0
	ba	.L15
	nop
.L15:
	ret
	restore
	.size	readVal, .-readVal
	.align 4
	.global nextFib
	.type	nextFib, #function
nextFib:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L31:
	mov	%i0, %l1
	mov	%i1, %l0
	add	%l0, %l1, %l0
	mov	%l0, %i0
	ba	.L32
	nop
.L32:
	ret
	restore
	.size	nextFib, .-nextFib
	.align 4
	.global isFib
	.type	isFib, #function
isFib:
	!#PROLOGUE# 0
	save	%sp, -112, %sp
	!#PROLOGUE 1
.L33:
	mov	%i0, %l1
	ldsw	[%fp-4], %l2
	mov	1, %l0
	mov	%l0, %l2
	ldsw	[%fp-8], %l3
	mov	1, %l0
	mov	%l0, %l3
.L35:
	mov	1, %l0
	cmp	%l2, %l1
	bl	.L36
	nop
	ba	.L37
	nop
.L36:
	ldsw	[%fp-12], %l0
	mov	%l3, %l0
	mov	%l2, %l3
	mov	%l0, %o0
	mov	%l3, %o1
	call	nextFib
	nop
	mov	%o0, %l0
	mov	%l0, %l2
.L38:
	mov	1, %l0
	cmp	%l2, %l1
	bl	.L36
	nop
	ba	.L37
	nop
.L37:
.L39:
	mov	1, %l0
	cmp	%l1, %l2
	be	.L40
	nop
	ba	.L41
	nop
.L40:
	mov	1, %l0
	mov	%l0, %i0
	ba	.L34
	nop
	ba	.L41
	nop
.L41:
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L34
	nop
.L34:
	ret
	restore
	.size	isFib, .-isFib
	.align 4
	.global main
	.type	main, #function
main:
	!#PROLOGUE# 0
	save	%sp, -112, %sp
	!#PROLOGUE 1
.L42:
	ldsw	[%fp-4], %l0
	mov	%g0, %l0
	st	%l0, [%fp-4]
	add	%fp, -8, %l3
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l3, %o1
	call	scanf
	nop
.L44:
	ldsw	[%fp-8], %l3
	mov	1, %l0
	sub	%g0, %l0, %l1
	mov	1, %l0
	cmp	%l3, %l1
	bne	.L45
	nop
	ba	.L46
	nop
.L45:
.L47:
	mov	10, %l1
	mov	1, %l0
	cmp	%l3, %l1
	bl	.L48
	nop
	ba	.L49
	nop
.L48:
	ldsw	[%fp-4], %l0
	mov	%l0, %o0
	mov	%l3, %o1
	call	append
	nop
	mov	%o0, %l0
	st	%l0, [%fp-4]
	ba	.L50
	nop
.L50:
	add	%fp, -8, %l3
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l3, %o1
	call	scanf
	nop
.L55:
	ldsw	[%fp-8], %l3
	mov	1, %l0
	sub	%g0, %l0, %l0
	mov	1, %l1
	cmp	%l3, %l0
	bne	.L45
	nop
	ba	.L46
	nop
.L52:
	mov	1, %l0
	sub	%l2, %l0, %l0
	mov	%l0, %l2
.L54:
	mov	%l2, %o0
	call	isFib
	nop
	mov	%o0, %l1
	mov	1, %l0
	xor	%l1, 1, %l0
	cmp	%l0, 1
	be	.L52
	nop
	ba	.L53
	nop
.L53:
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l2, %o1
	call	printf
	nop
	ldsw	[%fp-4], %l0
	mov	%l0, %o0
	call	freeNode
	nop
	mov	%g0, %l0
	st	%l0, [%fp-4]
	ba	.L50
	nop
.L49:
	ldsw	[%fp-12], %l2
	ldsw	[%fp-4], %l0
	mov	%l0, %o0
	call	readVal
	nop
	mov	%o0, %l0
	mov	%l0, %l2
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l2, %o1
	call	printf
	nop
.L51:
	mov	%l2, %o0
	call	isFib
	nop
	mov	%o0, %l1
	mov	1, %l0
	xor	%l1, 1, %l0
	cmp	%l0, 1
	be	.L52
	nop
	ba	.L53
	nop
.L46:
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L43
	nop
.L43:
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
