package ourck.genexprs;
/*-------------------------------------
 *	OURCK - 按产生式头存放的产生式体
 *	2018年5月6日 下午10:01:56
 *-------------------------------------

/* 
 * 产生式存放于List中
 */

import static ourck.lexicals.terminal.OperatorType.*;

import java.util.ArrayList;
import java.util.List;

import ourck.lexicals.Lexical;
import ourck.lexicals.nonterminal.*;
import ourck.lexicals.terminal.Operator;

public class GenExprs {
	public static class Expr_Exprs {
		// Expr → Term S
		public static final List<NonTerminal> genExpr = new ArrayList<NonTerminal>();
		static {
			genExpr.add(new Term());
			genExpr.add(new ExprConcat());
		}
	}
	
	public static class ExprConcat_Exprs {
		// S → R S1:
		public static final List<NonTerminal> genExpr = new ArrayList<NonTerminal>();
		static {
			genExpr.add(new ExprSuffix());
			genExpr.add(new ExprConcat());
		}
	}
	
	public static class ExprSuffix_Exprs {
		public static final List<Lexical> genExpr1 = new ArrayList<Lexical>();				// R → + Term
		static {
			genExpr1.add(new Operator(ADD));
			genExpr1.add(new Term());
		}
		public static final List<Lexical> genExpr2 = new ArrayList<Lexical>();				// R → - Term
		static {
			genExpr2.add(new Operator(SUB));
			genExpr2.add(new Term());
		}
	}
	
	public static class Factor_Exprs {
		public static final List<Lexical> genExpr1 = new ArrayList<Lexical>();				// Factor → (Expr)
		static {
			genExpr1.add(new Operator(LBK));
			genExpr1.add(new Expr());
			genExpr1.add( new Operator(RBK));
		}
		public static final List<Lexical> genExpr3 = new ArrayList<Lexical>();				// Factor → -Factor
		static {
			genExpr3.add(new Operator(SUB));
			genExpr3.add(new Factor());
		}
		// Factor → digit: Match directly.
	}
	
	public static class Term_Exprs {
		public static final List<NonTerminal> genExpr = new ArrayList<NonTerminal>();		// Term → Factor U
		static {
			genExpr.add(new Factor());
			genExpr.add(new TermConcat());
		}
	}
	
	public static class TermConcat_Exprs {
		public static final List<NonTerminal> genExpr = new ArrayList<NonTerminal>();		// U → T U1
		static {
			genExpr.add(new TermSuffix());
			genExpr.add(new TermConcat());
		}
	}
	
	public static class TermSuffix_Exprs {
		public static final List<Lexical> genExpr1 = new ArrayList<Lexical>();			// T → * Factor
		static{
			genExpr1.add(new Operator(MTP));
			genExpr1.add(new Factor());
		}
		public static final List<Lexical> genExpr2 = new ArrayList<Lexical>();			// T → / Factor
		static {
			genExpr2.add(new Operator(DIV));
			genExpr2.add(new Factor());
		}
	}
}