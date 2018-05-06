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
	public String toString() { return "TermSuffix #" + id + ": val = " + val; }
	
	@Override
	public double recursiveDown(Double inhAttr) throws NotMatchException {
		inh = inhAttr;
		LinkedStack<Terminal> temp = Analyzer.INPUT_STACK; // Backup.
		Terminal c = Analyzer.INPUT_STACK.getTop();
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
	
}
