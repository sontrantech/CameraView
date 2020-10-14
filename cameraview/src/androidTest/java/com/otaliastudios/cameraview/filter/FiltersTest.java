package com.otaliastudios.cameraview.filter;


import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.otaliastudios.cameraview.BaseTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class FiltersTest extends BaseTest {

    @Test
    public void testNewInstance() {
        // At least tests that all our default filters have a no-args constructor.
        Filters[] filtersArray = Filters.values();
        for (Filters filters : filtersArray) {
            Filter filter = filters.newInstance();
            assertNotNull(filter);
        }
    }
}
