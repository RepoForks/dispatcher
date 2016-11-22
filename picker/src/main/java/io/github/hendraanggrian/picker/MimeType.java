package io.github.hendraanggrian.picker;

import android.support.annotation.NonNull;

/**
 * List of supported mime types, taken from http://androidxref.com/4.4.4_r1/xref/frameworks/base/media/java/android/media/MediaFile.java#174.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public enum MimeType {

    AUDIO_ALL("audio/*"),
    AUDIO_MP3("audio/mp3"),
    AUDIO_M4A("audio/mp4"),
    AUDIO_WAV("audio/x-wav"),
    AUDIO_AMR("audio/amr"),
    AUDIO_AWB("audio/amr-wb"),
    AUDIO_WMA("audio/x-ms-wma"),
    AUDIO_OGG("audio/ogg"),
    AUDIO_AAC("audio/aac"),
    AUDIO_MKA("audio/x-matroska"),
    AUDIO_MID("audio/midi"),
    AUDIO_SMF("audio/sp-midi"),
    AUDIO_IMY("audio/imelody"),
    AUDIO_M3U("audio/x-mpegurl"),
    AUDIO_PLS("audio/x-scpls"),
    AUDIO_M3U8("audio/mpegurl"),
    AUDIO_FLAC("audio/flac"),

    IMAGE_ALL("image/*"),
    IMAGE_JPG("image/jpeg"),
    IMAGE_GIF("image/gif"),
    IMAGE_PNG("image/png"),
    IMAGE_BMP("image/x-ms-bmp"),
    IMAGE_WBMP("image/vnd.wap.wbmp"),
    IMAGE_WEBP("image/webp"),

    VIDEO_ALL("video/*"),
    VIDEO_MPG("video/mpeg"),
    VIDEO_MP4("video/mp4"),
    VIDEO_3GP("video/3gpp"),
    VIDEO_3G2("video/3gpp2"),
    VIDEO_MKV("video/x-matroska"),
    VIDEO_WEBM("video/webm"),
    VIDEO_TS("video/mp2ts"),
    VIDEO_AVI("video/avi"),
    VIDEO_WMV("video/x-ms-wmv"),
    VIDEO_ASF("video/x-ms-asf");

    @NonNull final String value;

    MimeType(@NonNull final String value) {
        this.value = value;
    }
}