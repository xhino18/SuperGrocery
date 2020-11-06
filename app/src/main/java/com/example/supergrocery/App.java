package com.example.supergrocery;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.multidex.MultiDexApplication;

import dagger.Provides;
import dagger.hilt.android.HiltAndroidApp;

import javax.inject.Singleton;

@HiltAndroidApp
public class App extends MultiDexApplication {

}