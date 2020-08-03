package com.qazstudy.model

data class Task(val header: Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (!header.contentEquals(other.header)) return false

        return true
    }

    override fun hashCode(): Int {
        return header.contentHashCode()
    }
}