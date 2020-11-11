package `in`.co.jnana.ui.dashboard

import `in`.co.jnana.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseAdapter(private val dataset: Array<String>):
    RecyclerView.Adapter<CourseAdapter.CourseViewHolder>(){

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//        val imageView : ImageView = itemView.findViewById(R.id.course_desc_image)
        val textView : TextView = itemView.findViewById(R.id.course_title)
//        val textView2 : TextView = itemView.findViewById(R.id.course_desc_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_card_view, parent, false)
        return CourseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.textView.text = dataset[position]
    }
}

