package com.diu.swan.app.ui.models

import java.io.Serializable

data class Hospital(
    var id: Int? = null ,
    var name : String? = null ,
    var phone : String? = null ,
    var address : String? = null,
    var estd : Int? = null,
    var details: String? =null

):Serializable
