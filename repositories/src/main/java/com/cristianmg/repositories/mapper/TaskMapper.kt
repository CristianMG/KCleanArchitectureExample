/*
 *
 *  * Copyright 2020 Cristian Menárguez González
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.cristianmg.repositories.mapper

import com.cristianmg.common_objects.TaskEntity
import com.cristianmg.database.converter.DateFormat
import com.cristianmg.model.Task
import com.cristianmg.model.TypeTask
import javax.inject.Inject


class TaskMapper @Inject constructor(private val dateFormat: DateFormat) : IMapper<TaskEntity, Task> {

    override fun mapToModel(entity: TaskEntity) =
            Task(entity.id, TypeTask.getTypeTaskById(entity.typeTask), entity.userId, entity.description, entity.duration, dateFormat.parseDatabaseFormatToCalendar(entity.date), entity.complete)


    override fun mapToEntity(model: Task): TaskEntity =
            TaskEntity(model.id, model.typeTask.idTask, model.description, model.userId, model.secondsToComplete, dateFormat.parseCalendarToDatabaseFormat(model.date), model.complete)


}