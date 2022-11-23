package com.example.dictionaryapp.presentation.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.data.repository.DictionaryRepository
import com.example.dictionaryapp.model.wordContent.WordMeaningItem
import com.example.dictionaryapp.model.wrapper.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DictionaryRepository):ViewModel() {
    suspend fun getWordMeaning(word:String = "bank"):
            DataOrException<ArrayList<WordMeaningItem>,Boolean,Exception>{
        return repository.getAllMeaning(word)
    }
}