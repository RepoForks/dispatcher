package io.github.hendraanggrian.dispatcher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class ActivityRequest extends DispatcherRequest {

    @NonNull private final Intent target;

    @Nullable Dispatcher.OnActivityResult onOK;
    @Nullable Dispatcher.OnActivityResult onCanceled;
    @Nullable Dispatcher.OnActivityResult onAny;

    ActivityRequest(@NonNull Source source, @NonNull Intent target) {
        super(source);
        this.target = target;
    }

    //region Intent methods
    @NonNull
    public ActivityRequest setAction(String action) {
        target.setAction(action);
        return this;
    }

    @NonNull
    public ActivityRequest setData(Uri data) {
        target.setData(data);
        return this;
    }

    @NonNull
    public ActivityRequest putExtra(String name, boolean... value) {
        if (value == null)
            return removeExtra(name);
        else if (value.length == 1)
            target.putExtra(name, value[0]);
        else
            target.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putExtra(String name, byte... value) {
        if (value == null)
            return removeExtra(name);
        else if (value.length == 1)
            target.putExtra(name, value[0]);
        else
            target.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putExtra(String name, char... value) {
        if (value == null)
            return removeExtra(name);
        else if (value.length == 1)
            target.putExtra(name, value[0]);
        else
            target.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putExtra(String name, short... value) {
        if (value == null)
            return removeExtra(name);
        else if (value.length == 1)
            target.putExtra(name, value[0]);
        else
            target.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putExtra(String name, int... value) {
        if (value == null)
            return removeExtra(name);
        else if (value.length == 1)
            target.putExtra(name, value[0]);
        else
            target.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putExtra(String name, long... value) {
        if (value == null)
            return removeExtra(name);
        else if (value.length == 1)
            target.putExtra(name, value[0]);
        else
            target.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putExtra(String name, float... value) {
        if (value == null)
            return removeExtra(name);
        else if (value.length == 1)
            target.putExtra(name, value[0]);
        else
            target.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putExtra(String name, double... value) {
        if (value == null)
            return removeExtra(name);
        else if (value.length == 1)
            target.putExtra(name, value[0]);
        else
            target.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putExtra(String name, String... value) {
        if (value == null)
            return removeExtra(name);
        else if (value.length == 1)
            target.putExtra(name, value[0]);
        else
            target.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putExtra(String name, CharSequence... value) {
        if (value == null)
            return removeExtra(name);
        else if (value.length == 1)
            target.putExtra(name, value[0]);
        else
            target.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putExtra(String name, Parcelable... value) {
        if (value == null)
            return removeExtra(name);
        else if (value.length == 1)
            target.putExtra(name, value[0]);
        else
            target.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putParcelableArrayListExtra(String name, ArrayList<? extends Parcelable> value) {
        target.putParcelableArrayListExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putIntegerArrayListExtra(String name, ArrayList<Integer> value) {
        target.putIntegerArrayListExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putStringArrayListExtra(String name, ArrayList<String> value) {
        target.putStringArrayListExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putCharSequenceArrayListExtra(String name, ArrayList<CharSequence> value) {
        target.putCharSequenceArrayListExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putExtra(String name, Serializable value) {
        target.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putExtra(String name, Bundle value) {
        target.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityRequest putExtras(Intent intent) {
        target.putExtras(intent);
        return this;
    }

    @NonNull
    public ActivityRequest putExtras(Bundle bundle) {
        target.putExtras(bundle);
        return this;
    }

    @NonNull
    public ActivityRequest replaceExtras(Intent intent) {
        target.replaceExtras(intent);
        return this;
    }

    @NonNull
    public ActivityRequest replaceExtras(Bundle bundle) {
        target.replaceExtras(bundle);
        return this;
    }

    @NonNull
    public ActivityRequest removeExtra(String name) {
        target.removeExtra(name);
        return this;
    }
    //endregion

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
        source.startActivityForResult(target, requestCode);
    }
}