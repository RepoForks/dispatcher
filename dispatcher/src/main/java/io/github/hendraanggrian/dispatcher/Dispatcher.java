package io.github.hendraanggrian.dispatcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

/**
 * Utility class with only static methods to start another Activity and listen to the result with certain callbacks.
 * <pre><code>
 * public class ExampleActivity extends Activity {
 *   {@literal @}Override protected void onCreate(Bundle savedInstanceState) {
 *     super.onCreate(savedInstanceState);
 *     setContentView(R.layout.example_activity);
 *     Dispatcher.startActivityForResult(this, new Intent(this, NextActivity.class), new Dispatcher.OnOK() {
 *       {@literal @}Override
 *       public void onOK(Intent data) {
 *           // do something
 *       }
 *     });
 *   }
 *
 *   {@literal @}Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
 *     super.onActivityResult(requestCode, resultCode, data);
 *     Dispatcher.onActivityResult(requestCode, resultCode, data);
 *   }
 * }
 * </code></pre>
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Dispatcher {

    @Nullable private static DispatcherRequest request;

    private Dispatcher() {
    }

    // [START] of Activity

    /**
     * Start an Activity for result with single callback, ignoring result code canceled and undefined.
     *
     * @param origin origin Activity.
     * @param dest   Intent to start.
     * @param onOK   callback on event that Activity returns result code {@link Activity#RESULT_OK}.
     */
    public static void startActivityForResult(@NonNull Activity origin, @NonNull Intent dest, @NonNull OnOK onOK) {
        startActivityForResult(origin, dest, onOK, null, null);
    }

    /**
     * Start an Activity for result with multiple callbacks, ignoring result code undefined.
     *
     * @param origin     origin Activity.
     * @param dest       Intent to start.
     * @param onOK       callback on event that Activity returns result code {@link Activity#RESULT_OK}.
     * @param onCanceled callback on event that Activity returns result code {@link Activity#RESULT_CANCELED}.
     */
    public static void startActivityForResult(@NonNull Activity origin, @NonNull Intent dest, @NonNull OnOK onOK, @Nullable OnCanceled onCanceled) {
        startActivityForResult(origin, dest, onOK, onCanceled, null);
    }

    /**
     * Start an Activity for result with multiple callbacks.
     *
     * @param origin      origin Activity.
     * @param dest        Intent to start.
     * @param onOK        callback on event that Activity returns result code {@link Activity#RESULT_OK}.
     * @param onCanceled  callback on event that Activity returns result code {@link Activity#RESULT_CANCELED}.
     * @param onUndefined callback on event that Activity returns result code that is not {@link Activity#RESULT_OK} and not {@link Activity#RESULT_CANCELED}.
     */
    public static void startActivityForResult(@NonNull Activity origin, @NonNull Intent dest, @NonNull OnOK onOK, @Nullable OnCanceled onCanceled, @Nullable OnUndefined onUndefined) {
        request = new DispatcherRequest(onOK, onCanceled, onUndefined);
        request.start(origin, dest);
    }

    // [END] of Activity

    // [START] of Fragment

    /**
     * Start an Activity for result with single callback, ignoring result code canceled and undefined.
     *
     * @param origin origin Fragment.
     * @param dest   Intent to start.
     * @param onOK   will be triggered when Activity returns result code {@link Activity#RESULT_OK}.
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public static void startActivityForResult(@NonNull android.app.Fragment origin, @NonNull Intent dest, @NonNull OnOK onOK) {
        startActivityForResult(origin, dest, onOK, null, null);
    }

    /**
     * Start an Activity for result with multiple callbacks, ignoring result code undefined.
     *
     * @param origin     origin Fragment.
     * @param dest       Intent to start.
     * @param onOK       callback on event that Activity returns result code {@link Activity#RESULT_OK}.
     * @param onCanceled callback on event that Activity returns result code {@link Activity#RESULT_CANCELED}.
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public static void startActivityForResult(@NonNull android.app.Fragment origin, @NonNull Intent dest, @NonNull OnOK onOK, @Nullable OnCanceled onCanceled) {
        startActivityForResult(origin, dest, onOK, onCanceled, null);
    }

    /**
     * Start an Activity for result with multiple callbacks.
     *
     * @param origin      origin Fragment.
     * @param dest        Intent to start.
     * @param onOK        callback on event that Activity returns result code {@link Activity#RESULT_OK}.
     * @param onCanceled  callback on event that Activity returns result code {@link Activity#RESULT_CANCELED}.
     * @param onUndefined callback on event that Activity returns result code that is not {@link Activity#RESULT_OK} and not {@link Activity#RESULT_CANCELED}.
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public static void startActivityForResult(@NonNull android.app.Fragment origin, @NonNull Intent dest, @NonNull OnOK onOK, @Nullable OnCanceled onCanceled, @Nullable OnUndefined onUndefined) {
        request = new DispatcherRequest(onOK, onCanceled, onUndefined);
        request.start(origin, dest);
    }

    // [END] of Fragment

    // [START] of support Fragment

    /**
     * Start an Activity for result with single callback, ignoring result code canceled and undefined.
     *
     * @param origin origin support Fragment.
     * @param dest   Intent to start.
     * @param onOK   callback on event that Activity returns result code {@link Activity#RESULT_OK}.
     */
    public static void startActivityForResult(@NonNull android.support.v4.app.Fragment origin, @NonNull Intent dest, @NonNull OnOK onOK) {
        startActivityForResult(origin, dest, onOK, null, null);
    }

    /**
     * Start an Activity for result with multiple callbacks, ignoring result code undefined.
     *
     * @param origin     origin support Fragment.
     * @param dest       Intent to start.
     * @param onOK       callback on event that Activity returns result code {@link Activity#RESULT_OK}.
     * @param onCanceled callback on event that Activity returns result code {@link Activity#RESULT_CANCELED}.
     */
    public static void startActivityForResult(@NonNull android.support.v4.app.Fragment origin, @NonNull Intent dest, @NonNull OnOK onOK, @Nullable OnCanceled onCanceled) {
        startActivityForResult(origin, dest, onOK, onCanceled, null);
    }

    /**
     * Start an Activity for result with multiple callbacks.
     *
     * @param origin      origin support Fragment.
     * @param dest        Intent to start.
     * @param onOK        callback on event that Activity returns result code {@link Activity#RESULT_OK}.
     * @param onCanceled  callback on event that Activity returns result code {@link Activity#RESULT_CANCELED}.
     * @param onUndefined callback on event that Activity returns result code that is not {@link Activity#RESULT_OK} and not {@link Activity#RESULT_CANCELED}.
     */
    public static void startActivityForResult(@NonNull android.support.v4.app.Fragment origin, @NonNull Intent dest, @NonNull OnOK onOK, @Nullable OnCanceled onCanceled, @Nullable OnUndefined onUndefined) {
        request = new DispatcherRequest(onOK, onCanceled, onUndefined);
        request.start(origin, dest);
    }

    // [END] of support Fragment

    /**
     * Finish an Activity with result code and no data.
     *
     * @param activity   Activity to finish.
     * @param resultCode result code to bring to returning Activity.
     */
    public static void finish(@NonNull Activity activity, int resultCode) {
        activity.setResult(resultCode);
        activity.finish();
    }

    /**
     * Finish an Activity with result code and data.
     *
     * @param activity   Activity to finish.
     * @param resultCode result code to bring to returning Activity.
     * @param data       intent returned from Activity that is started for result.
     */
    public static void finish(@NonNull Activity activity, int resultCode, @NonNull Intent data) {
        activity.setResult(resultCode, data);
        activity.finish();
    }

    /**
     * Static function that triggers listeners registered with startActivityForResult.
     *
     * @param requestCode randomly generated in {@link DispatcherRequest}.
     * @param resultCode  determine which listener to trigger.
     * @param data        intent returned from Activity that is started for result.
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (request != null && request.requestCode == requestCode) {
            if (resultCode == Activity.RESULT_OK)
                request.onOK.onOK(data);
            else if (resultCode == Activity.RESULT_CANCELED && request.onCanceled != null)
                request.onCanceled.onCanceled(data);
            else if (request.onUndefined != null)
                request.onUndefined.onUndefined(data, resultCode);
            request = null;
        }
    }

    /**
     * Listener that wish to be notified when the Activity receives Activity result
     * with {@link Activity#RESULT_OK}.
     */
    public interface OnOK {

        /**
         * @param data intent returned from Activity that is started for result.
         */
        void onOK(Intent data);
    }

    /**
     * Listener that wish to be notified when the Activity receives Activity result
     * with {@link Activity#RESULT_CANCELED}.
     */
    public interface OnCanceled {

        /**
         * @param data intent returned from Activity that is started for result.
         */
        void onCanceled(Intent data);
    }

    /**
     * Listener that wish to be notified when the Activity receives Activity result
     * with result code that is not {@link Activity#RESULT_OK} and not {@link Activity#RESULT_CANCELED}.
     */
    public interface OnUndefined {

        /**
         * @param data       intent returned from Activity that is started for result.
         * @param resultCode result code is not {@link Activity#RESULT_OK} and not {@link Activity#RESULT_CANCELED}.
         */
        void onUndefined(Intent data, int resultCode);
    }
}