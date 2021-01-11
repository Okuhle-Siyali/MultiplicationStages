package com.example.multiplicationstages

import java.util.Random

class PresenterMainActivity {
    var multiplicand : Int = 0
    var multiplier : Int = 0
    var attempts = 1
    var correctAnswer : Int = 0

    fun generateRandomNumberLimit(from : Int, to : Int) {
        val random = Random()
        multiplicand = random.nextInt(to - from) + from
        multiplier = random.nextInt(to - from) + from
    }
}