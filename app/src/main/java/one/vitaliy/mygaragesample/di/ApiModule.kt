package one.vitaliy.mygaragesample.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import one.vitaliy.mygaragesample.api.MyGarageApiClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal class ApiModule {
    @Provides
    fun provideApiService(): MyGarageApiClient {
        val moshi = Moshi.Builder()
            .build()
        val okHttpClient = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder().client(okHttpClient)
            .baseUrl("https://beegreen-hackathon.web.app/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        return retrofit.create(MyGarageApiClient::class.java)
    }
}