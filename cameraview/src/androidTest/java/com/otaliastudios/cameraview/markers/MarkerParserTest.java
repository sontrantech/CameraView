package com.otaliastudios.cameraview.markers;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;


import com.otaliastudios.cameraview.BaseTest;
import com.otaliastudios.cameraview.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class MarkerParserTest extends BaseTest {

    @Test
    public void testNullConstructor() {
        TypedArray array = mock(TypedArray.class);
        when(array.hasValue(R.styleable.CameraView_cameraAutoFocusMarker)).thenReturn(false);
        when(array.getString(R.styleable.CameraView_cameraAutoFocusMarker)).thenReturn(null);
        MarkerParser parser = new MarkerParser(array);
        assertNull(parser.getAutoFocusMarker());
    }

    @Test
    public void testConstructor() {
        TypedArray array = mock(TypedArray.class);
        when(array.hasValue(R.styleable.CameraView_cameraAutoFocusMarker)).thenReturn(true);
        when(array.getString(R.styleable.CameraView_cameraAutoFocusMarker)).thenReturn(Marker.class.getName());
        MarkerParser parser = new MarkerParser(array);
        assertNotNull(parser.getAutoFocusMarker());
        assertTrue(parser.getAutoFocusMarker() instanceof Marker);
    }

    public static class Marker implements AutoFocusMarker {

        public Marker() {
        }

        @Nullable
        @Override
        public View onAttach(@NonNull Context context, @NonNull ViewGroup container) {
            return null;
        }

        @Override
        public void onAutoFocusStart(@NonNull AutoFocusTrigger trigger, @NonNull PointF point) {
        }

        @Override
        public void onAutoFocusEnd(@NonNull AutoFocusTrigger trigger, boolean successful, @NonNull PointF point) {
        }
    }
}
