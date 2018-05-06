package ourck.lexicals.nonterminal;

import java.util.*;
import ourck.lexicals.NotMatchException;
import ourck.genexprs.GenExprs.TermConcat_Exprs;

public class TermConcat extends NonTerminal {
	private static int counter = 0;
	private static final int id = counter++;
	private double val, inh;

	@Override
	public String toString() { return "TermConcat #" + id + ": val = " + val; }
	
	@Override
	public double recursiveDown(Double inhAttr) throws NotMatchException {
		this.inh = inhAttr;
		ListIterator<NonTerminal> it = TermConcat_Exprs.genExpr.listIterator();
		
		try {
			double T_val = it.next().recursiveDown(inh);					// T.inh = U.inh;
			double U1_val = it.next().recursiveDown(T_val);					// U1.inh = T.val;
			val = U1_val;													// U.val = U1.val;
		} catch(NotMatchException e) {
//			NotMatchException e2 = new NotMatchException();
//			e2.initCause(e);
//			throw e2; // 关于含有空符号的产生式体，并不抛出异常			// S → ε:
			val = inh;													// S.val = S.inh
		}
		return val;
	}
	
}