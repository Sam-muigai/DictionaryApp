package com.example.dictionaryapp.data.network
import com.example.dictionaryapp.model.wordContent.WordMeaningItem
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface DictionaryApi {
    @GET("api/v2/entries/en/{word}")
    suspend fun getAllMeaning(
        @Path("word") word:String
    ):ArrayList<WordMeaningItem>
}