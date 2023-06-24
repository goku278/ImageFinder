package com.example.imagefinder.model

import com.google.gson.annotations.SerializedName

class Id {
    @SerializedName("name") var name: String? = null
    @SerializedName("value") var value: String? = null

    override fun toString(): String {
        return "Id(name=$name, value=$value)"
    }


}