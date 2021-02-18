package com.dtdubiel.android.usermanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dtdubiel.android.networking.model.User
import com.dtdubiel.android.usermanager.R

class UsersListAdapter(private val onUserLongClick: (Int) -> Unit) :
    ListAdapter<User, UsersListAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.user_list_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item = getItem(position), onUserLongClick = onUserLongClick)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: User, onUserLongClick: (Int) -> Unit) {
            itemView.apply {
                setOnLongClickListener {
                    onUserLongClick(item.id.toInt())
                    true
                }
                findViewById<TextView>(R.id.usernameTV).text = item.name
                findViewById<TextView>(R.id.emailTV).text = item.email
                findViewById<TextView>(R.id.creationTimeTV).text = item.created_at
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    }

}
