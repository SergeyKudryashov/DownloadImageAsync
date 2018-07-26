package com.ss.downloadimageasync;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String IMAGE_URL = "https://pp.userapi.com/c608924/v608924231/bace/tIWSKPf0w8w.jpg";

    private TextView mTextView;
    private Button mDownloadButton;
    private ProgressBar mProgressBar;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mTextView = findViewById(R.id.download_status_text_view);
        mDownloadButton = findViewById(R.id.download_button);
        mProgressBar = findViewById(R.id.downloading_progress_bar);
        mImageView = findViewById(R.id.downloaded_image);

        final String filesDir = getFilesDir().getAbsolutePath();

        final DownloadImageAsync downloadImageAsync = new DownloadImageAsync();
        downloadImageAsync.setCallbacks(new DownloadImageAsync.DownloadAsyncTaskCallbacks() {
            @Override
            public void onPreExecute() {
                mTextView.setText(getString(R.string.download_status_started));
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPostExecute(String s) {
                mTextView.setText(getString(R.string.download_status_finished));
                mProgressBar.setVisibility(View.GONE);
                mImageView.setImageDrawable(Drawable.createFromPath(s));
            }

            @Override
            public void onProgressUpdate(Integer i) {
                mProgressBar.setProgress(i);
            }
        });

        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadImageAsync.execute(IMAGE_URL, filesDir);
                mDownloadButton.setEnabled(false);
            }
        });
    }
}
