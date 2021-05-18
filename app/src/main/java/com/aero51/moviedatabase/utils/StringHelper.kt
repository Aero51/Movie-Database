package com.aero51.moviedatabase.utils

class StringHelper {

companion object {
    fun join(separator: String, input: List<String>): String {
        if (input == null || input.size <= 0) return ""
        val sb = StringBuilder()
        for (i in input.indices) {
            sb.append(input[i])

            // if not the last item
            if (i != input.size - 1) {
                sb.append(separator)
            }
        }
        return sb.toString()
    }
}
}