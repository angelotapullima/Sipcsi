package com.bufeotec.sipcsi.Feed.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

/*import com.andr.mvvm.RetrofitRoom.Models.ModelFeed;
import com.andr.mvvm.RetrofitRoom.Repository.FeedRoomDBRepository;
import com.andr.mvvm.RetrofitRoom.Repository.FeedWebServiceRepository;*/

import com.bufeotec.sipcsi.Feed.Models.ModelFeed;
import com.bufeotec.sipcsi.Feed.Repository.FeedRoomDBRepository;
import com.bufeotec.sipcsi.Feed.Repository.FeedWebServiceRepository;

import java.util.List;

public class FeedListViewModel extends AndroidViewModel {

    private FeedRoomDBRepository feedRoomDBRepository;
    private LiveData<List<ModelFeed>> mAllPosts;
    FeedWebServiceRepository feedWebServiceRepository;
    private final LiveData<List<ModelFeed>> retroObservable;
    public FeedListViewModel(Application application){
        super(application);
        feedRoomDBRepository = new FeedRoomDBRepository(application);
        feedWebServiceRepository = new FeedWebServiceRepository(application);
        retroObservable = feedWebServiceRepository.providesWebService();
        //feedRoomDBRepository.insertPosts(retroObservable.getValue());
        mAllPosts = feedRoomDBRepository.getAllPosts();

    }

    public LiveData<List<ModelFeed>> getAllPosts() {
        return mAllPosts;
    }

   /*public LiveData<List<ModelFeed>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

/*    public void insert(User user) {
        userRepository.insert(user);
    }*/
}
