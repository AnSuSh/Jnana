package `in`.co.jnana.ui.dashboard

import `in`.co.jnana.R
import `in`.co.jnana.databinding.FragmentDashboardBinding
import `in`.co.jnana.ui.course.CourseAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

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
        val adapter = CourseAdapter()

        dashboardViewModel.dataset.observe(viewLifecycleOwner, {
            it?.let {
                // For the listAdapter
                adapter.submitList(it)
            }
        })

        binding.dashboardVM = dashboardViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.coursesRecyclerView.adapter = adapter

        setHasOptionsMenu(true)
        return binding.root
    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.dashboard_search, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }

}
