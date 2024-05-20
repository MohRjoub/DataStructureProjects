package application;

public class BSTree<T extends Comparable<T>> {
	private TNode<T> root;

	public void insert(T data) {
		if (isEmpty())
			root = new TNode<>(data);
		else
			insert(data, root);
	}

	public void insert(T data, TNode<T> node) {
		if (data.compareTo((T) node.getData()) >= 0) { // insert into right subtree
			if (!node.hasRight())
				node.setRight(new TNode<>(data));
			else
				insert(data, node.getRight());
		} else { // insert into left subtree
			if (!node.hasLeft())
				node.setLeft(new TNode<>(data));
			else
				insert(data, node.getLeft());
		}
	}

	public void traverseInOrder() {
		traverseInOrder(root);
	}

	public TNode<T> find(T data) {
		return find(data, root);
	}

	public TNode<T> find(T data, TNode<T> node) {
		if (node != null) {
			int comp = node.getData().compareTo(data);
			if (comp == 0)
				return node;
			else if (comp > 0 && node.hasLeft())
				return find(data, node.getLeft());
			else
				return find(data, node.getRight());
		}
		return null;
	}

	private void traverseInOrder(TNode<T> node) {
		if (node != null) {
			traverseInOrder(node.getLeft());
			System.out.print(node + " ");
			traverseInOrder(node.getRight());
		}
	}

	public Stack<T> toStack() {
		return toStack(root);
	}

	private Stack<T> toStack(TNode<T> node) {
		Stack<T> stack = new Stack<>();
		if (node != null) {
			stack = stack.merge(toStack(node.getLeft()));
			stack.push(node.getData());
			stack = stack.merge(toStack(node.getRight()));
		}
		return stack;
	}

	public Queue<T> toQueue() {
		return toQueue(root);
	}

	private Queue<T> toQueue(TNode<T> node) {
		Queue<T> queue = new Queue<>();
		if (node != null) {
			queue = queue.merge(toQueue(node.getLeft()));
			queue.enqueue(node.getData());
			queue = queue.merge(toQueue(node.getRight()));
		}
		return queue;
	}

	public Queue<T> queueLevelOrder() {
		Queue<T> queue = new Queue<>();
		int h = height(root);
		for (int i = 1; i <= h; i++) {
			queue = queue.merge(currentLevelToQueue(root, i));
		}
		return queue;
	}

	private Queue<T> currentLevelToQueue(TNode<T> root, int level) {
		Queue<T> queue = new Queue<>();
		if (root == null)
			return queue;
		if (level == 1)
			queue.enqueue(root.getData());
		else if (level > 1) {
			queue = queue.merge(currentLevelToQueue(root.getLeft(), level - 1));
			queue = queue.merge(currentLevelToQueue(root.getRight(), level - 1));
		}
		return queue;
	}
	public Stack<T> stackLevelOrder() {
		Stack<T> stack = new Stack<>();
		int h = height(root);
		for (int i = 1; i <= h; i++) {
			stack = stack.merge(currentLevelToStack(root, i));
		}
		return stack;
	}

	private Stack<T> currentLevelToStack(TNode<T> root, int level) {
		Stack<T> stack = new Stack<>();
		if (root == null)
			return stack;
		if (level == 1)
			stack.push(root.getData());
		else if (level > 1) {
			stack = stack.merge(currentLevelToStack(root.getLeft(), level - 1));
			stack = stack.merge(currentLevelToStack(root.getRight(), level - 1));
		}
		return stack;
	}
	public TNode<T> largest() {
		return largest(root);
	}

	private TNode<T> largest(TNode<T> node) {
		if (node != null) {
			if (!node.hasRight())
				return (node);
			return largest(node.getRight());
		}
		return null;
	}

	public TNode<T> smallest() {
		return smallest(root);
	}

	private TNode<T> smallest(TNode<T> node) {
		if (node != null) {
			if (!node.hasLeft())
				return (node);
			return smallest(node.getLeft());
		}
		return null;
	}

	public int height() {
		return height(root);
	}

	public int height(TNode<T> node) {
		if (node == null)
			return 0;
		if (node.isLeaf())
			return 1;
		int left = 0;
		int right = 0;
		if (node.hasLeft())
			left = height(node.getLeft());
		if (node.hasRight())
			right = height(node.getRight());
		return (left > right) ? (left + 1) : (right + 1);
	}

	public int countLeaves() {
		return countLeaves(root);
	}

	private int countLeaves(TNode<T> node) {
		if (node == null) {
			return 0;
		}
		if (node.isLeaf()) {
			return 1;
		}
		return countLeaves(node.getLeft()) + countLeaves(node.getRight());
	}

	public int countParent() {
		return countParent(root);
	}

	private int countParent(TNode<T> node) {
		if (node == null || (!node.hasLeft() && !node.hasRight())) {
			return 0;
		}
		return 1 + countParent(node.getLeft()) + countParent(node.getRight());

	}

	public int size() {
		return size(root);
	}

	private int size(TNode<T> node) {
		if (node == null) {
			return 0;
		}
		return 1 + size(node.getLeft()) + size(node.getRight());
	}

	public T delete(T value) {
		if (isEmpty())
			return null;
		TNode<T> parent = root;
		TNode<T> current = root;
		boolean isLeftChild = false;

		while (current != null && current.getData().compareTo(value) != 0) {
			parent = current;
			if (value.compareTo(current.getData()) < 0) {
				current = current.getLeft();
				isLeftChild = true;
			} else {
				current = current.getRight();
				isLeftChild = false;
			}
		}

		if (current == null)
			return null;

		if (current.isLeaf()) { // is a leaf
			if (current == root) {
				root = null;
			} else {
				if (isLeftChild)
					parent.setLeft(null);
				else
					parent.setRight(null);
			}
		} else if (current.hasLeft() && !current.hasRight()) { // is a node with one child
			if (current == root) {
				root = current.getLeft();
			} else if (isLeftChild) {
				parent.setLeft(current.getLeft());
			} else {
				parent.setRight(current.getLeft());
			}
		} else if (current.hasRight() && !current.hasLeft()) { // is a node with one child
			if (current == root) {
				root = current.getRight();
			} else if (isLeftChild) {
				parent.setLeft(current.getRight());
			} else {
				parent.setRight(current.getRight());
			}
		} else { // is a node with two children
			TNode<T> successor = getSuccessor(current);
			if (current == root) {
				root = successor;
			} else if (isLeftChild) {
				parent.setLeft(successor);
			} else {
				parent.setRight(successor);
			}

			successor.setLeft(current.getLeft());
		}

		return current.getData();
	}

	private TNode<T> getSuccessor(TNode<T> node) {
		TNode<T> parentOfSuccessor = node;
		TNode<T> successor = node;
		TNode<T> current = node.getRight();
		while (current != null) {
			parentOfSuccessor = successor;
			successor = current;
			current = current.getLeft();
		}

		if (successor != node.getRight()) { // fix successor connections
			parentOfSuccessor.setLeft(successor.getRight());
			successor.setRight(node.getRight());
		}

		return successor;
	}

	@Override
	public String toString() {
		return toString(root);
	}

	private String toString(TNode<T> node) {
		String string = "";
		if (node != null) {
			string += toString(node.getLeft());
			string += node.getData() + " ";
			string += toString(node.getRight());
		}
		return string;
	}

	public boolean isEmpty() {
		return root == null;
	}

}