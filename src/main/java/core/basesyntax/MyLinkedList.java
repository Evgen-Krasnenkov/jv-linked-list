package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> previous;

        private Node(T element) {
            this.item = element;
        }

        private Node(Node<T> previous, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.previous = previous;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head = tail = newNode;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == 0 && index == size) {
            add(value);
            return;
        }
        if (size != 0 && index == size) {
            add(value);
            return;
        }
        if (index > 0 && index < size) {
            Node<T> currentNode = findNode(index);
            Node<T> addNode = new Node<>(null, value, currentNode);
            addNode.previous = currentNode.previous;
            currentNode.previous.next = addNode;
            currentNode.previous = addNode;
            size++;
            return;
        }
        if (index == 0 && index < size) {
            Node<T> currentNode = findNode(index);
            Node<T> addNode = new Node<>(null, value, currentNode);
            addNode.previous = currentNode.previous;
            currentNode.previous = addNode;
            head = addNode;
            size++;
            return;
        }

        if (index <= 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong Index.");
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (size == 0) {
                    Node<T> newNode = new Node<>(list.get(i));
                    head = tail = newNode;
                } else {
                    Node<T> newNode = new Node<>(tail, list.get(i), null);
                    tail.next = newNode;
                    tail = newNode;
                }
                size++;
            }
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findNode(index);
        Node<T> setNode = new Node<>(currentNode.previous, value, currentNode.next);
        if (index == 0) {
            currentNode.next.previous = setNode;
            head = setNode;
            unlinkNode(currentNode);
            return currentNode.item;
        }
        if (index == size - 1) {
            currentNode.previous.next = setNode;
            tail = setNode;
            unlinkNode(currentNode);
            return currentNode.item;
        }
        currentNode.previous.next = setNode;
        currentNode.next.previous = setNode;
        unlinkNode(currentNode);
        return currentNode.item;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = findNode(index);
        if (index == 0) {
            if (size == 1) {
                size = 0;
                head = tail = null;
                return currentNode.item;
            }
            currentNode.next.previous = null;
            head = currentNode.next;
            unlinkNode(currentNode);
            size--;
            return currentNode.item;
        }
        if (index == size - 1) {
            currentNode.previous.next = null;
            tail = currentNode.previous;
            unlinkNode(currentNode);
            size--;
            return currentNode.item;
        }
        currentNode.previous.next = currentNode.next;
        currentNode.next.previous = currentNode.previous;
        unlinkNode(currentNode);
        size--;
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> searchNode = head;
        while (searchNode != null) {
            if (searchNode.item == object
                    || (searchNode.item != null && searchNode.item.equals(object))) {
                if (searchNode == head || (searchNode != null && searchNode.equals(head))) {
                    if (searchNode.next == null) {
                        head = tail = null;
                        size--;
                        return true;
                    }
                    searchNode.next.previous = null;
                    head = searchNode.next;
                    unlinkNode(searchNode);
                    size--;
                    return true;
                }
                if (searchNode == tail || (searchNode != null && searchNode.equals(tail))) {
                    searchNode.previous.next = null;
                    tail = searchNode.previous;
                    unlinkNode(searchNode);
                    size--;
                    return true;
                }
                searchNode.previous.next = searchNode.next;
                searchNode.next.previous = searchNode.previous;
                unlinkNode(searchNode);
                size--;
                return true;
            }
            searchNode = searchNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void unlinkNode(Node node) {
        node.next = null;
        node.previous = null;
    }

    private Node<T> findNode(int index) {
        if ((index < size) && index >= 0 && (index <= size / 2) && head != null) {
            Node<T> currentNode = head;
            int i = 0;
            while (currentNode != null) {
                if (index == i) {
                    return currentNode;
                }
                currentNode = currentNode.next;
                i++;
            }
        }
        if ((index < size) && index >= 0 && (index > size / 2) && head != null) {
            Node<T> currentNode = tail;
            int i = size - 1;
            while (currentNode != null) {
                if (index == i) {
                    return currentNode;
                }
                currentNode = currentNode.previous;
                i--;
            }
        }
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is greater than size of our linked list");
        }
        return null;
    }
}
