package `in`.co.jnana.ui.course

import `in`.co.jnana.R
import `in`.co.jnana.database.JnanaDatabase
import `in`.co.jnana.databinding.CourseDetailFragmentBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.InternalCoroutinesApi

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

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args = CourseDetailFragmentArgs.fromBundle(requireArguments())
        val application = requireActivity().application
        val dataSource = JnanaDatabase.getInstance(application).courseDatabaseDAO
//        val sDataSource = JnanaDatabase.getInstance(application).courseStudentDAO

        val courseDetailViewModelFactory = CourseDetailViewModelFactory(
            args.courseID,
            dataSource,
//            sDataSource
        )

        courseDetailViewModel = ViewModelProvider(
            this,
            courseDetailViewModelFactory
        ).get(CourseDetailViewModel::class.java)
        courseDetailViewModel.getCourseData()

        courseDataBinding.buttonBuy.setOnClickListener {
//            courseDetailViewModel.buyCourse(args.courseID)
            Toast.makeText(this.context, "Yet to be Implemented", Toast.LENGTH_SHORT).show()
        }


        courseDataBinding.courseDetailVM = courseDetailViewModel
        courseDataBinding.lifecycleOwner = viewLifecycleOwner
        courseDataBinding.executePendingBindings()
    }


}