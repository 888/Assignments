import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Alan Chiang
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to AVL tree");
        }
        for (T datum : data) {
            if (datum == null) {
                throw new IllegalArgumentException("Cannot add null data to AVL tree");
            } else {
                add(datum);
            }
        }
    }

    /**
     *  Private method for updating height of a given node
     *  @param node the node whose height is updated
     */
    private void updateHeight(AVLNode<T> node) {
        if (node != null) {
            if (node.getRight() == null && node.getLeft() == null) {
                node.setHeight(0);
            } else if (node.getRight() == null) {
                node.setHeight(node.getLeft().getHeight() + 1);
            } else if (node.getLeft() == null) {
                node.setHeight(node.getRight().getHeight() + 1);
            } else {
                node.setHeight(Math.max(node.getLeft().getHeight(), node.getRight().getHeight()) + 1);
            }
        }
    }

    /**
     *  Private method for updating balance factor of a given node
     *  @param node the node whose balance factor is updated
     */
    private void updateBalanceFactor(AVLNode<T> node) {
        if (node != null) {
            if (node.getRight() == null && node.getLeft() == null) {
                node.setBalanceFactor(0);
            } else if (node.getRight() == null) {
                node.setBalanceFactor(node.getLeft().getHeight() + 1);
            } else if (node.getLeft() == null) {
                node.setBalanceFactor(-1 - node.getRight().getHeight());
            } else {
                node.setBalanceFactor(node.getLeft().getHeight() - node.getRight().getHeight());
            }
        }
    }

    /**
     * Private method for rebalancing AVL tree
     * @param node the node whose abs(balanceFactor) > 1
     */
    private AVLNode<T> rebalance(AVLNode<T> node) {
        AVLNode<T> toReturn;
        boolean rootChange = false;
        if (node.equals(root)) {
            rootChange = true;
        }
        if (node.getBalanceFactor() > 1) { //right rotation
            if (node.getLeft().getBalanceFactor() >= 0) {
                AVLNode<T> temp = node.getLeft().getRight();
                toReturn = node.getLeft();
                node.getLeft().setRight(node);
                node.setLeft(temp);
                updateHeight(toReturn.getLeft());
                updateBalanceFactor(toReturn.getLeft());
                updateHeight(toReturn.getRight());
                updateBalanceFactor(toReturn.getRight());
                updateHeight(toReturn);
                updateBalanceFactor(toReturn);
                if (rootChange) {
                    root = toReturn;
                }
                return toReturn;
            } else if (node.getLeft().getBalanceFactor() < 0) { //leftright rotation
                AVLNode<T> temp1 = node.getLeft().getRight().getLeft();
                AVLNode<T> temp2 = node.getLeft();
                node.setLeft(node.getLeft().getRight());
                node.getLeft().setLeft(temp2);
                node.getLeft().getLeft().setRight(temp1);
                AVLNode<T> temp = node.getLeft().getRight();
                toReturn = node.getLeft();
                node.getLeft().setRight(node);
                node.setLeft(temp);
                updateHeight(toReturn.getLeft());
                updateBalanceFactor(toReturn.getLeft());
                updateHeight(toReturn.getRight());
                updateBalanceFactor(toReturn.getRight());
                updateHeight(toReturn);
                updateBalanceFactor(toReturn);
                if (rootChange) {
                    root = toReturn;
                }
                return toReturn;
            }
        } else if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() <= 0) { //left rotation
                AVLNode<T> temp = node.getRight().getLeft();
                toReturn = node.getRight();
                node.getRight().setLeft(node);
                node.setRight(temp);
                updateHeight(toReturn.getLeft());
                updateBalanceFactor(toReturn.getLeft());
                updateHeight(toReturn.getRight());
                updateBalanceFactor(toReturn.getRight());
                updateHeight(toReturn);
                updateBalanceFactor(toReturn);
                if (rootChange) {
                    root = toReturn;
                }
                return toReturn;
            } else if (node.getRight().getBalanceFactor() > 0) { //rightleft rotation
                AVLNode<T> temp1 = node.getRight().getLeft().getRight();
                AVLNode<T> temp2 = node.getRight();
                node.setRight(node.getRight().getLeft());
                node.getRight().setRight(temp2);
                node.getRight().getRight().setLeft(temp1);
                AVLNode<T> temp = node.getRight().getLeft();
                toReturn = node.getRight();
                node.getRight().setLeft(node);
                node.setRight(temp);
                updateHeight(toReturn.getLeft());
                updateBalanceFactor(toReturn.getLeft());
                updateHeight(toReturn.getRight());
                updateBalanceFactor(toReturn.getRight());
                updateHeight(toReturn);
                updateBalanceFactor(toReturn);
                if (rootChange) {
                    root = toReturn;
                }
                return toReturn;
            }
        }
        throw new IllegalArgumentException("Fell through rebalance"); //for debugging only
    }

    /**
     * Private helper method for void add(T data)
     * @param data the data to be added
     * @param node the node to add it after
     */
    private AVLNode<T> addHelp(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        }
        if (data.compareTo(node.getData()) == 0) {
            return node;
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelp(data, node.getLeft()));
            updateHeight(node);
            updateBalanceFactor(node);
            if (Math.abs(node.getBalanceFactor()) > 1) {
                node = rebalance(node);
            }
            return node;
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelp(data, node.getRight()));
            updateHeight(node);
            updateBalanceFactor(node);
            if (Math.abs(node.getBalanceFactor()) > 1) {
                node = rebalance(node);
            }
            return node;
        }
        throw new IllegalArgumentException("Fell through addHelp");
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to BST");
        } else {
            root = addHelp(data, root);
        }
    }

    /**
     * Private helper method for T remove(T data)
     * @param data the data to be removed
     * @param node the next node up
     */
    private AVLNode<T> removeHelp(T data, AVLNode<T> node, AVLNode<T> dummy) {
        if (node == null) {
            throw new NoSuchElementException("Tree doesn't contain data");
        }
        if (data.equals(node.getData())) {
            AVLNode<T> temp;
            dummy.setData(node.getData());
            size--;
            if (node.getLeft() == null && node.getRight() == null) {
                temp = null;
            } else if (node.getLeft() != null && node.getRight() == null) {
                temp = node.getLeft();
                updateHeight(temp);
                updateBalanceFactor(temp);
                if (Math.abs(temp.getBalanceFactor()) > 1) {
                    temp = rebalance(node);
                }
            } else if (node.getRight() != null && node.getLeft() == null) {
                temp = node.getRight();
                updateHeight(temp);
                updateBalanceFactor(temp);
                if (Math.abs(temp.getBalanceFactor()) > 1) {
                    temp = rebalance(node);
                }
            } else if (node.getLeft() != null && node.getRight() != null) {
                AVLNode<T> actual = new AVLNode<>(null);
                node.setLeft(predecessor(node.getLeft(), actual));
                temp = node;
                temp.setData(actual.getData());
                updateHeight(temp);
                updateBalanceFactor(temp);
                if (Math.abs(temp.getBalanceFactor()) > 1) {
                    temp = rebalance(temp);
                }
            } else {
                throw new NoSuchElementException("Fell through removeHelp");
            }
            return temp;
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelp(data, node.getLeft(), dummy));
            updateHeight(node);
            updateBalanceFactor(node);
            if (Math.abs(node.getBalanceFactor()) > 1) {
                node = rebalance(node);
            }
            return node;
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelp(data, node.getRight(), dummy));
            updateHeight(node);
            updateBalanceFactor(node);
            if (Math.abs(node.getBalanceFactor()) > 1) {
                node = rebalance(node);
            }
            return node;
        }
        throw new NoSuchElementException("Data not found in tree");
    }

    /**
     * Private helper method for T remove(T data)
     * @param node the node to find the predecessor of
     * @return the predecessor
     */
    private AVLNode<T> predecessor(AVLNode<T> node, AVLNode<T> dummy) {
        if (node.getRight() == null) {
            dummy.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(predecessor(node.getRight(), dummy));
            updateHeight(node);
            updateBalanceFactor(node);
            if (Math.abs(node.getBalanceFactor()) > 1) {
                node = rebalance(node);
            }
        }
        return node;
    }

    @Override
    public T remove(T data) {
        AVLNode<T> dummy = new AVLNode<>(null);
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data from BST");
        }
        if (root == null) {
            throw new NoSuchElementException("Tree is empty");
        } else {
            root = removeHelp(data, root, dummy);
            return dummy.getData();
        }
    }

    /**
     * Private helper method for get(T data)
     * @param data the data to be retrieved
     * @param node the parent of the node to retrieve the data from
     * @return the data retrieved
     */
    private T getHelp(T data, AVLNode<T> node) {
        if (node.getLeft() != null) {
            AVLNode<T> leftChild = node.getLeft();
            if (data.equals(leftChild.getData())) {
                return leftChild.getData();
            } else if (data.compareTo(node.getData()) < 0) {
                return getHelp(data, leftChild);
            }
        }
        if (node.getRight() != null) {
            AVLNode<T> rightChild = node.getRight();
            if (data.equals(rightChild.getData())) {
                return rightChild.getData();
            } else if (data.compareTo(node.getData()) > 0) {
                return getHelp(data, rightChild);
            }
        }
        throw new NoSuchElementException("Data not found in tree");
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get null data from tree");
        }
        if (root == null) {
            throw new NoSuchElementException("Data not found in tree");
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
     * @return whether or not the AVL tree contains the data
     */
    private boolean containsHelp(T data, AVLNode<T> node) {
        if (node.getLeft() != null) {
            AVLNode<T> leftChild = node.getLeft();
            if (data.equals(leftChild.getData())) {
                return true;
            } else if (data.compareTo(node.getData()) < 0) {
                return containsHelp(data, leftChild);
            }
        }
        if (node.getRight() != null) {
            AVLNode<T> rightChild = node.getRight();
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
            throw new IllegalArgumentException("Tree does not store null data");
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
    private List<T> preorderHelp(AVLNode<T> node, List<T> output) {
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
    private List<T> postorderHelp(AVLNode<T> node, List<T> output) {
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
    private List<T> inorderHelp(AVLNode<T> node, List<T> output) {
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
        Queue<AVLNode<T>> qq = new LinkedList<>();
        qq.add(root);
        while (done < size) {
            AVLNode<T> temp = qq.remove();
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
        size = 0;
        root = null;
    }

    @Override
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}