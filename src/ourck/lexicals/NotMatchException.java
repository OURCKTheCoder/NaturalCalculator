package ourck.lexicals;
/*-------------------------------------
 *	OURCK - 不匹配某个产生式时抛出的“不匹配”异常
 *	2018年5月6日 下午11:37:59
 *-------------------------------------

/* 
 * 当输入不匹配某个产生式时，就会抛出该异常。
 * 调用者捕获后就能做出反应（比如，换一个产生式）。
 */
public class NotMatchException extends Exception {
	private static final long serialVersionUID = -2870206571200559752L;
	public NotMatchException(String msg) {
		super(msg);
	}
	public NotMatchException() {}
}
