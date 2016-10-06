package tomerbu.edu.dynamiclinksdemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class DeepLinkActivity extends AppCompatActivity {

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link);
        //init an api client
        googleApiClient = new GoogleApiClient.Builder(this).
                enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                }).addApi(AppInvite.API).build();


        Intent intent = getIntent();
        if (AppInviteReferral.hasReferral(intent)){
            getInvitationData(intent);
        }
    }

    private void getInvitationData(Intent intent) {

        //cleanup... from now on, goto Main Activity:
        AppInvite.AppInviteApi.getInvitation(googleApiClient,
                this,
                false);

        String deepLink = AppInviteReferral.getDeepLink(intent);
        String invitationId = AppInviteReferral.getInvitationId(intent);

        Log.d("Ness", deepLink);
        Log.d("Ness", invitationId);
    }

}
