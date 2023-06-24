package com.example.imagefinder.model

class Location {
    var street: String? = null
    var city: String? = null
    var state: String? = null
    var country: String? = null
    var postcode: String? = null
    var coordinates: String? = null
    var timezone: String? = null

    override fun toString(): String {
        return "Location(street=$street, city=$city, state=$state, country=$country, postcode=$postcode, coordinates=$coordinates, timezone=$timezone)"
    }


}