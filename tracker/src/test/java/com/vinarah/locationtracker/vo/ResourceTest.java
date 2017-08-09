package com.vinarah.locationtracker.vo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * @author Vincent
 * @since 2017/08/08
 */
@RunWith(JUnit4.class)
public class ResourceTest {
    @Test
    public void success() throws Exception {
        final String successMessage = "Success";
        final Resource<String> resource = Resource.success(successMessage);
        assertEquals(Status.SUCCESS, resource.status);
        assertNull(resource.message);
        assertEquals(successMessage, resource.value);
    }

    @Test
    public void loading() throws Exception {
        final Resource<String> resource = Resource.loading();
        assertEquals(Status.LOADING, resource.status);
        assertNull(resource.value);
        assertNull(resource.message);
    }

    @Test
    public void error() throws Exception {
        final String errorMessage = "Error";
        final Resource<String> resource = Resource.error(errorMessage);
        assertNull(resource.value);
        assertEquals(Status.ERROR, resource.status);
        assertEquals(errorMessage, resource.message);
    }

}