package com.example.danilaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.danilaproject.DetailsFragment.DetailsFragment
import com.example.danilaproject.TaskListFragment.TaskListFragment
import java.util.UUID

class MainActivity : AppCompatActivity(), TaskListFragment.Callbacks, DetailsFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val curFragment = supportFragmentManager.findFragmentById(R.id.container)

        if (curFragment == null) {
            val fragment = TaskListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .commit()
        }
    }


    override fun onTaskSelected(taskId : UUID) {
        val fragment = DetailsFragment.newInstance(taskId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onTaskDeleted(taskId: UUID) {
        supportFragmentManager.popBackStack()
    }
}