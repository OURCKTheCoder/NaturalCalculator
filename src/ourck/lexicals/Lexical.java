package ourck.lexicals;
/*-------------------------------------
 *	OURCK - 词素的公共接口
 *	2018年5月6日 下午11:37:22
 *-------------------------------------

/* 
 * 这个接口仅作为标记，用于向上转型。
 */

/*
 * 所有的符号及其语义规则如下：
 * 
Expr → Term S
	{ S.inh = Term.val;
	Expr.val = S.val; }
S → R S 1
	{ R.inh = S.inh;
	S 1 .inh = R.val;
	{ S.val = S 1 .val; }
S → ε		
	{ S.val = S.inh; }
R → + Term	
	{ R.val = R.inh + Term.val; }
R → - Term	
	{ R.val = R.inh – Term.val; }

Term → Factor U
	{ U.inh = Factor.val;
	Term.val = U.val; }
U → T U 1
	{ T.inh = U.inh;
	U 1 .inh = T.val;
	U.val = U 1 .val;}
U → ε
	{ U.val = U.inh; }
T → * Factor
	{ T.val = T.inh * Factor.val; }
T → / Factor 
	{ T.val = T.inh / Factor.val; }

Factor → (Expr)
	{ Factor.val = Expr.val; }
Factor → digit
	{ Factor.val = digit.val; }
Factor → -Factor 
	{ Factor.val = -Factor.val; }
	
(注: S 即为 ExprConcat , R 即为 ExprSuffix , U 为 TermConcat , T 为 TermSuffix )
 *
 */
public interface Lexical {}

