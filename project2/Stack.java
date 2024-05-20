package application;

public class Stack<T extends Comparable<T>> {
	private Queue<T> queue = new Queue<>();
	
	public Stack<T> push(T data) {
		Queue<T> tempQueue = new Queue<>();
		tempQueue.enqueue(data);
		while (!queue.isEmpty()) {
			tempQueue.enqueue(queue.dequeue());
		}
		queue = tempQueue;
		return this;
	}
	
	public T pop() {
		return queue.dequeue();
	}
	public T peek() {
		return queue.getFront();
	}
	public void clear() {
		queue.clear();
	}
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	public Stack<T> merge(Stack<T>stack) {
		Stack<T>tempStack = new Stack<>();
		while (!stack.isEmpty()) {
			tempStack.push(stack.pop());
		}
		while (!tempStack.isEmpty()) {
			push(tempStack.pop());
		}
		return this;
	}
}
