package ourck.mainconsole;
/*-------------------------------------
 *	OURCK - 主控制台
 *	2018年5月6日 下午11:47:13
 *-------------------------------------

/* 
 * 巴扎嘿
 */
import ourck.lexanalyzer.*;
import ourck.lexicals.NotMatchException;
import ourck.lexicals.nonterminal.StartChar;

import static ourck.mainconsole.ScreenReader.jin;

public class MainConsole {
	public static void main(String[] args) {
		NumAnalyzer nAnalyzer = new NumAnalyzer();
		OpAnalyzer oAnalyzer = new OpAnalyzer();
		
		while(true) {
			try {
				String testStr = jin();
				nAnalyzer.analyze(testStr);
				oAnalyzer.analyze(testStr);
				System.out.println(" = " + new StartChar().recursiveDown(null));
			} catch (NotMatchException e) {
				e.printStackTrace();
//				System.err.println(e.getMessage());
			} catch (NullPointerException e) {
				System.err.println(e.getMessage() + " Maybe '(' & ')' not match? ");
			} catch (NumberFormatException e) {
				System.err.println(" [!] Plz input a valid num!");
			} finally {
				System.out.println("--------------------------------------");
			}
		}

	}
}
