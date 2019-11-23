package com.openclassrooms.entrevoisins.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model object representing a Neighbour
 */
@EqualsAndHashCode(of = {"id"})
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
    private boolean isFavorite = true;

    /**
     * Constructeur sans isFavorite.
     *
     * @param id        id
     * @param name      name
     * @param avatarUrl chemin de l'avatar
     */
    public Neighbour(Integer id, String name, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }
}
