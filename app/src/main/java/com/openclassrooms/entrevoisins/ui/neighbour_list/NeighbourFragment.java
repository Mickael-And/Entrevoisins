package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.intents.IntentName;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment pour la page des voisins.
 */
public class NeighbourFragment extends Fragment {

    /**
     * Service de gestion des voisin(e)s.
     */
    private NeighbourApiService mApiService;

    /**
     * Composant affichant la liste des voisins.
     */
    @BindView(R.id.list_neighbours)
    RecyclerView mRecyclerView;

    /**
     * Create and return a new instance
     *
     * @return @{@link NeighbourFragment}
     */
    public static NeighbourFragment newInstance() {
        return new NeighbourFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        if (getContext() != null) {
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        } else {
            Log.i(getClass().getName(), "Context = null, pas de mise en place des items decoration sur la recyclerView");
        }
        initList();
        return view;
    }

    /**
     * Init the List of neighbours.
     */
    private void initList() {
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mApiService.getNeighbours(), neighbour -> {
            Intent intent = new Intent(getContext(), NeighbourInformationActivity.class);
            intent.putExtra(IntentName.INFORMATION_ACTIVITY_INTENT_NAME, neighbour.getId());
            startActivity(intent);
        }));
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
     * Fired if the user clicks on a delete button
     *
     * @param event event of delete neighbour
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        mApiService.deleteNeighbour(event.neighbour);
        initList();
    }
}
