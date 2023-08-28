package com.epam.rd.autocode.collection.tree;

import java.util.*;

/**
 * Binary Search Tree.<br>
 * This class uses the natural ordering to compare elements.<br>
 * This implementation does not provide any balancing.
 *
 * @author D. Kolesnikov, Y. Mishcheriakov
 */
public class BinaryTree {

    private static final String INDENT = "-~-";
    private static final String EOL = System.lineSeparator();

    private Node root;
    private int size;

    /**
     * Creates an empty binary tree.
     */
    public BinaryTree() {
        super();
    }

    /**
     * Creates an empty binary tree and adds all distinct elements.
     *
     * @param elements the elements to add.
     * @throws NullPointerException if the parameter is {@code null}
     *                              or contains {@code null} elements.
     */
    public BinaryTree(Integer... elements) {
        Objects.requireNonNull(elements);
        for (Integer element : elements) {
            add(element);
        }
    }


    /**
     * Adds an element to the tree.
     * If there is an element in the tree that,
     * using the compareTo method, is equal to the
     * element being added, then the addition does
     * not occur and the method returns false,
     * otherwise the element is included in the tree
     * and the method returns true.
     *
     * @param element the element to add.
     * @return {@code true} if the element was added (@code false} otherwise.
     * @throws NullPointerException if the parameter is {@code null}.
     */
    public boolean add(Integer element) {
        if (root == null) {
            root = new Node(element);
            size++;
            return true;
        } else {
            return addRecursive(root, element);
        }
    }

    private boolean addRecursive(Node current, Integer element) {
        if (element < current.value) {
            if (current.left == null) {
                current.left = new Node(element);
                size++;
                return true;
            } else {
                return addRecursive(current.left, element);
            }
        } else if (element > current.value) {
            if (current.right == null) {
                current.right = new Node(element);
                size++;
                return true;
            } else {
                return addRecursive(current.right, element);
            }
        }
        return false;
    }

    /**
     * Adds all non-existing in the tree elements to this tree.
     *
     * @param ar the elements to add.
     * @throws NullPointerException if the parameter is {@code null}
     *                              or contains {@code null} elements.
     */
    public void addAll(Integer... ar) {
        for (Integer element : ar) {
            add(element);
        }
    }

    /**
     * @return a string representation of the tree
     * that composed using natural ordering.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        inOrderTraversal(root, result);
        return "[" + result.substring(2) + "]";
    }
    private void inOrderTraversal(Node current, StringBuilder result) {
        if (current != null) {
            inOrderTraversal(current.left, result);
            result.append(", ").append(current.value);
            inOrderTraversal(current.right, result);
        }
    }


    /**
     * Removes the specified element from this tree if
     * it exists in this tree.
     *
     * @param element an element to remove.
     * @return {@code true} if the element was removed, otherwise {@code false}.
     * @throws NullPointerException if the parameter is {@code null}.
     */
    public Optional<Integer> remove(Integer element) {
        Objects.requireNonNull(element);
        List<Node> found = new ArrayList<>();
        found.add(null);
        root = removeRecursive(root, element, found);
        return Optional.ofNullable(found.get(0) != null ? found.get(0).value : null);
    }
    private Node removeRecursive(Node current, Integer element, List<Node> found) {
        if (current == null) {
            return null;
        }

        if (element < current.value) {
            current.left = removeRecursive(current.left, element, found);

        } else if (element > current.value) {
            current.right = removeRecursive(current.right, element, found);

        } else {
            found.set(0,current);
            if (current.left == null) {
                size--;
                return current.right;
            } else if (current.right == null) {
                size--;
                return current.left;
            }
            Node minNode = findMinNode(current.right);
            current.value = minNode.value;
            current.right = removeRecursive(current.right, minNode.value, found);
        }
        return current;
    }

    private Node findMinNode(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Returns the size of the tree.
     *
     * @return the number of elements in the tree.
     */
    public int size() {
        // place your code here
        return size;
    }

    /**
     * The helper method for you.<br>
     * Creates a 'tree' string representation of the tree.<br>
     * If the sequence of elements `[3, 1, 2, 5, 6, 4, 0]`,
     * in the specified order, was added to the tree,
     * then the following representation is expected:
     * <pre>
     *      7
     *   6
     *     5
     * 4
     *     2
     *   1
     *     0
     * </pre>
     * '4' is the root of this tree, '0' is the most left leaf,
     * and '7' is the most right leaf of the tree.
     *
     * @return a 'tree' string representation of the tree.
     */
    String asTreeString() {
        StringBuilder sb = new StringBuilder();
        asTreeString(sb, root, 0);
        return sb.toString();
    }

    private void asTreeString(StringBuilder sb, Node node, int k) {
        if (node == null) {
            return;
        }
        asTreeString(sb, node.right, k + 1);
        sb.append(INDENT.repeat(k));
        sb.append(String.format("%3s", node.value)).append(EOL);
        asTreeString(sb, node.left, k + 1);
    }

    private static final class Node {
        Integer value;
        Node left;
        Node right;

        Node(Integer e) {
            this.value = e;
        }

        public String toString() {
            return "Node[" + value + "]";
        }
    }
}
