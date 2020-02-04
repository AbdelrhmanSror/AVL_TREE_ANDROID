package com.example.avl_tree

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.abs

class Node(var value: Int, var left: Node? = null, var right: Node? = null, var height: Int = 0){
    private var showValue = false
    private var x = 0
    private var y = 0
    private var color = Color.rgb(150, 150, 250)

    companion object {
        private const val SIZE = 60
        private const val MARGIN = 30
    }
    /**
     * Causes a node to compute its position on the canvas given its parent's position.
     */
    fun positionSelf(x0: Int, x1: Int, y: Int) {
        this.y = y
        x = (x0 + x1) / 2
        if (left != null) {
            left!!.positionSelf(x0, if (right == null) x1 - 2 * MARGIN else x, y + SIZE + MARGIN)
        }
        if (right != null) {
            right!!.positionSelf(if (left == null) x0 + 2 * MARGIN else x, x1, y + SIZE + MARGIN)
        }
    }

    /**
     *  Draws the node and the line to its children.
     */

    fun draw(c: Canvas) {
        val linePaint = Paint()
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = 3f
        linePaint.color = Color.GRAY
        if (left != null) c.drawLine(x.toFloat(), y + SIZE / 2.toFloat(), left!!.x.toFloat(), left!!.y + SIZE / 2.toFloat(), linePaint)
        if (right != null) c.drawLine(x.toFloat(), y + SIZE / 2.toFloat(), right!!.x.toFloat(), right!!.y + SIZE / 2.toFloat(), linePaint)
        val fillPaint = Paint()
        fillPaint.style = Paint.Style.FILL
        fillPaint.color = color
        c.drawRect(x - SIZE / 2.toFloat(), y.toFloat(), x + SIZE / 2.toFloat(), y + SIZE.toFloat(), fillPaint)
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = SIZE * 2 / 3.toFloat()
        paint.textAlign = Paint.Align.CENTER
        c.drawText(if (showValue) value.toString() else "?", x.toFloat(), y + SIZE * 3 / 4.toFloat(), paint)
        if (height > 0) {
            val heightPaint = Paint()
            heightPaint.color = Color.MAGENTA
            heightPaint.textSize = SIZE * 2 / 3.toFloat()
            heightPaint.textAlign = Paint.Align.LEFT
            c.drawText(height.toString(), x + SIZE / 2 + 10.toFloat(), y + SIZE * 3 / 4.toFloat(), heightPaint)
        }
        if (left != null) left!!.draw(c)
        if (right != null) right!!.draw(c)
    }

    fun click(clickX: Float, clickY: Float, target: Int): Int {
        var hit = -1
        if (abs(x - clickX) <= SIZE / 2 && y <= clickY && clickY <= y + SIZE) {
            if (!showValue) {
                color = if (target != value) {
                    Color.RED
                } else {
                    Color.GREEN
                }
            }
            showValue = true
            hit = value
        }
        if (left != null && hit == -1) hit = left!!.click(clickX, clickY, target)
        if (right != null && hit == -1) hit = right!!.click(clickX, clickY, target)
        return hit
    }

    /**
     * Causes the node to reveal its value and draw itself in cyan.
     */
    fun invalidate() {
        color = Color.CYAN
        showValue = true
    }

}
