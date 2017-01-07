package com.itwashard.jetrubyviewer.data;

import com.itwashard.jetrubyviewer.Constants;
import com.itwashard.jetrubyviewer.data.model.Comment;
import com.itwashard.jetrubyviewer.data.model.Shot;
import com.itwashard.jetrubyviewer.data.remote.JetrubyService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Single;

/**
 * Created by stanislavzakharov on 22.12.16.
 */

@Singleton
public class DataManager {

    private final JetrubyService mJetrubyService;

    @Inject
    public DataManager(JetrubyService jetrubyService) {
        mJetrubyService = jetrubyService;
    }

    public Single<List<Shot>> getShots (int perPage, int page) {
        return mJetrubyService.getShots(Constants.DRIBBBLE_ACCESS_TOKEN, perPage, page);
    }

    public Single<List<Comment>> getComments (int id, int perPage, int page) {
        return mJetrubyService.getComments(id, Constants.DRIBBBLE_ACCESS_TOKEN, perPage, page);
    }

}
