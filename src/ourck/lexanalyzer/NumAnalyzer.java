package ourck.lexanalyzer;
/*-------------------------------------
 *	OURCK - 用于解析数字的词法分析器
 *	2018年5月6日 下午11:35:03
 *-------------------------------------

/* 
 * EnterTextHere
 */
import ourck.lexicals.terminal.Digit;

public class NumAnalyzer extends Analyzer {

	private static class Utils {
		public static boolean isNum(Character c) {
			return (c - '0' >= 0) && ('9' - c >= 0);
		}
	}
	
	@Override
	public void analyze(String text) {
		char[] chars = text.toCharArray();
		int state = 0;
		int phead = 0, prear = 0;
		
		// --------------Begin analyzing--------------
		StringBuilder numbuilder = new StringBuilder();
		for(int i = 0; i < chars.length + 1; i++) { // 1 more time for void char.
			Character c;
			if(i == chars.length) {
				c = null; // Void char.
				state = 3;
			}
			else c = chars[i];
			
			// NFA Begins:
			switch(state) {
			case 0:
				numbuilder.delete(0, numbuilder.length());
				phead = i;
				if(Utils.isNum(c)) {
					numbuilder.append(c);
					state = 1;
				}
				break;
			
			case 1:
				if(Utils.isNum(c)) {
					numbuilder.append(c);
					state = 1;
				}
				else if(c == '.') {
					numbuilder.append('.');
					state = 2;
				}
				else {
					i--; // [!] Backward 1 more time ahead!
					state = 3;
				}
				break;
			
			case 2:
				if(Utils.isNum(c)) {
					numbuilder.append(c);
					state = 2;
				}
				else if(c == '.') {
					throw new RuntimeException("[!] Invalid input! NO MORE '.'! ");
				}
				else {
					i--; // [!] Backward 1 more time ahead!
					state = 3;
				}
				break;
				
			case 3:
				i--; // "Wait a second!"
				prear = i;
//!				System.out.println(numbuilder.toString()); // TODO DEBUG ONLY
				SYMBOL_MAP.put(new CharPos(phead, prear),
						new Digit(Double.parseDouble(numbuilder.toString())));
				refreshStack();
				state = 0;
				i++; // "OK, now we can terminate."
				break;
			
			}
		}
	}
	
	public static void main(String[] args) {
		NumAnalyzer n = new NumAnalyzer();
		String str = "3141 5926 3626498.3154911";
		char[] chars = str.toCharArray();
		
		//1. Analyzing test.
		try {
			n.analyze(str);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		//2. Reading test.
		for(CharPos pos : SYMBOL_MAP.keySet()) {
			for(int i = pos.begin(); i <= pos.end(); i++) 
				System.out.print(chars[i]);
			System.out.println();
		}
		System.out.println(SYMBOL_MAP);
	}
	
}
