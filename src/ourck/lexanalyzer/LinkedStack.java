package ourck.lexanalyzer;
/*-------------------------------------
 *	OURCK - 自制栈
 *	2018年5月6日 下午11:34:35
 *-------------------------------------
 */
import java.util.*;

public class LinkedStack<T> {
	private LinkedList<T> data;
	private LinkedStack(Collection<T> collection) {
		this();
		data.addAll(collection);
	}
	private LinkedStack() {
		data = new LinkedList<>();
	}
	public LinkedStack(LinkedStack<T> old) {
		// Deep copy.
		this();
		for(T element : old.data) {
			this.data.addLast(element);
		}
	}
	
	public static <E> LinkedStack<E> generate() {
		return new LinkedStack<>();
	}
	// Auto type-captured factory method.
	public static <E> LinkedStack<E> generate(Collection<E> collection) {
		return new LinkedStack<>(collection);
	}
	
	public void push(T obj) { data.addFirst(obj); }
	public T pop() { return data.poll(); }
	public T getTop() { return data.peek(); }
	public boolean isEmpty() { return data.isEmpty(); }
	public void clear() { data.clear(); }
	
	public String toString() { return data.toString(); }
	
	public static void main(String[] args) {
		LinkedStack<String> s1 = LinkedStack.generate();
		s1.push("Test1"); s1.push("Test2"); s1.push("Test3");
		LinkedStack<String> s2 = new LinkedStack<>(s1);
		System.out.println(s1);
		System.out.println(s2);
		
		s1.pop();
		System.out.println(s1);
		System.out.println(s2);
	}
}
