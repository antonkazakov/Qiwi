package com.antonkazakov.qiwitask.ui.screen;

/**
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public interface BasePresenter<V extends MainView> {

    void onAttach(V view);

    void onDetach();

    void onDestroyed();

}
