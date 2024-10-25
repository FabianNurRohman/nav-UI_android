package com.dicoding.fundamentalsub1.utils

data class Event(
    val id: String,
    val name: String,
    val imageLogo: String,
    val mediaCover: String,
    val ownerName: String,
    val beginTime: String,
    val quota: Int,
    val registrant: Int,
    val description: String,
    val link: String
)