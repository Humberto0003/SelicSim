package br.edu.ifal.selicsim.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitHelper {

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.bcb.gov.br/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val selicApi: SelicApi =
        retrofit.create(SelicApi::class.java)
}