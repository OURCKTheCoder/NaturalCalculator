package ourck.lexanalyzer;
/*-------------------------------------
 *	OURCK - 用于解析运算符的词法分析器
 *	2018年5月6日 下午11:36:39
 *-------------------------------------

/* 
 * 左右括号，+-/*
 */
import ourck.lexicals.terminal.*;

public class OpAnalyzer extends Analyzer {

	private static class Utils {
		public static boolean isOp(Character c) {
			return c == '+' || c == '-' || c == '*' || c == '/' ||
					c == '(' || c == ')';
		}
	}
	
	@Override
	public void analyze(String text) {
		char[] chars = text.toCharArray();
		
		// --------------Begin analyzing--------------
		for(int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if(Utils.isOp(c)) {
				SYMBOL_MAP.put(new CharPos(i),
						new Operator(Operator.charToOpType(c)));
				refreshMap();
			}
		}
	}

	public static void main(String[] args) {
		OpAnalyzer o = new OpAnalyzer();
		o.analyze("+-*/asdefgh+as-ds");
		System.out.println(SYMBOL_MAP);
	}
}
