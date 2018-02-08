package tv.ismar.daisy.player.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.exoplayer2.util.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import tv.ismar.daisy.R;
import tv.ismar.daisy.bean.ClipBean;
import tv.ismar.daisy.player.PlayContract;
import tv.ismar.daisy.player.PlayerActivity;
import tv.ismar.daisy.player.presenter.PlayPresenter;
import tv.ismar.iqiyiplayer.SdkTestActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PlayActivity extends AppCompatActivity implements PlayContract.View {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    private String itemUrl;

    private PlayPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        if (intent == null) {
            throw new NullPointerException();
        } else {
            itemUrl = intent.getStringExtra("item_url");
        }


        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);


        mPresenter = new PlayPresenter(this);
        mPresenter.fetchItem(itemUrl);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void startPlay(ClipBean clipBean) {
        if (!TextUtils.isEmpty(clipBean.getIqiyi_4_0())) {
            String iqiyi = clipBean.getIqiyi_4_0();
            Intent intent = new Intent();
            intent.setClass(this, SdkTestActivity.class);
            intent.putExtra("data", iqiyi);
            startActivity(intent);
        }else {
            UriSample uriSample =
                    new UriSample(
                            "test",
                            false,
                            null,
                            clipBean.getM3u8(),
                            null,
                            null);
            Intent intent = uriSample.buildIntent(this);
            startActivity(intent);
        }
    }

    private static final class SampleGroup {

        public final String title;
        public final List<Sample> samples;

        public SampleGroup(String title) {
            this.title = title;
            this.samples = new ArrayList<>();
        }

    }

    private static final class DrmInfo {
        public final UUID drmSchemeUuid;
        public final String drmLicenseUrl;
        public final String[] drmKeyRequestProperties;
        public final boolean drmMultiSession;

        public DrmInfo(UUID drmSchemeUuid, String drmLicenseUrl,
                       String[] drmKeyRequestProperties, boolean drmMultiSession) {
            this.drmSchemeUuid = drmSchemeUuid;
            this.drmLicenseUrl = drmLicenseUrl;
            this.drmKeyRequestProperties = drmKeyRequestProperties;
            this.drmMultiSession = drmMultiSession;
        }

        public void updateIntent(Intent intent) {
            Assertions.checkNotNull(intent);
            intent.putExtra(PlayerActivity.DRM_SCHEME_EXTRA, drmSchemeUuid.toString());
            intent.putExtra(PlayerActivity.DRM_LICENSE_URL, drmLicenseUrl);
            intent.putExtra(PlayerActivity.DRM_KEY_REQUEST_PROPERTIES, drmKeyRequestProperties);
            intent.putExtra(PlayerActivity.DRM_MULTI_SESSION, drmMultiSession);
        }
    }

    private abstract static class Sample {
        public final String name;
        public final boolean preferExtensionDecoders;
        public final DrmInfo drmInfo;

        public Sample(String name, boolean preferExtensionDecoders, DrmInfo drmInfo) {
            this.name = name;
            this.preferExtensionDecoders = preferExtensionDecoders;
            this.drmInfo = drmInfo;
        }

        public Intent buildIntent(Context context) {
            Intent intent = new Intent(context, PlayerActivity.class);
            intent.putExtra(PlayerActivity.PREFER_EXTENSION_DECODERS, preferExtensionDecoders);
            if (drmInfo != null) {
                drmInfo.updateIntent(intent);
            }

            return intent;
        }

    }

    private static final class UriSample extends Sample {

        public final String uri;
        public final String extension;
        public final String adTagUri;

        public UriSample(String name, boolean preferExtensionDecoders, DrmInfo drmInfo, String uri,
                         String extension, String adTagUri) {
            super(name, preferExtensionDecoders, drmInfo);
            this.uri = uri;
            this.extension = extension;
            this.adTagUri = adTagUri;
        }

        @Override
        public Intent buildIntent(Context context) {
            return super.buildIntent(context)
                    .setData(Uri.parse(uri))
                    .putExtra(PlayerActivity.EXTENSION_EXTRA, extension)
                    .putExtra(PlayerActivity.AD_TAG_URI_EXTRA, adTagUri)
                    .setAction(PlayerActivity.ACTION_VIEW);
        }

    }

    private static final class PlaylistSample extends Sample {

        public final UriSample[] children;

        public PlaylistSample(String name, boolean preferExtensionDecoders, DrmInfo drmInfo,
                              UriSample... children) {
            super(name, preferExtensionDecoders, drmInfo);
            this.children = children;
        }

        @Override
        public Intent buildIntent(Context context) {
            String[] uris = new String[children.length];
            String[] extensions = new String[children.length];
            for (int i = 0; i < children.length; i++) {
                uris[i] = children[i].uri;
                extensions[i] = children[i].extension;
            }
            return super.buildIntent(context)
                    .putExtra(PlayerActivity.URI_LIST_EXTRA, uris)
                    .putExtra(PlayerActivity.EXTENSION_LIST_EXTRA, extensions)
                    .setAction(PlayerActivity.ACTION_VIEW_LIST);
        }

    }


}
