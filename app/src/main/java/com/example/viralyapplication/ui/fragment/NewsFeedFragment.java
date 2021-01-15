package com.example.viralyapplication.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.viralyapplication.R;
import com.example.viralyapplication.adapter.AdapterNewsFeed;
import com.example.viralyapplication.repository.api.NewsFeedApi;
import com.example.viralyapplication.repository.model.getUserModel;
import com.example.viralyapplication.repository.model.newsfeed.NewsFeedModel;
import com.example.viralyapplication.repository.model.newsfeed.postItemModel;
import com.example.viralyapplication.ui.activity.CreatePostActivity;
import com.example.viralyapplication.utility.Constant;
import com.example.viralyapplication.utility.NetworkProfile;
import com.example.viralyapplication.utility.Utils;
import com.example.viralyapplication.utility.VerticalListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedFragment extends BaseFragment implements AdapterNewsFeed.onClickPostListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String KEY_USER_ID = "key_user_id";
    public static final String KEY_USER_DATA = "key_user_data";
    private String mParam1;
    private String mParam2;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private AdapterNewsFeed mAdapter;
    private VerticalListView mListView;
    private ArrayList<postItemModel> mItemPost;
    private LinearLayout lnCreatePost;
    private int mCurrentPosition = -1;
    private postItemModel mPostItem;
    private Context mContext;

    public NewsFeedFragment() {
    }

    public static NewsFeedFragment newInstance(String param1, String param2) {
        NewsFeedFragment fragment = new NewsFeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_feed, container, false);
        mContext = getActivity();
        initView(view);
        getNewFeed();
        return view;

    }

    private void initView(View view) {
        mListView = view.findViewById(R.id.row_post);
        mItemPost = new ArrayList<>();
        mAdapter = new AdapterNewsFeed(getContext(), mItemPost, this);
        mListView.setAdapter(mAdapter);
        lnCreatePost = view.findViewById(R.id.lv_create_post);
        lnCreatePost.setOnClickListener(this);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorScheme(R.color.color_main);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNewFeed();
            }
        });

    }

    public void getUser() {
        showProgressDialog();
        NewsFeedApi api = NetworkProfile.getRetrofitInstance().create(NewsFeedApi.class);
        Call<getUserModel> call = api.getUser(Utils.getUid());
        call.enqueue(new Callback<getUserModel>() {
            @Override
            public void onResponse(Call<getUserModel> call, Response<getUserModel> response) {
                dismissProgressDialog();
                if (response.code() == Constant.IS_SUCCESS) {
                    if (response.body() != null) {
                    }
                } else {
                    Utils.handleErrorMessages(getContext(), response, R.string.unknown_account);
                }
            }

            @Override
            public void onFailure(Call<getUserModel> call, Throwable t) {
                dismissProgressDialog();
                Utils.showAlertDialogOk(getContext(),
                        getString(R.string.error_txt),
                        t.getMessage(),
                        (dialogInterface, i) -> dialogInterface.dismiss());
                Log.e("onFailure ", "" + t.getMessage());
            }
        });
    }

    public void getNewFeed() {
        showProgressDialog();
        NewsFeedApi api = NetworkProfile.getRetrofitInstance().create(NewsFeedApi.class);
        Call<NewsFeedModel> call = api.getNewsFeed();
        call.enqueue(new Callback<NewsFeedModel>() {
            @Override
            public void onResponse(Call<NewsFeedModel> call, Response<NewsFeedModel> response) {
                dismissProgressDialog();
                if (response.code() == Constant.IS_SUCCESS) {
                    if (response.body().getPosts() != null) {
                        mItemPost.clear();
                        mItemPost.addAll(response.body().getPosts());
                        mAdapter.notifyDataSetChanged();
                        mListView.setSelectionAfterHeaderView();
                    }
                } else {
                    Utils.handleErrorMessages(mContext, response, R.string.server_error);
                }
                refreshCompleted();
            }

            @Override
            public void onFailure(Call<NewsFeedModel> call, Throwable t) {
                dismissProgressDialog();
                refreshCompleted();
                Utils.showAlertDialogOk(getContext(),
                        getString(R.string.error_txt),
                        t.getMessage(),
                        (dialogInterface, i) -> dialogInterface.dismiss());
                Log.e("onFailure ", "" + t.getMessage());
            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.lv_create_post) {
            Intent intent = new Intent(getActivity(), CreatePostActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemPostClickListener(postItemModel itemPost, int position) {
        mCurrentPosition = position;
    }

    @Override
    public void onLikeClickListener(postItemModel itemPost, int position) {
        mCurrentPosition = position;
    }

    @Override
    public void onCommentClickListener(postItemModel itemPost, int position) {
        mCurrentPosition = position;
    }

    private void refreshCompleted() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}