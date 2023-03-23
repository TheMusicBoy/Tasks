package com.example.danilaproject.DetailsFragment

import android.app.Activity
import android.app.ActivityManager.TaskDescription
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.example.danilaproject.R
import com.example.danilaproject.databinding.FragmentTaskItemBinding
import com.example.domain.Task
import kotlinx.android.synthetic.main.fragment_details.*
import java.util.UUID

const val ARG_TASK_ID = "task_id"

class DetailsFragment : Fragment() {

    private lateinit var taskName : EditText
    private lateinit var taskDescription : EditText
    private lateinit var taskComplete : ImageButton
    private lateinit var taskMark : ImageButton
    private lateinit var deleteButton: ImageButton

    private val detailsViewModel : DetailsViewModel by lazy {
        ViewModelProviders.of(this).get(DetailsViewModel::class.java)
    }

    private lateinit var _task : Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _task = Task()
        val taskId : UUID = arguments?.getSerializable(ARG_TASK_ID) as UUID
        detailsViewModel.LoadTask(taskId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        taskName = view.findViewById(R.id.taskName)
        taskDescription = view.findViewById(R.id.taskDescription)
        taskMark = view.findViewById(R.id.markButton)
        taskComplete = view.findViewById(R.id.completeButton)
        deleteButton = view.findViewById(R.id.deleteButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsViewModel.taskLiveData.observe(
            viewLifecycleOwner,
            Observer { task ->
                task?.let {
                    _task = task
                    updateUI()
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                _task.Title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        taskName.addTextChangedListener(titleWatcher)

        val descriptionWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                _task.Description = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        taskDescription.addTextChangedListener(descriptionWatcher)

        taskMark.setOnClickListener {
            _task.Mark = !_task.Mark

            Toast.makeText(
                context,
                "taskMark",
                Toast.LENGTH_SHORT
            ).show()

            if (_task.Mark)
                taskMark.setBackgroundResource(R.drawable.ic_marked_task)
            else
                taskMark.setBackgroundResource(R.drawable.ic_not_marked_task)
        }

        taskComplete.setOnClickListener {
            _task.Completed = !_task.Completed
            if (_task.Completed)
                taskComplete.setBackgroundResource(R.drawable.ic_done_task)
            else
                taskComplete.setBackgroundResource(R.drawable.ic_not_done_task)
        }

        deleteButton.setOnClickListener {
            Toast.makeText(
                context,
                R.string.app_name,
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onStop() {
        super.onStop()
        detailsViewModel.UpdateTask(_task)
    }

    private fun updateUI() {
        taskName.setText(_task.Title)
        taskDescription.setText(_task.Description)

        if (_task.Completed)
            taskComplete.setBackgroundResource(R.drawable.ic_done_task)
        else
            taskDescription.setBackgroundResource(R.drawable.ic_not_done_task)

        if (_task.Mark)
            taskMark.setBackgroundResource(R.drawable.ic_marked_task)
        else
            taskMark.setBackgroundResource(R.drawable.ic_not_marked_task)

    }

    companion object {
        fun newInstance(taskId : UUID) : DetailsFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TASK_ID, taskId)
            }

            return DetailsFragment().apply {
                arguments = args
            }
        }
    }

}