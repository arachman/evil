	.section	".text"
	.align 4
	.global mod
	.type	mod, #function
mod:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L1:
	mov	%i0, %l0
	mov	%i1, %l1
.L3:
	mov	1, %l2
	cmp	%l0, %l1
	bl	.L4
	nop
	ba	.L5
	nop
.L4:
	mov	%l0, %i0
	ba	.L2
	nop
	ba	.L6
	nop
.L5:
	sub	%l0, %l1, %l0
	mov	%l0, %o0
	mov	%l1, %o1
	call	mod
	nop
	mov	%o0, %l0
	mov	%l0, %i0
	ba	.L2
	nop
	ba	.L6
	nop
.L6:
.L2:
	ret
	restore
	.size	mod, .-mod
	.align 4
	.global gcd
	.type	gcd, #function
gcd:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L7:
	mov	%i0, %l1
.L9:
	mov	%l1, %l0
	ldsw	[%l0+4], %l3
	mov	%g0, %l2
	mov	1, %l0
	cmp	%l3, %l2
	bne	.L10
	nop
	ba	.L11
	nop
.L10:
	ldsw	[%fp-4], %l0
	mov	%l1, %l0
	ldsw	[%l0+4], %l0
	mov	%l0, %l0
	mov	%l1, %l3
	mov	%l1, %l2
	ldsw	[%l2+0], %l4
	mov	%l1, %l2
	ldsw	[%l2+4], %l2
	mov	%l4, %o0
	mov	%l2, %o1
	call	mod
	nop
	mov	%o0, %l2
	st	%l2, [%l3+4]
	mov	%l1, %l2
	st	%l0, [%l2+0]
.L12:
	mov	%l1, %l0
	ldsw	[%l0+4], %l0
	mov	%g0, %l3
	mov	1, %l2
	cmp	%l0, %l3
	bne	.L10
	nop
	ba	.L11
	nop
.L11:
	mov	%l1, %l0
	ldsw	[%l0+0], %l0
	mov	%l0, %i0
	ba	.L8
	nop
.L8:
	ret
	restore
	.size	gcd, .-gcd
	.align 4
	.global fib
	.type	fib, #function
fib:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L13:
	mov	%i0, %l2
.L15:
	mov	%g0, %l1
	mov	1, %l0
	cmp	%l2, %l1
	be	.L16
	nop
	ba	.L17
	nop
.L16:
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L14
	nop
	ba	.L17
	nop
.L17:
.L18:
	mov	1, %l1
	mov	1, %l0
	cmp	%l2, %l1
	be	.L19
	nop
	ba	.L20
	nop
.L19:
	mov	1, %l0
	mov	%l0, %i0
	ba	.L14
	nop
	ba	.L21
	nop
.L20:
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
	ba	.L14
	nop
	ba	.L21
	nop
.L21:
.L14:
	ret
	restore
	.size	fib, .-fib
	.align 4
	.global main
	.type	main, #function
main:
	!#PROLOGUE# 0
	save	%sp, -112, %sp
	!#PROLOGUE 1
.L22:
	ldsw	[%fp-16], %l0
	mov	12, %o0
	call	malloc
	nop
	mov	%o0, %l0
	st	%l0, [%fp-16]
	ldsw	[%fp-16], %l1
	mov	8, %o0
	call	malloc
	nop
	mov	%o0, %l0
	st	%l0, [%l1+8]
	add	%fp, -4, %l1
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l1, %o1
	call	scanf
	nop
	add	%fp, -8, %l3
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l3, %o1
	call	scanf
	nop
.L24:
	ldsw	[%fp-4], %l1
	mov	%g0, %l0
	mov	1, %l4
	cmp	%l1, %l0
	bne	.L27
	nop
	ba	.L28
	nop
.L28:
	mov	%g0, %l4
.L27:
	ldsw	[%fp-8], %l3
	mov	%g0, %l2
	mov	1, %l0
	cmp	%l3, %l2
	bne	.L29
	nop
	ba	.L30
	nop
.L30:
	mov	%g0, %l0
.L29:
	and	%l4, %l0, %l0
	cmp	%l0, 1
	be	.L25
	nop
	ba	.L26
	nop
.L25:
	ldsw	[%fp-16], %l0
	ldsw	[%l0+8], %l0
	mov	%l1, %o0
	call	fib
	nop
	mov	%o0, %l1
	st	%l1, [%l0+0]
	ldsw	[%fp-16], %l0
	ldsw	[%l0+8], %l0
	mov	%l3, %o0
	call	fib
	nop
	mov	%o0, %l1
	st	%l1, [%l0+4]
	ldsw	[%fp-16], %l0
	ldsw	[%fp-16], %l1
	ldsw	[%l1+8], %l1
	ldsw	[%l1+0], %l1
	st	%l1, [%l0+4]
	ldsw	[%fp-16], %l1
	ldsw	[%fp-16], %l0
	ldsw	[%l0+8], %l0
	ldsw	[%l0+4], %l0
	st	%l0, [%l1+0]
	ldsw	[%fp-12], %l0
	ldsw	[%fp-16], %l0
	mov	%l0, %o0
	call	gcd
	nop
	mov	%o0, %l0
	mov	%l0, %l0
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l0, %o1
	call	printf
	nop
	add	%fp, -4, %l1
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l1, %o1
	call	scanf
	nop
	add	%fp, -8, %l3
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l3, %o1
	call	scanf
	nop
.L31:
	ldsw	[%fp-4], %l1
	mov	%g0, %l0
	mov	1, %l2
	cmp	%l1, %l0
	bne	.L32
	nop
	ba	.L33
	nop
.L33:
	mov	%g0, %l2
.L32:
	ldsw	[%fp-8], %l3
	mov	%g0, %l4
	mov	1, %l0
	cmp	%l3, %l4
	bne	.L34
	nop
	ba	.L35
	nop
.L35:
	mov	%g0, %l0
.L34:
	and	%l2, %l0, %l0
	cmp	%l0, 1
	be	.L25
	nop
	ba	.L26
	nop
.L26:
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L23
	nop
.L23:
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
