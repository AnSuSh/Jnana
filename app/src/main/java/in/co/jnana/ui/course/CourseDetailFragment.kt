package `in`.co.jnana.ui.course

import `in`.co.jnana.R
import `in`.co.jnana.databinding.CourseDetailFragmentBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class CourseDetailFragment : Fragment() {

    private lateinit var courseDetailViewModel: CourseDetailViewModel
    private lateinit var courseDataBinding: CourseDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        courseDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.course_detail_fragment, container, false)
        courseDataBinding.lifecycleOwner = viewLifecycleOwner
        return courseDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        courseDetailViewModel = ViewModelProvider(this).get(CourseDetailViewModel::class.java)
        courseDetailViewModel.setTitle("Course New")
        courseDetailViewModel.setDescription("Course description")
        courseDataBinding.invalidateAll()
    }

}