package one.vitaliy.mygaragesample.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class MyCarResponse(
    @Json(name = "battery")
    val battery: BatteryDto?,
    @Json(name = "body")
    val body: String?,
    @Json(name = "engine")
    val engine: EngineDto?,
    @Json(name = "model")
    val model: String?,
    @Json(name = "modelYear")
    val modelYear: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "renders")
    val renders: List<RenderDto>?,
    @Json(name = "trimLevel")
    val trimLevel: String?,
    @Json(name = "vin")
    val vin: String?
)