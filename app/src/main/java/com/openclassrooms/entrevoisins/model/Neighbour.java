package com.openclassrooms.entrevoisins.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model object representing a Neighbour
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Neighbour {

    /**
     * Identifier.
     */
    @Getter
    @Setter
    private Integer id;

    /**
     * Full name.
     */
    @Getter
    @Setter
    private String name;

    /**
     * Avatar.
     */
    @Getter
    @Setter
    private String avatarUrl;

    /**
     * State favorite of a neighbour.
     */
    @Getter
    @Setter
    private boolean isFavorite = false;

    /**
     * Adress.
     */
    @Getter
    @Setter
    private String adress;

    /**
     * Phone number.
     */
    @Getter
    @Setter
    private String phoneNumber;

    /**
     * Facebook link.
     */
    @Getter
    @Setter
    private String facebookLink;

    /**
     * Neighour description.
     */
    @Getter
    @Setter
    private String description;

}
