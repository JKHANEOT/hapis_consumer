package com.hapis.customer.ui.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hapis.customer.ui.models.HapisModel;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public abstract class BaseViewModal<V extends BaseView> extends ViewModel implements LifecycleObserver {

    protected ArrayList<MutableLiveData<HapisModel>> mObservableData = new ArrayList<>();
    protected V mView;
    protected LifecycleOwner mOwner;
    protected abstract void initObservableData();
    protected abstract void handleChangedDataModal(HapisModel data);

    public BaseViewModal(LifecycleOwner owner){
        this.mOwner = owner;
        mOwner.getLifecycle().addObserver(this);
    }

    public void setmObservableData(ArrayList<MutableLiveData<HapisModel>> mObservableData) {
        this.mObservableData = mObservableData;
        initObservableData();
    }

    public void registerView(V view){
        mView = view;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected void onCreate() {
        initObservableData();
        registerView(mView);
        for(LiveData<HapisModel> liveData : mObservableData) {
            liveData.observe(mOwner, new Observer<HapisModel>() {
                @Override
                public void onChanged(@Nullable HapisModel dataModal) {
                    handleChangedDataModal(dataModal);
                }
            });
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy(){
        for(LiveData<HapisModel> liveData : mObservableData) {
            liveData.removeObservers(mOwner);
        }
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private LifecycleOwner mOwner;
        public Factory(LifecycleOwner owner){
            this.mOwner = owner;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            try {
                return modelClass.getConstructor(LifecycleOwner.class).newInstance(mOwner);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
