package one.vitaliy.mygaragesample.api

import one.vitaliy.mygaragesample.api.model.MyCarResponse
import retrofit2.http.GET

internal interface MyGarageApiClient {
    @GET("garage_api.json")
    suspend fun getMyGarageInfo(): List<MyCarResponse>
}