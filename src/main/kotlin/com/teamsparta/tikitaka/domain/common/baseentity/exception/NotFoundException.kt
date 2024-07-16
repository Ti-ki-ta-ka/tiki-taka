package com.teamsparta.tikitaka.domain.common.baseentity.exception

data class NotFoundException(
    val modelName: String,
    val id: Long?
) : RuntimeException(
    "Model $modelName not found with id $id"
)