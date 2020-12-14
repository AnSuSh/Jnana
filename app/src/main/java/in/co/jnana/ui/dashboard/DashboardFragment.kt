package `in`.co.jnana.ui.dashboard

import `in`.co.jnana.R
import `in`.co.jnana.database.JnanaDatabase
import `in`.co.jnana.databinding.FragmentDashboardBinding
import `in`.co.jnana.ui.course.CourseAdapter
import `in`.co.jnana.ui.course.CourseClickListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.InternalCoroutinesApi

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var adapter: CourseAdapter

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val application = requireActivity().application
        val dataBase = JnanaDatabase.getInstance(application).courseDatabaseDAO

        val dashboardViewModelFactory = DashboardViewModelFactory(dataBase, application)
        dashboardViewModel =
            ViewModelProvider(this, dashboardViewModelFactory).get(DashboardViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)

//        val adapter = CourseAdapter(CourseClickListener {
//            Log.d(">>>>>>Log", "Reached adapter code")
//            Toast.makeText(this.context, "Clicked", Toast.LENGTH_SHORT).show()
//            findNavController().navigate(
//                DashboardFragmentDirections.actionNavigationDashboardToCourseDetail(
//                    it.title,
//                    it.description
//                )
//            )
//        })
        adapter = CourseAdapter(CourseClickListener {
            dashboardViewModel.onCourseItemClicked(it)
        })

        dashboardViewModel.navigateToCourseDetail.observe(viewLifecycleOwner, {
            it?.let {
                findNavController().navigate(
                    DashboardFragmentDirections.actionNavigationDashboardToCourseDetail(
                        it,
                        false
                    )
                )
                dashboardViewModel.onCourseDetailNavigated()
            }
        })

        dashboardViewModel.runCourseDatabaseSetup()

        binding.dashboardVM = dashboardViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.coursesRecyclerView.adapter = adapter

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashboardViewModel.dataset.observe(viewLifecycleOwner, {
            it?.let {
                // For the listAdapter
                adapter.submitList(it)
            }
        })
    }
}
