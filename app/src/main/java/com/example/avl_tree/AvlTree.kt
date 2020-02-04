package com.example.avl_tree

import android.util.Log
import kotlin.math.max


abstract class Tree {
    var root: Node? = null
        private set

    fun insert(value: Int) {
        root = insertRecursively(root, value)

    }


    fun search(value: Int): Node {
        return searchNode(value)!!
    }



    protected abstract fun insertRecursively(node: Node?, value: Int): Node?

    private fun searchNode(value: Int, target: Node? = root): Node? {
        when {
            target == null -> {
                Log.v("NodeExistence","node does not exist in tree")
                return null
            }
            value > target.value -> {
                return searchNode(value, target.right)
            }
            value < target.value -> {
                return searchNode(value, target.left)
            }
            else -> {
                return target
            }

        }
    }


    protected fun findLargestNode(target: Node? = root): Node? {
        if (target != null) {
            return when (target.right) {
                null -> target
                else -> findLargestNode(target.right)
            }
        } else {
            print("no largest value")
        }
        return null

    }


}

/**
 * The AVL tree and other self-balancing search trees like Red Black are useful to get all basic operations done in O(log n) time.
 * The AVL trees are more balanced compared to Red-Black Trees, but they may cause more rotations during insertion and deletion.
 * So if your application involves many frequent insertions and deletions, then Red Black trees should be preferred.
 * And if the insertions and deletions are less frequent and search is the more frequent operation,
 * then AVL tree should be preferred over Red Black Tree.
 */
class AvlTrees : Tree() {


    override fun insertRecursively(node: Node?, value: Int): Node? {
        when {
            node == null -> return Node(value)
            //insert right
            value > node.value
            -> {
                node.right = insertRecursively(node.right, value)
            }
            //insert left
            value < node.value
            -> {
                node.left = insertRecursively(node.left, value)

            }
            else -> return node
        }

        return insertionBalance(node, value)

    }

    private fun insertionBalance(node: Node, value: Int): Node {
        var balanceFactor = 0
        node.height = getHeight(node) + 1
        balanceFactor = getBalanceFactor(node)
        when {
            //if the unbalanced tree exist and value is smaller than the value in the left node then perform right rotation
            //left left case
            balanceFactor > 1 && value < node.left!!.value -> {
                return performRightRotation(node)
            }
            //if the unbalanced tree exist and value is larger than the value in the right node then perform left rotation
            //right right case
            balanceFactor < -1 && value > node.right!!.value -> {
                return performLeftRotation(node)
            }
            //if the unbalanced tree exist and value is larger than the value in the right of left node then perform left right rotation
            //left right case
            balanceFactor > 1 && value > node.left!!.value -> {
                node.left = performLeftRotation(node.left!!)
                return performRightRotation(node)

            }
            //if the unbalanced tree exist and value is smaller than the value in the left of right node then perform right left rotation
            //right left case
            balanceFactor < -1 && value < node.right!!.value -> {
                node.right = performRightRotation(node.right!!)
                return performLeftRotation(node)
            }
        }
        return node
    }


    private fun performLeftRotation(parent: Node): Node {
        //save the parent right and left of right child
        val newParent = parent.right
        val childLeft = newParent!!.left
        newParent.left = parent
        parent.right = childLeft
        updateHeights(parent, newParent)
        return newParent

    }

    private fun performRightRotation(parent: Node): Node {
        //save the parent right and left of right child
        val newParent = parent.left
        val childRight = newParent!!.right
        newParent.right = parent
        parent.left = childRight
        updateHeights(parent, newParent)
        return newParent

    }

    private fun updateHeights(child: Node, newParent: Node) {
        //update height of child first to reflect on parents
        child.height = getHeight(child) + 1
        newParent.height = getHeight(newParent) + 1
    }

    private fun getHeight(node: Node): Int {
        return max(node.left?.height ?: 0, node.right?.height ?: 0)
    }

    private fun getBalanceFactor(node: Node): Int {
        return (node.left?.height ?: 0) - (node.right?.height ?: 0)
    }

}
