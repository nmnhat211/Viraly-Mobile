package com.example.viralyapplication.utility;


import com.example.viralyapplication.repository.model.newsfeed.PostModel;

import java.util.ArrayList;

public interface AdapterClickListener {
        void onItemClicked(int amount, ArrayList<PostModel> PostModel);
}
