package br.edu.ifal.selicsim.api

import br.edu.ifal.selicsim.domain.Selic
import retrofit2.http.GET
import retrofit2.http.Query

interface SelicApi {

    @GET("dados/serie/bcdata.sgs.432/dados/ultimos/10")
    suspend fun getSelic(
        @Query("formato") formato: String = "json"
    ): List<Selic>
}