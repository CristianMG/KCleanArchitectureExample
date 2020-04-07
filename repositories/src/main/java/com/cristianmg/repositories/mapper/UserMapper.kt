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

import com.cristianmg.common_objects.UserEntity
import com.cristianmg.model.TypeTask
import com.cristianmg.model.User
import com.cristianmg.model.UserRole
import javax.inject.Inject


class UserMapper @Inject constructor() : IMapper<UserEntity, User> {

    override fun mapToModel(entity: UserEntity): User =
            User(entity.id, entity.email, entity.password, entity.name, UserRole.getRoleById(entity.role), TypeTask.getTypeTaskById(entity.typeTaskAvailable))

    override fun mapToEntity(model: User): UserEntity =
    UserEntity(model.id,model.email,model.password,model.name,model.role.id,model.availableTasks.map { it.idTask })


}