package com.example.avl_tree

import android.graphics.Canvas

/**
 * Wrapper class for the tree.
 * Most of the actual work is handled by the TreeNode class below so a lot of these are wrappers that call the recursive method on TreeNode.
 */
class BinarySearchTree {
    private val avlTree: Tree by lazy { AvlTrees() }
    /**
     * Inserts a value into the tree. Special handler for empty tree case and passes the rest of the work to TreeNode.
     */
    fun insert(value: Int) {
        avlTree.insert(value)
    }

    /**
     * Wrapper that causes the nodes to position themselves on the screen.
     */
    fun positionNodes(width: Int) {
        avlTree.root?.positionSelf(0, width, 0)
    }

    fun draw(c: Canvas) {
        avlTree.root?.draw(c)
    }

    fun click(x: Float, y: Float, target: Int): Int {
        return avlTree.root!!.click(x, y, target)
    }

    /**
     * Helper that finds the node with a given value in the tree.
     */
    private fun search(value: Int): Node? {
        return avlTree.search(value)
    }

    /**
     *Finds the node of the specified value and mark it as invalid in the UI.
     */
    fun invalidateNode(targetValue: Int) {
        val target = search(targetValue)
        target!!.invalidate()
    }
}