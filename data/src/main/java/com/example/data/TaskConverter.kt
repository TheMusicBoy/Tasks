package com.example.data

import android.icu.text.CaseMap
import android.util.EventLogTags
import com.example.data.Entities.TaskEntity
import com.example.domain.Task

class TaskConverter {

    companion object {
        fun ToEntity(model: Task) : TaskEntity {
            return TaskEntity(
                Id = model.Id,
                Title = model.Title,
                Description = model.Description,
                Mark = model.Mark,
                Completed = model.Completed
            )
        }

        fun ToNullableModel(entity : TaskEntity?) : Task? {
            return if (entity == null)
                null
            else Task(
                Id = entity.Id,
                Title = entity.Title,
                Description = entity.Description,
                Mark = entity.Mark,
                Completed = entity.Completed
            )
        }

        fun ToNonNullableModel(entity : TaskEntity) : Task {
            return Task(
                Id = entity.Id,
                Title = entity.Title,
                Description = entity.Description,
                Mark = entity.Mark,
                Completed = entity.Completed
            )
        }

    }
}

