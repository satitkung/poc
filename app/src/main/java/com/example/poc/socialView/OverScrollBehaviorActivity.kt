package com.example.poc.socialView

import android.os.Bundle

import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.example.poc.R


class OverScrollBehaviorActivity : AppCompatActivity() {

    private var nestedScrollView: NestedScrollView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        supportActionBar!!.setTitle(R.string.title_nested_scroll_view_behavior_demo)
        setContentView(R.layout.activity_nestedscrollview_behavior)

        //CoordinatorLayout direct child
//        nestedScrollView = this.findViewById(R.id.nested_content)
//
//        val targetView = findViewById<View>(R.id.over_scroll_id)
//
//        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
//        descriptionTextView.text = Html.fromHtml(Globals.DESC)
//
//        val layoutParams = nestedScrollView!!.layoutParams
//
//        if (layoutParams is CoordinatorLayout.LayoutParams) {
//            val behavior = layoutParams.behavior
//            if (behavior is OverScrollBounceBehavior) {
//
//                behavior.setTargetView(targetView)
//                behavior.setOnScrollChangeListener(object : OverScrollBounceBehavior.OnScrollChangeListener {
//                    override fun onScrollChanged(rate: Float) {
//                        descriptionTextView.alpha = rate
//                    }
//                })
//            }
//        }
    }
}
