package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.ChangeNeighbourStateEvent;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.intents.IntentName;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.information.NeighbourInformationActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment pour la page des favoris.
 */
public class FavoriteNeighbourFragment extends Fragment {

    /**
     * Service de gestion des voisin(e)s.
     */
    private NeighbourApiService mApiService;

    /**
     * Composant afichante la liste des voisins favoris.
     */
    @BindView(R.id.list_favorite_neighbours)
    RecyclerView mRecyclerView;

    /**
     * Composant affichant le message lorsqu'il n'y a pas de favori.
     */
    @BindView(R.id.tv_no_favorites)
    TextView tvNoFavorites;

    /**
     * Adapter de la {@link RecyclerView} contenant les voisins.
     */
    private MyFavoritesRecyclerViewAdapter favoritesAdapter;

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
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_neighbour_list, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        if (getContext() != null) {
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        } else {
            Log.i(getClass().getName(), "Context = null, pas de mise en place des items decoration sur la recyclerView");
        }
        this.initList();
        this.checkIfRecyclerViewIsEmpty();
        return view;
    }

    /**
     * Vérifie si la liste de voisin est vide.
     */
    private void checkIfRecyclerViewIsEmpty() {
        if (this.favoritesAdapter.getItemCount() == 0) {
            this.mRecyclerView.setVisibility(View.INVISIBLE);
            this.tvNoFavorites.setVisibility(View.VISIBLE);
        } else {
            this.mRecyclerView.setVisibility(View.VISIBLE);
            this.tvNoFavorites.setVisibility(View.INVISIBLE);
        }
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
        this.favoritesAdapter = new MyFavoritesRecyclerViewAdapter(favoritesNeighbours, neighbour -> {
            Intent intent = new Intent(getContext(), NeighbourInformationActivity.class);
            intent.putExtra(IntentName.INFORMATION_ACTIVITY_INTENT_NAME, neighbour.getId());
            startActivity(intent);
        });

        mRecyclerView.setAdapter(this.favoritesAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired when users delete the favori state of a neighbour.
     *
     * @param event event of delete favori state
     */
    @Subscribe
    public void onChangeNeighbourState(ChangeNeighbourStateEvent event) {
        this.favoritesAdapter.manageFavorite(event.neighbour);
        this.checkIfRecyclerViewIsEmpty();
    }

    /**
     * Fired if the user clicks on a delete button
     *
     * @param event event of delete neighbour
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        this.favoritesAdapter.deleteNeighbour(event.neighbour);
        this.checkIfRecyclerViewIsEmpty();
    }

}
