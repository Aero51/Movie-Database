package com.aero51.moviedatabase.utils;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;



public abstract class NetworkBoundResource<RequestType,ResultType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();


    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        appExecutors.diskIO().execute(() -> {
            if (!preExecute()) {
                result.postValue(Resource.error("Pre Execution Failed", 401, new RuntimeException("Pre Execution Condition Failed")));
                return;
            }
            appExecutors.mainThread().execute(() -> {
                LiveData<ResultType> dbSource = loadFromDb();
                result.addSource(dbSource, new Observer<ResultType>() {
                    @Override
                    public void onChanged(ResultType data) {
                        result.removeSource(dbSource);
                        if (NetworkBoundResource.this.shouldFetch(data)) {
                            NetworkBoundResource.this.preNetworkOperations();
                            NetworkBoundResource.this.fetchFromNetwork(dbSource);
                        } else {
                            result.addSource(dbSource, newData -> NetworkBoundResource.this.setValue(Resource.success(newData)));
                            NetworkBoundResource.this.onSuccess();
                        }
                    }
                });
            });
        });
    }

    @MainThread
    protected boolean preExecute() {
        return true;
    }

    @MainThread
    protected void preNetworkOperations() {}

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (result.getValue() != newValue) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType newData) {
                NetworkBoundResource.this.setValue(Resource.loading(newData));
            }
        });
        result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(ApiResponse<RequestType> response) {
                result.removeSource(apiResponse);
                result.removeSource(dbSource);
                //noinspection ConstantConditions
                if (response.isSuccessful()) {
                    appExecutors.diskIO().execute(() -> {
                        NetworkBoundResource.this.saveCallResult(NetworkBoundResource.this.processResponse(response));
                        appExecutors.mainThread().execute(() -> {
                            result.addSource(NetworkBoundResource.this.loadFromDb(), new Observer<ResultType>() {
                                @Override
                                public void onChanged(ResultType newData) {
                                    NetworkBoundResource.this.setValue(Resource.success(newData));
                                }
                            });
                            NetworkBoundResource.this.onSuccess();
                        });
                    });
                } else {
                    NetworkBoundResource.this.onFetchFailed();
                    if (response.actionError != null)
                        result.postValue(Resource.error(response.errorMessage, response.code, response.actionError));
                    else
                        result.addSource(dbSource, newData -> result.postValue(Resource.error(response.errorMessage, response.code, newData)));

                }
            }
        });
    }

    protected void onFetchFailed() {}

    protected void onSuccess(){}

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}