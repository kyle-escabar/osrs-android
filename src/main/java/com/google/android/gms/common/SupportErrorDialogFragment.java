package com.google.android.gms.common;

import android.app.Dialog;
import android.content.DialogInterface$OnCancelListener;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.gms.common.internal.Preconditions;

public class SupportErrorDialogFragment extends DialogFragment {
    private Dialog mDialog;
    private DialogInterface$OnCancelListener zaan;

    public SupportErrorDialogFragment() {
        super();
        this.mDialog = null;
        this.zaan = null;
    }

    public static SupportErrorDialogFragment newInstance(Dialog arg2, DialogInterface$OnCancelListener arg3) {
        SupportErrorDialogFragment v0 = new SupportErrorDialogFragment();
        Object v2 = Preconditions.checkNotNull(arg2, "Cannot display null dialog");
        ((Dialog)v2).setOnCancelListener(null);
        ((Dialog)v2).setOnDismissListener(null);
        v0.mDialog = ((Dialog)v2);
        if(arg3 != null) {
            v0.zaan = arg3;
        }

        return v0;
    }

    public static SupportErrorDialogFragment newInstance(Dialog arg1) {
        return SupportErrorDialogFragment.newInstance(arg1, null);
    }

    public void onCancel(DialogInterface arg2) {
        if(this.zaan != null) {
            this.zaan.onCancel(arg2);
        }
    }

    public Dialog onCreateDialog(Bundle arg1) {
        if(this.mDialog == null) {
            this.setShowsDialog(false);
        }

        return this.mDialog;
    }

    public void show(FragmentManager arg1, String arg2) {
        super.show(arg1, arg2);
    }
}

