package org.ut.colibritweet.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.ut.colibritweet.R;
import org.ut.colibritweet.pojo.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// Унаследовали наш адаптер от RecyclerView.Adapter
// Здесь же указали наш собственный ViewHolder, который предоставит нам доступ к View-компонентам
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetViewHolder> {

    private static final String TWITTER_RESPONSE_FORMAT="EEE MMM dd HH:mm:ss ZZZZZ yyyy"; // Thu Oct 26 07:31:08 +0000 2017
    private static final String MONTH_DAY_FORMAT = "MMM d"; // Oct 26

    //Источник строк для списка в приложении
    private List<Tweet> tweetList = new ArrayList<>();


    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item_view, parent, false);

        return new TweetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder tweetViewHolder, int position) {
        tweetViewHolder.bind(tweetList.get(position));
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }


    public void setItems(Collection<Tweet> tweets) {
        tweetList.addAll(tweets);
        notifyDataSetChanged();
    }

    public void clearItem() {
        tweetList.clear();
        notifyDataSetChanged();
    }
    // Предоставляет прямую ссылку на каждый View-компонент
    // Используется для кэширования View-компонентов и последующего быстрого доступа к ним
    class TweetViewHolder extends RecyclerView.ViewHolder {
        // Ваш ViewHolder должен содержать переменные для всех
        // View-компонентов, которым вы хотите задавать какие-либо свойства
        // в процессе работы пользователя со списком
        private ImageView userImageView;
        private TextView nameTextView;
        private TextView nickTextView;
        private TextView creationDateTextView;
        private TextView contentTextView;
        private ImageView tweetImageView;
        private TextView retweetsTextView;
        private TextView likesTextView;




        // Мы также создали конструктор, который принимает на вход View-компонент строкИ
        // и ищет все дочерние компоненты
        public TweetViewHolder(View itemView) {
            super(itemView);
            userImageView = itemView.findViewById(R.id.profile_image_view);
            nameTextView = itemView.findViewById(R.id.author_name_text_view);
            nickTextView = itemView.findViewById(R.id.author_nick_text_view);
            creationDateTextView = itemView.findViewById(R.id.creation_date_text_view);
            contentTextView = itemView.findViewById(R.id.tweet_content_text_view);
            tweetImageView = itemView.findViewById(R.id.tweet_image_view);
            retweetsTextView = itemView.findViewById(R.id.retweets_text_view);
            likesTextView = itemView.findViewById(R.id.likes_text_view);
        }

        public void bind(Tweet tweet) {

            nameTextView.setText(tweet.getUser().getName());
            nickTextView.setText(tweet.getUser().getNick());
            contentTextView.setText(tweet.getText());
            retweetsTextView.setText(String.valueOf(tweet.getRetweetCount()));
            likesTextView.setText(String.valueOf(tweet.getFavouriteCount()));

            String creationDateFormatted = getFormattedDate(tweet.getCreationDate());
            creationDateTextView.setText(creationDateFormatted);

            Picasso.with(itemView.getContext()).load(tweet.getUser().getImageUrl()).into(userImageView);

            String tweetPhotoUrl = tweet.getImageUrl();
            Picasso.with(itemView.getContext()).load(tweetPhotoUrl).into(tweetImageView);

            tweetImageView.setVisibility(tweetPhotoUrl != null ? View.VISIBLE : View.GONE);

        }

        private String getFormattedDate(String rawDate) {

            SimpleDateFormat utcFormat = new SimpleDateFormat(TWITTER_RESPONSE_FORMAT, Locale.ROOT);
            SimpleDateFormat displayedFormat = new SimpleDateFormat(MONTH_DAY_FORMAT, Locale.getDefault());

            try {
                Date date = utcFormat.parse(rawDate);
                return  displayedFormat.format(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }


    }


}