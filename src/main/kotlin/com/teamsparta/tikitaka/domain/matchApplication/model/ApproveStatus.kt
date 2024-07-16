package com.teamsparta.tikitaka.domain.matchApplication.model

enum class ApproveStatus {
    WAITING, APPROVE, REJECT;

    companion object {
        fun fromString(approveStatus: String): ApproveStatus {
            return try {
                valueOf(approveStatus.uppercase())
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Invalid category : $approveStatus")
            }
        }
    }
}