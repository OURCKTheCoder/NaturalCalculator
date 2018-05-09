package ourck.lexicals.nonterminal;

import ourck.lexicals.Lexical;
import ourck.lexicals.NotMatchException;

public abstract class NonTerminal implements Lexical {
	private double val;
	public void setVal(double val) { this.val = val; }
	public double getVal() { return this.val; } // Used for child class. ADD 'THIS'!
	abstract public double recursiveDown(Double inhAttr) throws NotMatchException;
}








