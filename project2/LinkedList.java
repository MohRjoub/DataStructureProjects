package application;

import java.util.Iterator;

public class LinkedList<T extends Comparable<T>>  implements Iterable<T>{
	private Node<T> head;
	private Node<T> tail;
	
	public void insert(T element) {
		Node<T> data = new Node<>(element);
		if (head == null) {
			head = data;
			tail = data;
		} else {
			Node<T> current;
			Node<T> prev = null;
			for (current = head; current != null
					&& current.getData().compareTo(data.getData()) < 0; prev = current, current = current.getNext());
			if (prev == null) {
				data.setNext(head);
				head = data;
			} else if (current == null) {
				tail.setNext(data);
				tail = data;
			} else {
				prev.setNext(data);
				data.setNext(current);
			}
		}
	}

	public void insertAtFirst(T element) {
		Node<T> data = new Node<>(element);
		if (head == null) {
			head = data;
			tail = data;
		} else {
			data.setNext(head);
			head = data;
		}
	}
	
	public void traverse() {
		Node<T> current = head;
		System.out.println("Head -->");
		while (current != null) {
			System.out.println(current.getData() + " -->");
			current = current.getNext();
		}
		System.out.println("Null");
	}

	@Override
	public String toString() {
		String string = "[";
		Node<T> current = head;
		while (current != null) {
			string += current.getData() + ",";
			current = current.getNext();
		}
		string = string.substring(0, string.length() - 1) + "]";
		return string;
	}

	public T search(T data) {
		Node<T> curr = head;
		while (curr != null) {
			if (curr.getData().equals(data))
				return curr.getData();
			curr = curr.getNext();
		}
		return null;
	}

	public T deleteLast() {
	        if (head == null || head.getNext() == null) {
	        	T data = null;
	        	if (head != null) {
	        		data = head.getData();
				}
	            head = null;
	            tail = null;
	            return data;
	        }
	        Node<T> current = head;
			for (; current.getNext() != tail ; current = current.getNext());
        	T data = tail.getData();
	        current.setNext(null);
	        tail = current; 
	        return data;
	}
	
	public void delete(T data) {
		if (head != null) {
			if (head.getData() == data && tail.getData() == data) {
				head = null;
				tail = null;
			} else {
				Node<T> current = head;
				Node<T> prev = null;
				for (; current != null && current.getData().compareTo(data) != 0; prev = current, current = current.getNext());
				if (prev == null) {
					head = current.getNext();
				} else if (current == null) {
					prev.setNext(null);
					tail = prev;
				} else {
					prev.setNext(current.getNext());
				}
			}
		}
	}

	public int length() {
		int length = 0;
		Node<T>current = head;
		while (current != null) {
			length++;
			current = current.getNext();
		}
		return length;
	}

	public void update(T data) {
		if (data != null) {
			if (head != null) {
				if (head.getData() == data && tail.getData() == data) {
					head = null;
					tail = null;
				} else {
					Node<T> current = head;
					Node<T> prev = null;
					for (; current != null
							&& current.getData().compareTo(data) != 0; prev = current, current = current.getNext());
					if (prev == null) {
						head = current.getNext();
					} else if (current == null) {
						prev.setNext(null);
						tail = prev;
					} else {
						prev.setNext(current.getNext());
					}
				}
			}
			Node<T> newData = new Node<>(data);
			if (head == null) {
				head = newData;
				tail = newData;
			} else {
				Node<T> current;
				Node<T> prev = null;
				for (current = head; current != null
						&& current.getData().compareTo(newData.getData()) < 0; prev = current, current = current.getNext());
				if (prev == null) {
					newData.setNext(head);
					head = newData;
				} else if (current == null) {
					tail.setNext(newData);
					tail = newData;
				} else {
					prev.setNext(newData);
					newData.setNext(current);
				}
			}
		}
	}
	
	public boolean isEmpty() {
		return head == null && tail == null;
	}
	
	public T getLast() {
		T data = null;
    	if (tail != null) {
    		data = tail.getData();
		}
		return data;
	}
	
	public T getFirst() {
		T data = null;
    	if (head != null) {
    		data = head.getData();
		}
		return data;	}

	@Override
	public Iterator<T> iterator() {
		return new ListIterator();
	}

	private class ListIterator implements Iterator<T> {
		private Node<T> curr = head;

		public boolean hasNext() {
			return curr != null;
		}

		public void remove() {
		}

		public T next() {
			T t = curr.getData();
			curr = curr.getNext();
			return t;
		}
	}

}
