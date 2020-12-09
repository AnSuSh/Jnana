package `in`.co.jnana.ui.course

import `in`.co.jnana.databinding.CourseCardViewBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

// Old declaration
//class CourseAdapter(private val clickListener: CourseClickListener) :
//    RecyclerView.Adapter<CourseViewHolder>() {
class CourseAdapter : ListAdapter<Course, CourseViewHolder>(CourseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        return CourseViewHolder.from(parent)
    }

    // Implemented in ListAdapter itself
//    override fun getItemCount(): Int {
//        return this.dataset.size
//    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class CourseViewHolder private constructor(
    private val binding: CourseCardViewBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(
            parent: ViewGroup
        ): CourseViewHolder {
//            val cardView =
//                LayoutInflater.from(parent.context)
//                    .inflate(R.layout.course_card_view, parent, false) as MaterialCardView
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CourseCardViewBinding.inflate(layoutInflater, parent, false)
            return CourseViewHolder(binding)
        }
    }

    fun bind(item: Course) {
        binding.course = item
    }
}

class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.courseID == newItem.courseID
    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem == newItem
    }
}
//
//class CourseClickListener(val clickListener: (course: Course) -> Unit) {
//    fun onClick(course: Course) = clickListener(course)
//}