package com.otaliastudios.cameraview.engine;

import android.support.annotation.NonNull;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.otaliastudios.cameraview.controls.Engine;

import org.junit.runner.RunWith;

/**
 * These tests work great on real devices, and are the only way to test actual CameraEngine
 * implementation - we really need to open the camera device.
 * Unfortunately they fail unreliably on emulated devices, due to some bug with the
 * emulated camera controller.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
// @RequiresDevice
public class Camera1IntegrationTest extends CameraIntegrationTest<Camera1Engine> {

    @NonNull
    @Override
    protected Engine getEngine() {
        return Engine.CAMERA1;
    }

    @Override
    protected long getMeteringTimeoutMillis() {
        return Camera1Engine.AUTOFOCUS_END_DELAY_MILLIS;
    }

    @Override
    public void testFrameProcessing_maxSize() {
        // Camera1Engine does not support different sizes.
        // super.testFrameProcessing_maxSize();
    }
}
