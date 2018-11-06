package mobilcorpapp.starwarssound;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.startapp.android.publish.adsCommon.StartAppAd;

public class MainActivity extends AppCompatActivity {

    public static Player player;

    private ViewPager pager;
    private PagerAdapter adapter;

    private final static int SOUNDS_ON_PAGE = 8;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = new Player(this);

        ImageButton nextButton = findViewById(R.id.next_button);
        ImageButton backButton = findViewById(R.id.back_button);
        ImageButton rateButton = findViewById(R.id.rate_button);

        setupPager();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition != 0)
                    pager.setCurrentItem(currentPosition - 1);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition < adapter.getCount() - 1)
                    pager.setCurrentItem(currentPosition + 1);
            }
        });

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps"));
                startActivity(intent);
            }
        });
    }

    private void setupPager() {
        pager = findViewById(R.id.view_pager);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPosition = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        int pages = player.getSounds().size() / SOUNDS_ON_PAGE;
        int rest = player.getSounds().size() % SOUNDS_ON_PAGE;
        final int totalAdapterPages = rest == 0 ? pages : pages + 1;

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return PagerItem.newInstance(i, SOUNDS_ON_PAGE);
            }

            @Override
            public int getCount() {
                return totalAdapterPages;
            }
        };

        pager.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}
