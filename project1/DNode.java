package application;

public class DNode<T extends Comparable<T>> {
	private T data;
	private DNode<T> next;
	private DNode<T> prev;

	public DNode(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public DNode<T> getNext() {
		return next;
	}

	public DNode<T> getPrev() {
		return prev;
	}

	public void setNext(DNode<T> next) {
		this.next = next;
	}

	public void setPrev(DNode<T> prev) {
		this.prev = prev;
	}

	public String toString() {
		return this.data.toString();
	}
}
