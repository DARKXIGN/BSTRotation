# BSTRotation
Warning! BinaryNode.java, SortedCollection.java, BinarySearchTree.java flie is not provide in this Repository.
## Binary Search Tree Rotation Implementation
This project provides a Java implementation of the rotation operation for a Binary Search Tree (BST). Rotations are fundamental transformations that locally restructure a tree to help maintain balance, forming the basis for more advanced data structures like AVL trees and Red-Black trees.

This implementation is contained within the BSTRotation class, which extends a basic BinarySearchTree_Placeholder class.

## 🚀 Features
Generic Implementation: The tree and rotation logic are implemented using Java generics (<T extends Comparable<T>>) to work with any comparable data type.

Core rotate Method: A single, elegant rotate(child, parent) method handles both left and right rotations automatically.

Robust Error Handling: The method validates its inputs, throwing NullPointerException for null nodes and IllegalArgumentException for nodes that are not in a direct parent-child relationship.

In-Place Modification: The rotation is performed efficiently by rearranging existing node pointers without creating any new nodes.

Self-Contained Testing: Includes three comprehensive instance methods (test1, test2, test3) to verify correctness for various scenarios.

Executable Main Method: A main method is included to run the built-in tests and print the results to the console.

## 📂 File Structure
The project consists of the following files:

BSTRotation.java: The main file containing the rotation logic and test cases.

BinarySearchTree_Placeholder.java: The base class that BSTRotation extends.

BinaryNode.java: Defines the structure of a single node in the tree.

SortedCollection.java: An interface implemented by the base tree class.

## ⚙️ How to Run
You can compile and run the project directly from the command line. The included main method in BSTRotation.java will automatically execute the test suite.

Compile all Java files:

Bash

```plaintext
javac *.java
```
Run the main test runner:

Bash

```plaintext
java BSTRotation
Expected Output:

Running BSTRotation Test
Test 1 (Root Left&Right Rotations): PASSED
Test 2 (Right Rotation):    PASSED
Test 3 (Left Rotation):     PASSED
```

The rotate Method
The core of this project is the rotate method, which intelligently performs the correct rotation.

Java
```plaintext
protected void rotate(BinaryNode<T> child, BinaryNode<T> parent)
```

Right Rotation: If the child is the left child of the parent, a right rotation is performed. The child moves up to take the parent's position, and the parent becomes the child's right node.

Left Rotation: If the child is the right child of the parent, a left rotation is performed. The child moves up, and the parent becomes the child's new left node.

The method correctly handles reassigning all necessary pointers, including the connection to the grandparent and the "middle" subtree that gets moved between the rotating nodes.

## ✅ Testing Strategy
The correctness of the implementation is verified by three targeted test methods inside BSTRotation.java:

test1(): Verifies both a left and right rotation at the root of the tree. It confirms that a rotation and its opposite rotation correctly restore the tree's original structure.

test2(): Checks a complex right rotation on a non-root node. This test ensures that pointers to a grandparent and all surrounding subtrees are handled correctly.

test3(): Checks a complex left rotation on a non-root node, providing comprehensive coverage for all pointer reassignments in the opposite direction.
