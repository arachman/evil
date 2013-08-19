binexp:
L1:
	loadinargument	base, 0, r1
	loadinargument	pow, 1, r2
	loadai	rarp, 4:sub, r3
	storeoutargument	8, 0
	call	malloc, 1
	loadret	r4
	storeai	r4, rarp, 4:sub
L3:
	loadi	%g0, r5
	loadi	1, r6
	comp	r2, r5, ccr
	cbreq	ccr, L4, L5
L4:
	loadai	rarp, 4:sub, r7
	loadi	1, r8
	storeai	r8, r7, 4:answer
	loadai	rarp, 4:sub, r9
	loadi	%g0, r10
	storeai	r10, r9, 0:multiplies
	loadai	rarp, 4:sub, r3
	storeret	r3
	jumpi	.L2
	jumpi	.L6
L6:
	loadi	2, r17
	storeoutargument	r2, 0
	storeoutargument	r17, 1
	call	.div, 2
	loadret	r18
	storeoutargument	r1, 0
	storeoutargument	r18, 1
	call	binexp, 2
	loadret	r16
	storeai	r16, rarp, 4:sub
L10:
	loadi	2, r19
	storeoutargument	r2, 0
	storeoutargument	r19, 1
	call	.div, 2
	loadret	r20
	loadi	2, r21
	mult	r20, r21, r22
	loadi	1, r23
	comp	r22, r2, ccr
	cbrne	ccr, L11, L12
L11:
	loadai	rarp, 4:sub, r24
	loadai	rarp, 4:sub, r26
	loadai	r26, 4:answer, r25
	mult	r1, r25, r27
	loadai	rarp, 4:sub, r29
	loadai	r29, 4:answer, r28
	mult	r27, r28, r30
	storeai	r30, r24, 4:answer
	loadai	rarp, 4:sub, r31
	loadai	rarp, 4:sub, r33
	loadai	r33, 0:multiplies, r32
	loadi	2, r34
	add	r32, r34, r35
	storeai	r35, r31, 0:multiplies
	jumpi	.L13
L13:
	loadai	rarp, 4:sub, r3
	storeret	r3
	jumpi	.L2
L2:
L12:
	loadai	rarp, 4:sub, r36
	loadai	rarp, 4:sub, r38
	loadai	r38, 4:answer, r37
	loadai	rarp, 4:sub, r40
	loadai	r40, 4:answer, r39
	mult	r37, r39, r41
	storeai	r41, r36, 4:answer
	loadai	rarp, 4:sub, r42
	loadai	rarp, 4:sub, r44
	loadai	r44, 0:multiplies, r43
	loadi	1, r45
	add	r43, r45, r46
	storeai	r46, r42, 0:multiplies
	jumpi	.L13
L5:
L7:
	loadi	1, r11
	loadi	1, r12
	comp	r2, r11, ccr
	cbreq	ccr, L8, L9
L8:
	loadai	rarp, 4:sub, r13
	storeai	r1, r13, 4:answer
	loadai	rarp, 4:sub, r14
	loadi	%g0, r15
	storeai	r15, r14, 0:multiplies
	loadai	rarp, 4:sub, r3
	storeret	r3
	jumpi	.L2
	jumpi	.L9
L9:
	jumpi	.L6
rpexp:
L14:
	loadinargument	base, 0, r47
	loadinargument	pow, 1, r48
	loadai	rarp, 12:r, r49
	storeoutargument	8, 0
	call	malloc, 1
	loadret	r50
	storeai	r50, rarp, 12:r
	loadai	rarp, 12:r, r51
	loadi	%g0, r52
	storeai	r52, r51, 0:multiplies
	loadai	rarp, 4:p, r53
	mov	r48, r53
	loadai	rarp, 8:sub, r54
	mov	r47, r54
L16:
	loadi	%g0, r55
	loadi	1, r56
	comp	r48, r55, ccr
	cbreq	ccr, L17, L18
L17:
	loadai	rarp, 12:r, r57
	loadi	1, r58
	storeai	r58, r57, 4:answer
	loadai	rarp, 12:r, r49
	storeret	r49
	jumpi	.L15
	jumpi	.L18
L18:
L19:
	loadi	2, r59
	storeoutargument	r53, 0
	storeoutargument	r59, 1
	call	.div, 2
	loadret	r60
	loadi	2, r61
	mult	r60, r61, r62
	loadi	1, r63
	comp	r62, r53, ccr
	cbreq	ccr, L20, L21
L20:
	mult	r54, r54, r64
	mov	r64, r54
	loadi	2, r65
	storeoutargument	r53, 0
	storeoutargument	r65, 1
	call	.div, 2
	loadret	r66
	mov	r66, r53
	loadai	rarp, 12:r, r67
	loadai	rarp, 12:r, r69
	loadai	r69, 0:multiplies, r68
	loadi	1, r70
	add	r68, r70, r71
	storeai	r71, r67, 0:multiplies
L22:
	loadi	2, r72
	storeoutargument	r53, 0
	storeoutargument	r72, 1
	call	.div, 2
	loadret	r73
	loadi	2, r74
	mult	r73, r74, r75
	loadi	1, r76
	comp	r75, r53, ccr
	cbreq	ccr, L20, L21
L21:
	loadai	rarp, 12:r, r77
	storeai	r54, r77, 4:answer
	loadi	2, r78
	storeoutargument	r53, 0
	storeoutargument	r78, 1
	call	.div, 2
	loadret	r79
	mov	r79, r53
L23:
	loadi	%g0, r80
	loadi	1, r81
	comp	r53, r80, ccr
	cbrgt	ccr, L24, L25
L24:
	mult	r54, r54, r82
	mov	r82, r54
	loadai	rarp, 12:r, r83
	loadai	rarp, 12:r, r85
	loadai	r85, 0:multiplies, r84
	loadi	1, r86
	add	r84, r86, r87
	storeai	r87, r83, 0:multiplies
L26:
	loadi	2, r88
	storeoutargument	r53, 0
	storeoutargument	r88, 1
	call	.div, 2
	loadret	r89
	loadi	2, r90
	mult	r89, r90, r91
	loadi	1, r92
	comp	r91, r53, ccr
	cbrne	ccr, L27, L28
L27:
	loadai	rarp, 12:r, r93
	loadai	rarp, 12:r, r95
	loadai	r95, 4:answer, r94
	mult	r94, r54, r96
	storeai	r96, r93, 4:answer
	loadai	rarp, 12:r, r97
	loadai	rarp, 12:r, r99
	loadai	r99, 0:multiplies, r98
	loadi	1, r100
	add	r98, r100, r101
	storeai	r101, r97, 0:multiplies
	jumpi	.L28
L28:
	loadi	2, r102
	storeoutargument	r53, 0
	storeoutargument	r102, 1
	call	.div, 2
	loadret	r103
	mov	r103, r53
L29:
	loadi	%g0, r104
	loadi	1, r105
	comp	r53, r104, ccr
	cbrgt	ccr, L24, L25
L25:
	loadai	rarp, 12:r, r49
	storeret	r49
	jumpi	.L15
L15:
checkexp:
L30:
	loadinargument	base, 0, r106
	loadinargument	pow, 1, r107
	loadai	rarp, 4:i, r108
	loadi	%g0, r109
	mov	r109, r108
	loadai	rarp, 8:result, r110
	loadi	1, r111
	mov	r111, r110
L32:
	loadi	%g0, r112
	loadi	1, r113
	comp	r107, r112, ccr
	cbreq	ccr, L33, L34
L33:
	loadi	1, r114
	storeret	r114
	jumpi	.L31
	jumpi	.L34
L34:
L35:
	loadi	1, r115
	comp	r108, r107, ccr
	cbrlt	ccr, L36, L37
L36:
	mult	r110, r106, r116
	mov	r116, r110
	loadi	1, r117
	add	r108, r117, r118
	mov	r118, r108
L38:
	loadi	1, r119
	comp	r108, r107, ccr
	cbrlt	ccr, L36, L37
L37:
	storeret	r110
	jumpi	.L31
L31:
main:
L39:
	addi	rarp, 4:base, r120
	read	r120
	addi	rarp, 8:pow, r121
	read	r121
L41:
	loadai	rarp, 8:pow, r121
	loadi	%g0, r122
	loadi	1, r123
	comp	r121, r122, ccr
	cbrlt	ccr, L42, L43
L42:
	loadi	%g0, r124
	storeret	r124
	jumpi	.L40
	jumpi	.L43
L43:
	loadai	rarp, 16:b, r125
	loadai	rarp, 4:base, r120
	storeoutargument	r120, 0
	storeoutargument	r121, 1
	call	binexp, 2
	loadret	r126
	storeai	r126, rarp, 16:b
	loadai	rarp, 16:b, r128
	loadai	r128, 4:answer, r127
	println	r127
	loadai	rarp, 16:b, r130
	loadai	r130, 0:multiplies, r129
	println	r129
	loadai	rarp, 20:rp, r131
	storeoutargument	r120, 0
	storeoutargument	r121, 1
	call	rpexp, 2
	loadret	r132
	storeai	r132, rarp, 20:rp
	loadai	rarp, 20:rp, r134
	loadai	r134, 4:answer, r133
	println	r133
	loadai	rarp, 20:rp, r136
	loadai	r136, 0:multiplies, r135
	println	r135
	loadai	rarp, 12:c, r137
	storeoutargument	r120, 0
	storeoutargument	r121, 1
	call	checkexp, 2
	loadret	r138
	mov	r138, r137
L44:
	loadai	rarp, 16:b, r141
	loadai	r141, 4:answer, r140
	loadi	1, r142
	comp	r140, r137, ccr
	cbreq	ccr, L48, L49
L49:
	loadi	%g0, r142
L48:
	loadai	rarp, 20:rp, r144
	loadai	r144, 4:answer, r143
	loadi	1, r145
	comp	r143, r137, ccr
	cbreq	ccr, L50, L51
L51:
	loadi	%g0, r145
L50:
	and	r142, r145, r139
	compi	r139, 1, ccr
	cbreq	ccr, L45, L46
L45:
	loadi	1, r146
	println	r146
	jumpi	.L47
L47:
	loadi	%g0, r148
	storeret	r148
	jumpi	.L40
L40:
L46:
	loadi	%g0, r147
	println	r147
	jumpi	.L47
