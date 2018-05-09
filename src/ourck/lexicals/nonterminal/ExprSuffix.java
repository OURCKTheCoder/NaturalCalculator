package ourck.lexicals.nonterminal;

import java.util.ListIterator;

import ourck.lexanalyzer.*;
import ourck.lexicals.*;
import ourck.lexicals.terminal.*;

import ourck.genexprs.GenExprs.ExprSuffix_Exprs;

public class ExprSuffix extends NonTerminal {
	private double val, inh;
	
	@Override
	public double recursiveDown(Double inhAttr) throws NotMatchException {
		inh = inhAttr;
		LinkedStack<Terminal> temp = Analyzer.INPUT_STACK; // Backup.
		
		Terminal c = null;
		// 因为S能产生空符号，因此当选择了S->RS（R即本符号）时可能存在NullPointerExp
		// （比如，全部输入已匹配完成，剩下的最后一个S成为空符号时，再getTop()会导致空指针异常）
		try {
			c = Analyzer.INPUT_STACK.getTop();
		} catch(NullPointerException e) {
			NotMatchException e2 = new NotMatchException();
			e2.initCause(new RuntimeException(e));
			throw e2;
		}
		
		ListIterator<Lexical> it = null;
		if(!(c instanceof Operator)) throw new NotMatchException();
		else {
			try {
				switch(((Operator)c).getOpchar()) {
				case ADD:
					Analyzer.INPUT_STACK.pop();
					it = ExprSuffix_Exprs.genExpr1.listIterator();			//1. R → + Term
					it.next(); // TODO Match '+'.
					
					val = inh + ((Term)it.next()).recursiveDown(null);		// R.val = R.inh + Term.val;
					
					break;
				case SUB:
					Analyzer.INPUT_STACK.pop();
					it = ExprSuffix_Exprs.genExpr2.listIterator();			//2. R → - Term
					it.next(); // TODO Match '-'.
					
					val = inh - ((Term)it.next()).recursiveDown(null);		// R.val = R.inh - Term.val;
					
					break;
				default:
//					Analyzer.INPUT_STACK = temp; // No need to Restore: no pop().
					throw new NotMatchException();
				}
			} catch(NotMatchException e) {
				Analyzer.INPUT_STACK = temp; // Restore.
				throw new NotMatchException(e.getMessage());
			}

		}
		return val;
	}
	
}
