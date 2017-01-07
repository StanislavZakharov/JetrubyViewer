package com.itwashard.jetrubyviewer;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.itwashard.jetrubyviewer.data.model.Shot;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

public class BrowseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Shot> mShots;
    private ClickListener mClickListener;

    @Inject
    BrowseAdapter() {
        mShots = Collections.emptyList();
    }

    public void setShots(List<Shot> shots) {
        mShots = shots;
    }

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return mShots.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shot, parent, false);
        return new ShotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ShotViewHolder) {
            Shot shot = mShots.get(position);
            ShotViewHolder viewHolder = (ShotViewHolder) holder;
            viewHolder.mShot = shot;
            viewHolder.likeCountText.setText(shot.likes_count);
            viewHolder.viewCountText.setText(shot.title);

//            added Description for shots
            if (shot.description != null ) {
                viewHolder.viewDescText.setText(Html.fromHtml(shot.description));
            } else {
                viewHolder.viewDescText.setText("");
            }

            Glide.with(holder.itemView.getContext()).load(shot.images.hidpi)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.shotImage);
        } else {
            ((ProgressViewHolder) holder).progress.setIndeterminate(true);
        }
    }

     class ShotViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image_shot)
        ImageView shotImage;
        @Bind(R.id.image_like) ImageView likeCountImage;
        @Bind(R.id.text_like_count)
        TextView likeCountText;
        @Bind(R.id.text_title) TextView viewCountText;
//        added Description for shots
        @Bind(R.id.text_description) TextView viewDescText;
        @Bind(R.id.layout_header) View viewHeader;

        Shot mShot;

        ShotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onShotClick(mShot);
                    }
                }
            });
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.progress)
        ProgressBar progress;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ClickListener {
        void onShotClick(Shot shot);
    }
}
