package com.qazstudy.model

data class Lesson(val icon: Array<Int>, val header: Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Lesson

        if (!icon.contentEquals(other.icon)) return false
        if (!header.contentEquals(other.header)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = icon.contentHashCode()
        result = 31 * result + header.contentHashCode()
        return result
    }
}