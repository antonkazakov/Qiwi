package com.antonkazakov.qiwitask.ui.screen;

import com.antonkazakov.qiwitask.data.beans.Element;

import java.util.List;

/**
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public interface MainView {

    void onElementsLoaded(List<Element> elements);

}
