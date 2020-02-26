package com.fernandocejas.sample.data

import android.content.Context
import com.fernandocejas.sample.core.extension.loadJSONFromAsset
import com.fernandocejas.sample.data.entity.UserEntity
import com.fernandocejas.sample.domain.model.TypeTask
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class SampleData {

    companion object {

        fun getSampleData(context: Context): List<UserEntity> {
            val turnsType = object : TypeToken<List<UserEntity>>() {}.type
            return Gson().fromJson<List<UserEntity>>(context.loadJSONFromAsset("UserSampleData.json"), turnsType)
        }


        fun getSingleUser(context: Context): UserEntity {
            val turnsType = object : TypeToken<List<UserEntity>>() {}.type
            return Gson().fromJson<List<UserEntity>>(context.loadJSONFromAsset("UserSampleData.json"), turnsType).random()
        }


        fun getUser(taskAvailable: List<TypeTask>, role: Int): UserEntity =
                UserEntity(UUID.randomUUID().toString(), "example@example.com", "example", "example", role, taskAvailable.map { it.idTask })


    }
}