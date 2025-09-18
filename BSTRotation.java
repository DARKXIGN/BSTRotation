/**
 * @file BSTRoation.java
 * @author HyuckJoon Kwon (Brian Kwon)
 * @version 1.0
 * @date 2025-09-17
 * @brief This class extends a binary search tree to include a rotation operation.
 */
public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree_Placeholder<T> {

    /**
     * Empty BSTRotation tree.
     */
    public BSTRotation() {
        super();
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a right
     * child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this
     * method will either throw a NullPointerException: when either reference is
     * null, or otherwise will throw an IllegalArgumentException.
     *
     * @param child  is the node being rotated from child to parent position
     * @param parent is the node being rotated from parent to child position
     * @throws NullPointerException     when either passed argument is null
     * @throws IllegalArgumentException when the provided child and parent
     * nodes are not initially (pre-rotation) related that way
     */
    protected void rotate(BinaryNode<T> child, BinaryNode<T> parent)
            throws NullPointerException, IllegalArgumentException {
        // 1. Check for null arguments.
        if (child == null || parent == null) {
            throw new NullPointerException("Child and parent nodes cannot be null.");
        }

        // 2. Check the child is actually a child of the parent.
        if (parent.getLeft() != child && parent.getRight() != child) {
            throw new IllegalArgumentException("The provided child is not a direct child of the parent.");
        }

        // 3. Keep track of the grandparent.
        BinaryNode<T> grandparent = parent.getParent();

        // 4. Perform the rotation based on whether the child is a left or right child.
        if (parent.getLeft() == child) {
            // This is a RIGHT rotation.
            // The child's right subtree becomes the parent's new left subtree.
            parent.setLeft(child.getRight());
            if (child.getRight() != null) {
                child.getRight().setParent(parent);
            }
            // The parent becomes the child's new right child.
            child.setRight(parent);
            parent.setParent(child);
        } else {
            // This is a LEFT rotation.
            // The child's left subtree becomes the parent's new right subtree.
            parent.setRight(child.getLeft());
            if (child.getLeft() != null) {
                child.getLeft().setParent(parent);
            }
            // The parent becomes the child's new left child.
            child.setLeft(parent);
            parent.setParent(child);
        }

        // 5. Update the grandparent's reference to point to the new child.
        child.setParent(grandparent);
        if (grandparent == null) {
            // If there was no grandparent, the parent was the root. The child is now the new root.
            this.root = child;
        } else if (grandparent.getLeft() == parent) {
            // If the parent was a left child, update the grandparent's left reference.
            grandparent.setLeft(child);
        } else {
            // If the parent was a right child, update the grandparent's right reference.
            grandparent.setRight(child);
        }
    }


    // TEST METHODS

    /**
     * Tests both a right and a left rotation where the parent node is the root.
     * It performs a right rotation and then a left rotation to restore the original state.
     * This covers root-level rotations for both directions.
     *
     * @return true: both rotations performed correctly, false: otherwise
     */
    public boolean test1() {
        BSTRotation<Integer> tree = new BSTRotation<>();
        BinaryNode<Integer> originalParent = new BinaryNode<>(40);
        BinaryNode<Integer> originalChild = new BinaryNode<>(20);

        // Parent is root, child is left
        tree.root = originalParent;
        originalParent.setLeft(originalChild);
        originalChild.setParent(originalParent);

        // Perform a right rotation
        tree.rotate(originalChild, originalParent);

        // Check the state after the right rotation
        boolean rightRotationCorrect = tree.root == originalChild &&
                originalChild.getRight() == originalParent &&
                originalParent.getParent() == originalChild;
        if (!rightRotationCorrect) return false;

        // Perform a left rotation to undo the change
        // The former child (20) is now the parent of the former parent (40)
        tree.rotate(originalParent, originalChild);

        // Check that the tree is back to its original state
        return tree.root == originalParent &&
                originalParent.getLeft() == originalChild &&
                originalChild.getParent() == originalParent &&
                originalChild.getRight() == null; // Ensure no lingering links
    }

    /**
     * Right rotation on a non-root node with multiple subtrees.
     * This ensures all pointers are correctly reassigned in a more complex scenario.
     *
     * <pre>
     * Initial Tree:                 Rotated Tree:
     *   50                           50
     *  /                            /
     * 40 (parent)                 20 (child)
     * / \                        /  \
     * 20 45                     10   40 (parent)
     * / \                            / \
     * 10 30                         30  45
     * </pre>
     * @return true: rotation is correct, false: otherwise
     */
    public boolean test2() {
        BSTRotation<Integer> tree = new BSTRotation<>();
        BinaryNode<Integer> gp = new BinaryNode<>(50);
        BinaryNode<Integer> parent = new BinaryNode<>(40);
        BinaryNode<Integer> child = new BinaryNode<>(20);
        BinaryNode<Integer> S = new BinaryNode<>(45);   // Sibling
        BinaryNode<Integer> G1 = new BinaryNode<>(10);  // Grandchild 1
        BinaryNode<Integer> G2 = new BinaryNode<>(30);  // Grandchild 2

        tree.root = gp;
        gp.setLeft(parent); parent.setParent(gp);
        parent.setLeft(child); child.setParent(parent);
        parent.setRight(S); S.setParent(parent);
        child.setLeft(G1); G1.setParent(child);
        child.setRight(G2); G2.setParent(child);

        tree.rotate(child, parent);

        // Verify all links after rotation
        return tree.root == gp &&
                gp.getLeft() == child && child.getParent() == gp &&
                child.getLeft() == G1 && G1.getParent() == child &&
                child.getRight() == parent && parent.getParent() == child &&
                parent.getLeft() == G2 && G2.getParent() == parent &&
                parent.getRight() == S && S.getParent() == parent;
    }

    /**
     * Left rotation on a non-root node with multiple subtrees.
     * This is opposite rotation direction of test2.
     *
     * <pre>
     * Initial Tree:                 Rotated Tree:
     * 50                           50
     * \                             \
     * 60 (parent)                   80 (child)
     * / \                          /  \
     * 55 80 (child)      (parent) 60  90
     * / \                         / \
     * 70 90                      55 70
     * </pre>
     * @return true: rotation is correct, false: otherwise
     */
    public boolean test3() {
        BSTRotation<Integer> tree = new BSTRotation<>();
        BinaryNode<Integer> gp = new BinaryNode<>(50);
        BinaryNode<Integer> parent = new BinaryNode<>(60);
        BinaryNode<Integer> child = new BinaryNode<>(80);
        BinaryNode<Integer> S = new BinaryNode<>(55);   // Sibling
        BinaryNode<Integer> G1 = new BinaryNode<>(70);  // Grandchild 1
        BinaryNode<Integer> G2 = new BinaryNode<>(90);  // Grandchild 2

        tree.root = gp;
        gp.setRight(parent); parent.setParent(gp);
        parent.setLeft(S); S.setParent(parent);
        parent.setRight(child); child.setParent(parent);
        child.setLeft(G1); G1.setParent(child);
        child.setRight(G2); G2.setParent(child);

        tree.rotate(child, parent);

        // Verify after rotation
        return tree.root == gp &&
                gp.getRight() == child && child.getParent() == gp &&
                child.getRight() == G2 && G2.getParent() == child &&
                child.getLeft() == parent && parent.getParent() == child &&
                parent.getRight() == G1 && G1.getParent() == parent &&
                parent.getLeft() == S && S.getParent() == parent;
    }

    /**
     * Main method to run all the tests.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("BSTRotation Tests");
        BSTRotation<Integer> testRunner = new BSTRotation<>();
        System.out.println("Test 1 (Root Left&Right Rotations): " + (testRunner.test1()));
        System.out.println("Test 2 (Right Rotation):    " + (testRunner.test2()));
        System.out.println("Test 3 (Left Rotation):     " + (testRunner.test3()));
    }
}