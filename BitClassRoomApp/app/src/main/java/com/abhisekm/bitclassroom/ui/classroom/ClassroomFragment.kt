package com.abhisekm.bitclassroom.ui.classroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.abhisekm.bitclassroom.R
import com.abhisekm.bitclassroom.databinding.FragmentClassroomBinding

class ClassroomFragment : Fragment() {
    companion object {
        fun newInstance() = ClassroomFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentClassroomBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_classroom, container, false)

        val arguments = ClassroomFragmentArgs.fromBundle(requireArguments())

        binding.textView.text = "Lesson id is  ${arguments.classroomId}"

        return binding.root
    }
}