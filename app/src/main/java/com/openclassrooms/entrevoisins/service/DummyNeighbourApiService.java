package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    /**
     * Liste des voisins.
     */
    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    @Override
    public void changeNeighbourFavoriteState(Neighbour neighbour) {
        neighbour.setFavorite(!neighbour.isFavorite());
    }

    @Override
    public Neighbour getNeighbour(Integer id) {
        for (Neighbour neighbour : this.neighbours) {
            if (neighbour.getId().equals(id)) {
                return neighbour;
            }
        }
        Neighbour neighbour = new Neighbour();
        neighbour.setId(Integer.MAX_VALUE);
        return neighbour;
    }


}
