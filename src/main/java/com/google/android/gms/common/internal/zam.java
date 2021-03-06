package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable$Creator;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zam implements Parcelable$Creator {
    public zam() {
        super();
    }

    public final Object createFromParcel(Parcel arg8) {
        Parcelable v4_1;
        int v0 = SafeParcelReader.validateObjectHeader(arg8);
        Account v1 = null;
        int v2 = 0;
        GoogleSignInAccount v4 = ((GoogleSignInAccount)v1);
        int v3;
        for(v3 = 0; arg8.dataPosition() < v0; v3 = SafeParcelReader.readInt(arg8, v5)) {
            int v5 = SafeParcelReader.readHeader(arg8);
            switch(SafeParcelReader.getFieldId(v5)) {
                case 1: {
                    goto label_20;
                }
                case 2: {
                    goto label_17;
                }
                case 3: {
                    goto label_15;
                }
                case 4: {
                    goto label_12;
                }
            }

            SafeParcelReader.skipUnknownField(arg8, v5);
            continue;
        label_17:
            Parcelable v1_1 = SafeParcelReader.createParcelable(arg8, v5, Account.CREATOR);
            continue;
        label_20:
            v2 = SafeParcelReader.readInt(arg8, v5);
            continue;
        label_12:
            v4_1 = SafeParcelReader.createParcelable(arg8, v5, GoogleSignInAccount.CREATOR);
            continue;
        label_15:
        }

        SafeParcelReader.ensureAtEnd(arg8, v0);
        return new ResolveAccountRequest(v2, v1, v3, ((GoogleSignInAccount)v4_1));
    }

    public final Object[] newArray(int arg1) {
        return new ResolveAccountRequest[arg1];
    }
}

