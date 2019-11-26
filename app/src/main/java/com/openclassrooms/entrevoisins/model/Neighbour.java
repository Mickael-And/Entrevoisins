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
    private boolean isFavorite = false;

    /**
     * Adress.
     */
    @Getter
    @Setter
    private String adress = "Saint pierre du mont à 5km";

    /**
     * Phone number.
     */
    @Getter
    @Setter
    private String phoneNumber = "+33 6 86 57 90 14";

    /**
     * Facebook link.
     */
    @Getter
    @Setter
    private String facebookLink = "www.facebook.fr/";

    /**
     * Neighour description.
     */
    @Getter
    @Setter
    private String description = "Bonjour! Je osuhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner! J'aime les jeux de cartes tels la belote et le tarot.";

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
