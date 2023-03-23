package com.example.danilaproject.TaskListFragment

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
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
import kotlinx.android.synthetic.main.fragment_details.*
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

        private lateinit var _task: Task
        private val taskName: TextView = itemView.findViewById(R.id.taskName)
        private val taskDescription: TextView = itemView.findViewById(R.id.taskDescription)
        private val taskMark: ImageButton = itemView.findViewById(R.id.taskMark)
        private val taskComplete: ImageButton = itemView.findViewById(R.id.taskComplete)


        fun bind(task: Task) {
            _task = task
            taskName.text = task.Title
            taskDescription.text = task.Description

            if (task.Mark)
                taskMark.setBackgroundResource(R.drawable.ic_marked_task)
            else
                taskMark.setBackgroundResource(R.drawable.ic_not_marked_task)

            if (task.Completed)
                taskComplete.setBackgroundResource(R.drawable.ic_done_task)
            else
                taskComplete.setBackgroundResource(R.drawable.ic_not_done_task)
        }

        init {
            itemView.setOnClickListener(this)

            taskComplete.setOnClickListener {
                _task.Completed = taskListViewModel.tasksLiveData.value!![position].Completed
                taskListViewModel.tasksLiveData.value?.get(position)!!.Completed = _task.Completed

                if (_task.Completed)
                    completeButton.setBackgroundResource(R.drawable.ic_done_task)
                else
                    completeButton.setBackgroundResource(R.drawable.ic_not_done_task)

                taskListViewModel.UpdateTask(_task)
            }
        }

        override fun onClick(v: View?) {
            callbacks?.onTaskSelected(_task.Id)
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
        callbacks = context as TaskListFragment.Callbacks?
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