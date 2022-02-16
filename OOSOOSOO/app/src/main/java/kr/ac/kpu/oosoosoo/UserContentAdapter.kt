package kr.ac.kpu.oosoosoo

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_content_item.*
import kotlinx.android.synthetic.main.user_content_item.view.*
import org.jetbrains.anko.startActivity

class UserContentAdapter: RecyclerView.Adapter<UserContentAdapter.ViewHolder>() {

    var contentList = mutableListOf<String>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemIndex: TextView = view.findViewById(R.id.user_content_item_index)
        val itemTitle: TextView = view.findViewById(R.id.user_content_item_title)
        val itemBox: LinearLayout = view.findViewById(R.id.user_content_item)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.user_content_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemIndex.text = (position+1).toString()
        viewHolder.itemTitle.text = contentList[position]

        viewHolder.itemBox.setOnClickListener{
            val intent = Intent(viewHolder.itemView.context, ContentsActivity::class.java)
            intent.putExtra("title", viewHolder.itemTitle.text.toString())
            startActivity(viewHolder.itemView?.context, intent, null)
        }
    }

    override fun getItemCount() = contentList.size
}