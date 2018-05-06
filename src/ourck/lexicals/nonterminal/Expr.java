package ourck.lexicals.nonterminal;
/*-------------------------------------
 *	OURCK - 非终结符号：Expr
 *	2018年5月6日 下午11:39:23
 *-------------------------------------

/* 
 * 开始符号，推导从这开始。
 */

import java.util.*;
import ourck.lexicals.NotMatchException;
import ourck.lexicals.terminal.Digit;
import ourck.lexicals.terminal.Operator;
import ourck.lexicals.terminal.OperatorType;
import ourck.lexicals.terminal.Terminal;
import ourck.genexprs.GenExprs.Expr_Exprs;
import ourck.lexanalyzer.Analyzer;
import ourck.lexanalyzer.LinkedStack;

public class Expr extends NonTerminal {
//	private static final Map<Integer, List<Lexical>> GENEXPR_LIST = new LinkedHashMap<>();
//	static {
//		List<Lexical> exprBody1 = new ArrayList<Lexical>();
//		Collections.addAll(exprBody1, new Term(), new ExprConcat());
//		GENEXPR_LIST.put(1, exprBody1);
//	} //由于表达式及其语义动作各异，没办法采用统一的办法。
	
	private static int counter = 0;
	private static final int id = counter++; // Reference counter.
	private double val; // 开始符号没有继承属性

	@Override
	public double recursiveDown(Double inhAttr) throws NotMatchException {
		ListIterator<NonTerminal> it = Expr_Exprs.genExpr.listIterator();
		
		try {
			double term_val = it.next().recursiveDown(null);
			this.val = it.next().recursiveDown(term_val);					// S.inh = Term.val; Expr.val = S.val;
		} catch(NotMatchException e) {
			NotMatchException e2 = new NotMatchException();
			e2.initCause(e);
			throw e2;
		}
		
		return val;
	}

	@Override
	public String toString() { return "Expression #" + id + ": val = " + val; }

	public static void main(String[] args) throws NotMatchException {
		List<Terminal> testdata = new ArrayList<Terminal>();
		{
			testdata.add(new Digit(3.14));
			testdata.add(new Operator(OperatorType.ADD));
			testdata.add(new Digit(3.14));
		}
		Analyzer.INPUT_STACK = LinkedStack.generate(testdata);
		System.out.println(Analyzer.INPUT_STACK);
		System.out.println(new Expr().recursiveDown(null));
	}
}
