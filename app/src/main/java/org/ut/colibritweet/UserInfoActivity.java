package org.ut.colibritweet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class UserInfoActivity extends AppCompatActivity {

    private ImageView userImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        userImageView = findViewById(R.id.user_image_view);

        // ver 2.7
        //  Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(userImageView);

        // ver 2.5.2
        Picasso.with(this).load("https://rostov.ru/uploads/posts/2019-08/1565355564_gr-87kg-gold-ilia-ermolenko-rus-df_-david-losonczi-hun-3.jpg").into(userImageView);




    }
}