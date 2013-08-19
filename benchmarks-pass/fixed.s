	.section	".text"
	.align 4
	.global int2fix
	.type	int2fix, #function
int2fix:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L1:
	mov	%i0, %l0
	sethi 	%hi(65536), %l1
	or	%l1, %lo(65536), %l1
	mulx	%l0, %l1, %l0
	mov	%l0, %i0
	ba	.L2
	nop
.L2:
	ret
	restore
	.size	int2fix, .-int2fix
	.align 4
	.global printfixhelper
	.type	printfixhelper, #function
printfixhelper:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L3:
	mov	%i0, %l1
	ldsw	[%fp-4], %l0
	mov	10, %l0
	mov	%l1, %o0
	mov	%l0, %o1
	call	.div
	nop
	mov	%o0, %l0
	mov	10, %l2
	mulx	%l0, %l2, %l0
	sub	%l1, %l0, %l0
	mov	%l0, %l0
.L4:
	ret
	restore
	.size	printfixhelper, .-printfixhelper
	.align 4
	.global printfix
	.type	printfix, #function
printfix:
	!#PROLOGUE# 0
	save	%sp, -104, %sp
	!#PROLOGUE 1
.L5:
	mov	%i0, %l0
	ldsw	[%fp-4], %l2
	sethi 	%hi(65536), %l1
	or	%l1, %lo(65536), %l1
	mov	%l0, %o0
	mov	%l1, %o1
	call	.div
	nop
	mov	%o0, %l1
	mov	%l1, %l2
	ldsw	[%fp-8], %l1
	sethi 	%hi(65536), %l1
	or	%l1, %lo(65536), %l1
	mulx	%l2, %l1, %l1
	sub	%l0, %l1, %l0
	mov	%l0, %l1
	sethi	%hi(.LLC0), %g1
	or	%g1, %lo(.LLC0), %o0
	mov	%l2, %o1
	call	printf
	nop
	sethi	%hi(.LLC1), %g1
	or	%g1, %lo(.LLC1), %o0
	mov	%l1, %o1
	call	printf
	nop
.L6:
	ret
	restore
	.size	printfix, .-printfix
	.align 4
	.global fixmul
	.type	fixmul, #function
fixmul:
	!#PROLOGUE# 0
	save	%sp, -112, %sp
	!#PROLOGUE 1
.L7:
	mov	%i0, %l2
	mov	%i1, %l0
	ldsw	[%fp-4], %l1
	mov	256, %l1
	mov	%l2, %o0
	mov	%l1, %o1
	call	.div
	nop
	mov	%o0, %l1
	mov	%l1, %l1
	ldsw	[%fp-8], %l2
	mov	256, %l2
	mov	%l0, %o0
	mov	%l2, %o1
	call	.div
	nop
	mov	%o0, %l0
	mov	%l0, %l2
	ldsw	[%fp-12], %l0
	mulx	%l1, %l2, %l0
	mov	%l0, %l0
	mov	%l0, %i0
	ba	.L8
	nop
.L8:
	ret
	restore
	.size	fixmul, .-fixmul
	.align 4
	.global xcubed
	.type	xcubed, #function
xcubed:
	!#PROLOGUE# 0
	save	%sp, -96, %sp
	!#PROLOGUE 1
.L9:
	mov	%i0, %l0
	mov	%l0, %o0
	mov	%l0, %o0
	mov	%l0, %o1
	call	fixmul
	nop
	mov	%o0, %l0
	mov	%l0, %o2
	call	fixmul
	nop
	mov	%o0, %l0
	mov	%l0, %i0
	ba	.L10
	nop
.L10:
	ret
	restore
	.size	xcubed, .-xcubed
	.align 4
	.global integrate
	.type	integrate, #function
integrate:
	!#PROLOGUE# 0
	save	%sp, -112, %sp
	!#PROLOGUE 1
.L11:
	mov	%i0, %l1
	mov	%i1, %l3
	mov	%i2, %l4
	ldsw	[%fp-4], %l5
	mov	%g0, %l0
	mov	%l0, %o0
	call	int2fix
	nop
	mov	%o0, %l0
	mov	%l0, %l5
	mov	%l1, %o0
	call	int2fix
	nop
	mov	%o0, %l0
	mov	%l0, %l1
	mov	%l3, %o0
	call	int2fix
	nop
	mov	%o0, %l0
	mov	%l0, %l3
	ldsw	[%fp-8], %l2
	sub	%l3, %l1, %l0
	mov	%l0, %o0
	mov	%l4, %o1
	call	.div
	nop
	mov	%o0, %l0
	mov	%l0, %l2
.L13:
	mov	%g0, %l0
	mov	1, %l6
	cmp	%l2, %l0
	be	.L14
	nop
	ba	.L15
	nop
.L14:
	mov	1, %l6
	mov	1, %l0
	mov	%l0, %l2
	ba	.L15
	nop
.L15:
	mov	%g0, %l6
	ldsw	[%fp-12], %l6
	mov	%l1, %l6
.L16:
	mov	1, %l0
	cmp	%l6, %l3
	ble	.L17
	nop
	ba	.L18
	nop
.L17:
	mov	%l6, %o0
	call	xcubed
	nop
	mov	%o0, %l0
	mov	%l0, %o0
	mov	%l4, %o1
	call	.div
	nop
	mov	%o0, %l0
	add	%l5, %l0, %l0
	mov	%l0, %l5
	add	%l6, %l2, %l0
	mov	%l0, %l6
	mov	1, %l0
	cmp	%l6, %l3
	ble	.L17
	nop
	ba	.L18
	nop
.L18:
	mov	%l5, %i0
	ba	.L12
	nop
.L12:
	ret
	restore
	.size	integrate, .-integrate
	.align 4
	.global main
	.type	main, #function
main:
	!#PROLOGUE# 0
	save	%sp, -120, %sp
	!#PROLOGUE 1
.L19:
	add	%fp, -20, %l4
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l4, %o1
	call	scanf
	nop
.L21:
	ldsw	[%fp-20], %l4
	mov	%g0, %l1
	mov	1, %l0
	cmp	%l4, %l1
	bg	.L22
	nop
	ba	.L23
	nop
.L22:
	add	%fp, -4, %l6
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l6, %o1
	call	scanf
	nop
	add	%fp, -8, %l5
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l5, %o1
	call	scanf
	nop
	add	%fp, -12, %l3
	sethi	%hi(.LLC2), %o0
	or	%o0, %lo(.LLC2), %o0
	mov	%l3, %o1
	call	scanf
	nop
	ldsw	[%fp-16], %l2
	mov	%g0, %l0
	mov	%l0, %l2
.L24:
	sethi 	%hi(32768), %l1
	or	%l1, %lo(32768), %l1
	mov	1, %l0
	cmp	%l2, %l1
	bl	.L25
	nop
	ba	.L26
	nop
.L25:
	ldsw	[%fp-4], %l6
	mov	%l6, %o0
	ldsw	[%fp-8], %l5
	mov	%l5, %o1
	ldsw	[%fp-12], %l3
	mov	%l3, %o2
	call	integrate
	nop
	mov	1, %l0
	add	%l2, %l0, %l0
	mov	%l0, %l2
	sethi 	%hi(32768), %l0
	or	%l0, %lo(32768), %l0
	mov	1, %l1
	cmp	%l2, %l0
	bl	.L25
	nop
	ba	.L26
	nop
.L26:
	mov	%l6, %o0
	mov	%l5, %o1
	mov	%l3, %o2
	call	integrate
	nop
	mov	%o0, %l0
	mov	%l0, %o3
	call	printfix
	nop
	mov	1, %l0
	sub	%l4, %l0, %l0
	mov	%l0, %l4
	mov	%g0, %l0
	mov	1, %l1
	cmp	%l4, %l0
	bg	.L22
	nop
	ba	.L23
	nop
.L23:
	mov	%g0, %l0
	mov	%l0, %i0
	ba	.L20
	nop
.L20:
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
