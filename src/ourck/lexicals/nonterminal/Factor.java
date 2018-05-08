package ourck.lexicals.nonterminal;

import ourck.genexprs.GenExprs.Factor_Exprs;

import java.util.*;

import ourck.lexanalyzer.*;
import ourck.lexicals.*;
import ourck.lexicals.terminal.*;

public class Factor extends NonTerminal {
	private static int counter = 0;
	private static final int id = counter++;
	private double val;
	
	@Override
	public double recursiveDown(Double intAttr) throws NotMatchException {
		LinkedStack<Terminal> temp = Analyzer.INPUT_STACK; // Backup.
		Terminal c = Analyzer.INPUT_STACK.getTop();
		ListIterator<Lexical> it = null;
		
		if(c instanceof Digit) {
			//1. Factor -> digit
			val = ((Digit)c).getVal(); 										// Factor.val = digit.val;
			Analyzer.INPUT_STACK.pop();
		}
		else if(c instanceof Operator) {
			switch (((Operator) c).getOpchar()) {
			case LBK:
				//2. Factor → (Expr)
				Analyzer.INPUT_STACK.pop();
				it = Factor_Exprs.genExpr1.listIterator();
				it.next(); // TODO Match '('.
				
				try {
					val = ((Expr)it.next()).recursiveDown(null);			// Factor.val = Expr.val;
				} catch(NotMatchException e) {
					Analyzer.INPUT_STACK = temp; // Restore.
					throw new NotMatchException();
				}
				
				if(Analyzer.INPUT_STACK.getTop().equals(it.next()))
					Analyzer.INPUT_STACK.pop(); // TODO Match ')'.
				else throw new NotMatchException(" [!] ')' Expected!");
				
				break;
				
			case SUB:
				//3. Factor → -Factor
				Analyzer.INPUT_STACK.pop();
				it = Factor_Exprs.genExpr3.listIterator();
				it.next(); // TODO Match '-'.
				
				try {
					val = -((Factor)it.next()).recursiveDown(null);			// Factor.val = -digit.val;
				} catch(NotMatchException e) {
					Analyzer.INPUT_STACK = temp; // Restore.
					throw new NotMatchException();
				}
				
				break;

			default: throw new NotMatchException(); 
			}
		}
		return val;
	}

	@Override
	public String toString() { return "Factor #" + id + ": val = " + val; }
	
	public static void main(String[] args) throws NotMatchException {
		List<Terminal> testdata = new ArrayList<Terminal>();
		{
			testdata.add(new Digit(-3.14));
		}
		Analyzer.INPUT_STACK = LinkedStack.generate(testdata);
		System.out.println(Analyzer.INPUT_STACK);
		System.out.println(new Factor().recursiveDown(null));
	}
}
