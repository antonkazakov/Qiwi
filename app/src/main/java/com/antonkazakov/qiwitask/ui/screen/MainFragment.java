package com.antonkazakov.qiwitask.ui.screen;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antonkazakov.qiwitask.R;
import com.antonkazakov.qiwitask.data.beans.Element;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements MainView {

    private static final String TAG = MainFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private WidgetGeneratorAdapter widgetGeneratorAdapter;
    private MainPresenter mainPresenter;
    private static Bundle recyclerViewState;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        widgetGeneratorAdapter = new WidgetGeneratorAdapter();
        mainPresenter = new MainPresenter();
        mainPresenter.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(widgetGeneratorAdapter);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        recyclerViewState = new Bundle();
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        recyclerViewState.putParcelable(TAG, listState);
        mainPresenter.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (recyclerViewState != null) {
            Parcelable listState = recyclerViewState.getParcelable(TAG);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
        mainPresenter.onAttach(this);
    }

    @Override
    public void onElementsLoaded(List<Element> elements) {
        widgetGeneratorAdapter.insertStartItems(elements);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainPresenter.onDestroyed();
        widgetGeneratorAdapter.clearSubscriptions();
    }

}
