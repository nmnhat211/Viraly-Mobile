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
import com.example.viralyapplication.repository.model.CommentsModel;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {


    private ArrayList<CommentsModel> commentItem;
    private Context mContext;
    private com.example.viralyapplication.repository.model.newsfeed.ContentModel ContentModel;
    private NewsFeedAdapter.onClickPostListener mListener;


    public CommentAdapter(Context mContext, ArrayList<CommentsModel> commentItem) {
        this.commentItem = commentItem;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        if (commentItem != null)
            return commentItem.size();
        return 0;
    }

    @Override
    public CommentsModel getItem(int position) {
        if (position < commentItem.size() && position > -1)
            return commentItem.get(position);
        return new CommentsModel();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup viewGroup) {

        CommentAdapter.Holder holder = null;
        if (currentView == null) {
            currentView = View.inflate(mContext, R.layout.include_row_dislay_comment_layout, null);
            holder = new CommentAdapter.Holder();
            currentView.setTag(holder);
        } else {
            holder = (CommentAdapter.Holder) currentView.getTag();
        }

        CommentsModel item = getItem(position);
        holder.tvComment = currentView.findViewById(R.id.tv_content_comment);
        holder.tvName = currentView.findViewById(R.id.tv_name_user_new_feed);

        holder.tvName.setText(item.getName());
        holder.tvComment.setText(item.getComment());

        return currentView;
    }


    public class Holder {
        TextView tvComment, tvName;
    }
}
