package com.yl.library.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yl.library.R;

import java.util.List;

/**
 * @author Yang Shihao
 * @date 2017/7/26
 */

public class PictureSelectorUtils {

    public static void getImageSingleOption(Activity activity, int requestCode) {
        initImageSelection(getPictureSelector(activity), PictureConfig.SINGLE, null, 1, requestCode);
    }

    public static void getImageSingleOption(Fragment fragment, int requestCode) {
        initImageSelection(getPictureSelector(fragment), PictureConfig.SINGLE, null, 1, requestCode);
    }

    public static void getImageMultipleOption(Activity activity, int max, int requestCode) {
        initImageSelection(getPictureSelector(activity), PictureConfig.MULTIPLE, null, max, requestCode);
    }

    public static void getImageMultipleOption(Fragment fragment, int max, int requestCode) {
        initImageSelection(getPictureSelector(fragment), PictureConfig.MULTIPLE, null, max, requestCode);
    }

    public static void getImageMultipleOption(Activity activity, List<LocalMedia> medias, int max, int requestCode) {
        initImageSelection(getPictureSelector(activity), PictureConfig.MULTIPLE, medias, max, requestCode);
    }

    public static void getImageMultipleOption(Fragment fragment, List<LocalMedia> medias, int max, int requestCode) {
        initImageSelection(getPictureSelector(fragment), PictureConfig.MULTIPLE, medias, max, requestCode);
    }

    public static void getVideoSingleOption(Activity activity, int requestCode) {
        initVideoSelection(getPictureSelector(activity), PictureConfig.SINGLE, requestCode);
    }

    public static void getVideoSingleOption(Fragment fragment, int requestCode) {
        initVideoSelection(getPictureSelector(fragment), PictureConfig.SINGLE, requestCode);
    }

    public static void getImageSingleCropOption(Activity activity, int w, int h, int requestCode) {
        initImageSelection(getPictureSelector(activity), w, h, requestCode);
    }

    public static void getImageSingleCropOption(Fragment fragment, int w, int h, int requestCode) {
        initImageSelection(getPictureSelector(fragment), w, h, requestCode);
    }

    private static void initImageSelection(PictureSelector selector, int selectionMode, List<LocalMedia> medias, int max, int requestCode) {
        selector.openGallery(PictureMimeType.ofImage())
                .theme(R.style.PictureSelector)
                .setOutputCameraPath("/" + FileLocalUtils.FILE_NAME + "/image")
                .maxSelectNum(max)
                .imageSpanCount(4)
                .selectionMode(selectionMode)
                .selectionMedia(medias)
                .previewImage(true)
                .isCamera(true)
                .isZoomAnim(true)
                .sizeMultiplier(0.5f)
                .compress(true)
                .previewEggs(true)
                .forResult(requestCode);
    }

    private static void initVideoSelection(PictureSelector selector, int selectionMode, int requestCode) {
        selector.openGallery(PictureMimeType.ofVideo())
                .theme(R.style.PictureSelector)
                .selectionMode(selectionMode)
                .setOutputCameraPath("/" + FileLocalUtils.FILE_NAME + "/image")
                .imageSpanCount(4)
                .previewVideo(true)
                .isCamera(true)
                .videoQuality(1)
                .compress(true)
                .videoQuality(0)
                .recordVideoSecond(10)
                .forResult(requestCode);
    }

    private static void initImageSelection(PictureSelector selector, int w, int h, int requestCode) {
        selector.openGallery(PictureMimeType.ofImage())
                .theme(R.style.PictureSelector)
                .setOutputCameraPath("/" + FileLocalUtils.FILE_NAME + "/image")
                .maxSelectNum(1)
                .imageSpanCount(4)
                .selectionMode(PictureConfig.SINGLE)
                .selectionMedia(null)
                .previewImage(true)
                .isCamera(true)
                .enableCrop(true)
                .rotateEnabled(true)
                .withAspectRatio(w, h)
                .scaleEnabled(true)
                .showCropFrame(true)
                .freeStyleCropEnabled(false)
                .isZoomAnim(true)
                .sizeMultiplier(0.5f)
                .compress(true)
                .previewEggs(true)
                .forResult(requestCode);
    }

    private static PictureSelector getPictureSelector(Activity activity) {
        return PictureSelector.create(activity);
    }

    private static PictureSelector getPictureSelector(Fragment fragment) {
        return PictureSelector.create(fragment);
    }
}
