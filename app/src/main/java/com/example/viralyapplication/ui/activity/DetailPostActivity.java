package com.example.viralyapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.viralyapplication.R;
import com.example.viralyapplication.repository.model.newsfeed.postItemModel;
import com.example.viralyapplication.utility.Utils;

public class DetailPostActivity extends ToolbarActivity {
    private TextView tvNameUser, tvTimePost, tvContentPost, tvTitleLikeNumber,
            tvTitleCommentNumber;
    private ImageView ivAvatar, ivUserPosted, ivAvatarCommentUser,
            ivLikeAction, ivCommentAction, ivOptional;
    private RelativeLayout rlSentComment;
    private ConstraintLayout mCstRoot;
    public static final String KEY_DATA_POST = "key_data_post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDisplayTitle(true);
        setDisplayBack(true);
        setTitleToolbar(getString(R.string.detail_post_txt));
        Intent intent = getIntent();
        initView(intent);
    }

    private void initView(Intent intent) {
        tvNameUser = findViewById(R.id.tv_name_user_new_feed);
        tvTimePost = findViewById(R.id.tv_time_posted);
        tvContentPost = findViewById(R.id.tv_content_post);

        tvTitleLikeNumber = findViewById(R.id.tv_number_like);
        tvTitleCommentNumber = findViewById(R.id.tv_number_comment);

        ivLikeAction = findViewById(R.id.iv_like_action);
        ivCommentAction = findViewById(R.id.iv_comment_action);
        ivOptional = findViewById(R.id.iv_optional);
        ivAvatar = findViewById(R.id.iv_avatar_user);
        ivUserPosted = findViewById(R.id.iv_image_user_posted);

        ivAvatarCommentUser = findViewById(R.id.iv_avatar_comment_user);
        rlSentComment = findViewById(R.id.rl_send_comment);
        mCstRoot = findViewById(R.id.cnts_root);
        postItemModel postItem = (postItemModel) intent.getSerializableExtra(KEY_DATA_POST);
        initData(postItem);
    }

    private void initData(postItemModel postItem) {
        tvNameUser.setText(postItem.getUser().getDisplayName());
        tvTimePost.setText(Utils.convertDate(postItem.getCreatedAt()));
        tvContentPost.setText(postItem.getCaption());
        tvTitleLikeNumber.setText(postItem.getLikes().size() + 1 + "");
        tvTitleCommentNumber.setText(postItem.getComments().size() + "");
        Utils.loadCircleView(getApplicationContext(), postItem.getUser().getAvatar(), ivAvatar, 5);
        if (postItem.getContent() != null) {
            Utils.loadView(getApplicationContext(), postItem.getContent().get(0).getUrl(), ivUserPosted);
        }
    }
}