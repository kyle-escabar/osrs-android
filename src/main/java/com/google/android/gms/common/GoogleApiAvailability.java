package com.google.android.gms.common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog$Builder;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface$OnCancelListener;
import android.content.DialogInterface$OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.GuardedBy;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.NotificationCompat$BigTextStyle;
import androidx.core.app.NotificationCompat$Builder;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.base.R$drawable;
import com.google.android.gms.base.R$string;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zabq;
import com.google.android.gms.common.api.internal.zabr;
import com.google.android.gms.common.api.internal.zabu;
import com.google.android.gms.common.internal.ConnectionErrorMessages;
import com.google.android.gms.common.internal.DialogRedirect;
import com.google.android.gms.common.internal.HideFirstParty;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.internal.base.zal;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoogleApiAvailability extends GoogleApiAvailabilityLight {
    @SuppressLint(value={"HandlerLeak"}) final class zaa extends zal {
        private final Context zaaq;

        public zaa(GoogleApiAvailability arg1, Context arg2) {
            this.zaar = arg1;
            Looper v1 = Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper();
            super(v1);
            this.zaaq = arg2.getApplicationContext();
        }

        public final void handleMessage(Message arg4) {
            int v4;
            if(arg4.what != 1) {
                v4 = arg4.what;
                StringBuilder v2 = new StringBuilder(50);
                v2.append("Don\'t know how to handle this message: ");
                v2.append(v4);
                Log.w("GoogleApiAvailability", v2.toString());
            }
            else {
                v4 = this.zaar.isGooglePlayServicesAvailable(this.zaaq);
                if(this.zaar.isUserResolvableError(v4)) {
                    this.zaar.showErrorNotification(this.zaaq, v4);
                    return;
                }
            }
        }
    }

    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final Object mLock;
    private static final GoogleApiAvailability zaao;
    @GuardedBy(value="mLock") private String zaap;

    static {
        GoogleApiAvailability.mLock = new Object();
        GoogleApiAvailability.zaao = new GoogleApiAvailability();
        GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    GoogleApiAvailability() {
        super();
    }

    public Task checkApiAvailability(GoogleApi arg5, GoogleApi[] arg6) {
        Preconditions.checkNotNull(arg5, "Requested API must not be null.");
        int v0 = arg6.length;
        int v1;
        for(v1 = 0; v1 < v0; ++v1) {
            Preconditions.checkNotNull(arg6[v1], "Requested API must not be null.");
        }

        ArrayList v0_1 = new ArrayList(arg6.length + 1);
        ((List)v0_1).add(arg5);
        ((List)v0_1).addAll(Arrays.asList(((Object[])arg6)));
        return GoogleApiManager.zabc().zaa(((Iterable)v0_1)).continueWith(new com.google.android.gms.common.zaa(this));
    }

    @KeepForSdk @ShowFirstParty public int getClientVersion(Context arg1) {
        return super.getClientVersion(arg1);
    }

    public Dialog getErrorDialog(Activity arg2, int arg3, int arg4) {
        return this.getErrorDialog(arg2, arg3, arg4, null);
    }

    public Dialog getErrorDialog(Activity arg2, int arg3, int arg4, DialogInterface$OnCancelListener arg5) {
        return GoogleApiAvailability.zaa(((Context)arg2), arg3, DialogRedirect.getInstance(arg2, this.getErrorResolutionIntent(((Context)arg2), arg3, "d"), arg4), arg5);
    }

    @Nullable @KeepForSdk @ShowFirstParty public Intent getErrorResolutionIntent(Context arg1, int arg2, @Nullable String arg3) {
        return super.getErrorResolutionIntent(arg1, arg2, arg3);
    }

    @Nullable public PendingIntent getErrorResolutionPendingIntent(Context arg1, int arg2, int arg3) {
        return super.getErrorResolutionPendingIntent(arg1, arg2, arg3);
    }

    @Nullable public PendingIntent getErrorResolutionPendingIntent(Context arg2, ConnectionResult arg3) {
        if(arg3.hasResolution()) {
            return arg3.getResolution();
        }

        return this.getErrorResolutionPendingIntent(arg2, arg3.getErrorCode(), 0);
    }

    public final String getErrorString(int arg1) {
        return super.getErrorString(arg1);
    }

    public static GoogleApiAvailability getInstance() {
        return GoogleApiAvailability.zaao;
    }

    @HideFirstParty public int isGooglePlayServicesAvailable(Context arg1) {
        return super.isGooglePlayServicesAvailable(arg1);
    }

    @KeepForSdk @ShowFirstParty public int isGooglePlayServicesAvailable(Context arg1, int arg2) {
        return super.isGooglePlayServicesAvailable(arg1, arg2);
    }

    public final boolean isUserResolvableError(int arg1) {
        return super.isUserResolvableError(arg1);
    }

    @MainThread public Task makeGooglePlayServicesAvailable(Activity arg4) {
        int v0 = GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        Preconditions.checkMainThread("makeGooglePlayServicesAvailable must be called from the main thread");
        v0 = this.isGooglePlayServicesAvailable(((Context)arg4), v0);
        Object v1 = null;
        if(v0 == 0) {
            return Tasks.forResult(v1);
        }

        zabu v4 = zabu.zac(arg4);
        ((com.google.android.gms.common.api.internal.zal)v4).zab(new ConnectionResult(v0, ((PendingIntent)v1)), 0);
        return v4.getTask();
    }

    @TargetApi(value=26) public void setDefaultNotificationChannelId(@NonNull Context arg2, @NonNull String arg3) {
        if(PlatformVersion.isAtLeastO()) {
            Preconditions.checkNotNull(arg2.getSystemService("notification").getNotificationChannel(arg3));
        }

        Object v2 = GoogleApiAvailability.mLock;
        __monitor_enter(v2);
        try {
            this.zaap = arg3;
            __monitor_exit(v2);
            return;
        label_12:
            __monitor_exit(v2);
        }
        catch(Throwable v3) {
            goto label_12;
        }

        throw v3;
    }

    public boolean showErrorDialogFragment(Activity arg1, int arg2, int arg3, DialogInterface$OnCancelListener arg4) {
        Dialog v2 = this.getErrorDialog(arg1, arg2, arg3, arg4);
        if(v2 == null) {
            return 0;
        }

        GoogleApiAvailability.zaa(arg1, v2, "GooglePlayServicesErrorDialog", arg4);
        return 1;
    }

    public boolean showErrorDialogFragment(Activity arg2, int arg3, int arg4) {
        return this.showErrorDialogFragment(arg2, arg3, arg4, null);
    }

    public void showErrorNotification(Context arg3, int arg4) {
        this.zaa(arg3, arg4, null, ((GoogleApiAvailabilityLight)this).getErrorResolutionPendingIntent(arg3, arg4, 0, "n"));
    }

    public void showErrorNotification(Context arg3, ConnectionResult arg4) {
        this.zaa(arg3, arg4.getErrorCode(), null, this.getErrorResolutionPendingIntent(arg3, arg4));
    }

    public final boolean zaa(Context arg3, ConnectionResult arg4, int arg5) {
        PendingIntent v0 = this.getErrorResolutionPendingIntent(arg3, arg4);
        if(v0 != null) {
            this.zaa(arg3, arg4.getErrorCode(), null, GoogleApiActivity.zaa(arg3, v0, arg5));
            return 1;
        }

        return 0;
    }

    public final boolean zaa(Activity arg2, @NonNull LifecycleFragment arg3, int arg4, int arg5, DialogInterface$OnCancelListener arg6) {
        Dialog v3 = GoogleApiAvailability.zaa(((Context)arg2), arg4, DialogRedirect.getInstance(arg3, this.getErrorResolutionIntent(((Context)arg2), arg4, "d"), 2), arg6);
        if(v3 == null) {
            return 0;
        }

        GoogleApiAvailability.zaa(arg2, v3, "GooglePlayServicesErrorDialog", arg6);
        return 1;
    }

    @Nullable public final zabq zaa(Context arg3, zabr arg4) {
        IntentFilter v0 = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        v0.addDataScheme("package");
        zabq v1 = new zabq(arg4);
        arg3.registerReceiver(((BroadcastReceiver)v1), v0);
        v1.zac(arg3);
        if(!this.isUninstalledAppPossiblyUpdating(arg3, "com.google.android.gms")) {
            arg4.zas();
            v1.unregister();
            return null;
        }

        return v1;
    }

    public static Dialog zaa(Activity arg3, DialogInterface$OnCancelListener arg4) {
        ProgressBar v0 = new ProgressBar(((Context)arg3), null, 0x101007A);
        v0.setIndeterminate(true);
        v0.setVisibility(0);
        AlertDialog$Builder v2 = new AlertDialog$Builder(((Context)arg3));
        v2.setView(((View)v0));
        v2.setMessage(ConnectionErrorMessages.getErrorMessage(((Context)arg3), 18));
        v2.setPositiveButton("", null);
        AlertDialog v0_1 = v2.create();
        GoogleApiAvailability.zaa(arg3, ((Dialog)v0_1), "GooglePlayServicesUpdatingDialog", arg4);
        return ((Dialog)v0_1);
    }

    static void zaa(Activity arg1, Dialog arg2, String arg3, DialogInterface$OnCancelListener arg4) {
        if((arg1 instanceof FragmentActivity)) {
            SupportErrorDialogFragment.newInstance(arg2, arg4).show(((FragmentActivity)arg1).getSupportFragmentManager(), arg3);
            return;
        }

        ErrorDialogFragment.newInstance(arg2, arg4).show(arg1.getFragmentManager(), arg3);
    }

    static Dialog zaa(Context arg5, int arg6, DialogRedirect arg7, DialogInterface$OnCancelListener arg8) {
        AlertDialog$Builder v0_1;
        Dialog v0 = null;
        if(arg6 == 0) {
            return v0;
        }

        TypedValue v1 = new TypedValue();
        arg5.getTheme().resolveAttribute(0x1010309, v1, true);
        if("Theme.Dialog.Alert".equals(arg5.getResources().getResourceEntryName(v1.resourceId))) {
            v0_1 = new AlertDialog$Builder(arg5, 5);
        }

        if((((AlertDialog$Builder)v0)) == null) {
            v0_1 = new AlertDialog$Builder(arg5);
        }

        ((AlertDialog$Builder)v0).setMessage(ConnectionErrorMessages.getErrorMessage(arg5, arg6));
        if(arg8 != null) {
            ((AlertDialog$Builder)v0).setOnCancelListener(arg8);
        }

        String v8 = ConnectionErrorMessages.getErrorDialogButtonMessage(arg5, arg6);
        if(v8 != null) {
            ((AlertDialog$Builder)v0).setPositiveButton(((CharSequence)v8), ((DialogInterface$OnClickListener)arg7));
        }

        String v5 = ConnectionErrorMessages.getErrorTitle(arg5, arg6);
        if(v5 != null) {
            ((AlertDialog$Builder)v0).setTitle(((CharSequence)v5));
        }

        return ((AlertDialog$Builder)v0).create();
    }

    @TargetApi(value=20) private final void zaa(Context arg6, int arg7, String arg8, PendingIntent arg9) {
        if(arg7 == 18) {
            this.zaa(arg6);
            return;
        }

        if(arg9 == null) {
            if(arg7 == 6) {
                Log.w("GoogleApiAvailability", "Missing resolution for ConnectionResult.RESOLUTION_REQUIRED. Call GoogleApiAvailability#showErrorNotification(Context, ConnectionResult) instead.");
            }

            return;
        }

        arg8 = ConnectionErrorMessages.getErrorNotificationTitle(arg6, arg7);
        String v0 = ConnectionErrorMessages.getErrorNotificationMessage(arg6, arg7);
        Resources v1 = arg6.getResources();
        Object v2 = arg6.getSystemService("notification");
        Builder v8 = new Builder(arg6).setLocalOnly(true).setAutoCancel(true).setContentTitle(((CharSequence)arg8)).setStyle(new BigTextStyle().bigText(((CharSequence)v0)));
        if(DeviceProperties.isWearable(arg6)) {
            Preconditions.checkState(PlatformVersion.isAtLeastKitKatWatch());
            v8.setSmallIcon(arg6.getApplicationInfo().icon).setPriority(2);
            if(DeviceProperties.isWearableWithoutPlayStore(arg6)) {
                v8.addAction(R$drawable.common_full_open_on_phone, v1.getString(R$string.common_open_on_phone), arg9);
            }
            else {
                v8.setContentIntent(arg9);
            }
        }
        else {
            v8.setSmallIcon(0x108008A).setTicker(v1.getString(R$string.common_google_play_services_notification_ticker)).setWhen(System.currentTimeMillis()).setContentIntent(arg9).setContentText(((CharSequence)v0));
        }

        if(PlatformVersion.isAtLeastO()) {
            Preconditions.checkState(PlatformVersion.isAtLeastO());
            String v9 = this.zag();
            if(v9 == null) {
                v9 = "com.google.android.gms.availability";
                NotificationChannel v0_1 = ((NotificationManager)v2).getNotificationChannel(v9);
                String v6 = ConnectionErrorMessages.getDefaultNotificationChannelName(arg6);
                if(v0_1 == null) {
                    ((NotificationManager)v2).createNotificationChannel(new NotificationChannel(v9, ((CharSequence)v6), 4));
                }
                else if(!v6.equals(v0_1.getName())) {
                    v0_1.setName(((CharSequence)v6));
                    ((NotificationManager)v2).createNotificationChannel(v0_1);
                }
            }

            v8.setChannelId(v9);
        }

        Notification v6_1 = v8.build();
        switch(arg7) {
            case 1: 
            case 2: 
            case 3: {
                arg7 = 10436;
                GooglePlayServicesUtilLight.sCanceledAvailabilityNotification.set(false);
                break;
            }
            default: {
                arg7 = 0x9B6D;
                break;
            }
        }

        ((NotificationManager)v2).notify(arg7, v6_1);
    }

    final void zaa(Context arg4) {
        new zaa(this, arg4).sendEmptyMessageDelayed(1, 120000);
    }

    @VisibleForTesting(otherwise=2) private final String zag() {
        Object v0 = GoogleApiAvailability.mLock;
        __monitor_enter(v0);
        try {
            __monitor_exit(v0);
            return this.zaap;
        label_6:
            __monitor_exit(v0);
        }
        catch(Throwable v1) {
            goto label_6;
        }

        throw v1;
    }
}

