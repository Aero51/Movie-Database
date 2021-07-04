package com.aero51.moviedatabase.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;


import com.aero51.moviedatabase.MainActivity;
import com.aero51.moviedatabase.R;

import java.util.ArrayList;

public class DynamicFragmentPagerAdapter extends PagerAdapter {
    private static final String TAG = "DynamicFragmentPagerAdapter";

    private final FragmentManager fragmentManager;

    public static abstract class FragmentIdentifier implements Parcelable {


        //Parcelable protocol requires a Parcelable.Creator object called CREATOR on class com.aero51.moviedatabase.ui
        //this error happens when app is killed by system after cca 30 mins on inactivity

        //TODO implement parcelable creator  for different fragments
/*
        public static final Parcelable.Creator CREATOR = new Creator<Object>() {
            @Override
            public FragmentIdentifier createFromParcel(Parcel source) {
              return new FragmentIdentifier(source) {
                  @Override
                  public int describeContents() {
                      return 0;
                  }
                  @Override
                  protected Fragment createFragment() {

                      MovieDetailsFragment tvShowsFragment=new MovieDetailsFragment();
                      return tvShowsFragment;
                  }
              };
            }

            @Override
            public FragmentIdentifier[] newArray(int size) {
                return new FragmentIdentifier[size];
            }
        };
*/

        private final String fragmentTag;
        private final Bundle args;

        public FragmentIdentifier(@NonNull String fragmentTag, @Nullable Bundle args) {
            this.fragmentTag = fragmentTag;
            this.args = args;
        }

        protected FragmentIdentifier(Parcel in) {
            fragmentTag = in.readString();
            args = in.readBundle(getClass().getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(fragmentTag);
            dest.writeBundle(args);
        }

        protected final Fragment newFragment() {
            Fragment fragment = createFragment();
            Bundle oldArgs = fragment.getArguments();
            Bundle newArgs = new Bundle();
            if (oldArgs != null) {
                newArgs.putAll(oldArgs);
            }
            if (args != null) {
                newArgs.putAll(args);
            }
            fragment.setArguments(newArgs);
            return fragment;
        }

        protected abstract Fragment createFragment();
    }

    public void replaceFragment(int index, FragmentIdentifier fragmentIdentifier) {
        fragmentIdentifiers.set(index, fragmentIdentifier);
        notifyDataSetChanged();


    }

    public String getFragmentTagForPosition(int position) {
        return fragmentIdentifiers.get(position).fragmentTag;
    }

    private ArrayList<FragmentIdentifier> fragmentIdentifiers = new ArrayList<>();
    private FragmentTransaction currentTransaction = null;
    private Fragment currentPrimaryItem = null;
    private Context context;

    //context is added for purpose of restarting the app when bad parcelable exception occurs
    public DynamicFragmentPagerAdapter(FragmentManager fragmentManager, Context context) {
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    // this method is not needed because app uses bottom navigation view instead of tab layout
    //it is needed for search activity
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {

            case 0:
                return context.getResources().getString(R.string.movies);
            case 1:
                return context.getResources().getString(R.string.tv_shows);
            case 2:
                return   context.getResources().getString(R.string.actors);

        }
        return null;
    }

    private int findIndexIfAdded(FragmentIdentifier fragmentIdentifier) {
        for (int i = 0, size = fragmentIdentifiers.size(); i < size; i++) {
            FragmentIdentifier identifier = fragmentIdentifiers.get(i);
            if (identifier.fragmentTag.equals(fragmentIdentifier.fragmentTag)) {
                return i;
            }
        }
        return -1;
    }


    public void addFragment(FragmentIdentifier fragmentIdentifier) {
        if (findIndexIfAdded(fragmentIdentifier) < 0) {
            fragmentIdentifiers.add(fragmentIdentifier);
            notifyDataSetChanged();
        }
    }

    public void removeFragment(FragmentIdentifier fragmentIdentifier) {
        int index = findIndexIfAdded(fragmentIdentifier);
        if (index >= 0) {
            fragmentIdentifiers.remove(index);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return fragmentIdentifiers.size();
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        if (container.getId() == View.NO_ID) {
            throw new IllegalStateException("ViewPager with adapter " + this
                    + " requires a view id");
        }
    }

    @SuppressWarnings("ReferenceEquality")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (currentTransaction == null) {
            currentTransaction = fragmentManager.beginTransaction();
        }
        final FragmentIdentifier fragmentIdentifier = fragmentIdentifiers.get(position);
        // Do we already have this fragment?
        final String name = fragmentIdentifier.fragmentTag;
        Fragment fragment = fragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            currentTransaction.attach(fragment);
        } else {
            fragment = fragmentIdentifier.newFragment();
            currentTransaction.add(container.getId(), fragment, fragmentIdentifier.fragmentTag);
        }
        if (fragment != currentPrimaryItem) {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (currentTransaction == null) {
            currentTransaction = fragmentManager.beginTransaction();
        }
        currentTransaction.detach((Fragment) object);
    }

    @SuppressWarnings("ReferenceEquality")
    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment != currentPrimaryItem) {
            if (currentPrimaryItem != null) {
                currentPrimaryItem.setMenuVisibility(false);
                currentPrimaryItem.setUserVisibleHint(false);
            }
            fragment.setMenuVisibility(true);
            fragment.setUserVisibleHint(true);
            currentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        if (currentTransaction != null) {
            currentTransaction.commitNowAllowingStateLoss();
            currentTransaction = null;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return ((Fragment) object).getView() == view;
    }

    @Override
    public Parcelable saveState() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("fragmentIdentifiers", fragmentIdentifiers);
        return bundle;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        Bundle bundle = ((Bundle) state);
        bundle.setClassLoader(loader);


        try {
            fragmentIdentifiers = bundle.getParcelableArrayList("fragmentIdentifiers");

        } catch (BadParcelableException e) {
            e.printStackTrace();
            triggerRebirth(context, MainActivity.class);
        }
    }

    //restarts the app
    public static void triggerRebirth(Context context, Class myClass) {
        Intent intent = new Intent(context, myClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        //Runtime.getRuntime().exit(0);


    }


}


