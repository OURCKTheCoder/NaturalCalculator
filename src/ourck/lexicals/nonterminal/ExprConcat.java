package ourck.lexicals.nonterminal;

import java.util.*;
import ourck.lexicals.NotMatchException;
import ourck.genexprs.GenExprs.ExprConcat_Exprs;

public class ExprConcat extends NonTerminal {
	private static int counter = 0;
	private static final int id = counter++;
	private double val, inh;
	
	@Override
	public double recursiveDown(Double inhAttr) throws NotMatchException {
		this.inh = inhAttr;
		
		ListIterator<NonTerminal> it = ExprConcat_Exprs.genExpr.listIterator();
		
		double R_val = 0;
		try {
			R_val = it.next().recursiveDown(inh);						// R.inh = S.inh;
			double S1_val = it.next().recursiveDown(R_val);				// S1.inh = R.val;
			val = S1_val;												// S.val = S1.val;
		} catch(NotMatchException e) {
//			NotMatchException e2 = new NotMatchException();
//			e2.initCause(e);
//			throw e2; // 关于含有空符号的产生式体，并不抛出异常			// S → ε:
			val = inh;													// S.val = S.inh
		}
		return val;
	}
	@Override
	public String toString() { return "ExprConcat #" + id + ": val = " + val; }
}