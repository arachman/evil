	.section	".text"
	.align 4
	.global initNode
	.type	initNode, #function
initNode:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L1:
	mov	%i0, %l1
	mov	%i1, %l2
	ldsw	[%fp-4], %l0
	mov	12, %o0
	call	malloc
	nop
	mov	%o0, %l0
	st	%l0, [%fp-4]
	ldsw	[%fp-4], %l0
	mov	%l2, %l2
	st	%l2, [%l0+4]
	ldsw	[%fp-4], %l0
	st	%l1, [%l0+8]
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
	.size	initNode, .-initNode
	.align 4
	.global printNode
	.type	printNode, #function
printNode:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L3:
	mov	%i0, %l0
	mov	%l0, %l0
	ldsw	[%l0+8], %l0
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l0, %o1
	call	printf
	nop
.L4:
	ret
	restore
	.size	printNode, .-printNode
	.align 4
	.global abs
	.type	abs, #function
abs:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L5:
	mov	%i0, %l2
.L7:
	mov	%g0, %l1
	mov	1, %l0
	cmp	%l2, %l1
	bg	.L8
	nop
	ba	.L9
	nop
.L8:
	mov	%l2, %i0
	ba	.L6
	nop
	ba	.L9
	nop
.L9:
	sub	%g0, %l2, %l0
	mov	%l0, %i0
	ba	.L6
	nop
.L6:
	ret
	restore
	.size	abs, .-abs
	.align 4
	.global sqrt
	.type	sqrt, #function
sqrt:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L10:
	mov	%i0, %l1
	ldsw	[%fp-4], %l2
	mov	1, %l0
	mov	%l0, %l2
	ldsw	[%fp-8], %l3
	mov	%l1, %o0
	mov	%l2, %o1
	call	.div
	nop
	mov	%o0, %l0
	add	%l2, %l0, %l3
	mov	2, %l0
	mov	%l3, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l0
	mov	%l0, %l3
.L12:
	sub	%l3, %l2, %l0
	mov	%l0, %o0
	call	abs
	nop
	mov	%o0, %l0
	mov	1, %l4
	mov	1, %l2
	cmp	%l0, %l4
	bg	.L13
	nop
	ba	.L14
	nop
.L13:
	mov	%l3, %l2
	mov	%l1, %o0
	mov	%l2, %o1
	call	.div
	nop
	mov	%o0, %l0
	add	%l2, %l0, %l3
	mov	2, %l0
	mov	%l3, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l0
	mov	%l0, %l3
.L15:
	sub	%l3, %l2, %l0
	mov	%l0, %o0
	call	abs
	nop
	mov	%o0, %l2
	mov	1, %l4
	mov	1, %l0
	cmp	%l2, %l4
	bg	.L13
	nop
	ba	.L14
	nop
.L14:
.L16:
	mulx	%l3, %l3, %l0
	mov	1, %l2
	cmp	%l0, %l1
	bg	.L17
	nop
	ba	.L18
	nop
.L17:
	mov	1, %l0
	sub	%l3, %l0, %l0
	mov	%l0, %l3
.L19:
	mulx	%l3, %l3, %l2
	mov	1, %l0
	cmp	%l2, %l1
	bg	.L17
	nop
	ba	.L18
	nop
.L18:
	mov	%l3, %i0
	ba	.L11
	nop
.L11:
	ret
	restore
	.size	sqrt, .-sqrt
	.align 4
	.global makeList
	.type	makeList, #function
makeList:
	!#PROLOGUE# 0
	save	%sp, -112, %sp
	!#PROLOGUE 1
.L20:
	mov	%i0, %l2
	ldsw	[%fp-4], %l0
	mov	2, %l1
	mov	%g0, %l0
	mov	%l1, %o0
	mov	%l0, %o1
	call	initNode
	nop
	mov	%o0, %l0
	st	%l0, [%fp-4]
	ldsw	[%fp-8], %l0
	ldsw	[%fp-4], %l0
	st	%l0, [%fp-8]
	ldsw	[%fp-12], %l3
	mov	3, %l0
	mov	%l0, %l3
.L22:
	mov	1, %l0
	add	%l2, %l0, %l0
	mov	1, %l1
	cmp	%l3, %l0
	bl	.L23
	nop
	ba	.L24
	nop
.L23:
	ldsw	[%fp-8], %l1
	ldsw	[%fp-8], %l0
	mov	%l3, %o0
	mov	%l0, %o1
	call	initNode
	nop
	mov	%o0, %l0
	st	%l0, [%l1+0]
	ldsw	[%fp-8], %l0
	ldsw	[%l0+0], %l0
	st	%l0, [%fp-8]
	mov	2, %l0
	add	%l3, %l0, %l0
	mov	%l0, %l3
.L25:
	mov	1, %l0
	add	%l2, %l0, %l0
	mov	1, %l1
	cmp	%l3, %l0
	bl	.L23
	nop
	ba	.L24
	nop
.L24:
	ldsw	[%fp-4], %l0
	mov	%l0, %i0
	ba	.L21
	nop
.L21:
	ret
	restore
	.size	makeList, .-makeList
	.align 4
	.global rem
	.type	rem, #function
rem:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L26:
	mov	%i0, %l0
	ldsw	[%fp-4], %l1
	mov	%l0, %l0
	st	%l0, [%fp-4]
.L28:
	mov	%l0, %l1
	ldsw	[%l1+4], %l2
	mov	%g0, %l1
	mov	1, %l3
	cmp	%l2, %l1
	bne	.L29
	nop
	ba	.L30
	nop
.L29:
	mov	%l0, %l1
	ldsw	[%l1+4], %l2
	mov	%l0, %l1
	ldsw	[%l1+0], %l1
	st	%l1, [%l2+0]
	ba	.L30
	nop
.L30:
.L31:
	mov	%l0, %l1
	ldsw	[%l1+0], %l3
	mov	%g0, %l1
	mov	1, %l2
	cmp	%l3, %l1
	bne	.L32
	nop
	ba	.L33
	nop
.L32:
	mov	%l0, %l1
	ldsw	[%l1+0], %l1
	mov	%l0, %l2
	ldsw	[%l2+4], %l2
	st	%l2, [%l1+4]
	ba	.L33
	nop
.L33:
	mov	%l0, %l0
	mov	%l0, %o0
	call	free
	nop
	ldsw	[%fp-4], %l1
	mov	%l1, %i0
	ba	.L27
	nop
.L27:
	ret
	restore
	.size	rem, .-rem
	.align 4
	.global nuke
	.type	nuke, #function
nuke:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L34:
	mov	%i0, %l3
	ldsw	[%fp-4], %l2
	mov	%l3, %l0
	ldsw	[%l0+8], %l0
	mov	%l0, %l2
	ldsw	[%fp-8], %l4
	mov	2, %l0
	mulx	%l2, %l0, %l0
	mov	%l0, %l4
	mov	%l3, %l0
	ldsw	[%l0+0], %l0
	mov	%l0, %i0
	mov	%i0, %l3
.L36:
	mov	%l3, %l3
	mov	%g0, %l1
	mov	1, %l0
	cmp	%l3, %l1
	bne	.L37
	nop
	ba	.L38
	nop
.L37:
.L39:
	mov	%l3, %l0
	ldsw	[%l0+8], %l0
	mov	1, %l1
	cmp	%l0, %l4
	be	.L40
	nop
	ba	.L41
	nop
.L40:
	mov	%l3, %l3
	mov	%l3, %o0
	call	rem
	nop
	mov	%o0, %l0
	mov	%l0, %i0
	mov	%i0, %l3
	add	%l4, %l2, %l0
	mov	%l0, %l4
	ba	.L42
	nop
.L42:
.L46:
	mov	%l3, %l3
	mov	%g0, %l1
	mov	1, %l0
	cmp	%l3, %l1
	bne	.L37
	nop
	ba	.L38
	nop
.L41:
.L43:
	mov	%l3, %l0
	ldsw	[%l0+8], %l0
	mov	1, %l1
	cmp	%l0, %l4
	bg	.L44
	nop
	ba	.L45
	nop
.L44:
	mov	%l3, %l0
	ldsw	[%l0+8], %l0
	add	%l0, %l2, %l0
	mov	%l0, %o0
	mov	%l2, %o1
	call	.div
	nop
	mov	%o0, %l0
	mulx	%l0, %l2, %l0
	mov	%l0, %l4
	ba	.L45
	nop
.L45:
	mov	%l3, %l0
	ldsw	[%l0+0], %l0
	mov	%l0, %i0
	mov	%i0, %l3
	ba	.L42
	nop
.L38:
.L35:
	ret
	restore
	.size	nuke, .-nuke
	.align 4
	.global reduce
	.type	reduce, #function
reduce:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L47:
	mov	%i0, %l2
	mov	%i1, %l1
	ldsw	[%fp-4], %l0
	mov	%l1, %o0
	call	sqrt
	nop
	mov	%o0, %l0
	mov	%l0, %l0
.L49:
	mov	%l2, %l1
	ldsw	[%l1+8], %l1
	mov	1, %l3
	cmp	%l1, %l0
	bl	.L50
	nop
	ba	.L51
	nop
.L50:
	mov	%l2, %l2
	mov	%l2, %o0
	call	nuke
	nop
	mov	%l2, %l1
	ldsw	[%l1+0], %l1
	mov	%l1, %i0
	mov	%i0, %l2
.L52:
	mov	%l2, %l1
	ldsw	[%l1+8], %l1
	mov	1, %l3
	cmp	%l1, %l0
	bl	.L50
	nop
	ba	.L51
	nop
.L51:
.L48:
	ret
	restore
	.size	reduce, .-reduce
	.align 4
	.global printList
	.type	printList, #function
printList:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L53:
	mov	%i0, %l1
.L55:
	mov	%l1, %l1
	mov	%g0, %l0
	mov	1, %l2
	cmp	%l1, %l0
	bne	.L56
	nop
	ba	.L57
	nop
.L56:
	mov	%l1, %l1
	mov	%l1, %o0
	call	printNode
	nop
	mov	%l1, %l0
	ldsw	[%l0+0], %l0
	mov	%l0, %i0
	mov	%i0, %l1
.L58:
	mov	%l1, %l1
	mov	%g0, %l0
	mov	1, %l2
	cmp	%l1, %l0
	bne	.L56
	nop
	ba	.L57
	nop
.L57:
.L54:
	ret
	restore
	.size	printList, .-printList
	.align 4
	.global main
	.type	main, #function
main:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L59:
	add	%fp, -4, %l1
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l1, %o1
	call	scanf
	nop
.L61:
	ldsw	[%fp-4], %l1
	mov	2, %l2
	mov	1, %l0
	cmp	%l1, %l2
	bl	.L62
	nop
	ba	.L63
	nop
.L62:
	mov	1, %l0
	sub	%g0, %l0, %l0
	mov	%l0, %i0
	ba	.L60
	nop
	ba	.L63
	nop
.L63:
	ldsw	[%fp-8], %l0
	mov	%l1, %o0
	call	makeList
	nop
	mov	%o0, %l0
	st	%l0, [%fp-8]
	ldsw	[%fp-8], %l0
	mov	%l0, %o0
	mov	%l1, %o1
	call	reduce
	nop
	ldsw	[%fp-8], %l0
	mov	%l0, %o0
	call	printList
	nop
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L60
	nop
.L60:
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
