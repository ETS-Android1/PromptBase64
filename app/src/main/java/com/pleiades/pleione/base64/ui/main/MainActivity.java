package com.pleiades.pleione.base64.ui.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.pleiades.pleione.base64.R;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.layout_tab);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = viewPager.getCurrentItem();
                PlaceholderFragment fragment = (PlaceholderFragment) sectionsPagerAdapter.instantiateItem(viewPager, index);

                fragment.convert();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO
        // https://stackoverflow.com/questions/35608721/how-to-add-custom-item-on-global-long-press-contextmenu-like-translate-and-wikip/38539059
        // https://stackoverflow.com/questions/8571501/how-to-check-whether-a-string-is-base64-encoded-or-not
        // initialize intent
//        Intent intent = getIntent();
//        if (intent != null) {
//            // clear the intent to prevent again receiving on resuming main
//            setIntent(new Intent());
//
//            if (intent.getAction().equals(Intent.ACTION_PROCESS_TEXT) && intent.hasExtra(Intent.EXTRA_PROCESS_TEXT)) {
//                String input = intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT);
//                Base64.
//            }
//        }
    }

    static class SectionsPagerAdapter extends FragmentPagerAdapter {

        // constructor
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? "ENCODE" : "DECODE";
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}