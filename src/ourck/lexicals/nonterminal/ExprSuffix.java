package ourck.lexicals.nonterminal;

import java.util.ListIterator;

import ourck.lexanalyzer.*;
import ourck.lexicals.*;
import ourck.lexicals.terminal.*;

import ourck.genexprs.GenExprs.ExprSuffix_Exprs;

public class ExprSuffix extends NonTerminal {
	private static int counter = 0;
	private static final int id = counter++;
	private double val, inh;
	
	@Override
	public double recursiveDown(Double inhAttr) throws NotMatchException {
		inh = inhAttr;
		LinkedStack<Terminal> temp = Analyzer.INPUT_STACK; // Backup.
		Terminal c = Analyzer.INPUT_STACK.getTop();
		ListIterator<Lexical> it = null;
		
		if(!(c instanceof Operator)) throw new NotMatchException();
		else {
			switch(((Operator)c).getOpchar()) {
			case ADD:
				Analyzer.INPUT_STACK.pop();
				it = ExprSuffix_Exprs.genExpr1.listIterator();
				it.next(); // TODO Match '+'.
				
				try {
					val = inh + ((Term)it.next()).recursiveDown(null);	// R.val = R.inh + Term.val;
				} catch(NotMatchException e) {
					Analyzer.INPUT_STACK = temp; // Restore.
					throw new NotMatchException();
				}
				
				break;
			case SUB:
				Analyzer.INPUT_STACK.pop();
				it = ExprSuffix_Exprs.genExpr2.listIterator();
				it.next(); // TODO Match '-'.
				
				try {
					val = inh - ((Term)it.next()).recursiveDown(null);		// R.val = R.inh - Term.val;
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
	public String toString() { return "ExprSuffix #" + id + ": val = " + val; }
	
}
