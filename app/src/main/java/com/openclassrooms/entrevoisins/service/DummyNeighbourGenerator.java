package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe abstraite contenant les données factices nécessaires à l'application.
 */
abstract class DummyNeighbourGenerator {

    /**
     * Liste des voisins.
     */
    static List<Neighbour> DUMMY_NEIGHBOURS = Arrays.asList(
            new Neighbour(1, "Caroline", "http://i.pravatar.cc/150?u=a042581f4e29026704d", false, "Saint pierre du mont à 5km", "+33 6 86 57 90 14", "www.facebook.fr/caroline", "Bonjour! Je souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner! J'aime les jeux de cartes tels la belote et le tarot."),
            new Neighbour(2, "Jack", "http://i.pravatar.cc/150?u=a042581f4e29026704e", false, "Mont de marsan à 1km", "+33 6 86 57 90 15", "www.facebook.fr/jack", "Ducebatur oculos gnarus praecepit suam."),
            new Neighbour(3, "Chloé", "http://i.pravatar.cc/150?u=a042581f4e29026704f", false, "Saint Perdon à 10km", "+33 6 86 57 90 16", "www.facebook.fr/chloé", "Utilia imperatoris coniugem ad mariti imperatoris multa coniugem reducere ad."),
            new Neighbour(4, "Vincent", "http://i.pravatar.cc/150?u=a042581f4e29026704a", false, " Bretagne de Marsan à 11km", "+33 6 86 57 90 17", "www.facebook.fr/vincent", "Diuturnum cum uxor mariti uxor aerario pauperis Publicola humatur filia nobilitas virginis dotatur filia stipe."),
            new Neighbour(5, "Elodie", "http://i.pravatar.cc/150?u=a042581f4e29026704b", false, "Benquet à 15km", "+33 6 86 57 90 18", "www.facebook.fr/elodie", "Et ardore dotis species uxoresque illis nomine futura et nomine ad species sit in post et dotis est futura coniunx."),
            new Neighbour(6, "Sylvain", "http://i.pravatar.cc/150?u=a042581f4e29026704c", false, "Saint-Sever à 28km", "+33 6 86 57 90 19", "www.facebook.fr/sylvain", "Et tempestate Sericus funditabat praefectum negotio rapti tali Campensis ex negotio nomine et Sericus questi et ea ex venenis se eius sunt vitamque praefectum ut coniux sunt emersit negotio in."),
            new Neighbour(7, "Laetitia", "http://i.pravatar.cc/150?u=a042581f4e29026703d", false, "Saint-Avit à 7km", "+33 6 86 57 90 20", "www.facebook.fr/laetitia", "Super fidis vita id prodesse vocis protectoribus Montius ad in quidem statuas addensque fidis nec prodesse post securius post placeret primos fieri quod securius quod super ille perferens tunc adimenda praefecto docens super quod conperto haec mandaverat primos securius conperto."),
            new Neighbour(8, "Dan", "http://i.pravatar.cc/150?u=a042581f4e29026703b", false, "Tartas à 29km", "+33 6 86 57 90 21", "www.facebook.fr/dan", "Spernat contemnere sermo reiciat legant rebus est est litteras sermo Graecis primum spernat hoc in paene dicat primum Medeam facere quibus admirer reiciat contemnere Latina rebus admirer paene in litteras cum scripta est Latina paene igitur eos igitur non delectari Ennii eos non quod contemnere in qui gravissimis non enim."),
            new Neighbour(9, "Joseph", "http://i.pravatar.cc/150?u=a042581f4e29026704d", false, "Grenade sur l'Adour à 26km", "+33 6 86 57 90 22", "www.facebook.fr/joseph", "Ut ulla maioribus priscis est est inpulit super ut priscis Aginatium est fama ad est ulla fama priscis nunc ad."),
            new Neighbour(10, "Emma", "http://i.pravatar.cc/150?u=a042581f4e29026706d", false, "Aire sur l'Adour à 32km", "+33 6 86 57 90 23", "www.facebook.fr/emma", "Praecipua multa sic bene multa summatem te antea praecipua ideo multa multa ante Romam te enixius paeniteat enixius coactusque haec aliquem coactusque tamquam interrogatus summatem coactusque tumentemque ideo suscipieris bene et numquam enixius interrogatus enixius sic tenuem sic introieris enixius tenuem salutatum mentiri bene sic numquam coactusque sic praecipua coactusque nummatum mentiri numquam exoptatus tenuem te tamquam ante suscipieris vidisse haec salutatum visus salutatum paeniteat et haec si vidisse interrogatus honestus ut aliquem exoptatus et Romam tamquam interrogatus vidisse ob introieris te nunc primitus visus et mentiri aliquem ad visus decennium coactusque decennium tamquam ante et suscipieris suscipieris summatem bona."),
            new Neighbour(11, "Patrick", "http://i.pravatar.cc/150?u=a042581f4e29026702d", false, "Villeneuve de Marsan à 13km", "+33 6 86 57 90 24", "www.facebook.fr/patrick", "Potestas rectore sperabatur statuit prope metum quae adesset quae dispelleret quicquam dispelleret multitudini suppliciter transferri ut ut metum statuit profecturus sed difficilisque sed subinde localibus victu inediae ut inediae quorum."),
            new Neighbour(12, "Ludovic", "http://i.pravatar.cc/150?u=a042581f3e39026702d", false, "Rion-des-Landes à 63km", "+33 6 86 57 90 25", "www.facebook.fr/ludovic", "Libentius maxima suo animal modo.")
    );

    /**
     * Genère la listre des voisins.
     *
     * @return les voisins
     */
    static List<Neighbour> generateNeighbours() {
        return new ArrayList<>(DUMMY_NEIGHBOURS);
    }
}
