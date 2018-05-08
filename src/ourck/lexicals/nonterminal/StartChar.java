package ourck.lexicals.nonterminal;

import ourck.lexanalyzer.Analyzer;
import ourck.lexicals.NotMatchException;

public class StartChar extends NonTerminal {
	private double val;
	@Override
	public double recursiveDown(Double inhAttr) throws NotMatchException {
		val = new Expr().recursiveDown(null);
		
		if(Analyzer.INPUT_STACK.isEmpty())
			return val;
		else throw new NotMatchException(" [!] Plz check grammar!");
	}

	@Override
	public String toString() { return "StartChar"; }

}
