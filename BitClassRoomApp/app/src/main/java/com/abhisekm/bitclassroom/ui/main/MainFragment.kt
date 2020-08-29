package com.abhisekm.bitclassroom.ui.main

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

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding : MainFragmentBinding = DataBindingUtil.inflate(layoutInflater, R.layout.main_fragment, container, false)
        val application = requireNotNull(this.activity).application

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToClassroom.observe(viewLifecycleOwner, Observer { classroom ->
            classroom?.let {
                this.findNavController().navigate(MainFragmentDirections.actionMainFragmentToClassroomFragment(it.id))
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }

}