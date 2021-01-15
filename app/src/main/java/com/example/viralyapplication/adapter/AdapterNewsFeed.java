package com.example.viralyapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.viralyapplication.R;
import com.example.viralyapplication.repository.model.newsfeed.ContentModel;
import com.example.viralyapplication.repository.model.newsfeed.postItemModel;
import com.example.viralyapplication.utility.Utils;

import java.util.ArrayList;

public class AdapterNewsFeed extends BaseAdapter {
    private ArrayList<postItemModel> postItem;
    private Context mContext;
    private ContentModel ContentModel;
    private onClickPostListener mListener;



    public AdapterNewsFeed(Context mContext, ArrayList<postItemModel> itemPost, onClickPostListener listener) {
        this.postItem = itemPost;
        this.mContext = mContext;
        this.mListener = listener;
    }

    public void setCaptionPost(int position, String caption) {
        postItem.get(position).setCaption(caption);
        notifyDataSetChanged();
    }

    public void setStatusItem(int position, String url) {
        for (ContentModel model : postItem.get(position).getContent()) {
            model.setUrl(url);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if (postItem != null)
            return postItem.size();
        return 0;
    }

    @Override
    public postItemModel getItem(int position) {
        if (position < postItem.size() && position > -1)
            return postItem.get(position);
        return new postItemModel();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup parent) {
        Holder holder = null;
        if (currentView == null) {
            currentView = View.inflate(mContext, R.layout.include_row_newfeed_layout, null);
            holder = new AdapterNewsFeed.Holder();
            currentView.setTag(holder);
        } else {
            holder = (Holder) currentView.getTag();
        }

        postItemModel postItem = getItem(position);

        holder.tvNameUser = currentView.findViewById(R.id.tv_name_user_new_feed);
        holder.tvTimePost = currentView.findViewById(R.id.tv_time_posted);
        holder.tvContentPost = currentView.findViewById(R.id.tv_content_post);

        holder.tvTitleLikeNumber = currentView.findViewById(R.id.tv_number_like);
        holder.tvTitleCommentNumber = currentView.findViewById(R.id.tv_number_comment);

        holder.ivLikeAction = currentView.findViewById(R.id.iv_like_action);
        holder.ivCommentAction = currentView.findViewById(R.id.iv_comment_action);
        holder.ivOptional = currentView.findViewById(R.id.iv_optional);
        holder.ivAvatar = currentView.findViewById(R.id.iv_avatar_user);
        holder.ivUserPosted = currentView.findViewById(R.id.iv_image_user_posted);

        holder.ivAvatarCommentUser = currentView.findViewById(R.id.iv_avatar_comment_user);
        holder.edtCommentUser = currentView.findViewById(R.id.edt_status_user);
        holder.rlSentComment = currentView.findViewById(R.id.rl_send_comment);
        holder.mCstRoot = currentView.findViewById(R.id.cnts_root);


        holder.tvNameUser.setText(postItem.getUser().getDisplayName());
        holder.tvTimePost.setText(postItem.getCreatedAt());
        holder.tvContentPost.setText(postItem.getCaption());
        holder.tvTitleLikeNumber.setText(postItem.getLikes().size() + "");
        holder.tvTitleCommentNumber.setText(postItem.getComments().size() + "");

        holder.mCstRoot.setOnClickListener(v -> {
            mListener.onItemPostClickListener(postItem, position);
        });


        Utils.loadCircleView(mContext, postItem.getUser().getAvatar(), holder.ivAvatar, 5);
        if (postItem.getContent() != null) {
            Utils.loadView(mContext, postItem.getContent().get(0).getUrl(), holder.ivUserPosted);
        }

        return currentView;
    }


    public class Holder {
        TextView tvNameUser, tvTimePost, tvContentPost, tvTitleLikeNumber,
                tvTitleCommentNumber;
        ImageView ivAvatar, ivUserPosted, ivAvatarCommentUser,
                ivLikeAction, ivCommentAction, edtCommentUser, ivOptional;
        RelativeLayout rlSentComment;
        ConstraintLayout mCstRoot;

    }

    public interface onClickPostListener {
        void onItemPostClickListener(postItemModel itemPost, int position);

        void onLikeClickListener(postItemModel itemPost, int position);

        void onCommentClickListener(postItemModel itemPost, int position);
    }
}

