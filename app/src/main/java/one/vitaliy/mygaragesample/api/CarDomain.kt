package one.vitaliy.mygaragesample.api

import kotlinx.serialization.Serializable
import one.vitaliy.mygaragesample.api.model.MyCarResponse
import one.vitaliy.mygaragesample.api.model.RenderViewPoint
import vwg.skoda.maulcompose.lib.components.MaulListItemData

@Serializable
internal data class CarDomain(
    val body: String,
    val enginePower: Int? = null,
    val engineType: String,
    val engineCapacity: Double? = null,
    val batteryCapacity: Int? = null,
    val model: String,
    val modelYear: String,
    val name: String,
    val listImageUrl: String,
    val detailImageUrl: String,
    val trimLevel: String,
    val vin: String
)

// TODO: Ideally this should be done in some sort of a repository
internal fun MyCarResponse.toDomain() = CarDomain(
    batteryCapacity = battery?.capacityInKWh,
    body = body.orEmpty(),
    engineType = engine?.type.orEmpty(),
    engineCapacity = engine?.capacityInLiters,
    enginePower = engine?.powerInKW,
    model = model.orEmpty(),
    modelYear = modelYear.orEmpty(),
    name = name.orEmpty(),
    trimLevel = trimLevel.orEmpty(),
    vin = vin.orEmpty(),
    listImageUrl = renders.orEmpty()
        .firstOrNull { it.viewPoint == RenderViewPoint.GARAGE_L }?.url.orEmpty(),
    detailImageUrl = renders.orEmpty()
        .firstOrNull { it.viewPoint == RenderViewPoint.MAIN }?.url.orEmpty(),
)

// TODO: Obviously in production code these strings would be localized
internal fun CarDomain.getVehicleDetails(): List<MaulListItemData> = buildList {
    add(MaulListItemData(primaryText = "Název vozu", secondaryText = name))
    add(MaulListItemData(primaryText = "Model", secondaryText = model))
    add(MaulListItemData(primaryText = "Stupeň výbavy ", secondaryText = trimLevel))
    add(MaulListItemData(primaryText = "VIN ", secondaryText = vin))
    add(
        MaulListItemData(
            primaryText = "Motor ",
            secondaryText = buildString {
                engineCapacity?.let {
                    append(it)
                }
                append(engineType)
            }
        )
    )
    add(MaulListItemData(primaryText = "Maximální výkon", secondaryText = "$enginePower kW"))
}