package com.itwashard.jetrubyviewer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itwashard.jetrubyviewer.BrowseAdapter;
import com.itwashard.jetrubyviewer.DisplayMetricsUtil;
import com.itwashard.jetrubyviewer.R;
import com.itwashard.jetrubyviewer.activity.BaseActivity;
import com.itwashard.jetrubyviewer.activity.ShotActivity;
import com.itwashard.jetrubyviewer.data.model.Shot;
import com.itwashard.jetrubyviewer.userInterface.browse.BrowseMvpView;
import com.itwashard.jetrubyviewer.userInterface.browse.BrowsePresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

public class BrowseFragment extends Fragment implements BrowseMvpView,
        BrowseAdapter.ClickListener {

    @Inject
    BrowseAdapter mBrowseAdapter;
    @Inject
    BrowsePresenter mBrowsePresenter;

    @Bind(R.id.button_message)
    Button mMessageButton;
    @Bind(R.id.image_message)
    ImageView mMessageImage;
    @Bind(R.id.progress)
    ProgressBar mRecyclerProgress;
    @Bind(R.id.recycler_shots)
    RecyclerView mShotRecycler;
    @Bind(R.id.swipe_to_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.text_message)
    TextView mMessageText;
    @Bind(R.id.toolbar_browse)
    Toolbar mToolbar;
    @Bind(R.id.layout_message)
    View mMessageLayout;

//    FAB here
/*    @Bind(R.id.fab)
    FloatingActionButton mFloatingActionButton;*/

    private boolean mIsTabletLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((BaseActivity) getActivity()).activityComponent().inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_browse, container, false);
        ButterKnife.bind(this, fragmentView);
        mBrowsePresenter.attachView(this);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mIsTabletLayout = DisplayMetricsUtil.isScreenW(600);

        setupViews();
        mBrowsePresenter.getShots(BrowsePresenter.SHOT_COUNT, BrowsePresenter.SHOT_PAGE);

//        FAB initializing
//        setFloatingActionButton();

        return fragmentView;
    }

    private void setupViews() {
        mBrowseAdapter.setClickListener(this);

        mShotRecycler.setLayoutManager(setLayoutManager());
        mShotRecycler.setHasFixedSize(true);
        mShotRecycler.setAdapter(mBrowseAdapter);

        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBrowsePresenter.getShots(BrowsePresenter.SHOT_COUNT, BrowsePresenter.SHOT_PAGE);
            }
        });
    }

    private RecyclerView.LayoutManager setLayoutManager() {

        RecyclerView.LayoutManager layoutManager;
        if (!mIsTabletLayout) {
            layoutManager = new LinearLayoutManager(getActivity());
        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 6);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return 3;
                }
            });
            layoutManager = gridLayoutManager;
        }
        return layoutManager;
    }

    @OnClick(R.id.button_message)
    public void onReloadButtonClick() {
        mBrowsePresenter.getShots(BrowsePresenter.SHOT_COUNT, BrowsePresenter.SHOT_PAGE);
    }

    @Override
    public void onShotClick(Shot shot) {
        Intent intent = ShotActivity.getStartIntent(getActivity(), shot);
        startActivity(intent);
    }

    /** Browse MVP View method implementation **/

    @Override
    public void showProgress() {
        if (mShotRecycler.getVisibility() == View.VISIBLE && mBrowseAdapter.getItemCount() > 0) {
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mRecyclerProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerProgress.setVisibility(View.GONE);
    }

    @Override
    public void showShots(final List<Shot> shots) {
        mBrowseAdapter.setShots(shots);
        mBrowseAdapter.notifyDataSetChanged();
        mShotRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        mShotRecycler.setVisibility(View.GONE);
        mMessageImage.setImageResource(R.drawable.ic_no_wifi);
        mMessageText.setText(getString(R.string.text_error_loading_shots));
        mMessageButton.setText(getString(R.string.text_reload));
        showMessageLayout(true);
    }

    @Override
    public void showEmpty() {
        mShotRecycler.setVisibility(View.GONE);
        mMessageImage.setImageResource(R.drawable.ic_no_shots);
        mMessageText.setText(getString(R.string.text_no_shots));
        mMessageButton.setText(getString(R.string.text_check_again));
        showMessageLayout(true);
    }

    @Override
    public void showMessageLayout(boolean show) {
        mMessageLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

     /*FAB method if want to show FAB on Browse Fragment and return top of the screen
     * Also see fragment_browse.xml*/


/*    public void setFloatingActionButton() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShotRecycler.smoothScrollToPosition(0);
                LinearLayoutManager llm = (LinearLayoutManager) mShotRecycler.getLayoutManager();
                llm.scrollToPosition(0);
            }
        });
    }*/



}