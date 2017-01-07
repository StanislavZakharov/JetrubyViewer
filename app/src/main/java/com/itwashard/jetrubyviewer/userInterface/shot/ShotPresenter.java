package com.itwashard.jetrubyviewer.userInterface.shot;

import com.itwashard.jetrubyviewer.data.DataManager;
import com.itwashard.jetrubyviewer.data.model.Comment;
import com.itwashard.jetrubyviewer.userInterface.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

public class ShotPresenter extends BasePresenter<ShotMvpView> {

    // We'll handle pagination in the future...
    public static final int SHOT_COUNT = 50;
    public static final int SHOT_PAGE = 0;

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private List<Comment> mComments;

    @Inject
    ShotPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void getComments(int id, int perPage, int page) {
        checkViewAttached();
        getMvpView().showProgress();

        Single<List<Comment>> single;
        if (mComments == null) {
            single = mDataManager.getComments(id, perPage, page);
        } else {
            single = Single.just(mComments);
        }

        mSubscription = single
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleSubscriber<List<Comment>>() {
                    @Override
                    public void onSuccess(List<Comment> comments) {
                        mComments = comments;
                        getMvpView().hideProgress();
                        if (comments.isEmpty()) {
                            getMvpView().showEmptyComments();
                        } else {
                            getMvpView().showComments(comments);
                        }
                        getMvpView().showCommentsTitle(!comments.isEmpty());
                    }

                    @Override
                    public void onError(Throwable error) {
                        Timber.e(error, "There was an error retrieving the comments");
                        getMvpView().hideProgress();
                        getMvpView().showError();
                    }
                });

    }
}
