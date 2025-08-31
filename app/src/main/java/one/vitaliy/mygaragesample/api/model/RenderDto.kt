package one.vitaliy.mygaragesample.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class RenderDto(
    @Json(name = "url")
    val url: String?,
    @Json(name = "viewPoint")
    val viewPoint: RenderViewPoint?
)

@JsonClass(generateAdapter = false)
internal enum class RenderViewPoint {
    @Json(name = "main")
    MAIN,

    @Json(name = "garage_l")
    GARAGE_L,
}
