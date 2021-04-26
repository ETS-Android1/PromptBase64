package com.pleiades.pleione.base64.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {
    private final MutableLiveData<String> hint = new MutableLiveData<>();
    private final MutableLiveData<String> output = new MutableLiveData<>();

    public void setHint(String hint) {
        this.hint.setValue(hint);
    }

    public LiveData<String> getHint() {
        return hint;
    }

    public void setOutput(String output) {
        this.output.setValue(output);
    }

    public LiveData<String> getOutput() {
        return output;
    }
}