package tomerbu.edu.dynamiclinksdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_INVITE = 10;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inviteSomeone();
            }
        });

        //init an api client
        googleApiClient = new GoogleApiClient.Builder(this).
                enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }
        }).addApi(AppInvite.API).build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        testTheInvitation();
    }

    private void testTheInvitation() {
        AppInvite.AppInviteApi.getInvitation(googleApiClient,
                this,
                true)/*not a must, just used for debugging purposes*/
                .setResultCallback(new ResultCallback<AppInviteInvitationResult>() {
                    @Override
                    public void onResult(@NonNull AppInviteInvitationResult result) {
                        if (result.getStatus().isSuccess()){
                            Intent intent = result.getInvitationIntent();
                            String deepLink = AppInviteReferral.getDeepLink(intent);
                            String invitationId = AppInviteReferral.getInvitationId(intent);

                            Log.d("Ness", intent.toString());
                            Log.d("Ness", deepLink);
                            Log.d("Ness", invitationId);

                        }
                    }
                });
    }

    private void inviteSomeone() {
        /*String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();*/
        Intent intent = new AppInviteInvitation.IntentBuilder(
                   "Play with Me")
                    .setMessage("It's Awesome")
                    .setDeepLink(Uri.parse("https://invitesdemo.org/"+"dfs0rwe03240"))
                    .setCustomImage(Uri.parse("http://www.adaringadventure.com/wp-content/uploads/2010/08/Sheep-shopping2.jpg"))
                    .setCallToActionText("Let's Go!")
                    .build();
            startActivityForResult(intent, REQUEST_CODE_INVITE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
