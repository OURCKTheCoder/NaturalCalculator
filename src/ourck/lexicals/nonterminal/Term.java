package ourck.lexicals.nonterminal;

import ourck.lexicals.*;
import ourck.lexicals.terminal.*;

import java.util.*;
import ourck.genexprs.GenExprs.Term_Exprs;
import ourck.lexanalyzer.*;

public class Term extends NonTerminal {
	private static int counter = 0;
	private static final int id = counter++;
	private double val; // Term本身就没有继承属性

	@Override
	public double recursiveDown(Double inhAttr) throws NotMatchException {
		ListIterator<NonTerminal> it = Term_Exprs.genExpr.listIterator();
		
		try {
			double factor_val = it.next().recursiveDown(null);				// U.inh = Factor.val;
			this.val = it.next().recursiveDown(factor_val);					// Term.val = U.val;
		} catch(NotMatchException e) {
			NotMatchException e2 = new NotMatchException();
			e2.initCause(e);
			throw e2;
		}
		return val;
		
	}

	@Override
	public String toString() { return "Term #" + id + ": val = " + val; }

	public static void main(String[] args) throws NotMatchException {
		List<Terminal> testdata = new ArrayList<Terminal>();
		{
			testdata.add(new Digit(3.14));
			testdata.add(new Operator(OperatorType.MTP));
			testdata.add(new Digit(3.14));
		}
		Analyzer.INPUT_STACK = LinkedStack.generate(testdata);
		System.out.println(Analyzer.INPUT_STACK);
		System.out.println(new Term().recursiveDown(null));
	}
}
