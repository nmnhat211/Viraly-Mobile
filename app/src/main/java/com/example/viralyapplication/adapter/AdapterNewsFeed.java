package com.example.viralyapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viralyapplication.R;
import com.example.viralyapplication.repository.model.newsfeed.ContentModel;
import com.example.viralyapplication.repository.model.newsfeed.PostModel;
import com.example.viralyapplication.utility.AdapterClickListener;
import com.example.viralyapplication.utility.Utils;

import java.util.ArrayList;
import java.util.List;

public class AdapterNewsFeed extends RecyclerView.Adapter<AdapterNewsFeed.MyViewHolder> {
    private ArrayList<PostModel> mArrayPostModel = new ArrayList<>();
    private Context mContext;
    private ContentModel ContentModel;
    private AdapterClickListener listener;

    public AdapterNewsFeed(Context mContext, ArrayList<PostModel> mArrayPostModel, AdapterClickListener listener) {
        this.mArrayPostModel = mArrayPostModel;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.include_row_newfeed_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PostModel postModel = mArrayPostModel.get(position);
        Utils.loadCircleView(mContext, postModel.getUser().getAvatar(), holder.ivAvatar, 5);
        if (postModel.getContent() != null){
            Utils.loadView(mContext, postModel.getContent().get(0).getUrl(), holder.ivUserPosted);
        }
        holder.tvNameUser.setText(postModel.getUser().getDisplayName());
        holder.tvTimePost.setText(postModel.getCreatedAt());
        holder.tvContentPost.setText(postModel.getCaption());
        holder.tvTitleLikeNumber.setText(postModel.getLikes().size() +"");
        holder.tvTitleCommentNumber.setText(postModel.getComments().size()+"");
    }

    @Override
    public int getItemCount() {
        return mArrayPostModel.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameUser, tvTimePost, tvContentPost, tvTitleLikeNumber,
                tvTitleCommentNumber;
        ImageView ivAvatar, ivUserPosted, ivAvatarCommentUser,
                tvLikeAction, tvCommentAction, edtCommentUser, ivOptional ;
        RelativeLayout rlSentComment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameUser = itemView.findViewById(R.id.tv_name_user_new_feed);
            tvTimePost = itemView.findViewById(R.id.tv_time_posted);
            tvContentPost = itemView.findViewById(R.id.tv_content_post);

            tvTitleLikeNumber = itemView.findViewById(R.id.tv_number_like);
            tvTitleCommentNumber = itemView.findViewById(R.id.tv_number_comment);
            tvLikeAction = itemView.findViewById(R.id.iv_like_action);
            tvCommentAction = itemView.findViewById(R.id.iv_comment_action);

            ivOptional = itemView.findViewById(R.id.iv_optional);
            ivAvatar = itemView.findViewById(R.id.iv_avatar_user);
            ivUserPosted = itemView.findViewById(R.id.iv_image_user_posted);

            ivAvatarCommentUser = itemView.findViewById(R.id.iv_avatar_comment_user);
            edtCommentUser = itemView.findViewById(R.id.edt_status_user);
            rlSentComment = itemView.findViewById(R.id.rl_send_comment);
        }
    }

    public interface onClickPostListener {
        void onItemPostClickListener(PostModel postModel);

        void onLikeClickListener(List<Object> likes);

        void onCommentClickListener(List<Object> comment);
    }
}

