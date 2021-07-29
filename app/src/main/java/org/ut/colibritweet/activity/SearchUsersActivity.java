package org.ut.colibritweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.ut.colibritweet.R;
import org.ut.colibritweet.adapter.UsersAdapter;
import org.ut.colibritweet.pojo.User;

import java.util.Arrays;
import java.util.Collection;

public class SearchUsersActivity extends AppCompatActivity {

    private RecyclerView usersRecyclerView;
    private UsersAdapter userAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);


        initRecyclerView();
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
