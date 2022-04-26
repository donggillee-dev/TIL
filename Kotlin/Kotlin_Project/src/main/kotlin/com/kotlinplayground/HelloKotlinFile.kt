package com.kotlinplayground

fun main() {
    var x = 1

    loop@ for(i in 1..5) {
        println("i in label : $i")

        innerloop@ for(j in 1..5) {
            println(
                buildString {
                    append("j in label : ")
                    append(j)
                },
            )

            if(j == 2) continue@loop
        }
    }
}