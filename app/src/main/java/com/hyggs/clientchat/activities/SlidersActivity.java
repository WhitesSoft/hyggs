package com.hyggs.clientchat.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.hyggs.clientchat.R;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SlidersActivity extends FragmentActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.layoutButtons)
    LinearLayout buttonsLayout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewPager)
    ViewPager imageViewPager;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sliderCounter)
    ImageView sliderCounter;

    private static final int NUM_PAGES = 3;
    private ScreenSlidePagerAdapter screenSlidePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliders);
        ButterKnife.bind(this);

        screenSlidePagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        screenSlidePagerAdapter.slidersActivity = this;
        imageViewPager.setAdapter(screenSlidePagerAdapter);
        imageViewPager.setCurrentItem(0);

        imageViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageSelected(int pageNumber) {
                Log.i("Selected", String.valueOf(pageNumber));
                if(pageNumber==0){
                    sliderCounter.setImageResource(R.drawable.ic_slider_one_selected);
                }else if(pageNumber==1){
                    sliderCounter.setImageResource(R.drawable.ic_slider_two_selected);
                }else{
                    sliderCounter.setImageResource(R.drawable.ic_slider_three_selected);
                }
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });



        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                imageViewPager.setAlpha(1);
                startCarrusel();
            }
        }, 0, 5000);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.loginButton)
    void loginInActivity() {
//        Toast.makeText(this, "Login pressed.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
//        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.registerButton)
    void registerButton() {
//        Toast.makeText(this, "Register pressed.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
//        finish();
    }

    private void startCarrusel() {
        SlidersActivity.this.runOnUiThread(() -> {
            if (imageViewPager.getCurrentItem() == 2) {
                imageViewPager.setCurrentItem(0);
                sliderCounter.setImageResource(R.drawable.ic_slider_one_selected);
            } else {
                imageViewPager.setCurrentItem(imageViewPager.getCurrentItem() + 1);
                if (imageViewPager.getCurrentItem()==1){
                    sliderCounter.setImageResource(R.drawable.ic_slider_two_selected);
                } else {
                    sliderCounter.setImageResource(R.drawable.ic_slider_three_selected);
                }
            }
        });
    }

    private static class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private SlidersActivity slidersActivity;
        private onScreenSlide mCallBack;

        private interface onScreenSlide {
            public void slided();
        }

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            ScreenSlidePageFragment screen = new ScreenSlidePageFragment();
            screen.setPosition(position);
            return screen;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public static class ScreenSlidePageFragment extends Fragment {

        private int position;

        public void setPosition(int post) {
            this.position = post;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_sliders, container, false);

            TextView title = rootView.findViewById(R.id.mainText);
            ImageView backgroundImage = rootView.findViewById(R.id.fragment_slide_component_imageview);

            switch (this.position) {
                case 0:
                    backgroundImage.setImageResource(R.drawable.single_slider_one);
                    title.setText(Html.fromHtml(getResources().getString(R.string.text_slider_one)));
                    break;
                case 1:
                    backgroundImage.setImageResource(R.drawable.single_slider_two);
                    title.setText(Html.fromHtml(getResources().getString(R.string.text_slider_two)));
                    break;
                case 2:
                    backgroundImage.setImageResource(R.drawable.single_slider_three);
                    title.setText(Html.fromHtml(getResources().getString(R.string.text_slider_three)));
                    break;
                default:
                    Log.i("Entró", "Aquí caso 4");

                    break;
            }

            return rootView;
        }
    }
}


