package io.github.hendraanggrian.contentpicker;

import android.support.annotation.NonNull;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class MimeType {

    @NonNull public static final String ALL = "*/*";

    @NonNull public static final String AUDIO_ALL = "audio/*";
    @NonNull public static final String AUDIO_MP3 = "audio/mp3";
    @NonNull public static final String AUDIO_M4A = "audio/mp4";
    @NonNull public static final String AUDIO_WAV = "audio/x-wav";
    @NonNull public static final String AUDIO_AMR = "audio/amr";
    @NonNull public static final String AUDIO_AWB = "audio/amr-wb";
    @NonNull public static final String AUDIO_WMA = "audio/x-ms-wma";
    @NonNull public static final String AUDIO_OGG = "audio/ogg";
    @NonNull public static final String AUDIO_AAC = "audio/aac";
    @NonNull public static final String AUDIO_MKA = "audio/x-matroska";
    @NonNull public static final String AUDIO_MID = "audio/midi";
    @NonNull public static final String AUDIO_SMF = "audio/sp-midi";
    @NonNull public static final String AUDIO_IMY = "audio/imelody";
    @NonNull public static final String AUDIO_M3U = "audio/x-mpegurl";
    @NonNull public static final String AUDIO_PLS = "audio/x-scpls";
    @NonNull public static final String AUDIO_M3U8 = "audio/mpegurl";
    @NonNull public static final String AUDIO_FLAC = "audio/flac";

    @NonNull public static final String IMAGE_ALL = "image/*";
    @NonNull public static final String IMAGE_JPG = "image/jpeg";
    @NonNull public static final String IMAGE_GIF = "image/gif";
    @NonNull public static final String IMAGE_PNG = "image/png";
    @NonNull public static final String IMAGE_BMP = "image/x-ms-bmp";
    @NonNull public static final String IMAGE_WBMP = "image/vnd.wap.wbmp";
    @NonNull public static final String IMAGE_WEBP = "image/webp";

    @NonNull public static final String TEXT_ALL = "text/*";
    @NonNull public static final String TEXT_PLAIN = "text/plain";
    @NonNull public static final String TEXT_HTML = "text/html";

    @NonNull public static final String VIDEO_ALL = "video/*";
    @NonNull public static final String VIDEO_MPG = "video/mpeg";
    @NonNull public static final String VIDEO_MP4 = "video/mp4";
    @NonNull public static final String VIDEO_3GP = "video/3gpp";
    @NonNull public static final String VIDEO_3G2 = "video/3gpp2";
    @NonNull public static final String VIDEO_MKV = "video/x-matroska";
    @NonNull public static final String VIDEO_WEBM = "video/webm";
    @NonNull public static final String VIDEO_TS = "video/mp2ts";
    @NonNull public static final String VIDEO_AVI = "video/avi";
    @NonNull public static final String VIDEO_WMV = "video/x-ms-wmv";
    @NonNull public static final String VIDEO_ASF = "video/x-ms-asf";

    @NonNull public static final String PDF = "application/pdf";
    @NonNull public static final String DOC = "application/msword";
    @NonNull public static final String XLS = "application/vnd.ms-excel";
    @NonNull public static final String PPT = "application/mspowerpoint";
    @NonNull public static final String OGG = "application/ogg";
    @NonNull public static final String ZIP = "application/zip";
}