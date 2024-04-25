package application;



public class LinkedList<T extends Comparable<T>> {
	private Node<T> head;

	public LinkedList() {
		head = new Node<>(null);
	}
	
	public void insert(T element) {
		Node<T> data = new Node<>(element);
		Node<T> current;
		Node<T> prev = head;
		for (current = head.getNext(); current != null
				&& current.getData().compareTo(data.getData()) < 0; prev = current, current = current.getNext());
		if (prev == head) {
			data.setNext(head.getNext());
			head.setNext(data);
		} else if (current == head.getNext()) {
			prev.setNext(data);
			data.setNext(head.getNext());
		} else {
			prev.setNext(data);
			data.setNext(current);
		}

	}

	public void traverse() {
		Node<T> current = head;
		System.out.println("Head -->");
		while (current.getNext() != null) {
			System.out.println(current.getNext().getData() + " -->");
			current = current.getNext();
		}
		System.out.println("Null");
	}

	public Node<T> get(int i) {
		int j = 0;
		Node<T> current = head.getNext();
		while (current != null) {
			if (j == i) {
				return current;
			}
			j++;
			current = current.getNext();
		}
		return null;
	}

	@Override
	public String toString() {
		String string = "[";
		Node<T> current = head.getNext();
		while (current != null) {
			string += current.getData() + ",";
			current = current.getNext();
		}
		string = string.substring(0, string.length() - 1) + "]";
		return string;
	}

	public T search(T data) {
		Node<T> current = head.getNext();
		for (; current != null && current.getData().compareTo(data) <= 0; current = current.getNext()) {
			if (current.getData().compareTo(data) == 0) {
				return current.getData();
			}
		}
		return null;
	}

	public Node<T> delete(T data) {
		Node<T> current = head.getNext();
		Node<T> prev = head;
		for (; current != null && current.getData().compareTo(data) != 0; prev = current, current = current.getNext());
		if (current == null) {
			prev.setNext(null);
			return current;
		} else if (prev == head) {
			head.setNext(current.getNext());
			return current;
		} else {
			prev.setNext(current.getNext());
			return current;
		}
	}

	public int length() {
		int length = 0;
		Node<T>current = head;
		while (current.getNext() != null) {
			length++;
			current = current.getNext();
		}
		return length;
	}

	public Node<T> getHead() {
		return head;
	}
}
