package com.fernandocejas.sample.data.repository.user

import com.fernandocejas.sample.data.entity.UserEntity
import java.util.*

class SampleData {


    companion object {
        fun getUsers(): List<UserEntity> {
            return mutableListOf(
                    UserEntity(
                            UUID.randomUUID().toString(),
                            "ruben.garcia@gmail.com",
                            "12345",
                            "Rubén García",
                            UserEntity.ROLE_ADMIN)
                    ,
                    UserEntity(
                            UUID.randomUUID().toString(),
                            "rafael.martin@gmail.com",
                            "123456",
                            "Rafael Martín",
                            UserEntity.ROLE_TECHNICAL)
                    ,
                    UserEntity(
                            UUID.randomUUID().toString(),
                            "sarah.lópez@gmail.com",
                            "1234567",
                            "Sarah López",
                            UserEntity.ROLE_TECHNICAL)
            )
        }

    }
}