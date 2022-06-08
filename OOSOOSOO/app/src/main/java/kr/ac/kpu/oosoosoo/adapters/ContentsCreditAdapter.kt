package kr.ac.kpu.oosoosoo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recy_item_credits_card.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.contents.CreditsInfo

class ContentsCreditAdapter(context: Context, cardListData: List<CreditsInfo>): RecyclerView.Adapter<ContentsCreditAdapter.ViewHolder>() {
    private var creditList: List<CreditsInfo>? = cardListData

    val context = context

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(context: Context){
            itemView.tv_credit_name.text = creditList!![position]!!.people!!.name
            itemView.tv_credit_role.text = creditList!![position]!!.credit!!.role
            val url = "https://image.tmdb.org/t/p/original" + creditList!![position].people!!.profile_path

            Glide.with(context)
                .load(url)
                .into(itemView.img_credit_item)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recy_item_credits_card, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(context)
    }

    override fun getItemCount(): Int {
        return if(creditList == null) 0 else creditList!!.size
    }

}