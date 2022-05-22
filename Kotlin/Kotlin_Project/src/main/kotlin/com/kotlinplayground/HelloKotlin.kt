package com.kotlinplayground

import java.util.IllegalFormatException

data class Person(
    val name: String = "",
    val age: Int = 0
) {
    var hh: String = ""
    var price: Double = 0.0
        set(value : Double) {
            if (value >= 0.0) {
                println("Setter called")
                field = value
            } else {
                println("test")
                throw java.lang.IllegalArgumentException("Illegal value")
            }
        }
}

fun main() {
//    var person = Person("test")
    var person = Person()

    person.hh = 1.toString()

    println("${person.hh} & ${person.price}")
}