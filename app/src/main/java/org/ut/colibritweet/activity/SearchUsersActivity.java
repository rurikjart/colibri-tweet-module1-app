package org.ut.colibritweet.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.ut.colibritweet.R;
import org.ut.colibritweet.adapter.UsersAdapter;
import org.ut.colibritweet.network.HttpClient;
import org.ut.colibritweet.pojo.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class SearchUsersActivity extends AppCompatActivity {

    private RecyclerView usersRecyclerView;
    private UsersAdapter usersAdapter;
    private Toolbar toolbar;
    private EditText queryEditText;
    private Button searchButton;

    private HttpClient httpClient;

    private SwipeRefreshLayout swipeRefreshLayout;

    // обрабатываем нажатие кнопки домой
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return  true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);


        initRecyclerView();

        toolbar = findViewById(R.id.toolbar);
        queryEditText = toolbar.findViewById(R.id.query_edit_text);
        searchButton = toolbar.findViewById(R.id.search_button);

        //определяем кнопку домой в тулбаре
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        httpClient = new HttpClient();


        searchUsers();

        // назначаем слушатель на элементу поиска в экранной клавиатуре

        queryEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchUsers();
                    return  true;
                }
                return false;
            }
        });

        //вытягиваем элемент свайп рефеш

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchUsers();
            }
        });



    }

    //обработка клика по кнопке поиска в тулбаре

    public  void onClickSearchButton(View view) {
        searchUsers();

    }






    private void initRecyclerView() {
        usersRecyclerView = findViewById(R.id.users_recycler_view);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        UsersAdapter.OnUserClickListener onUserClickListener = new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(User user) {
              //  Toast.makeText(SearchUsersActivity.this, "user " + user.getName(), Toast.LENGTH_SHORT).show(); // сначало сообщаем потом открываем октивити
                Intent intent = new Intent(SearchUsersActivity.this, UserInfoActivity.class);
                intent.putExtra(UserInfoActivity.USER_ID, user.getId());
                startActivity(intent);
            }
        };


        usersAdapter = new UsersAdapter(onUserClickListener);
        usersRecyclerView.setAdapter(usersAdapter);
    }

    @SuppressLint("StaticFieldLeak")
    private void searchUsers() {
        final String query = queryEditText.getText().toString();
        if(query.length() == 0) {
            Toast.makeText(SearchUsersActivity.this, R.string.not_enough_symbols_msg, Toast.LENGTH_SHORT).show();
            return;
        }

        new UsersAsyncTask().execute(query);
    }

    @SuppressLint("StaticFieldLeak")
    private class UsersAsyncTask extends AsyncTask<String, Integer, Collection<User>> {

        @Override
        protected void onPreExecute() {
            swipeRefreshLayout.setRefreshing(true); // так как обновление и выполнение в этом модуле одно
        }

        @Override
        protected Collection<User> doInBackground(String... params) {
            String query = params[0];
            try {
                return httpClient.readUsers(query);
            } catch (IOException | JSONException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Collection<User> users) {

            swipeRefreshLayout.setRefreshing(false);

            //успешный ответ
            if (users != null) {
                usersAdapter.clearItems();
                usersAdapter.setItems(users);
            }
            //ошибка
            else {
                Toast.makeText(SearchUsersActivity.this, R.string.loading_error_msg, Toast.LENGTH_SHORT).show();
            }

        }
    }

    private Collection<User> getUsers() {
        return Arrays.asList(
                new User(
                        929257819349700608L,
                        "http://i.imgur.com/DvpvklR.png",
                        "DevColibri",
                        "@devcolibri",
                        "Sample description",
                        "USA",
                        42,
                        42
                ),

                new User(
                        44196397L,
                        "https://pbs.twimg.com/profile_images/782474226020200448/zDo-gAo0_400x400.jpg",
                        "Elon Musk",
                        "@elonmusk",
                        "Hat Salesman",
                        "Boring",
                        14,
                        13
                )
        );
    }


}
