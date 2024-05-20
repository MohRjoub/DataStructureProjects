package application;

public class Queue<T extends Comparable<T>> {
	private LinkedList<T> linkedList = new LinkedList<>();

	public boolean isEmpty() {
		return linkedList.isEmpty();
	}

	public void clear() {
		while (!linkedList.isEmpty()) {
			linkedList.deleteLast();
		}
	}

	public void enqueue(T data) {
		linkedList.insertAtFirst(data);
	}

	public T getFront() {
		return linkedList.getLast();
	}

	public T dequeue() {
		return linkedList.deleteLast();
	}

	public int size() {
		return linkedList.length();
	}

	public Queue<T> merge(Queue<T> queue) {
		while (!queue.isEmpty()) {
			enqueue(queue.dequeue());
		}
		return this;
	}

}
