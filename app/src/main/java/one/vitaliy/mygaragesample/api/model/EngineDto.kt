package one.vitaliy.mygaragesample.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class EngineDto(
    @Json(name = "powerInKW")
    val powerInKW: Int?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "capacityInLiters")
    val capacityInLiters: Double?
)
