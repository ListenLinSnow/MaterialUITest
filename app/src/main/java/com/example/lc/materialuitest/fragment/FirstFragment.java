package com.example.lc.materialuitest.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lc.materialuitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstFragment extends Fragment {

    @BindView(R.id.iv_fragment_first)
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Glide.with(getContext()).load(R.mipmap.xiaoyu).into(imageView);
        Glide.with(getActivity()).load(R.mipmap.xiaoyu).into(imageView);


    }
}
