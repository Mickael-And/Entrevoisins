package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     *
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     *
     * @param neighbour neighbour to delete
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Get a neighbour.
     *
     * @param id id of the neighbour
     * @return neighbour
     */
    Neighbour getNeighbour(Integer id);

}
