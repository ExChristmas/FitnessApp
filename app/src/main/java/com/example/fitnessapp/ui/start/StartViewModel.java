package com.example.fitnessapp.ui.start;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StartViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mTextEml;
    private MutableLiveData<String> mTextPas;

    StartViewModel() {
        mText.setValue("s");
        mTextEml.setValue("ghn");
        mTextEml.setValue("frtghb");
    }

    public MutableLiveData<String> getmText() {
        return mText;
    }

    public MutableLiveData<String> getmTextEml() {
        return mTextEml;
    }

    public MutableLiveData<String> getmTextPas() {
        return mTextPas;
    }
}