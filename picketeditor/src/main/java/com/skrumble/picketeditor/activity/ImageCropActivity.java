package com.skrumble.picketeditor.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skrumble.picketeditor.PickerEditor;
import com.skrumble.picketeditor.R;
import com.skrumble.picketeditor.activity.GalleryActivity;
import com.skrumble.picketeditor.utility.Utility;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.yalantis.ucrop.UCropFragment;
import com.yalantis.ucrop.UCropFragmentCallback;

import java.io.File;

public class ImageCropActivity extends AppCompatActivity implements UCropFragmentCallback {

    public static final String EXTRA_IMAGE_SRC = "EXTRA_IMAGE_SRC";
    private boolean mShowLoader = false;
    private UCropFragment fragment;
    private Uri originalUri, destinationUri;
    private TextView mCropTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop);

        String imagePath = getIntent().getStringExtra(EXTRA_IMAGE_SRC);

        originalUri = Uri.fromFile(new File(imagePath));

        destinationUri = Uri.fromFile(Utility.createImageInCatchFolder(this));

        UCrop uCrop = UCrop.of(originalUri, destinationUri);

        uCrop = advancedConfig(uCrop);

        fragment = uCrop.getFragment(uCrop.getIntent(this).getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, UCropFragment.TAG).commitAllowingStateLoss();

        setStatusBarColor(Color.BLACK);

        mCropTextView = findViewById(R.id.crop_text_view);
        mProgressBar = findViewById(R.id.crop_progress_bar);
        mProgressBar.setVisibility(View.GONE);

        mCropTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowLoader = true;
                fragment.cropAndSaveImage();
            }
        });
    }

    /**
     * Sometimes you want to adjust more options, it's done via {@link com.yalantis.ucrop.UCrop.Options} class.
     *
     * @param uCrop - ucrop builder instance
     * @return - ucrop builder instance
     */
    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();

        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);

        options.setCompressionQuality(50);

        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);

        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);

        options.setToolbarColor(ContextCompat.getColor(this, R.color.ally_accent_color));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.ally_accent_color));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.ally_accent_color));
        /*
        This sets max size for bitmap that will be decoded from source Uri.
        More size - more memory allocation, default implementation uses screen diagonal.

        options.setMaxBitmapSize(640);
        */

       /*
        Tune everything (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧

        options.setMaxScaleMultiplier(5);
        options.setImageToCropBoundsAnimDuration(666);
        options.setDimmedLayerColor(Color.CYAN);
        options.setCircleDimmedLayer(true);
        options.setShowCropFrame(false);
        options.setCropGridStrokeWidth(20);
        options.setCropGridColor(Color.GREEN);
        options.setCropGridColumnCount(2);
        options.setCropGridRowCount(1);
        options.setToolbarCropDrawable(R.drawable.your_crop_icon);
        options.setToolbarCancelDrawable(R.drawable.your_cancel_icon);

        // Color palette
        options.setToolbarColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setRootViewBackgroundColor(ContextCompat.getColor(this, R.color.your_color_res));

        // Aspect ratio options
        options.setAspectRatioOptions(1,
            new AspectRatio("WOW", 1, 2),
            new AspectRatio("MUCH", 3, 4),
            new AspectRatio("RATIO", CropImageView.DEFAULT_ASPECT_RATIO, CropImageView.DEFAULT_ASPECT_RATIO),
            new AspectRatio("SO", 16, 9),
            new AspectRatio("ASPECT", 1, 1));

       */

        return uCrop.withOptions(options);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = getWindow();
            if (window != null) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
            }
        }
    }

    @Override
    public void loadingProgress(boolean showLoader) {
        if (mShowLoader){
            mProgressBar.setVisibility(View.VISIBLE);
            mCropTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCropFinish(UCropFragment.UCropResult result) {
        Intent intent = new Intent();

        if (GalleryActivity.activity != null) {
            GalleryActivity.activity.finish();
        }

        switch (result.mResultCode) {
            case RESULT_OK:

                Uri output = UCrop.getOutput(result.mResultData);

                if (output != null){
                    intent.putExtra(PickerEditor.RESULT_FILE, output.getPath());
                }else {
                    intent.putExtra(PickerEditor.RESULT_FILE, originalUri.getPath());
                }

                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                intent.putExtra(PickerEditor.RESULT_FILE, originalUri.getPath());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    public void backButtonClickAction(View view) {
        finish();
    }
}