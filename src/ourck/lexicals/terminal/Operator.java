package ourck.lexicals.terminal;

import static ourck.lexicals.terminal.OperatorType.*;

public class Operator extends Terminal {
	private final OperatorType op;
	public Operator(OperatorType op) { this.op = op; }
	public static OperatorType charToOpType(char c) {
		switch(c) {
		case '+': return ADD;
		case '-': return SUB;
		case '*':
		case 'X':
		case 'x': return MTP;
		case '/': return DIV;
		case '(': return LBK;
		case ')': return RBK;
		default: return null; // TODO default = ?
		}
	}
	public OperatorType getOpchar() { return op; } 
	@Override
	public String toString() { return "<OP>" + op; }
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Operator) ? 
				(((Operator)obj).op == this.op ? true : false)
				: false;
	}
}
