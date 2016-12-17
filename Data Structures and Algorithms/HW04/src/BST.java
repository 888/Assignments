import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a binary search tree.
 *
 * @author Alan Chiang
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST.
     * YOU DO NOT NEED TO IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot add null data to BST");
        }
        for (T datum : data) {
            if (datum == null) {
                throw new IllegalArgumentException(
                        "Cannot add null data to BST");
            }
            add(datum);
        }
    }

    /**
     * Private helper method for void add(T data)
     * @param data the data to be added
     * @param node the node to add it after
     */
    private void addHelp(T data, BSTNode<T> node) {
        if (data.equals(node.getData())) {
            return;
        } else if (node.getLeft() != null
                && data.equals(node.getLeft().getData())) {
            return;
        } else if (node.getRight() != null
                && data.equals(node.getRight().getData())) {
            return;
        }
        if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTNode<>(data));
                size++;
            } else {
                addHelp(data, node.getLeft());
            }
        } else if (data.compareTo(node.getData()) > 0) {
            if (node.getRight() == null) {
                node.setRight(new BSTNode<>(data));
                size++;
            } else {
                addHelp(data, node.getRight());
            }
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to BST");
        }
        if (root == null) {
            root = new BSTNode<>(data);
            size++;
        } else if (data.equals(root.getData())) {
            return;
        } else {
            addHelp(data, root);
        }
    }

    /**
     * Private helper method for T remove(T data)
     * @param data the data to be removed
     * @param node the parent of the to-be-removed node
     * @return the removed data
     */
    private T removeHelp(T data, BSTNode<T> node) {
        if (node.getLeft() != null) {
            BSTNode<T> leftChild = node.getLeft();
            if (data.equals(leftChild.getData())) {
                return removeCases(node, leftChild);
            } else if (data.compareTo(node.getData()) < 0) {
                return removeHelp(data, leftChild);
            }
        }
        if (node.getRight() != null) {
            BSTNode<T> rightChild = node.getRight();
            if (data.equals(rightChild.getData())) {
                return removeCases(node, rightChild);
            } else if (data.compareTo(node.getData()) > 0) {
                return removeHelp(data, rightChild);
            }
        }
        throw new NoSuchElementException("Data not found in BST");
    }

    /**
     * Private helper method for T remove(T data)
     * @param parent the parent to remove from
     * @param child the node to dereference
     * @return the removed data
     */
    private T removeCases(BSTNode<T> parent, BSTNode<T> child) {
        T output = child.getData();
        if (child.getLeft() == null && child.getRight() == null) {
            if (child == root) {
                root = null;
            } else if (parent.getLeft() != null
                    && parent.getLeft().equals(child)) {
                parent.setLeft(null);
            } else if (parent.getRight() != null
                    && parent.getRight().equals(child)) {
                parent.setRight(null);
            }
        } else if (child.getLeft() != null && child.getRight() == null) {
            if (child == root) {
                if (root.getLeft() != null) {
                    root = root.getLeft();
                } else if (root.getRight() != null) {
                    root = root.getRight();
                }
            } else if (parent.getLeft() != null
                    && parent.getLeft().equals(child)) {
                parent.setLeft(child.getLeft());
            } else if (parent.getRight() != null
                    && parent.getRight().equals(child)) {
                parent.setRight(child.getLeft());
            }
        } else if (child.getRight() != null && child.getLeft() == null) {
            if (child == root) {
                if (root.getLeft() != null) {
                    root = root.getLeft();
                } else if (root.getRight() != null) {
                    root = root.getRight();
                }
            } else if (parent.getLeft() != null
                    && parent.getLeft().equals(child)) {
                parent.setLeft(child.getRight());
            } else if (parent.getRight() != null
                    && parent.getRight().equals(child)) {
                parent.setRight(child.getRight());
            }
        } else if (child.getLeft() != null && child.getRight() != null) {
            child.setData(predecessor(child));
        } else {
            throw new NoSuchElementException("Data not found in BST");
        }
        size--;
        return output;
    }

    /**
     * Private helper method for T remove(T data)
     * @param node the node to find the predecessor of
     * @return the predecessor's data
     */
    private T predecessor(BSTNode<T> node) {
        if (node.getLeft() != null) {
            BSTNode<T> pred = node.getLeft();
            T predData;
            if (pred.getRight() != null) {
                while (pred.getRight().getRight() != null) {
                    pred = pred.getRight();
                }
                predData = pred.getRight().getData();
                if (pred.getRight().getLeft() != null) {
                    pred.setRight(pred.getRight().getLeft());
                } else {
                    pred.setRight(null);
                }
            } else {
                predData = pred.getData();
                if (pred.getLeft() != null) {
                    node.setLeft(pred.getLeft());
                } else {
                    node.setLeft(null);
                }
            }
            return predData;
        } else {
            throw new NoSuchElementException("BST does not contain null data");
        }
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot remove null data from BST");
        }
        if (root == null) {
            throw new NoSuchElementException("BST does not contain null data");
        } else if (data.equals(root.getData())) {
            return removeCases(null, root);
        } else {
            return removeHelp(data, root);
        }
    }

    /**
     * Private helper method for get(T data)
     * @param data the data to be retrieved
     * @param node the parent of the node to retrieve the data from
     * @return the data retrieved
     */
    private T getHelp(T data, BSTNode<T> node) {
        if (node.getLeft() != null) {
            BSTNode<T> leftChild = node.getLeft();
            if (data.equals(leftChild.getData())) {
                return leftChild.getData();
            } else if (data.compareTo(node.getData()) < 0) {
                return getHelp(data, leftChild);
            }
        }
        if (node.getRight() != null) {
            BSTNode<T> rightChild = node.getRight();
            if (data.compareTo(rightChild.getData()) == 0) {
                return rightChild.getData();

            } else if (data.compareTo(node.getData()) > 0) {
                return getHelp(data, rightChild);
            }
        }
        throw new NoSuchElementException("Data not found in BST");
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get null data from BST");
        }
        if (root == null) {
            throw new NoSuchElementException("Data not found in BST");
        } else if (data.compareTo(root.getData()) == 0) {
            return root.getData();
        } else {
            return getHelp(data, root);
        }
    }

    /**
     * Private helper method for boolean contains(T data)
     * @param data the data to search for in the tree.
     * @param node the parent of the checked node
     * @return whether or not the BST contains the data
     */
    private boolean containsHelp(T data, BSTNode<T> node) {
        if (node.getLeft() != null) {
            BSTNode<T> leftChild = node.getLeft();
            if (data.equals(leftChild.getData())) {
                return true;
            } else if (data.compareTo(node.getData()) < 0) {
                return containsHelp(data, leftChild);
            }
        }
        if (node.getRight() != null) {
            BSTNode<T> rightChild = node.getRight();
            if (data.equals(rightChild.getData())) {
                return true;
            } else if (data.compareTo(node.getData()) > 0) {
                return containsHelp(data, rightChild);
            }
        }
        return false;
    }


    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("BST does not store null data");
        } else if (root == null) {
            return false;
        } else if (data.equals(root.getData())) {
            return true;
        } else {
            return containsHelp(data, root);
        }
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Private helper method for List preorder()
     * @param node the current node in this traversal
     * @param output the list of data for this traversal
     * @return the updated list
     */
    private List<T> preorderHelp(BSTNode<T> node, List<T> output) {
        if (node != null) {
            output.add(node.getData());
            preorderHelp(node.getLeft(), output);
            preorderHelp(node.getRight(), output);
        }
        return output;
    }

    @Override
    public List<T> preorder() {
        ArrayList<T> output = new ArrayList<>();
        return preorderHelp(root, output);
    }

    /**
     * Private helper method for List postorder()
     * @param node the current node in this traversal
     * @param output the list of data for this traversal
     * @return the updated list
     */
    private List<T> postorderHelp(BSTNode<T> node, List<T> output) {
        if (node != null) {
            postorderHelp(node.getLeft(), output);
            postorderHelp(node.getRight(), output);
            output.add(node.getData());
        }
        return output;
    }

    @Override
    public List<T> postorder() {
        ArrayList<T> output = new ArrayList<>();
        return postorderHelp(root, output);
    }

    /**
     * Private helper method for List inorder()
     * @param node the current node in this traversal
     * @param output the list of data for this traversal
     * @return the updated list
     */
    private List<T> inorderHelp(BSTNode<T> node, List<T> output) {
        if (node != null) {
            inorderHelp(node.getLeft(), output);
            output.add(node.getData());
            inorderHelp(node.getRight(), output);
        }
        return output;
    }

    @Override
    public List<T> inorder() {
        ArrayList<T> output = new ArrayList<>();
        return inorderHelp(root, output);
    }

    @Override
    public List<T> levelorder() {
        int done = 0;
        ArrayList<T> output = new ArrayList<>();
        Queue<BSTNode<T>> qq = new LinkedList<>();
        qq.add(root);
        while (done < size) {
            BSTNode<T> temp = qq.remove();
            if (temp != null) {
                output.add(temp.getData());
                qq.add(temp.getLeft());
                qq.add(temp.getRight());
                done++;
            }
        }
        return output;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Private helper method for int height()
     * @param node the node to measure height of
     * @return height of the node
     */
    private int heightHelp(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return Math.max(heightHelp(node.getLeft()),
                    heightHelp(node.getRight())) + 1;
        }
    }

    @Override
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return Math.max(heightHelp(root.getLeft()),
                    heightHelp(root.getRight())) + 1;
        }
    }

    @Override
    public BSTNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
