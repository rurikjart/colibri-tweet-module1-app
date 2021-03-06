package org.ut.colibritweet.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.ut.colibritweet.R;
import org.ut.colibritweet.adapter.TweetAdapter;
import org.ut.colibritweet.network.HttpClient;
import org.ut.colibritweet.pojo.Tweet;
import org.ut.colibritweet.pojo.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class UserInfoActivity extends AppCompatActivity {

    public static final String USER_ID = "user_Id";

    private ImageView userImageView;
    private TextView nameTextView;
    private TextView nickTextView;
    private TextView descriptionTextView;
    private TextView locationTextView;
    private TextView followingCountTextView;
    private TextView followersCountTextView;

    private RecyclerView tweetsRecyclerView;

    //адаптер заполнения списка RecyclerView
    private TweetAdapter tweetAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private HttpClient httpClient;

    private Toolbar toolbar;
    private int taskInProgressCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        long userId = getIntent().getLongExtra(USER_ID, -1);
        // Toast.makeText(this, "UserId = " + userId, Toast.LENGTH_LONG).show();
        Log.d("HttpTest", "UserId = " + String.valueOf(userId));

        userImageView = findViewById(R.id.user_image_view);
        userImageView = findViewById(R.id.user_image_view);
        nameTextView = findViewById(R.id.user_name_text_view);
        nickTextView = findViewById(R.id.user_nick_text_view);
        descriptionTextView = findViewById(R.id.user_description_text_view);
        locationTextView = findViewById(R.id.user_location_text_view);
        followingCountTextView = findViewById(R.id.following_count_text_view);
        followersCountTextView = findViewById(R.id.followers_count_text_view);

        initUserToolbar();

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tweetAdapter.clearItem();
                loadUserInfo(userId);
                loadTweets(userId);
            }
        });

        initRecyclerView();

        httpClient = new HttpClient();
        loadUserInfo(userId);
        loadTweets(userId);

    }




    // активация кнопки поиска на тулбаре текущего активити
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_info_menu, menu);
        return true;
    }

    // вызов активности поиска пользователей
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(this, SearchUsersActivity.class);
            startActivity(intent);
        }

        return true;
    }

    private void initUserToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }




    private void loadTweets(long userId) {
        new TweetsAsyncTask().execute(userId);
    }



    private void initRecyclerView() {
        tweetsRecyclerView = findViewById(R.id.tweets_recycler_view);
        // ограничение прокрутки только одним элементом
        ViewCompat.setNestedScrollingEnabled(tweetsRecyclerView, false);
        //ставим разделительную линию между твитами
        tweetsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tweetAdapter = new TweetAdapter();
        tweetsRecyclerView.setAdapter(tweetAdapter);
    }

    private void loadUserInfo(final long userId) {
        //1
        //  User user = getUser();
        //  displayUserInfo(user);

        //2
       /* Runnable readUserRunnable = new Runnable() {
            @Override
            public void run() {
                try {


                    String userInfo =  httpClient.readUserInfo(userId);
                  //  Log.d("HttpTest", userInfo);
                    Runnable showResultRunnable = new Runnable() {
                        @Override
                        public void run() {
                                Toast.makeText(UserInfoActivity.this, userInfo, Toast.LENGTH_SHORT).show();
                        }
                    };
                    runOnUiThread(showResultRunnable);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

      new Thread(readUserRunnable).start(); */

        new UserInfoAsyncTask().execute(userId);

    }

    private void displayUserInfo(User user) {
        // ver 2.7
        //  Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(userImageView);

        // ver 2.5.2
        Picasso.with(this).load(user.getImageUrl()).into(userImageView);

        nameTextView.setText(user.getName());
        nickTextView.setText(user.getNick());

        descriptionTextView.setText(user.getDescription());
        locationTextView.setText(user.getLocation());

        String followingCount = String.valueOf(user.getFollowingCount());
        followingCountTextView.setText(followingCount);

        String followersCount = String.valueOf(user.getFollowersCount());
        followersCountTextView.setText(followersCount);

        //отображение в заголовке пользоватеьского тулбара наименование пользователя
        getSupportActionBar().setTitle(user.getName());
    }


    @SuppressLint("StaticFieldLeak")
    private class UserInfoAsyncTask extends AsyncTask<Long, Intent, User> {

        @Override
        protected void onPreExecute() {
            setRefreshLayoutVisible(true);
        }

        //отправляем на фоновое исполнение процесс
        protected User doInBackground(Long... ids) {
            try {
                // достаем userId, который передли в метод execute
                Long userId = ids[0];
                return httpClient.readUserInfo(userId);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(User result) {
            //  Toast.makeText(UserInfoActivity.this, result, Toast.LENGTH_SHORT).show();
            // Log.d("HttpTest", result);
            setRefreshLayoutVisible(false);


            //проверка на возникновение ошибок и исключений

            //успешный ответ
            if (result != null) {
                displayUserInfo(result);

            }
            //ошибка
            else {
                Toast.makeText(UserInfoActivity.this, R.string.loading_error_msg, Toast.LENGTH_SHORT).show();
            }
        }

    }

    //
    @SuppressLint("StaticFieldLeak")
    private class TweetsAsyncTask extends AsyncTask<Long, Integer, Collection<Tweet>> {

        @Override
        protected void onPreExecute() {
            setRefreshLayoutVisible(true);
        }

        protected Collection<Tweet> doInBackground(Long... ids) {

            try {
                Long userId = ids[0];

                return httpClient.readTweets(userId);


            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }

        }

        protected void onPostExecute(Collection<Tweet> tweets) {

            setRefreshLayoutVisible(false);


            if (tweets != null) {
                tweetAdapter.setItems(tweets);
            }
            else {
                   Toast.makeText(UserInfoActivity.this, R.string.loading_error_msg, Toast.LENGTH_SHORT).show();
               }
        }
    }

    private void setRefreshLayoutVisible(boolean visible) {
        if(visible) {
            taskInProgressCount++;
            if (taskInProgressCount == 1) swipeRefreshLayout.setRefreshing(true);
        } else {
            taskInProgressCount--;
            if(taskInProgressCount == 0) swipeRefreshLayout.setRefreshing(false);
            }
        }
}