package com.blogspot.soyamr.recipes3.presentation.common.utils

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.blogspot.soyamr.domain.common.extentions.orZero
import com.blogspot.soyamr.recipes3.R

class DotPagerIndicatorDecoration : ItemDecoration() {

    private val indicatorHeight = (DP * 16).toInt()

    private val indicatorItemDiameter = DP * 8

    private val indicatorItemPadding = DP * 4

    private val interpolator: Interpolator = AccelerateDecelerateInterpolator()
    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val colorActive = ContextCompat.getColor(parent.context, R.color.black_solid_100)
        val colorInactive = ContextCompat.getColor(parent.context, R.color.black_solid_25)

        val itemCount = parent.adapter?.itemCount.orZero()
        if (itemCount <= 1) return

        // center horizontally, calculate width and subtract half from center
        val totalLength = indicatorItemDiameter * itemCount
        val paddingBetweenItems = 0.coerceAtLeast(itemCount - 1) * indicatorItemPadding
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f

        // center vertically in the allotted space
        val indicatorPosY = parent.height - indicatorHeight / 2f
        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount, colorInactive)


        // find active page (which should be highlighted)
        val layoutManager = parent.layoutManager as? LinearLayoutManager
        val activePosition = layoutManager?.findFirstVisibleItemPosition() ?: return
        if (activePosition == RecyclerView.NO_POSITION) return

        // find offset of active page (if the user is scrolling)
        val activeChild = layoutManager.findViewByPosition(activePosition)
        val left = activeChild!!.left
        val width = activeChild.width

        // on swipe the active item will be positioned from [-width, 0]
        // interpolate offset for smooth animation
        val progress = interpolator.getInterpolation(left * -1 / width.toFloat())
        drawHighlights(
            canvas = c,
            indicatorStartX = indicatorStartX,
            indicatorPosY = indicatorPosY,
            highlightedPosition = activePosition,
            progress = progress,
            colorActive = colorActive
        )
    }

    private fun drawInactiveIndicators(
        canvas: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int,
        colorInactive: Int
    ) {
        paint.color = colorInactive

        // width of item indicator including padding
        val itemWidth = indicatorItemDiameter + indicatorItemPadding
        var start = indicatorStartX
        for (i in 0 until itemCount) {
            // draw the inactive circle for every item
            canvas.drawCircle(start, indicatorPosY, indicatorItemDiameter / 2, paint)
            start += itemWidth
        }
    }

    private fun drawHighlights(
        canvas: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        highlightedPosition: Int,
        progress: Float,
        colorActive: Int
    ) {
        paint.color = colorActive

        // width of item indicator including padding
        val itemWidth = indicatorItemDiameter + indicatorItemPadding

        // keep highlight current item if progress less than 0.5
        val newPositionToHighlight = highlightedPosition + if (progress > 0.5f) 1 else 0
        val highlightStart = indicatorStartX + itemWidth * newPositionToHighlight
        canvas.drawCircle(
            /* cx = */ highlightStart,
            /* cy = */ indicatorPosY,
            /* radius = */ indicatorItemDiameter / 2,
            /* paint = */ paint
        )
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = indicatorHeight
    }

    companion object {
        private val DP = Resources.getSystem().displayMetrics.density
    }
}