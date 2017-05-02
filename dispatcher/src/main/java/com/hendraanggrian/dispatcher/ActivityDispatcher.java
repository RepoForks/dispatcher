package com.hendraanggrian.dispatcher;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class ActivityDispatcher extends Dispatcher {

    @NonNull private final Intent target;

    @Nullable OnOK onOK;
    @Nullable OnCanceled onCanceled;
    @Nullable OnAny onAny;

    ActivityDispatcher(@NonNull Source source, int requestCode, @NonNull Intent target) {
        super(source, requestCode);
        this.target = target;
    }

    @Override
    public String toString() {
        return requestCode + " - " + target.toString();
    }

    public void dispatch(@Nullable OnOK onOK) {
        dispatch(onOK, null, null);
    }

    public void dispatch(@Nullable OnOK onOK, @Nullable OnCanceled onCanceled) {
        dispatch(onOK, onCanceled, null);
    }

    public void dispatch(@Nullable OnAny onAny) {
        dispatch(null, null, onAny);
    }

    public void dispatch(@Nullable OnOK onOK, @Nullable OnCanceled onCanceled, @Nullable OnAny onAny) {
        this.onOK = onOK;
        this.onCanceled = onCanceled;
        this.onAny = onAny;
        Dispatcher.queueRequest(this);
        source.startActivityForResult(target, requestCode);
    }

    public interface OnOK {
        void onOK(Intent data);
    }

    public interface OnCanceled {
        void onCanceled(Intent data);
    }

    public interface OnAny {
        void onAny(int requestCode, int resultCode, Intent data);
    }
}