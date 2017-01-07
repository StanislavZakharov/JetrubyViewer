package com.itwashard.jetrubyviewer.userInterface.base;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
