package `in`.co.jnana.ui.profile

import `in`.co.jnana.database.Course
import `in`.co.jnana.databinding.CourseCardViewBinding
import `in`.co.jnana.ui.course.CourseClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class EnrolledCourseAdapter(private val clickListener: CourseClickListener) :
    ListAdapter<Course, ECourseViewHolder>(ECourseDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ECourseViewHolder {
        return ECourseViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ECourseViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class ECourseViewHolder private constructor(
    private val binding: CourseCardViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(
            parent: ViewGroup
        ): ECourseViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CourseCardViewBinding.inflate(layoutInflater, parent, false)
            return ECourseViewHolder(binding)
        }
    }

    fun bind(item: Course, clickListener: CourseClickListener) {
        binding.course = item
        binding.courseClick = clickListener
    }
}

class ECourseDiffCallback : DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.courseID == newItem.courseID
    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem == newItem
    }
}