package com.example.avl_tree

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import java.util.*

@SuppressLint("ViewConstructor")
class BinaryTreeView(context: Context?, private val textView: TextView) : View(context) {
    private var tree: BinarySearchTree? = null
    private var searchSequence: ArrayList<Int>? = null
    private var searchPosition = 0
    /**
     * inserts the nodes into the tree
     * and generates the order in which the user will have to guess the nodes.
     */
    fun initialize() {
        tree = BinarySearchTree()
        TREE_SIZE =10
        val random = generateRandomSequence()
        for (value in random) {
            tree!!.insert(value)
        }
        tree!!.positionNodes(this.width)
        searchSequence = generateRandomSequence(TREE_SIZE)
        searchPosition = 0
        updateMessage()
        invalidate()
    }
    fun insert(){
        tree!!.insert(TREE_SIZE)
        tree!!.positionNodes(this.width)
        searchSequence!!.add(TREE_SIZE)
        TREE_SIZE++
        invalidate()
    }

    /**
     * Generates a randomly ordered list of the numbers 1 through 20.
     */
    private fun generateRandomSequence(size: Int = TREE_SIZE): ArrayList<Int> {
        val numbers = ArrayList<Int>(size)
        for (i in 0 until size) {
            numbers.add(i + 1)
        }
        numbers.shuffle()
        return numbers
    }

    public override fun onDraw(canvas: Canvas) {
        if (tree != null) {
            tree!!.draw(canvas)
        }
    }

    /**
     * Updates the text field at the top of the UI with the desired node.
     */
    private fun updateMessage() {
        if (searchPosition < searchSequence!!.size) textView.text =context.getString(R.string.LookingFor,searchSequence!![searchPosition])
        else textView.text = context.getString(R.string.done)
    }

    /**
     * Finds the node that was tapped by the user and reveals
     * the correct node if the user chose the wrong node.
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (tree != null && searchPosition < searchSequence!!.size) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val targetValue = searchSequence!![searchPosition]
                        val hitValue = tree!!.click(event.x, event.y, targetValue)
                        if (hitValue != -1) {
                            invalidate()
                            if (hitValue != targetValue) {
                                tree!!.invalidateNode(targetValue)
                            }
                            searchSequence!!.remove(targetValue)
                            searchPosition++
                            updateMessage()
                            return true
                        }
                    }

            }
        }
        return super.onTouchEvent(event)
    }

    companion object {
        private var TREE_SIZE = 10
    }

}