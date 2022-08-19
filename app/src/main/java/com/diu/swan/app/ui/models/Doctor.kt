package com.diu.swan.app.ui.models

import java.io.Serializable

data class Doctor(
    var id: Long? = null ,
    var name : String? = null ,
    var phone : Long? = null ,
    var bmdc : String? = null ,
    var degree : String? = null ,
    var specalist : String? = null ,
    var details: String? =null

): Serializable
