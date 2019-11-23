package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Event fired when a user change the favori state of a neighbour.
 */
public class ChangeNeighbourStateEvent {

    /**
     * Neighbour.
     */
    public Neighbour neighbour;

    /**
     * Constructor.
     *
     * @param neighbour neighbour
     */
    public ChangeNeighbourStateEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}
