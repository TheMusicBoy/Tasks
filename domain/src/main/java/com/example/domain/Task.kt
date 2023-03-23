package com.example.domain

import java.lang.reflect.Constructor
import java.util.*

data class Task (
    val Id : UUID = UUID.randomUUID(),
    var Title : String = "",
    var Description : String = "",
    var Mark : Boolean = false,
    var Completed : Boolean = false
    )