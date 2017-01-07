package com.itwashard.jetrubyviewer.fragments;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.itwashard.jetrubyviewer.CommentAdapter;
import com.itwashard.jetrubyviewer.R;
import com.itwashard.jetrubyviewer.activity.BaseActivity;
import com.itwashard.jetrubyviewer.data.model.Comment;
import com.itwashard.jetrubyviewer.data.model.Shot;
import com.itwashard.jetrubyviewer.userInterface.shot.ShotMvpView;
import com.itwashard.jetrubyviewer.userInterface.shot.ShotPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

public class ShotFragment extends Fragment implements ShotMvpView {

    private static final String ARGUMENT_SHOT = "ARGUMENT_SHOT";

    @Inject
    CommentAdapter mCommentsAdapter;
    @Inject
    ShotPresenter mShotPresenter;

    @Bind(R.id.recycler_comments)
    RecyclerView mCommentsRecycler;
    @Bind(R.id.toolbar_shot)
    Toolbar mToolbar;
    @Bind(R.id.image_shot)
    ImageView mShotImage;
    /*@Bind(R.id.text_title)
    TextView mTitleText;*/

    @Bind(R.id.text_description)
    TextView mDescriptionText;

    @Bind(R.id.text_like_count) TextView mLikeText;
    @Bind(R.id.text_comments_title) TextView mCommentsTitleText;
    @Bind(R.id.progress)
    View mProgress;
    @Bind(R.id.text_error_message) View mErrorText;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;


    public static ShotFragment newInstance(Shot shot) {
        ShotFragment shotFragment = new ShotFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_SHOT, shot);
        shotFragment.setArguments(bundle);
        return shotFragment;
    }

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
        View fragmentView = inflater.inflate(R.layout.fragment_shot, container, false);
        ButterKnife.bind(this, fragmentView);

        Shot shot = getArguments().getParcelable(ARGUMENT_SHOT);

        if (shot == null) {
            throw new IllegalArgumentException("Shotfragment requires a shot instance");
        }

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mShotPresenter.attachView(this);

        mCommentsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCommentsRecycler.setHasFixedSize(true);
        mCommentsRecycler.setAdapter(mCommentsAdapter);

        setupLayout(shot);
        mShotPresenter.getComments(shot.id, ShotPresenter.SHOT_COUNT, ShotPresenter.SHOT_PAGE);

        return fragmentView;
    }

    private void setupLayout(Shot shot) {
        Glide.with(this).load(shot.images.hidpi)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mShotImage);

        if (mCollapsingToolbarLayout != null) {
            mCollapsingToolbarLayout.setTitle(shot.title);
        }

//        mTitleText.setText(shot.title);

        if (shot.description != null) {
            mDescriptionText.setText(Html.fromHtml(shot.description));
        } else {
            mDescriptionText.setText(R.string.text_no_shot_description);
        }

        mLikeText.setText(shot.likes_count);
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showComments(List<Comment> comments) {
        mCommentsAdapter.setComments(comments);
        mCommentsAdapter.notifyDataSetChanged();
        mCommentsRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCommentsTitle(boolean hasComments) {
        mCommentsTitleText.setText(getString(hasComments ?
                R.string.text_recent_comments : R.string.text_no_recent_comments));
        mCommentsTitleText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        mCommentsRecycler.setVisibility(View.GONE);
        mCommentsTitleText.setVisibility(View.GONE);
        mErrorText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyComments() {
        mCommentsTitleText.setVisibility(View.VISIBLE);
        mCommentsRecycler.setVisibility(View.GONE);
    }
}
