package application;

public interface Listable<T extends Comparable<T>>{
	boolean isEmpty();
	void Add(T data);
	boolean delete(T data);
	boolean delete(int index);
    int find(T data);
    T get(int index);
    void travers();
    void clear();
}