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

import com.cristianmg.common_objects.FarmEntity
import com.cristianmg.common_objects.FarmerLocationEntity
import com.cristianmg.model.Farm
import com.cristianmg.model.FarmerLocation
import javax.inject.Inject


class FarmMapper @Inject constructor() : IMapper<FarmEntity, Farm> {

    override fun mapToModel(entity: FarmEntity): Farm =
            Farm(entity.farmerID, entity.category ?: "", entity.item ?: "", entity.zipcode
                    ?: "", entity.phone
                    ?: "", entity.locationEntity?.let { FarmerLocation(it.latitude, it.longitude, it.humanAddress) })


    override fun mapToEntity(model: Farm): FarmEntity =
            FarmEntity(model.farmerID, model.category, model.item, model.zipcode, model.phone, model.location?.let { FarmerLocationEntity(it.latitude, it.longitude, it.humanAddress) })


}