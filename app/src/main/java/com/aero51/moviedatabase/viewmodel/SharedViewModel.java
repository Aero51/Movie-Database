package com.aero51.moviedatabase.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.utils.SingleLiveEvent;

public class SharedViewModel extends ViewModel {

private MutableLiveData<EpgProgram> liveEpgProgram=new MutableLiveData<>();
private SingleLiveEvent<Boolean> shouldSwitchFragments= new SingleLiveEvent<>();
private Integer index;

public void ChangeEpgTvFragment(Integer index,EpgProgram epgProgram){
    this.index=index;
    shouldSwitchFragments.setValue(true);
    liveEpgProgram.setValue(epgProgram);
}

public LiveData<EpgProgram> getLiveDataProgram(){
    return liveEpgProgram;
}
public LiveData<Boolean> getSingleLiveShouldSwitchFragments(){
    return shouldSwitchFragments;
}
}
