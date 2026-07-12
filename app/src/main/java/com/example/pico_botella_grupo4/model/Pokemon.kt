package com.example.pico_botella_grupo4.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("id")
    val id:Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("img")
    val img:String
)
