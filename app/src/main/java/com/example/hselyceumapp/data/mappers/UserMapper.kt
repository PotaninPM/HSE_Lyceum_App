package com.example.hselyceumapp.data.mappers

import com.example.hselyceumapp.data.local.UserEntity
import com.example.hselyceumapp.data.network.dto.UserDto
import com.example.hselyceumapp.domain.model.User

fun UserDto.toEntity() = UserEntity(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl
)

fun UserDto.toDomain() = User(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl
)

fun UserEntity.toUser() = User(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl
)