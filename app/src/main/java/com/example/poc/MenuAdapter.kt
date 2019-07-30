package com.example.poc

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_menu_poc_view.view.*

class MenuAdapter(
    var data : List<MainActivity.MenuItem>,
    val menuClickListener: (MainActivity.MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>()  {

    override fun onBindViewHolder(holder: MenuViewHolder, p1: Int) {
        val item = data[p1]
        holder.tvTitle.text = item.name
        holder.btnStart.setOnClickListener {
            menuClickListener.invoke(item)
        }
    }

    override fun onCreateViewHolder(view: ViewGroup, p1: Int): MenuViewHolder {
        return MenuViewHolder(
            LayoutInflater.from(view.context).inflate(
                R.layout.item_menu_poc_view,
                view,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MenuViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle = view.tv_title
        val btnStart = view.btn_start
    }
}