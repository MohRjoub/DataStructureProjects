package application;


public class DoublyLinkedList<T extends Comparable<T>> {
	private DNode<T> head;

	public DoublyLinkedList() {
		this.head = new DNode<>(null);

	}

	public void insert(T data) {
		DNode<T> newNode = new DNode<>(data);
		if (head.getNext() == null) {
			head.setNext(newNode);
			newNode.setPrev(head);
		} else if (head.getNext().getData().compareTo(newNode.getData()) >= 0) {
			newNode.setNext(head.getNext());
			newNode.getNext().setPrev(newNode);
			newNode.setPrev(head);
			head.setNext(newNode);
		} else {
			DNode<T> current = head.getNext();
			while (current.getNext() != null && current.getNext().getData().compareTo(newNode.getData()) < 0) {
				current = current.getNext();
			}
			newNode.setNext(current.getNext());
			if (current.getNext() != null) {
				newNode.getNext().setPrev(newNode);
			}
			current.setNext(newNode);
			newNode.setPrev(current);
		}
	}

	public void traverse() {
		DNode<T> current = head.getNext();
		while (current != null) {
			System.out.print(current.getData() + " ");
			current = current.getNext();
		}
	}

	public DNode<T> get(int i) {
		int j=0;
		DNode<T> current = head.getNext();
		while (current != null) {
			if (j==i) {
				return current;
			}
			j++;
			current = current.getNext();
		}
		return null;
	}
	
	public void backTraverse() {
		DNode<T> current = head.getNext();
		if (current != null) {
			while (current.getNext() != null) {
				current = current.getNext();
			}
			while (current.getPrev() != head) {
				System.out.print(current.getData() + " ");
				current = current.getPrev();
			}
			System.out.print(current.getData() + " ");
		}
	}

	public void backTraverseRec() {
		backTraverseRec(head.getNext());

	}

	private void backTraverseRec(DNode<T> curr) {
		if (curr != null) {
			backTraverseRec(curr.getNext());
			System.out.print(curr.getData() + " ");
		}
	}

	public void removeDublecateItrative() {
		DNode<T> current = head.getNext();
		if (current==null) {
			return;
		}
		while (current.getNext() != null && current.getData().compareTo(current.getNext().getData()) <= 0) {
			if (current.getData().compareTo(current.getNext().getData()) == 0) {
				delete(current.getNext().getData());
			}
			current = current.getNext();
		}
	}
	
	public void removeDuplecateRecursion() {
		removeDuplecateRec(head.getNext());
	}
	
	private void removeDuplecateRec(DNode<T> current) {
		if ( current==null || current.getNext()==null) {
			return;
		}
			if (current.getData().compareTo(current.getNext().getData()) == 0) {
				delete(current.getNext().getData());
		}
			removeDuplecateRec(current.getNext());
	}
	public DNode<T> find(T data) {
		DNode<T> current = head.getNext();
		while (current != null && current.getData().compareTo(data) <= 0) {
			if (current.getData().compareTo(data) == 0) {
				return current;
			}
			current = current.getNext();
		}
		return null;
	}

	public int length() {
		int length = 0;
		DNode<T>current = head;
		while (current.getNext() != null) {
			length++;
			current = current.getNext();
		}
		return length;
	}

	public DNode<T> delete(T data) {
		DNode<T> curr = head.getNext();
		while (curr != null && curr.getData().compareTo(data) <= 0) {
			if (curr.getData().compareTo(data) == 0)
				break;
			curr = curr.getNext();
		}
		if (curr != null && curr.getData().compareTo(data) == 0) { // found
			if (curr.getPrev() == head && curr.getNext() == null) // case 0: delete one item
				head.setNext(null);
			else if (curr.getPrev() == head) { // case 1: delete 1st item
				curr.getNext().setPrev(head);
				head.setNext(curr.getNext());
			} else if (curr.getNext() == null) { // case 3: delete last item
				curr.getPrev().setNext(null);
			} else { // case 2: delete between
				curr.getPrev().setNext(curr.getNext());
				curr.getNext().setPrev(curr.getPrev());
			}
			return curr;
		}
		return null;
	}

	public void reverse() {
		DNode<T> temp = null;
		DNode<T> current = head;
		while (current != null) {
			temp = current.getPrev();
			current.setPrev(current.getNext());
			current.setNext(temp);
			current = current.getPrev();
		}
		if (temp != null) {
			head = temp.getPrev();
		}
	}

	public String toString() {
		String string = "[";
		DNode<T> current = head.getNext();
		while (current != null) {
			string+= current.getData() + ",";
			current = current.getNext();
			}
		string = string.substring(0,string.length()-1) + "]";
		return string;
	}
	public T search(T data) {
		DNode<T> current = head.getNext();
		for (; current != null && current.getData().compareTo(data) <= 0; current = current.getNext()) {
			if (current.getData().compareTo(data) == 0) {
				return current.getData();
			}
		}
		return null;
	}
}
