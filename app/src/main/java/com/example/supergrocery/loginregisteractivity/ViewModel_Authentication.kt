package com.example.supergrocery.loginregisteractivity

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supergrocery.api.API
import com.example.supergrocery.models.ModelMainToken
import com.example.supergrocery.models.ModelSendCode
import com.example.supergrocery.models.UserRegisterData
import com.example.supergrocery.room.ItemsDao
import kotlinx.coroutines.launch

class ViewModel_Authentication @ViewModelInject constructor(
        val preferences: SharedPreferences,
        val api: API
) : ViewModel() {

    val registerLiveData = MutableLiveData<ModelMainToken<UserRegisterData>>();
    val sendCodeLiveData = MutableLiveData<ModelSendCode>();
    val verifyPhoneLiveData = MutableLiveData<ModelMainToken<UserRegisterData>>();
    val loginPhoneLiveData = MutableLiveData<ModelSendCode>();

    fun registerCall( name: String,email:String,phone_number:String,account_type:Int,nuis:String,platform:Short,firebase_token:String) {
        viewModelScope.launch {
            registerLiveData.value=api.register(name,email,phone_number,account_type,nuis,platform,firebase_token)
        }
    }
    fun startPhoneNumberVerification(user_phone:String){
        viewModelScope.launch {
           sendCodeLiveData.value=api.sendCode(user_phone)
        }
    }
    fun verifyPhoneNumberWithCode(code:String,userId:Int){
        viewModelScope.launch {
            verifyPhoneLiveData.value=api.verifyCode(code,userId)
        }
    }
    fun login_phoneNumber(phoneNumber:String){
        viewModelScope.launch {
            loginPhoneLiveData.value=api.sendCode(phoneNumber)
        }
    }
}