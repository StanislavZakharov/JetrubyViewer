package com.itwashard.jetrubyviewer;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itwashard.jetrubyviewer.data.model.Comment;
import com.itwashard.jetrubyviewer.util.CustomTabUtil;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> mComments;
    private ClickListener mClickListener;

    @Inject
    CommentAdapter() {
        mComments = Collections.emptyList();
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        holder.mComment = comment;
        holder.userNameText.setText(comment.user.username);
        holder.commentText.setText(Html.fromHtml(comment.body));
        Glide.with(holder.itemView.getContext())
                .load(comment.user.avatarUrl).into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image_avatar)
        ImageView userImage;
        @Bind(R.id.text_user_name)
        TextView userNameText;
        @Bind(R.id.text_time) TextView timeText;
        @Bind(R.id.text_comment) TextView commentText;

        Comment mComment;

        CommentViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.onCommentClick(mComment);
                }
            });
            commentText.setMovementMethod(new TextViewLinkHandler() {
                @Override
                public void onLinkClick(String url) {
                    CustomTabUtil.open(itemView.getContext(), Uri.parse(url));
                }
            });

            userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomTabUtil.open(itemView.getContext(), Uri.parse(mComment.user.htmlUrl));
                }
            });
        }
    }

    interface ClickListener {
        void onCommentClick(Comment comment);
    }
}
