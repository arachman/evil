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
	mov	1, %l0
	mov	%l2, %i0
	ba	.L6
	nop
	ba	.L9
	nop
.L9:
	mov	%g0, %l0
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
	ldsw	[%fp-4], %l3
	mov	1, %l0
	mov	%l0, %l3
	ldsw	[%fp-8], %l2
	mov	%l1, %o0
	mov	%l3, %o1
	call	.div
	nop
	mov	%o0, %l0
	add	%l3, %l0, %l2
	mov	2, %l0
	mov	%l2, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l0
	mov	%l0, %l2
.L12:
	sub	%l2, %l3, %l0
	mov	%l0, %o0
	call	abs
	nop
	mov	%o0, %l0
	mov	1, %l4
	mov	1, %l3
	cmp	%l0, %l4
	bg	.L13
	nop
	ba	.L14
	nop
.L13:
	mov	%l2, %l3
	mov	%l1, %o0
	mov	%l3, %o1
	call	.div
	nop
	mov	%o0, %l0
	add	%l3, %l0, %l4
	mov	2, %l0
	mov	%l4, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l0
	mov	%l0, %l2
	sub	%l2, %l3, %l0
	mov	%l0, %o0
	call	abs
	nop
	mov	%o0, %l4
	mov	1, %l5
	mov	1, %l0
	cmp	%l4, %l5
	bg	.L13
	nop
	ba	.L14
	nop
.L14:
.L15:
	mulx	%l2, %l2, %l0
	mov	1, %l3
	cmp	%l0, %l1
	bg	.L16
	nop
	ba	.L17
	nop
.L16:
	mov	1, %l0
	sub	%l2, %l0, %l0
	mov	%l0, %l2
	mulx	%l2, %l2, %l3
	mov	1, %l0
	cmp	%l3, %l1
	bg	.L16
	nop
	ba	.L17
	nop
.L17:
	mov	%l2, %i0
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
.L18:
	mov	%i0, %l2
	ldsw	[%fp-4], %l0
	mov	2, %l0
	mov	%l0, %o0
	mov	%g0, %l0
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
.L20:
	mov	1, %l0
	add	%l2, %l0, %l0
	mov	1, %l1
	cmp	%l3, %l0
	bl	.L21
	nop
	ba	.L22
	nop
.L21:
	ldsw	[%fp-8], %l1
	mov	%l3, %o0
	ldsw	[%fp-8], %l0
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
	mov	1, %l0
	add	%l2, %l0, %l0
	mov	1, %l1
	cmp	%l3, %l0
	bl	.L21
	nop
	ba	.L22
	nop
.L22:
	ldsw	[%fp-4], %l0
	mov	%l0, %i0
	ba	.L19
	nop
.L19:
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
.L23:
	mov	%i0, %l0
	ldsw	[%fp-4], %l1
	mov	%l0, %l0
	st	%l0, [%fp-4]
.L25:
	mov	%l0, %l1
	ldsw	[%l1+4], %l2
	mov	%g0, %l1
	cmp	%l2, %l1
	bne	.L26
	nop
	ba	.L27
	nop
.L26:
	mov	1, %l1
	mov	%l0, %l1
	ldsw	[%l1+4], %l2
	mov	%l0, %l1
	ldsw	[%l1+0], %l1
	st	%l1, [%l2+0]
	ba	.L27
	nop
.L27:
	mov	%g0, %l1
.L28:
	mov	%l0, %l1
	ldsw	[%l1+0], %l2
	mov	%g0, %l1
	cmp	%l2, %l1
	bne	.L29
	nop
	ba	.L30
	nop
.L29:
	mov	1, %l1
	mov	%l0, %l1
	ldsw	[%l1+0], %l2
	mov	%l0, %l1
	ldsw	[%l1+4], %l1
	st	%l1, [%l2+4]
	ba	.L30
	nop
.L30:
	mov	%g0, %l1
	mov	%l0, %l0
	mov	%l0, %o0
	call	free
	nop
	ldsw	[%fp-4], %l1
	mov	%l1, %i0
	ba	.L24
	nop
.L24:
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
.L31:
	mov	%i0, %l2
	ldsw	[%fp-4], %l1
	mov	%l2, %l0
	ldsw	[%l0+8], %l0
	mov	%l0, %l1
	ldsw	[%fp-8], %l3
	mov	2, %l0
	mulx	%l1, %l0, %l0
	mov	%l0, %l3
	mov	%l2, %l0
	ldsw	[%l0+0], %l0
	mov	%l0, %i0
	mov	%i0, %l2
.L33:
	mov	%l2, %l2
	mov	%g0, %l0
	cmp	%l2, %l0
	bne	.L34
	nop
	ba	.L35
	nop
.L34:
.L36:
	mov	%l2, %l0
	ldsw	[%l0+8], %l4
	mov	1, %l0
	cmp	%l4, %l3
	be	.L37
	nop
	ba	.L38
	nop
.L37:
	mov	1, %l0
	mov	%l2, %l2
	mov	%l2, %o0
	call	rem
	nop
	mov	%o0, %l0
	mov	%l0, %i0
	mov	%i0, %l2
	add	%l3, %l1, %l0
	mov	%l0, %l3
	ba	.L39
	nop
.L39:
	mov	%l2, %l2
	mov	%g0, %l0
	cmp	%l2, %l0
	bne	.L34
	nop
	ba	.L35
	nop
.L38:
	mov	%g0, %l0
.L40:
	mov	%l2, %l0
	ldsw	[%l0+8], %l0
	mov	1, %l4
	cmp	%l0, %l3
	bg	.L41
	nop
	ba	.L42
	nop
.L41:
	mov	1, %l4
	mov	%l2, %l0
	ldsw	[%l0+8], %l0
	add	%l0, %l1, %l0
	mov	%l0, %o0
	mov	%l1, %o1
	call	.div
	nop
	mov	%o0, %l0
	mulx	%l0, %l1, %l0
	mov	%l0, %l3
	ba	.L42
	nop
.L42:
	mov	%g0, %l4
	mov	%l2, %l0
	ldsw	[%l0+0], %l0
	mov	%l0, %i0
	mov	%i0, %l2
	ba	.L39
	nop
.L35:
.L32:
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
.L43:
	mov	%i0, %l2
	mov	%i1, %l0
	ldsw	[%fp-4], %l3
	mov	%l0, %o0
	call	sqrt
	nop
	mov	%o0, %l0
	mov	%l0, %l3
.L45:
	mov	%l2, %l0
	ldsw	[%l0+8], %l1
	mov	1, %l0
	cmp	%l1, %l3
	bl	.L46
	nop
	ba	.L47
	nop
.L46:
	mov	%l2, %l2
	mov	%l2, %o0
	call	nuke
	nop
	mov	%l2, %l0
	ldsw	[%l0+0], %l0
	mov	%l0, %i0
	mov	%i0, %l2
	mov	%l2, %l0
	ldsw	[%l0+8], %l0
	mov	1, %l1
	cmp	%l0, %l3
	bl	.L46
	nop
	ba	.L47
	nop
.L47:
.L44:
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
.L48:
	mov	%i0, %l1
.L50:
	mov	%l1, %l1
	mov	%g0, %l0
	cmp	%l1, %l0
	bne	.L51
	nop
	ba	.L52
	nop
.L51:
	mov	%l1, %l1
	mov	%l1, %o0
	call	printNode
	nop
	mov	%l1, %l0
	ldsw	[%l0+0], %l0
	mov	%l0, %i0
	mov	%i0, %l1
	mov	%l1, %l1
	mov	%g0, %l0
	cmp	%l1, %l0
	bne	.L51
	nop
	ba	.L52
	nop
.L52:
.L49:
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
.L53:
	add	%fp, -4, %l0
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l0, %o1
	call	scanf
	nop
.L55:
	ldsw	[%fp-4], %l0
	mov	2, %l2
	mov	1, %l1
	cmp	%l0, %l2
	bl	.L56
	nop
	ba	.L57
	nop
.L56:
	mov	1, %l1
	mov	1, %l1
	sub	%g0, %l1, %l1
	mov	%l1, %i0
	ba	.L54
	nop
	ba	.L57
	nop
.L57:
	mov	%g0, %l1
	ldsw	[%fp-8], %l1
	mov	%l0, %o0
	call	makeList
	nop
	mov	%o0, %l1
	st	%l1, [%fp-8]
	ldsw	[%fp-8], %l1
	mov	%l1, %o0
	mov	%l0, %o1
	call	reduce
	nop
	ldsw	[%fp-8], %l1
	mov	%l1, %o0
	call	printList
	nop
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L54
	nop
.L54:
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
