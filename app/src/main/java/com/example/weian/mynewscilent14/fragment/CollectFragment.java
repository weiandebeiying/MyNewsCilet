package com.example.weian.mynewscilent14.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.weian.mynewscilent14.R;
import com.example.weian.mynewscilent14.adapter.CollectAdapter;
import com.example.weian.mynewscilent14.application.MyApplication;
import com.example.weian.mynewscilent14.bean.Collect;

import java.util.List;

import db.MyDBHelper;

/**
 * Created by weian on 2017/7/7.
 */

public class CollectFragment extends Fragment {
    private MyApplication app;
    private List<Collect> collects;
    private CollectAdapter adapter;
    private MyDBHelper helper;
    private ListView listView;
    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.collect_fragment,container,false);
        listView = (ListView) view.findViewById(R.id.collect_listview);
        textView = (TextView) view.findViewById(R.id.collect_textview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        helper = new MyDBHelper(getContext());
        helper.getWritableDatabase();
        app = (MyApplication) getActivity().getApplication();
        collects = app.getCollect();
        if (collects.size()!=0){
            textView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }else {
            textView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        adapter = new CollectAdapter(getContext(),collects);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }
}
