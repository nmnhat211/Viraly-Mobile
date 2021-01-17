package com.example.viralyapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.viralyapplication.R;
import com.example.viralyapplication.adapter.CommentAdapter;
import com.example.viralyapplication.repository.model.CommentsModel;
import com.example.viralyapplication.repository.model.newsfeed.postItemModel;
import com.example.viralyapplication.utility.Utils;

import java.util.ArrayList;

public class DetailPostActivity extends ToolbarActivity{
    private TextView tvNameUser, tvTimePost, tvContentPost, tvTitleLikeNumber,
            tvTitleCommentNumber;
    private EditText edtComment;
    private ImageView ivAvatar, ivUserPosted, ivAvatarCommentUser, ivSent,
            ivLikeAction, ivCommentAction, ivOptional;
    private RelativeLayout rlSentComment;
    private ConstraintLayout mCstRoot;
    private CommentAdapter mAdapter;
    private ArrayList<CommentsModel> mItemComment;
    private CommentsModel mItem;
    private ListView mLitView;
    public static final String KEY_DATA_POST = "key_data_post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        Intent intent = getIntent();
        initView(intent);
    }

    private void initView(Intent intent) {
        setDisplayTitle(true);
        setDisplayBack(true);
        setDisplayHome(true);
        setTitleToolbar(getString(R.string.detail_post_txt));
        tvNameUser = findViewById(R.id.tv_name_user_new_feed);
        tvTimePost = findViewById(R.id.tv_time_posted);
        tvContentPost = findViewById(R.id.tv_content_post);
        edtComment = findViewById(R.id.edt_comment_user);

        tvTitleLikeNumber = findViewById(R.id.tv_number_like);
        tvTitleCommentNumber = findViewById(R.id.tv_number_comment);

        ivSent = findViewById(R.id.iv_send);
        ivLikeAction = findViewById(R.id.iv_like_action);
        ivCommentAction = findViewById(R.id.iv_comment_action);
        ivOptional = findViewById(R.id.iv_optional);
        ivAvatar = findViewById(R.id.iv_avatar_user);
        ivUserPosted = findViewById(R.id.iv_image_user_posted);
        mItemComment = new ArrayList<>();
//        mAdapter = new CommentAdapter(getApplicationContext(), mItemComment);
//        mLitView.setAdapter(mAdapter);
//        mLitView = findViewById(R.id.lv_comment);


        ivAvatarCommentUser = findViewById(R.id.iv_avatar_comment_user);
        rlSentComment = findViewById(R.id.rl_send_comment);
        mCstRoot = findViewById(R.id.cnts_root);

        ivSent.setOnClickListener(this);
        postItemModel postItem = (postItemModel) intent.getSerializableExtra(KEY_DATA_POST);
        initData(postItem);
    }

    private void initData(postItemModel postItem) {
        tvNameUser.setText(postItem.getUser().getDisplayName());
        tvTimePost.setText(Utils.convertDate(postItem.getCreatedAt()));
        tvContentPost.setText(postItem.getCaption());
        tvTitleLikeNumber.setText(postItem.getLikes().size() + 1 + "");
        tvTitleCommentNumber.setText(postItem.getComments().size() + 1+"");
        Utils.loadCircleView(getApplicationContext(), postItem.getUser().getAvatar(), ivAvatar, 5);
        if (postItem.getContent() != null) {
            Utils.loadView(getApplicationContext(), postItem.getContent().get(0).getUrl(), ivUserPosted);
        }

    }

    private void sentComment(){
        String name = "nmnhat211";
        String comment = edtComment.getText().toString().trim();
        mItem = new CommentsModel(name, comment);
        mItemComment.add(mItem);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_send){
            sentComment();
        }
        super.onClick(view);
    }
}