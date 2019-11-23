package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.ChangeNeighbourStateEvent;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment pour la page des favoris.
 */
public class FavoriteNeighbourFragment extends Fragment {

    private NeighbourApiService mApiService;
    private RecyclerView mRecyclerView;


    /**
     * Create and return a new instance
     *
     * @return @{@link FavoriteNeighbourFragment}
     */
    public static FavoriteNeighbourFragment newInstance() {
        return new FavoriteNeighbourFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        initList();
        return view;
    }

    /**
     * Init the List of neighbours.
     */
    private void initList() {
        List<Neighbour> favoritesNeighbours = new ArrayList<>();
        for (Neighbour neighbour : mApiService.getNeighbours()) {
            if (neighbour.isFavorite()) {
                favoritesNeighbours.add(neighbour);
            }
        }
        mRecyclerView.setAdapter(new MyFavoritesRecyclerViewAdapter(favoritesNeighbours));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired when users delete the favori state of a neighbour.
     *
     * @param event event of delete favori state
     */
    @Subscribe
    public void onChangeNeighbourState(ChangeNeighbourStateEvent event) {
        mApiService.changeNeighbourFavoriteState(event.neighbour);
        initList();
    }

    /**
     * Fired if the user clicks on a delete button
     *
     * @param event event of delete neighbour
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        initList();
    }

}
