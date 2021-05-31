package com.jina.wishbook.Camera;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.jina.wishbook.R;

public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_progress_dialog);
    }
}
