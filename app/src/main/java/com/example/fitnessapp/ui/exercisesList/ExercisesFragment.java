package com.example.fitnessapp.ui.exercisesList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager.widget.ViewPager;

import com.example.fitnessapp.R;
import com.example.fitnessapp.model.entities.Exercise;
import com.google.android.material.tabs.TabLayout;

public class ExercisesFragment extends Fragment {

    private MutableLiveData<Exercise> mutableLiveData;

    public ExercisesFragment() {
        mutableLiveData = new MutableLiveData<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.exercises_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TabLayoutPagerAdapter tabLayoutPagerAdapter =
                new TabLayoutPagerAdapter(getChildFragmentManager());

        tabLayoutPagerAdapter.setLiveData(mutableLiveData);

        ViewPager viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(tabLayoutPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();

        mutableLiveData.observeForever(exercise -> {
            ExerciseInfoFragment exerciseInfoFragment = new ExerciseInfoFragment();
            exerciseInfoFragment.setExercise(exercise);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, exerciseInfoFragment);
            transaction.commit();

        });
    }

    static class TabLayoutPagerAdapter extends FragmentStatePagerAdapter {

        final int PAGE_COUNT = 3;

        MutableLiveData liveData;

        TabLayoutPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        private void setLiveData(MutableLiveData liveData){
            this.liveData = liveData;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position <= 2) {
                return new TabExercisesFragment(position, liveData);
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "руки";
                case 1:
                    return "туловище";
                default:
                    return "ноги";
            }
        }
    }
}