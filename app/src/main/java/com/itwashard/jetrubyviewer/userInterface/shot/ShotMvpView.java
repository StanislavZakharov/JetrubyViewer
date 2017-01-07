package com.itwashard.jetrubyviewer.userInterface.shot;

import com.itwashard.jetrubyviewer.data.model.Comment;
import com.itwashard.jetrubyviewer.userInterface.base.MvpView;

import java.util.List;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

public interface ShotMvpView extends MvpView {

    void showProgress();

    void hideProgress();

    void showComments(List<Comment> comments);

    void showError();

    void showEmptyComments();

    void showCommentsTitle(boolean hasComments);
}
