package com.otaliastudios.cameraview.filter;


import android.support.annotation.NonNull;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.otaliastudios.cameraview.BaseTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class NoFilterTest extends BaseTest {

    @Test
    public void testGetFragmentShader() {
        NoFilter filter = new NoFilter();
        String defaultFragmentShader = new DummyFilter().createDefaultFragmentShader();
        assertEquals(defaultFragmentShader, filter.getFragmentShader());
    }

    public static class DummyFilter extends BaseFilter {
        @NonNull
        @Override
        public String getFragmentShader() {
            return "whatever";
        }
    }
}
