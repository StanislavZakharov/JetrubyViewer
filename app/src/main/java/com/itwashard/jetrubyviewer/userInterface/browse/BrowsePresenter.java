package com.itwashard.jetrubyviewer.userInterface.browse;

import com.itwashard.jetrubyviewer.data.DataManager;
import com.itwashard.jetrubyviewer.data.model.Shot;
import com.itwashard.jetrubyviewer.userInterface.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

public class BrowsePresenter extends BasePresenter<BrowseMvpView> {

    // We'll handle pagination in the future...
    public static final int SHOT_COUNT = 50;
    public static final int SHOT_PAGE = 0;

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    BrowsePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void getShots(int perPage, int page) {
        checkViewAttached();
        getMvpView().showMessageLayout(false);
        getMvpView().showProgress();

        mSubscription = mDataManager.getShots(perPage, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleSubscriber<List<Shot>>() {
                    @Override
                    public void onSuccess(List<Shot> shots) {
                        getMvpView().hideProgress();
                        if (!shots.isEmpty()) {
                            getMvpView().showShots(shots);
                        } else {
                            getMvpView().showEmpty();
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        Timber.e(error, "There was an error retrieving the shots");
                        getMvpView().hideProgress();
                        getMvpView().showError();
                    }
                });
    }

}
