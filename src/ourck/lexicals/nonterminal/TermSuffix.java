package ourck.lexicals.nonterminal;

import java.util.*;

import ourck.lexanalyzer.*;
import ourck.lexicals.*;
import ourck.lexicals.terminal.*;

import ourck.genexprs.GenExprs.TermSuffix_Exprs;

public class TermSuffix extends NonTerminal {
	private static int counter = 0;
	private static final int id = counter++;
	private double val, inh;
	
	@Override
	public double recursiveDown(Double inhAttr) throws NotMatchException {
		inh = inhAttr;
		LinkedStack<Terminal> temp = Analyzer.INPUT_STACK; // Backup.
		
		Terminal c = null;
		// 因为U能产生空符号，因此当选择了U->TU（T即本符号）时可能存在NullPointerExp。
		// （比如，全部输入已匹配完成，剩下的最后一个U成为空符号时，再getTop()会导致空指针异常）
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
			switch(((Operator)c).getOpchar()) {
			case MTP:
				Analyzer.INPUT_STACK.pop();
				it = TermSuffix_Exprs.genExpr1.listIterator();
				it.next(); // TODO Match '*'.
				
				try {
					val = inh * ((Factor)it.next()).recursiveDown(null);	// T.val = T.inh * Factor.val;
				} catch(NotMatchException e) {
					Analyzer.INPUT_STACK = temp; // Restore.
					throw new NotMatchException();
				}
				
				break;
			case DIV:
				Analyzer.INPUT_STACK.pop();
				it = TermSuffix_Exprs.genExpr2.listIterator();
				it.next(); // TODO Match '/'.
				
				try {
					val = inh / ((Factor)it.next()).recursiveDown(null);		// T.val = T.inh / Factor.val;
				} catch(NotMatchException e) {
					Analyzer.INPUT_STACK = temp; // Restore.
					throw new NotMatchException();
				}
				
				break;
			default:
				throw new NotMatchException();
			}
		}
		return val;
	}
	
	@Override
	public String toString() { return "TermSuffix #" + id + ": val = " + val; }
	
}
