package com.yl.library.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Yang Shihao
 */

public class RxSchedulers {

    /*public static <T> FlowableTransformer<T, T> io_main() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }*/

    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
