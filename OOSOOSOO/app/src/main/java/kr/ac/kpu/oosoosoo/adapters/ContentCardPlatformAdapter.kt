package kr.ac.kpu.oosoosoo.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recy_item_content_card.view.*
import kotlinx.android.synthetic.main.recy_item_platform_icon.view.*
import kr.ac.kpu.oosoosoo.R

class ContentCardPlatformAdapter(context: Context, platformListData: ArrayList<String>): RecyclerView.Adapter<ContentCardPlatformAdapter.ViewHolder>() {

    //컨텐츠의 상영 플랫폼 리스트
    private var platformList : ArrayList<String>? = platformListData

    val context : Context = context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        //layout 파일에 값 출력
        fun bind(result: String) {
            var imgResource: Int? = 0

            when (result.lowercase().trim()) {
                "netflix" -> itemView.card_platform_icon.setImageResource(R.drawable.netflix_logo_40dp)
                "watcha" -> itemView.card_platform_icon.setImageResource(R.drawable.watcha_logo_40dp)
                "wavve" -> itemView.card_platform_icon.setImageResource(R.drawable.wavve_logo_40dp)
                "tving" -> itemView.card_platform_icon.setImageResource(R.drawable.tving_logo_40dp)
                "disney plus" -> itemView.card_platform_icon.setImageResource(R.drawable.disney_logo_40dp)
                "naver" -> itemView.card_platform_icon.setImageResource(R.drawable.naver_logo)
            }


        }
    }

    //출력할 xml파일 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.recy_item_platform_icon, parent,
            false
        )
    )

    //bind 과정
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(platformList!![position])
        Log.d("test",platformList!![position])
    }

    override fun getItemCount(): Int = platformList!!.size

}
