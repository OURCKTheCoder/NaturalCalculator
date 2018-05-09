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
		
		if(c instanceof Digit) {												//1. Factor -> digit V
			it = Factor_Exprs.genExpr1.listIterator();
			double V_inh = 
					((Digit)Analyzer.INPUT_STACK.pop()).getVal(); 			//   V.inh = digit.val;
			it.next();
			val = ((FactorSuffix)it.next()).recursiveDown(V_inh);				//   Factor.val = V.val;
			
		}
		
		else if(c instanceof Operator) {
			try {
				switch (((Operator)c).getOpchar()) {
				case LBK:														//2. Factor → (Expr) V
					Analyzer.INPUT_STACK.pop();
					it = Factor_Exprs.genExpr2.listIterator();
					it.next(); // TODO Match '('.
					double V_inh = ((Expr)it.next()).recursiveDown(null);		//   V.inh = Expr.val;
					if(Analyzer.INPUT_STACK.getTop().equals(it.next()))
						Analyzer.INPUT_STACK.pop(); // TODO Match ')'.
					else throw new NotMatchException(" [!] ')' Expected!");
					val = ((FactorSuffix)it.next()).recursiveDown(V_inh);		//   Factor.val = V.val;
					break;
					
				case SUB:														//3. Factor → -Factor V
					Analyzer.INPUT_STACK.pop();
					it = Factor_Exprs.genExpr3.listIterator();
					it.next(); // TODO Match '-'.
					double V_inh2 = -((Factor)it.next()).recursiveDown(null);	//   V.inh = -Factor.val;
					val = ((FactorSuffix)it.next()).recursiveDown(V_inh2);		//   Factor.val = V.val;
					break;
					
				case ADD:														//4. Factor → +Factor V
					Analyzer.INPUT_STACK.pop();
					it = Factor_Exprs.genExpr4.listIterator();
					it.next(); // TODO Match '-'.
					double	V_inh3 = ((Factor)it.next()).recursiveDown(null);	//   V.inh = Factor.val;
					val = ((FactorSuffix)it.next()).recursiveDown(V_inh3);		//   Factor.val = V.val;
					break;
					
				default: 
					throw new NotMatchException();
				}
			} catch(NotMatchException e) {
				Analyzer.INPUT_STACK = temp; // Restore.
				throw new NotMatchException();
			}

		}

		return val;
	
	}

	public static void main(String[] args) throws NotMatchException {
		List<Terminal> testdata = new ArrayList<Terminal>();
		{
			Collections.addAll(testdata,
					new Digit(3),
					new Operator(POW), new Operator(ADD),new Operator(SUB),new Operator(ADD),
					new Digit(3));
		}
		Analyzer.INPUT_STACK = LinkedStack.generate(testdata);
		System.out.println(Analyzer.INPUT_STACK);
		System.out.println(new Factor().recursiveDown(null));
	}
}
