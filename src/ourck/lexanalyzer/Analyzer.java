package ourck.lexanalyzer;
/*-------------------------------------
 *	OURCK - 词法分析器
 *	2018年5月1日 下午8:18:30
 *-------------------------------------

/* 
 * 用于对用户输入的表达式执行词法分析，并记录在符号表中。
 * 同时初始化符号表的迭代器，模拟一个“栈”。
 * 设计遵循装饰器设计模式。使用时，多个Analyzer子类实例处理同1个输入。
 */

import java.util.*;
import ourck.lexicals.terminal.*;

public abstract class Analyzer {
	public static final Map<CharPos, Terminal> SYMBOL_MAP = 
			new TreeMap<CharPos, Terminal>(); // keep input order
	public static LinkedStack<Terminal> INPUT_STACK = LinkedStack.generate();
	public static void refreshMap() {
		INPUT_STACK = LinkedStack.generate(SYMBOL_MAP.values());
	}
	abstract void analyze(String text);
}

class CharPos implements Comparable<CharPos>{
	private final int start, end;
	public CharPos(int start, int end) {
		this.start = start; this.end = end;
	}
	public CharPos(int indexpos) { 
		start = indexpos; end = indexpos;
	}
	public String toString() { return "<CharPos>(" + start + ", " + end + ")"; }
	public int begin() { return start; }
	public int end() { return end; }
	@Override
	public int compareTo(CharPos obj) { // By start position.
		return (this.start < obj.start) ? -1 :
			((this.start == obj.start) ? 0 : 1);
	}
}