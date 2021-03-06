package com.google.android.gms.internal.measurement;

final class zzaak implements zzaap {
    private zzaap[] zzbvm;

    zzaak(zzaap[] arg1) {
        super();
        this.zzbvm = arg1;
    }

    public final boolean zzd(Class arg6) {
        zzaap[] v0 = this.zzbvm;
        int v1 = v0.length;
        int v3;
        for(v3 = 0; v3 < v1; ++v3) {
            if(v0[v3].zzd(arg6)) {
                return 1;
            }
        }

        return 0;
    }

    public final zzaao zze(Class arg6) {
        zzaap[] v0 = this.zzbvm;
        int v1 = v0.length;
        int v2;
        for(v2 = 0; v2 < v1; ++v2) {
            zzaap v3 = v0[v2];
            if(v3.zzd(arg6)) {
                return v3.zze(arg6);
            }
        }

        String v1_1 = "No factory is available for message type: ";
        String v6 = String.valueOf(arg6.getName());
        v6 = v6.length() != 0 ? v1_1.concat(v6) : new String(v1_1);
        throw new UnsupportedOperationException(v6);
    }
}

