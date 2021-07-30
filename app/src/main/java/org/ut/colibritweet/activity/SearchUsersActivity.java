package org.ut.colibritweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.ut.colibritweet.R;
import org.ut.colibritweet.adapter.UsersAdapter;
import org.ut.colibritweet.pojo.User;

import java.util.Arrays;
import java.util.Collection;

public class SearchUsersActivity extends AppCompatActivity {

    private RecyclerView usersRecyclerView;
    private UsersAdapter userAdapter;
    private Toolbar toolbar;
    private EditText queryEditText;
    private Button searchButton;

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



    }

    //обработка клика по кнопке поиска в тулбаре

    public  void onClickSearchButton(View view) {
        searchUsers();

    }






    private void initRecyclerView() {
        usersRecyclerView = findViewById(R.id.users_recycler_view);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        UsersAdapter.OnUserClickListener onUserClickListener = new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(User user) {
              //  Toast.makeText(SearchUsersActivity.this, "user " + user.getName(), Toast.LENGTH_SHORT).show(); // сначало сообщаем потом открываем октивити
                Intent intent = new Intent(SearchUsersActivity.this, UserInfoActivity.class);
                intent.putExtra(UserInfoActivity.USER_ID, user.getId());
                startActivity(intent);
            }
        };


        userAdapter = new UsersAdapter(onUserClickListener);
        usersRecyclerView.setAdapter(userAdapter);
    }

    private void searchUsers() {
        Collection<User> users = getUsers();
        userAdapter.setItems(users);
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
