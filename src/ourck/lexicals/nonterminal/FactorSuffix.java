package ourck.lexicals.nonterminal;

import java.util.*;

import ourck.lexanalyzer.*;
import ourck.lexicals.*;
import ourck.lexicals.terminal.*;
import static ourck.genexprs.GenExprs.FactorSuffix_Exprs;

public class FactorSuffix extends NonTerminal {
	private double val, inh;
	
	@Override
	public double recursiveDown(Double inhAttr) throws NotMatchException {
		inh = inhAttr;
		LinkedStack<Terminal> temp = Analyzer.INPUT_STACK; // Backup.
		
		Terminal c = null;
		// 这个时候读栈顶为空的话说明FactorSuffix应该选择空符号产生式。
		try {
			c = Analyzer.INPUT_STACK.getTop();
		} catch(NullPointerException e) {
			val = inh;
			return val;
		}
		
		ListIterator<Lexical> it = null;
		if(!(c instanceof Operator)) throw new NotMatchException();
		else {
			OperatorType opchar = ((Operator)c).getOpchar();
			switch(opchar) {
			case POW:
				Analyzer.INPUT_STACK.pop();
				it = FactorSuffix_Exprs.genExpr.listIterator();
				it.next(); // TODO Match '^'.
				
				try {
					double tempval = inh; // TODO Stack corrupted?????
					double factor_val = ((Factor)it.next()).recursiveDown(null);
					val = Math.pow(tempval, factor_val);
				} catch(NotMatchException e) {
					Analyzer.INPUT_STACK = temp;
					throw new NotMatchException();
				}
				
				break;
			default : 
//				Analyzer.INPUT_STACK = temp; // No need to Restore: no pop().
//				NotMatchException e2 = new NotMatchException();
//				e2.initCause(e);
//				throw e2; // 关于含有空符号的产生式体，并不抛出异常			// S → ε:
				val = inh;													// S.val = S.inh
				break;
			}
		}
		
		return val;
	}

}
