package com.google.android.gms.internal.measurement;

import android.content.BroadcastReceiver$PendingResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager$NameNotFoundException;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.MainThread;
import com.google.android.gms.common.internal.Preconditions;

public final class zzgd {
    private final zzgg zzamz;

    public zzgd(zzgg arg1) {
        super();
        Preconditions.checkNotNull(arg1);
        this.zzamz = arg1;
    }

    @MainThread public final void onReceive(Context arg13, Intent arg14) {
        zzgn v3 = zzgn.zza(arg13, null, null);
        zzfi v8 = v3.zzgi();
        if(arg14 == null) {
            v8.zziy().log("Receiver called with null intent");
            return;
        }

        v3.zzgl();
        String v0 = arg14.getAction();
        v8.zzjc().zzg("Local receiver got", v0);
        if("com.google.android.gms.measurement.UPLOAD".equals(v0)) {
            arg14 = new Intent().setClassName(arg13, "com.google.android.gms.measurement.AppMeasurementService");
            arg14.setAction("com.google.android.gms.measurement.UPLOAD");
            v8.zzjc().log("Starting wakeful intent.");
            this.zzamz.doStartService(arg13, arg14);
            return;
        }

        if("com.android.vending.INSTALL_REFERRER".equals(v0)) {
            try {
                v3.zzgh().zzc(new zzge(this, v3, v8));
            }
            catch(Exception v0_1) {
                v8.zziy().zzg("Install Referrer Reporter encountered a problem", v0_1);
            }

            BroadcastReceiver$PendingResult v9 = this.zzamz.doGoAsync();
            v0 = arg14.getStringExtra("referrer");
            if(v0 == null) {
                v8.zzjc().log("Install referrer extras are null");
                if(v9 != null) {
                    v9.finish();
                }

                return;
            }

            v8.zzja().zzg("Install referrer extras are", v0);
            if(!v0.contains("?")) {
                String v1 = "?";
                v0 = String.valueOf(v0);
                v0 = v0.length() != 0 ? v1.concat(v0) : new String(v1);
            }

            Bundle v6 = v3.zzgg().zza(Uri.parse(v0));
            if(v6 == null) {
                v8.zzjc().log("No campaign defined in install referrer broadcast");
                if(v9 == null) {
                    return;
                }

                v9.finish();
                return;
            }

            long v4 = arg14.getLongExtra("referrer_timestamp_seconds", 0) * 1000;
            if(v4 == 0) {
                v8.zziy().log("Install referrer is missing timestamp");
            }

            v3.zzgh().zzc(new zzgf(this, v3, v4, v6, arg13, v8, v9));
        }
    }

    public static boolean zza(Context arg4) {
        Preconditions.checkNotNull(arg4);
        try {
            PackageManager v1 = arg4.getPackageManager();
            if(v1 == null) {
                return 0;
            }

            ActivityInfo v4 = v1.getReceiverInfo(new ComponentName(arg4, "com.google.android.gms.measurement.AppMeasurementReceiver"), 0);
            if(v4 != null && (v4.enabled)) {
                return 1;
            }
        }
        catch(PackageManager$NameNotFoundException ) {
        }

        return 0;
    }
}

