package com.engineblue.data.entity

val graph = hashMapOf<String, List<String>>().apply {
    put("04", listOf("18")) //Almeria
    put("11", listOf("21", "29", "41")) // Cadiz
    put("14", listOf("41", "29", "18", "23")) // Cordoba
    put("18", listOf("04", "14", "29", "23")) // Granada
    put("21", listOf("11", "41")) // Huelva
    put("23", listOf("14", "18")) // Jaen
    put("29", listOf("11", "41", "14", "18")) // Malaga
    put("41", listOf("21", "29", "11", "14")) // Sevilla
}
