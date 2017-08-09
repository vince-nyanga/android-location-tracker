package com.scadsoftware.locationtracker.binding;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * @author Vincent
 * @since 2017/08/09
 */

public class BindingAdapters {

    @BindingAdapter("show")
    public static void show(View view, boolean show){
        view.setVisibility(show ? View.VISIBLE: View.GONE);
    }
}
