package com.itwashard.jetrubyviewer.userInterface.browse;

import com.itwashard.jetrubyviewer.data.model.Shot;
import com.itwashard.jetrubyviewer.userInterface.base.MvpView;

import java.util.List;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

public interface BrowseMvpView extends MvpView {

    void showProgress();

    void hideProgress();

    void showShots(List<Shot> shots);

    void showError();

    void showEmpty();

    void showMessageLayout(boolean show);
}
