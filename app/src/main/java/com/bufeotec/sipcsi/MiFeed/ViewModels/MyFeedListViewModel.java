package com.bufeotec.sipcsi.MiFeed.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.bufeotec.sipcsi.MiFeed.Models.ModelMyFeed;
import com.bufeotec.sipcsi.MiFeed.Repository.MyFeedRoomDBRepository;
import com.bufeotec.sipcsi.MiFeed.Repository.MyFeedWebServiceRepository;

import java.util.List;

public class MyFeedListViewModel extends AndroidViewModel {

    private MyFeedRoomDBRepository myFeedRoomDBRepository;
    private LiveData<List<ModelMyFeed>> mAllPosts;
    MyFeedWebServiceRepository myFeedWebServiceRepository;
    private final LiveData<List<ModelMyFeed>> retroObservable;


    public MyFeedListViewModel(Application application){
        super(application);
        myFeedRoomDBRepository = new MyFeedRoomDBRepository(application);
        myFeedWebServiceRepository = new MyFeedWebServiceRepository(application);
        retroObservable = myFeedWebServiceRepository.providesWebService();
        //myFeedRoomDBRepository.insertPosts(retroObservable.getValue());
        mAllPosts = myFeedRoomDBRepository.getAllPosts();

    }

    public LiveData<List<ModelMyFeed>> getAllPosts() {
        return mAllPosts;
    }

   /*public LiveData<List<ModelFeed>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

    public void deleteAllPost() {
        myFeedRoomDBRepository.deleteAllPost();
    }
/*    public void insert(User user) {
        userRepository.insert(user);
    }*/
}
