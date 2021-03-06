package com.skrumble.picketeditor.utility;

import android.net.Uri;
import android.provider.MediaStore;

public class Constants {
    public static final int sScrollbarAnimDuration = 300;
    public static String[] IMAGES_PROJECTION = new String[]{
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.DATE_MODIFIED,
    };
    public static String[] VIDEOS_PROJECTION = new String[]{
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.BUCKET_ID,
            MediaStore.Video.Media.DATE_TAKEN,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DATE_MODIFIED,
    };
    public static String[] IMAGES_AND_VIDEOS_PROJECTION = new String[] {
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.TITLE
    };

//    public static final int TYPE_IMAGE = 1;
//    public static final int TYPE_VIDEO = 2;

    public static Uri IMAGES_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    public static Uri VIDEO_URI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    public static Uri IMAGES_AND_VIDEO_URI = MediaStore.Files.getContentUri("external");
    public static Uri EXTERNAL_URI = MediaStore.Files.getContentUri("external");

    public static String IMAGES_ORDERBY = MediaStore.Images.Media.DATE_TAKEN + " DESC";
    public static String VIDEOS_ORDERBY = MediaStore.Video.Media.DATE_TAKEN + " DESC";
    public static String IMAGES_AND_VIDEOS_ORDERBY = MediaStore.Files.FileColumns.DATE_ADDED + " DESC";

    public static int SPAN_COUNT = 3;

}
