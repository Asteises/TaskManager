package manager;

public class Node<E> {
    private E data;
    private Node<E> next;
    private Node<E> prev;

    public Node(E data, Node<E> next, Node<E> prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public E getData() {
        return data;
    }

    public Node<E> getNext() {
        return next;
    }

    public Node<E> getPrev() {
        return prev;
    }

    public void setData(E data) {
        this.data = data;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }
}
