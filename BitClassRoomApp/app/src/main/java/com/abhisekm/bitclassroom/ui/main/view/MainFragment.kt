package com.abhisekm.bitclassroom.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        ViewModelProvider(this).get(MainViewModel::class.java)
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

        viewModel.navigateToDatabaseLesson.observe(viewLifecycleOwner, Observer { classroom ->
            classroom?.let {
                this.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToClassroomFragment(it.id)
                )
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }

}