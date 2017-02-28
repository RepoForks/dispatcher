package io.github.hendraanggrian.dispatcher;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class ActivityRequest extends DispatcherRequest {

    @NonNull private final Intent destination;

    @Nullable Dispatcher.OnActivityResult onOK;
    @Nullable Dispatcher.OnActivityResult onCanceled;
    @Nullable Dispatcher.OnActivityResult onAny;

    ActivityRequest(@NonNull Source source, @NonNull Intent destination) {
        super(source);
        this.destination = destination;
    }

    @NonNull
    public ActivityRequest onOK(@NonNull Dispatcher.OnActivityResult onOK) {
        this.onOK = onOK;
        return this;
    }

    @NonNull
    public ActivityRequest onCanceled(@NonNull Dispatcher.OnActivityResult onCanceled) {
        this.onCanceled = onCanceled;
        return this;
    }

    @NonNull
    public ActivityRequest onAny(@NonNull Dispatcher.OnActivityResult onAny) {
        this.onAny = onAny;
        return this;
    }

    @Override
    public void dispatch() {
        Dispatcher.queueRequest(this);
        source.startActivityForResult(destination, requestCode);
    }
}