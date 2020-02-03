package com.example.poc.socialView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat

/**
 * author : magic
 * date   : 19/05/2019
 * mail   : 562224864cross@gmail.com
 */
class OverScrollBounceBehavior : CoordinatorLayout.Behavior<View> {

    private var mNormalHeight = 0
    private var mMaxHeight = 0
    private var mFactor = 1.8f
    private var mOverScrollY: Int = 0
    private var mTargetView: View? = null
    private var mListener: OnScrollChangeListener? = null

    constructor() {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
                                     child: View,
                                     directTargetChild: View,
                                     target: View,
                                     nestedScrollAxes: Int, type: Int): Boolean {
        findTargetView()
        Log.d(TAG, "onStartNestedScroll type = $type")
        //TYPE_TOUCH handle over scroll
        if (checkTouchType(type) && checkTargetView()) {
            mOverScrollY = 0
            mNormalHeight = mTargetView!!.height
            mMaxHeight = (mNormalHeight * mFactor).toInt()
        }
        return true
    }

    fun setFactor(factor: Float) {
        this.mFactor = factor
    }

    fun setOnScrollChangeListener(listener: OnScrollChangeListener) {
        this.mListener = listener
    }

    fun setTargetView(targetView: View) {
        //set a target view from outside, target view should be NestedScrollView child
        this.mTargetView = targetView
    }

    private fun findTargetView() {
        //implement a fixed find target view as you wish
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout,
                                child: View,
                                target: View,
                                dxConsumed: Int, dyConsumed: Int,
                                dxUnconsumed: Int, dyUnconsumed: Int,
                                type: Int) {
        //unconsumed == 0 no overScroll
        //unconsumed > 0 overScroll up
        if (dyUnconsumed >= 0) {
            return
        }
        Log.d(TAG, "onNestedScroll : dyUnconsumed = $dyUnconsumed")
        mOverScrollY -= dyUnconsumed
        Log.d(TAG, "onNestedScroll : mOverScrollY = " + mOverScrollY + "type = " + type)
        //TYPE_TOUCH handle over scroll
        if (checkTouchType(type) && checkTargetView()) {
            if (mOverScrollY > 0 && mTargetView!!.layoutParams.height + Math.abs(mOverScrollY) <= mMaxHeight) {
                mTargetView!!.layoutParams.height = mNormalHeight + Math.abs(mOverScrollY)
                mTargetView!!.requestLayout()
                if (mListener != null) {
                    mListener!!.onScrollChanged(calculateRate(mTargetView, mMaxHeight, mNormalHeight))
                }
            }
        }
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout,
                                    child: View,
                                    target: View,
                                    type: Int) {
        Log.d(TAG, "onStopNestedScrolltype = $type")
        //TYPE_TOUCH handle over scroll
        if (checkTouchType(type)
                && checkTargetView()
                && mTargetView!!.height > mNormalHeight) {
            val animation = ResetAnimation(mTargetView!!, mNormalHeight, mListener)
            animation.duration = 300
            mTargetView!!.startAnimation(animation)
        }
    }

    private fun checkTouchType(type: Int): Boolean {
        return type == ViewCompat.TYPE_TOUCH
    }

    private fun checkTargetView(): Boolean {
        return mTargetView != null
    }

    class ResetAnimation internal constructor(internal var view: View, internal var targetHeight: Int, internal var listener: OnScrollChangeListener?) : Animation() {
        internal var originalHeight: Int = 0
        internal var extraHeight: Int = 0

        init {
            this.originalHeight = view.height
            this.extraHeight = this.targetHeight - originalHeight
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            val newHeight = (targetHeight - extraHeight * (1 - interpolatedTime)).toInt()
            view.layoutParams.height = newHeight
            view.requestLayout()
            if (listener != null) {
                listener!!.onScrollChanged(calculateRate(view, originalHeight, targetHeight))
            }
        }
    }

    interface OnScrollChangeListener {
        fun onScrollChanged(rate: Float)
    }

    companion object {

        private val TAG = "Behavior"

        private fun calculateRate(targetView: View?, maxHeight: Int, targetHeight: Int): Float {
            var rate = 0f
            if (targetView != null) {
                rate = (maxHeight - targetView.layoutParams.height.toFloat()) / (maxHeight - targetHeight)
            }
            return rate
        }
    }
}
