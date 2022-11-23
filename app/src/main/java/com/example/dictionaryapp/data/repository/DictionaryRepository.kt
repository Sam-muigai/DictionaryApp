package com.example.dictionaryapp.data.repository

import android.util.Log
import com.example.dictionaryapp.data.network.DictionaryApi
import com.example.dictionaryapp.model.wordContent.WordMeaningItem
import com.example.dictionaryapp.model.wrapper.DataOrException
import javax.inject.Inject

class DictionaryRepository @Inject constructor(private val api:DictionaryApi) {
    private val dataOrException:
            DataOrException<ArrayList<WordMeaningItem>,Boolean,Exception> = DataOrException()
    suspend fun getAllMeaning(word:String):DataOrException<ArrayList<WordMeaningItem>,Boolean,Exception>{
        try {
         dataOrException.data = api.getAllMeaning(word = word)
        }catch (e:Exception){
            dataOrException.Exc = e
        }
        Log.d("INSIDE","getWord: ${dataOrException.data}")
        return dataOrException
    }
}