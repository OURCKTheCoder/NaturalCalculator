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
				it = FactorSuffix_Exprs.genExpr.listIterator();		//1.V → ^ Factor
				it.next(); // TODO Match '^'.
				
				try {
					double factor_val = ((Factor)it.next()).recursiveDown(null);
					val = Math.pow(inh, factor_val);				//  val = pow(V.inh, Factor.val)
				} catch(NotMatchException e) {
					Analyzer.INPUT_STACK = temp;
					throw new NotMatchException();
				}
				
				break;
			default : 
				val = inh;											//2. S → ε: S.val = S.inh
				break;
			}
		}
		
		return val;
	}

}
