package com.antonkazakov.qiwitask.data.repo;

import android.support.annotation.NonNull;

import com.antonkazakov.qiwitask.App;
import com.antonkazakov.qiwitask.data.beans.Element;
import com.antonkazakov.qiwitask.data.beans.Response;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import rx.Observable;

/**
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public class FakeRepository {

    public static final String FILE = "response";

    @NonNull
    public Observable<List<Element>> getContent() {
        InputStream in = App.getContext().getResources().openRawResource(
                App.getContext().getResources().getIdentifier(FILE,
                        "raw", App.getContext().getPackageName()));
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        return Observable.unsafeCreate(subscriber -> {
            List<Element> elements = new Gson().fromJson(inputStreamReader, Response.class).getContent().getElements();
            elements.remove(0);
            subscriber.onNext(elements);
        });
    }

}
