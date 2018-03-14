package com.yl.library.rx;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * @author Yang Shihao
 */
public class RxBus {

    private final FlowableProcessor<Object> mBus;

    private RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getInstance() {
        return Holder.BUS;
    }

    public void send(Object obj) {
        mBus.onNext(obj);
    }

    public <T> Flowable<T> register(Class<T> cls) {
        return mBus.ofType(cls);
    }

    public Flowable<Object> register() {
        return mBus;
    }

    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }

    public void unregisterAll() {
        mBus.onComplete();
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }
}
