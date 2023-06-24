package com.example.imagefinder.model

class Profile {
    var name: String? = null
    var location: String? = null
    var email: String? = null
    var dob: String? = null
    var noDays: String? = null
    var image: String? = null

    override fun toString(): String {
        return "Profile(name=$name, location=$location, email=$email, dob=$dob, noDays=$noDays, image=$image)"
    }
}