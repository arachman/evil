	.section	".text"
	.align 4
	.global LinkedListnewNode
	.type	LinkedListnewNode, #function
LinkedListnewNode:
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
	st	%l1, [%l0+0]
	ldsw	[%fp-4], %l1
	mov	%g0, %l0
	st	%l0, [%l1+4]
	ldsw	[%fp-4], %l0
	mov	%l0, %i0
	ba	.L2
	nop
.L2:
	ret
	restore
	.size	LinkedListnewNode, .-LinkedListnewNode
	.align 4
	.global LinkedListcreate
	.type	LinkedListcreate, #function
LinkedListcreate:
	!#PROLOGUE# 0
	save	%sp, -112, %sp
	!#PROLOGUE 1
.L3:
	mov	%i0, %l1
	ldsw	[%fp-4], %l0
	mov	%g0, %l0
	mov	%l0, %o0
	call	LinkedListnewNode
	nop
	mov	%o0, %l0
	st	%l0, [%fp-4]
	ldsw	[%fp-8], %l0
	ldsw	[%fp-4], %l0
	st	%l0, [%fp-8]
	ldsw	[%fp-12], %l2
	mov	1, %l0
	mov	%l0, %l2
.L5:
	mov	1, %l0
	cmp	%l2, %l1
	bl	.L6
	nop
	ba	.L7
	nop
.L6:
	ldsw	[%fp-8], %l3
	mov	%l2, %o0
	call	LinkedListnewNode
	nop
	mov	%o0, %l0
	st	%l0, [%l3+4]
	ldsw	[%fp-8], %l0
	ldsw	[%l0+4], %l0
	st	%l0, [%fp-8]
	mov	1, %l0
	add	%l2, %l0, %l0
	mov	%l0, %l2
.L8:
	mov	1, %l0
	cmp	%l2, %l1
	bl	.L6
	nop
	ba	.L7
	nop
.L7:
	ldsw	[%fp-4], %l0
	mov	%l0, %i0
	ba	.L4
	nop
.L4:
	ret
	restore
	.size	LinkedListcreate, .-LinkedListcreate
	.align 4
	.global LinkedListdestroy
	.type	LinkedListdestroy, #function
LinkedListdestroy:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L9:
	mov	%i0, %l0
	ldsw	[%fp-4], %l2
	mov	%l0, %l0
	st	%l0, [%fp-4]
.L11:
	ldsw	[%fp-4], %l2
	mov	%g0, %l1
	mov	1, %l0
	cmp	%l2, %l1
	bne	.L12
	nop
	ba	.L13
	nop
.L12:
	ldsw	[%fp-8], %l0
	ldsw	[%fp-4], %l0
	ldsw	[%l0+4], %l0
	st	%l0, [%fp-8]
	ldsw	[%fp-4], %l2
	mov	%l2, %o0
	call	free
	nop
	ldsw	[%fp-8], %l0
	st	%l0, [%fp-4]
.L14:
	ldsw	[%fp-4], %l2
	mov	%g0, %l1
	mov	1, %l0
	cmp	%l2, %l1
	bne	.L12
	nop
	ba	.L13
	nop
.L13:
.L10:
	ret
	restore
	.size	LinkedListdestroy, .-LinkedListdestroy
	.align 4
	.global LinkedListfind
	.type	LinkedListfind, #function
LinkedListfind:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L15:
	mov	%i0, %l0
	mov	%i1, %l2
.L17:
	mov	%l0, %l0
	mov	%g0, %l1
	mov	1, %l3
	cmp	%l0, %l1
	be	.L18
	nop
	ba	.L19
	nop
.L18:
	mov	%g0, %l1
	mov	%l1, %i0
	ba	.L16
	nop
	ba	.L19
	nop
.L19:
.L20:
	mov	%l0, %l1
	ldsw	[%l1+0], %l3
	mov	1, %l1
	cmp	%l3, %l2
	be	.L21
	nop
	ba	.L22
	nop
.L21:
	mov	%l0, %l0
	mov	%l0, %i0
	ba	.L16
	nop
	ba	.L22
	nop
.L22:
	mov	%l0, %l0
	ldsw	[%l0+4], %l0
	mov	%l0, %i0
	mov	%l2, %i1
	ba	.L15
	nop
	mov	%o0, %l0
	mov	%l0, %i0
	ba	.L16
	nop
.L16:
	ret
	restore
	.size	LinkedListfind, .-LinkedListfind
	.align 4
	.global main
	.type	main, #function
main:
	!#PROLOGUE# 0
	save	%sp, -120, %sp
	!#PROLOGUE 1
.L23:
	add	%fp, -4, %l0
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l0, %o1
	call	scanf
	nop
	add	%fp, -8, %l1
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l1, %o1
	call	scanf
	nop
	add	%fp, -12, %l2
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l2, %o1
	call	scanf
	nop
	ldsw	[%fp-20], %l1
	mov	%g0, %l0
	st	%l0, [%fp-20]
	ldsw	[%fp-16], %l0
	ldsw	[%fp-4], %l0
	mov	%l0, %o0
	call	LinkedListcreate
	nop
	mov	%o0, %l0
	st	%l0, [%fp-16]
.L25:
	ldsw	[%fp-12], %l2
	mov	%g0, %l0
	mov	1, %l1
	cmp	%l2, %l0
	bg	.L26
	nop
	ba	.L27
	nop
.L26:
	ldsw	[%fp-16], %l0
	ldsw	[%fp-8], %l1
	mov	%l0, %o0
	mov	%l1, %o1
	call	LinkedListfind
	nop
	mov	%o0, %l0
	st	%l0, [%fp-20]
	mov	1, %l0
	sub	%l2, %l0, %l0
	mov	%l0, %l2
.L28:
	mov	%g0, %l1
	mov	1, %l0
	cmp	%l2, %l1
	bg	.L26
	nop
	ba	.L27
	nop
.L27:
.L29:
	ldsw	[%fp-20], %l1
	mov	%g0, %l2
	mov	1, %l0
	cmp	%l1, %l2
	be	.L30
	nop
	ba	.L31
	nop
.L30:
	mov	1, %l0
	sub	%g0, %l0, %l0
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l0, %o1
	call	printf
	nop
	ldsw	[%fp-16], %l0
	mov	%l0, %o0
	call	LinkedListdestroy
	nop
	mov	1, %l0
	sub	%g0, %l0, %l0
	mov	%l0, %i0
	ba	.L24
	nop
	ba	.L31
	nop
.L31:
	ldsw	[%fp-20], %l0
	ldsw	[%l0+0], %l0
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l0, %o1
	call	printf
	nop
	ldsw	[%fp-16], %l0
	mov	%l0, %o0
	call	LinkedListdestroy
	nop
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L24
	nop
.L24:
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
