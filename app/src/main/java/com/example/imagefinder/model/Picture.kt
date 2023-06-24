package com.example.imagefinder.model

class Picture {
    var large: String? = null
    var medium: String? = null
    var thumbnail: String? = null

    override fun toString(): String {
        return "Picture(large=$large, medium=$medium, thumbnail=$thumbnail)"
    }


}