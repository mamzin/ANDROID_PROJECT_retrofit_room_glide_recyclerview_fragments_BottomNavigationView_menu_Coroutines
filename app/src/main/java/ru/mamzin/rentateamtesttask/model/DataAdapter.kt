package ru.mamzin.rentateamtesttask.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mamzin.rentateamtesttask.R

class DataAdapter(private val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    var userlist = mutableListOf<User>()

    fun setUserList(list: List<User>) {
        this.userlist = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = userlist[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(data)
        }
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        var firstname: TextView = itemLayoutView.findViewById(R.id.tvfirst_name)
        var lastname: TextView = itemLayoutView.findViewById(R.id.tvlast_name)

        fun bind(data: User) {
            firstname.text = data.first_name
            lastname.text = data.last_name
        }

    }

    interface CellClickListener {
        fun onCellClickListener(data: User)
    }

}