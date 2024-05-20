package application;

public class CustomList <T extends Comparable<T>> implements Listable<T> {
	T[] list;
	int count;
	public CustomList(int size) {
		list =  (T[])new Comparable[size];
	}

	@Override
	public boolean isEmpty() {
		if(count == 0)
			return true;
		return false;
	}

	@Override
	public void Add(T data) {
		if(count>=list.length)
			resize();
			list[count++]=data;
	}

	@Override
	public boolean delete(T data) {
		int index = find(data);
		if (index != -1) {
			for (int i = index + 1; i < count; i++) {
				list[i - 1] = list[i];
			}
			count--;
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(int index) {
		if (index != -1) {
			for (int i = index + 1; i < count; i++) {
				list[i - 1] = list[i];
			}
			count--;
			return true;
		}
		return false;

	}

	@Override
	public int find(T data) {
		for (int i = 0; i < count; i++) {
			if(list[i].compareTo(data)==0)
				return i;
		}
		return -1;
	}

	@Override
	public T get(int index) {
		if (index<count) {
			return list[index];
		}
		return null;
	}

	@Override
	public void travers() {
		for (int i = 0; i < count; i++) {
			System.out.println(list[i]);
		}

	}

	@Override
	public void clear() {
		count=0;
	}
	
	private void resize() {
		T[] newList = (T[])new Comparable[list.length*2];
		System.arraycopy(list, 0, newList, 0, list.length);
		list=newList;
	}
}