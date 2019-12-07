package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.ChangeNeighbourStateEvent;
import com.openclassrooms.entrevoisins.intents.IntentName;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NeighbourInformationActivity extends AppCompatActivity {

    /**
     * Image de la barre d'application.
     */
    @BindView(R.id.appbar_picture)
    ImageView header;

    /**
     * Layout permettant l'animation de la barre d'outils.
     */
    @BindView(R.id.collasping_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    /**
     * Barre de l'application.
     */
    @BindView(R.id.appbar_information_activity)
    AppBarLayout appBarLayout;

    /**
     * La barre d'outils.
     */
    @BindView(R.id.anim_toolbar)
    Toolbar toolbar;

    /**
     * {@link TextView} qui affiche le nom du voisin(e).
     */
    @BindView(R.id.tv_neighbour_name)
    TextView tvName;

    /**
     * {@link TextView} qui affiche l'adresse du voisin(e).
     */
    @BindView(R.id.tv_neighbour_adress)
    TextView tvAdress;

    /**
     * {@link TextView} qui affiche le numéro de téléphone du voisin(e).
     */
    @BindView(R.id.tv_neighbour_phone)
    TextView tvPhoneNumber;

    /**
     * {@link TextView} qui affiche le lien Facebook du voisin(e).
     */
    @BindView(R.id.tv_neighbour_facebook)
    TextView tvFacebookLink;

    /**
     * {@link TextView} qui affiche la description du voisin(e).
     */
    @BindView(R.id.tv_neighbour_description)
    TextView tvDescription;

    /**
     * Permet l'ajout ou la suppression du statut de favori à un voisin.
     */
    @BindView(R.id.fab_favorite)
    FloatingActionButton fabFavorite;


    /**
     * Service permettant la gestion des voisin(e)s
     */
    private NeighbourApiService mApiService = DI.getNeighbourApiService();

    /**
     * Représente l'état de la barre d'application:
     * <ul>
     * <li>true: barre étendue</li>
     * <li>false: barre réduite</li>
     * </ul>
     */
    private boolean appBarExpanded;

    /**
     * Menu de la barre d'outlis.
     */
    private Menu collapsedMenu;

    /**
     * Représente la couleur la plus présente sur l'image de la barre d'application.
     */
    private int mutedColor;

    /**
     * Voisin(e) séléctioné(e) que l'on souhaite afficher.
     */
    private Neighbour selectedNeighbour;

    private static final String FAVORITE_MENU_ITEM_TITLE = "Favorite";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_neighbour);
        ButterKnife.bind(this);

        // fourni la barre d'application que l'on souhaite utiliser
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationOnClickListener(view -> finish());

        initAppBarLayout();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.selectedNeighbour = this.mApiService.getNeighbour(extras.getInt(IntentName.INFORMATION_ACTIVITY_INTENT_NAME));
            updateComponentValues();
        }
    }

    /**
     * Met à jour les valeurs des composants de la vue.
     */
    private void updateComponentValues() {
        if (this.selectedNeighbour.getId() != Integer.MAX_VALUE) {
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(this.selectedNeighbour.getAvatarUrl())
                    .placeholder(new ColorDrawable(Color.BLUE)) // affichage lors du chargement
                    .error(new ColorDrawable(Color.RED)) // affichage si erreur lors du chargement
                    .fallback(new ColorDrawable(Color.GRAY))// affichage lorsqu'un Url est null
                    .into(new BitmapImageViewTarget(this.header) {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            super.onResourceReady(resource, transition);
                            Palette.from(resource).generate(palette -> {
                                if (palette != null) {
                                    mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                                    collapsingToolbarLayout.setContentScrimColor(mutedColor);
                                } else {
                                    Log.i(getClass().getName(), "palette = null, pas de récupération de couleur");
                                }
                            });
                        }
                    });
            this.collapsingToolbarLayout.setTitle(this.selectedNeighbour.getName());
            this.tvName.setText(this.selectedNeighbour.getName());
            this.tvAdress.setText(this.selectedNeighbour.getAdress());
            this.tvPhoneNumber.setText(this.selectedNeighbour.getPhoneNumber());
            this.tvFacebookLink.setText(this.selectedNeighbour.getFacebookLink());
            this.tvDescription.setText(this.selectedNeighbour.getDescription());
            if (this.selectedNeighbour.isFavorite()) {
                this.fabFavorite.setImageResource(R.drawable.ic_star_white_24dp);
            } else {
                this.fabFavorite.setImageResource(R.drawable.ic_star_border_white_24dp);
            }
        }
    }

    /**
     * Initialisation de la barre d'application en fonction de son état.
     */
    private void initAppBarLayout() {
        this.appBarLayout.addOnOffsetChangedListener((appBarLayout1, i) -> {

            // Vertical offset == 0 indicates appBar is fully expanded
            if (Math.abs(i) >= 250) {
                this.appBarExpanded = false;
                if (getWindow().getStatusBarColor() != this.mutedColor) {
                    setStatusBarColor(this.mutedColor);
                }
                invalidateOptionsMenu();
            } else {
                this.appBarExpanded = true;
                if (getWindow().getStatusBarColor() != Color.TRANSPARENT) {
                    setStatusBarColor(Color.TRANSPARENT);
                }
                invalidateOptionsMenu();
            }
        });
    }

    /**
     * Permet le changement de couleur de la barre de notifications.
     *
     * @param color couleur à afficher
     */
    private void setStatusBarColor(int color) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.collapsedMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (this.collapsedMenu != null && !this.appBarExpanded) {
            // collapsed
            if (this.selectedNeighbour.isFavorite()) {
                this.collapsedMenu.add(FAVORITE_MENU_ITEM_TITLE).setIcon(R.drawable.ic_star_white_24dp)
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            } else {
                this.collapsedMenu.add(FAVORITE_MENU_ITEM_TITLE).setIcon(R.drawable.ic_star_border_white_24dp)
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            }
        }
        return super.onPrepareOptionsMenu(this.collapsedMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(FAVORITE_MENU_ITEM_TITLE)) {
            if (this.selectedNeighbour.isFavorite()) {
                item.setIcon(R.drawable.ic_star_border_white_24dp);
                this.selectedNeighbour.setFavorite(false);
            } else {
                item.setIcon(R.drawable.ic_star_white_24dp);
                this.selectedNeighbour.setFavorite(true);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab_favorite)
    public void changeNeighbourFavoriteState() {
        // Ajout des .hide() et .show() car bug google dans la v28.0.0. Le logo du FAB disparait. A supprimer une fois résolu
        if (this.selectedNeighbour.isFavorite()) {
            this.fabFavorite.hide();
            this.fabFavorite.setImageResource(R.drawable.ic_star_border_white_24dp);
            this.fabFavorite.show();
            this.selectedNeighbour.setFavorite(false);
            EventBus.getDefault().post(new ChangeNeighbourStateEvent(this.selectedNeighbour));
        } else {
            this.fabFavorite.hide();
            this.fabFavorite.setImageResource(R.drawable.ic_star_white_24dp);
            this.fabFavorite.show();
            this.selectedNeighbour.setFavorite(true);
            EventBus.getDefault().post(new ChangeNeighbourStateEvent(this.selectedNeighbour));
        }
    }
}
