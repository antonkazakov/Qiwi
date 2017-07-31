package com.antonkazakov.qiwitask.ui.screen;

import android.util.Log;

import com.antonkazakov.qiwitask.data.repo.FakeRepository;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public class MainPresenter implements BasePresenter<MainView> {

    private static final String TAG = MainPresenter.class.getClass().getSimpleName();

    private static FakeRepository fakeRepository = new FakeRepository();
    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private MainView mainView;

    @Override
    public void onAttach(MainView view) {
        mainView = view;
    }

    void create() {
        fakeRepository
                .getContent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(elements -> {
                    if (mainView != null)
                        mainView.onElementsLoaded(elements);
                }, throwable -> Log.e(TAG, "call: ", throwable));
    }

    @Override
    public void onDetach() {
        mainView = null;
    }

    @Override
    public void onDestroyed() {
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.clear();
        }
    }
}
