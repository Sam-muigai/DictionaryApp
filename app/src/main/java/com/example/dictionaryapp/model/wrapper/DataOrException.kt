package com.example.dictionaryapp.model.wrapper

data class DataOrException<T,Boolean,E:Exception> (
    var data:T? = null,
    var isLoading:Boolean? = null,
    var Exc: E? = null
        )