package com.example.danilaproject.TaskListFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.danilaproject.R
import com.example.danilaproject.databinding.FragmentTaskItemBinding
import com.example.danilaproject.databinding.FragmentTaskListBinding
import com.example.domain.Task
import java.util.*


class TaskListFragment : Fragment() {

    interface Callbacks {
        fun onTaskSelected(taskId : UUID)
    }

    private val taskListViewModel : TaskListViewModel by lazy {
        ViewModelProviders.of(this).get(TaskListViewModel::class.java)
    }

    private lateinit var recyclerView : RecyclerView
    private var adapter : TaskAdapter? = TaskAdapter(emptyList())
    private var callbacks : Callbacks? = null


    private inner class TaskHolder(view: View)
        : ViewHolder(view), View.OnClickListener {

        private val binding : FragmentTaskItemBinding
        private lateinit var _task : Task


        fun bind(task: Task) {
            _task = task
            binding.taskName.text = task.Title
            binding.taskDescription.text = task.Description

            if (task.Mark)
                binding.taskMark.setBackgroundResource(R.drawable.ic_marked_task)
            else
                binding.taskMark.setBackgroundResource(R.drawable.ic_not_marked_task)

            if (task.Completed)
                binding.taskComplete.setBackgroundResource(R.drawable.ic_done_task)
            else
                binding.taskComplete.setBackgroundResource(R.drawable.ic_not_done_task)
        }

        init {
            binding = FragmentTaskItemBinding.inflate(layoutInflater)
        }

        override fun onClick(v: View?) {
            // todo: add impementation
        }
    }

    private inner class TaskAdapter(var TaskList : List<Task>) : RecyclerView.Adapter<TaskHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
            val view = layoutInflater.inflate(R.layout.fragment_task_item, parent, false)
            return TaskHolder(view)
        }

        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val task = TaskList[position]
            holder.bind(task)
        }

        override fun getItemCount(): Int {
            return TaskList.size
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        recyclerView = view.findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskListViewModel.tasksLiveData.observe(
            viewLifecycleOwner,
            Observer { tasks ->
                tasks?.let {
                    updateUI(tasks)
                }
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_list_task, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_task -> {
                val task = Task()
                taskListViewModel.CreateTask(task)
                callbacks?.onTaskSelected(task.Id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI(tasks : List<Task>) {
        adapter = TaskAdapter(tasks)
        recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance() : TaskListFragment {
            return TaskListFragment()
        }
    }

}