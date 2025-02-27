package com.otaliastudios.cameraview.picture;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.otaliastudios.cameraview.CameraLogger;
import com.otaliastudios.cameraview.PictureResult;

/**
 * Helps with logging.
 */
public abstract class FullPictureRecorder extends PictureRecorder {
    private static final String TAG = FullPictureRecorder.class.getSimpleName();
    protected static final CameraLogger LOG = CameraLogger.create(TAG);

    public FullPictureRecorder(@NonNull PictureResult.Stub stub,
                               @Nullable PictureResultListener listener) {
        super(stub, listener);
    }
}
