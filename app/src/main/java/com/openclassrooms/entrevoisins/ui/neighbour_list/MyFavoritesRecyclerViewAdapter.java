package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter de la {@link RecyclerView} des voisins favoris.
 */
public class MyFavoritesRecyclerViewAdapter extends RecyclerView.Adapter<MyFavoritesRecyclerViewAdapter.ViewHolder> {

    /**
     * Liste des voisins favoris.
     */
    private final List<Neighbour> favoritesNeighbours;

    /**
     * Callback permettant l'écoute sur un item de la liste.
     */
    private final OnItemClickListener onItemClickListener;

    MyFavoritesRecyclerViewAdapter(List<Neighbour> items, OnItemClickListener onItemClickListener) {
        this.favoritesNeighbours = items;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = favoritesNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);
        holder.itemView.setOnClickListener(view -> this.onItemClickListener.onItemClick(neighbour));
    }

    @Override
    public int getItemCount() {
        return favoritesNeighbours.size();
    }


    /**
     * Ajoute le voisin si il est favori. Le supprime sinon.
     *
     * @param neighbour voisin que l'on souhaite ajouter ou supprimer de la liste
     */
    void manageFavorite(Neighbour neighbour) {
        if (neighbour.isFavorite()) {
            if (!this.favoritesNeighbours.contains(neighbour)) {
                this.favoritesNeighbours.add(neighbour);
                notifyDataSetChanged();
            }
        } else {
            this.favoritesNeighbours.remove(neighbour);
            notifyDataSetChanged();
        }
    }

    /**
     * Supprime un voisin de la liste de l'adapter.
     *
     * @param neighbour voisin que l'on souhaite supprimer
     */
    void deleteNeighbour(Neighbour neighbour) {
        if (this.favoritesNeighbours.contains(neighbour)) {
            this.favoritesNeighbours.remove(neighbour);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * Callback pour obtenir un onClickListener sur la recyclerView.
     */
    public interface OnItemClickListener {
        void onItemClick(Neighbour neighbour);
    }
}
