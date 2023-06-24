package com.example.imagefinder.model

class Login {
    var uuid: String? = null
    var username: String? = null
    var password: String? = null
    var salt: String? = null
    var md5: String? = null
    var sha1: String? = null
    var sha256: String? = null

    override fun toString(): String {
        return "Login(uuid=$uuid, username=$username, password=$password, salt=$salt, md5=$md5, sha1=$sha1, sha256=$sha256)"
    }


}