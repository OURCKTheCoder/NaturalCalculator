package ourck.lexicals.terminal;

public class Digit extends Terminal {
	private final double val;
	public Digit() { this.val = 0; } // Only used for type determination.
	public Digit(double val) { this.val = val; }
	public double getVal() { return val; }
	@Override
	public String toString() { return "<Digit>" + val; }
	@Override
	public boolean equals(Object obj) {
		// Only judge by class, not by val.
		return (obj instanceof Digit) ? true : false;
	}
}
