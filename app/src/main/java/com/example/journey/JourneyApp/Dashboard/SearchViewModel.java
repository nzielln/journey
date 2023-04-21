package com.example.journey.JourneyApp.Dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<String> search;

    public SearchViewModel(){
        search = new MutableLiveData<>();
    }

    public void setSearch(String newSearch){
        search.setValue(newSearch);
    }
    public MutableLiveData<String> getSearch(){
        return search;

    }
}
