package com.androidx.jetpack;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by meikai on 2019/11/10.
 */
public class BusViewModel extends ViewModel {

    public MutableLiveData<String> name = new MutableLiveData<>();
}
