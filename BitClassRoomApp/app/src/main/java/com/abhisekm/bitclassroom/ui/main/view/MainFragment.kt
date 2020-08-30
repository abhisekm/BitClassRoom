package com.abhisekm.bitclassroom.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.abhisekm.bitclassroom.R
import com.abhisekm.bitclassroom.databinding.MainFragmentBinding
import com.abhisekm.bitclassroom.ui.main.adapter.LessonsAdapter
import com.abhisekm.bitclassroom.ui.main.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, MainViewModel.Factory(activity.application))
            .get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: MainFragmentBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.main_fragment, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.lessonList.adapter = LessonsAdapter(LessonsAdapter.OnClickListener {
            viewModel.joinClassroom(it)
        })

        viewModel.navigateToDatabaseLesson.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {lesson ->
                this.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToClassroomFragment(lesson.id, lesson.heading)
                )
            }
        })

        // Observer for the network error.
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {networkError ->
              if (networkError) onNetworkError()
            }
        })

        return binding.root
    }

    /**
     * Method for displaying a Toast error message for network errors.
     */
    private fun onNetworkError() {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
    }



}