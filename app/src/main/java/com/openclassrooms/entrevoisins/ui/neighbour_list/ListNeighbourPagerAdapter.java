package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Adapter permettant la gestion des différentes pages.
 */
public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     *
     * @param position page position
     * @return associated fragment
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return NeighbourFragment.newInstance();
        } else {
            return FavoriteNeighbourFragment.newInstance();
        }
    }

    /**
     * get the number of pages
     *
     * @return page number
     */
    @Override
    public int getCount() {
        return 2;
    }
}