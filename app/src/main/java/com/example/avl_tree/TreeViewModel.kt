package com.example.avl_tree

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class TreeViewModel :ViewModel(){

    private val _onStartPressed=MutableLiveData<Boolean?>()
    val onStartPressed:LiveData<Boolean?>
    get() = _onStartPressed
    private val _onInsertPressed=MutableLiveData<Boolean?>()
    val onInsertPressed:LiveData<Boolean?>
        get() = _onInsertPressed


    fun start(){
        _onStartPressed.value=true
    }
    fun insert(){
        _onInsertPressed.value=true
    }
    fun onStartDone(){
        _onStartPressed.value=false

    }
    fun onInsertDone(){
        _onInsertPressed.value=false

    }

}