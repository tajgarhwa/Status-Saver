package android.statussaver.com.statussaver.activities;

import android.content.Intent;
import android.statussaver.com.statussaver.R;
import android.statussaver.com.statussaver.utils.SettingsApps;
import android.statussaver.com.statussaver.utils.ToastCustom;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class IntroActivity extends YouTubeBaseActivity {

    private YouTubePlayerView youTubePlayerView;
    private TextView skip_btn;
    private SettingsApps settings;

    private Intent intent;
    private String  getActvity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        youTubePlayerView = findViewById(R.id.youtubePlayerView);

        settings = new SettingsApps(this);
        skip_btn = findViewById(R.id.skip_btn);
        playVideo("4VBoFvH_aiQ",youTubePlayerView);

        intent = getIntent();
        if (intent !=null && intent.getExtras() !=null){
            getActvity = intent.getExtras().getString("pass");
        }
        if ( getActvity!=null && getActvity.equalsIgnoreCase("showSkip")){
            skip_btn.setText("Close");
        }
        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( getActvity!=null && getActvity.equalsIgnoreCase("showSkip")){
                    finish();
                }else {
                    settings.setShowGuide(false);
                    Intent intent = new Intent(IntroActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public void playVideo(final String videoId, YouTubePlayerView youTubePlayerView) {
        youTubePlayerView.initialize(getString(R.string.youtube_api_key),
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(videoId);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        ToastCustom.setToast(IntroActivity.this,"Please Update your YouTube app");
                    }
                });
    }
}
