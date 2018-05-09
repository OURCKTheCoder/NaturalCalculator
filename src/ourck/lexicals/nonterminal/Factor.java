package ourck.lexicals.nonterminal;

import ourck.genexprs.GenExprs.Factor_Exprs;
import static ourck.lexicals.terminal.OperatorType.*;

import java.util.*;

import ourck.lexanalyzer.*;
import ourck.lexicals.*;
import ourck.lexicals.terminal.*;

public class Factor extends NonTerminal {
	private double val;
	
	@Override
	public double recursiveDown(Double intAttr) throws NotMatchException {
		LinkedStack<Terminal> temp = Analyzer.INPUT_STACK; // Backup.
		Terminal c = Analyzer.INPUT_STACK.getTop();
		ListIterator<Lexical> it = null;
		
		if(c instanceof Digit) {											//1. Factor -> digit V
			it = Factor_Exprs.genExpr1.listIterator();
			double V_inh = 
					((Digit)Analyzer.INPUT_STACK.pop()).getVal(); 		//   V.inh = digit.val;
			it.next();
			val = ((FactorSuffix)it.next()).recursiveDown(V_inh);			//   Factor.val = V.val;
			
		}
		else if(c instanceof Operator) {
			switch (((Operator)c).getOpchar()) {
			case LBK:														//2. Factor → (Expr) V
				Analyzer.INPUT_STACK.pop();
				it = Factor_Exprs.genExpr2.listIterator();
				it.next(); // TODO Match '('.

				double V_inh;
				try {
					V_inh = ((Expr)it.next()).recursiveDown(null);			//   V.inh = Expr.val;
				} catch(NotMatchException e) {
					Analyzer.INPUT_STACK = temp; // Restore.
					throw new NotMatchException();
				}
				
				if(Analyzer.INPUT_STACK.getTop().equals(it.next()))
					Analyzer.INPUT_STACK.pop(); // TODO Match ')'.
				else throw new NotMatchException(" [!] ')' Expected!");
				
				try {
					val = ((FactorSuffix)it.next()).recursiveDown(V_inh);	//   Factor.val = V.val;
				} catch(NotMatchException e) {
					Analyzer.INPUT_STACK = temp; // Restore.
					throw new NotMatchException();
				}
				
				break;
				
			case SUB:														//3. Factor → -Factor V
				Analyzer.INPUT_STACK.pop();
				it = Factor_Exprs.genExpr3.listIterator();
				it.next(); // TODO Match '-'.
				
				double V_inh2;
				try {
					V_inh2 = -((Factor)it.next()).recursiveDown(null);		//   V.inh = -Factor.val;
				} catch(NotMatchException e) {
					Analyzer.INPUT_STACK = temp; // Restore.
					throw new NotMatchException();
				}
				
				try {
					val = ((FactorSuffix)it.next()).recursiveDown(V_inh2);	//   Factor.val = V.val;
				} catch(NotMatchException e) {
					Analyzer.INPUT_STACK = temp; // Restore.
					throw new NotMatchException();
				}
				
				break;
				
			case ADD:														//4. Factor → +Factor V
				Analyzer.INPUT_STACK.pop();
				it = Factor_Exprs.genExpr3.listIterator();
				it.next(); // TODO Match '-'.
				
				double V_inh3;
				try {
					V_inh3 = ((Factor)it.next()).recursiveDown(null);		//   V.inh = Factor.val;
				} catch(NotMatchException e) {
					Analyzer.INPUT_STACK = temp; // Restore.
					throw new NotMatchException();
				}
				
				try {
					val = ((FactorSuffix)it.next()).recursiveDown(V_inh3);	//   Factor.val = V.val;
				} catch(NotMatchException e) {
					Analyzer.INPUT_STACK = temp; // Restore.
					throw new NotMatchException();
				}
				
				break;
				
			default: 
				throw new NotMatchException();

			}
		}
		else {																//4. Factor → Factor1^Factor2
			it = Factor_Exprs.genExpr4.listIterator();
			double f1_val = ((Factor)it.next()).recursiveDown(null);
			Lexical powchar = Analyzer.INPUT_STACK.getTop();
			
			// Is this char a '^'?
			if(powchar instanceof Operator && 
			((Operator)powchar).getOpchar() == POW) {
				Analyzer.INPUT_STACK.pop();
				it.next(); // TODO Match '^'.
				double f2_val = ((Factor)it.next()).recursiveDown(null);
				
				val = Math.pow(f1_val, f2_val);
			}
			else throw new NotMatchException();
			
		}
		return val;
	}

	public static void main(String[] args) throws NotMatchException {
		List<Terminal> testdata = new ArrayList<Terminal>();
		{
			testdata.add(new Digit(2));
			testdata.add(new Operator(POW));
			testdata.add(new Digit(2));	
			testdata.add(new Operator(POW));
			testdata.add(new Digit(2));
		}
		Analyzer.INPUT_STACK = LinkedStack.generate(testdata);
		System.out.println(Analyzer.INPUT_STACK);
		System.out.println(new Factor().recursiveDown(null));
	}
}
