package com.qazstudy.util

data class Lesson(val header: Array<String>, val description: Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Lesson

        if (!header.contentEquals(other.header)) return false
        if (!description.contentEquals(other.description)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = header.contentHashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}