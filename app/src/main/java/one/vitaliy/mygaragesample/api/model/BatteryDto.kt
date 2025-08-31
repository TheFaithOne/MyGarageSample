package one.vitaliy.mygaragesample.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class BatteryDto(
    @Json(name = "capacityInKWh")
    val capacityInKWh: Int?
)
