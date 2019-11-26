package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeighbourInformationActivity extends AppCompatActivity {

    @BindView(R.id.appbar_picture)
    ImageView header;
    @BindView(R.id.collasping_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appbar_information_activity)
    AppBarLayout appBarLayout;
    @BindView(R.id.anim_toolbar)
    Toolbar toolbar;


    NeighbourApiService mApiService = DI.getNeighbourApiService();
    private boolean appBarExpanded;
    private Menu collapsedMenu;
    private int mutedColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_information_neighbour);
        ButterKnife.bind(this);

        setSupportActionBar(this.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        appBarLayout.addOnOffsetChangedListener((appBarLayout1, i) -> {

            // Vertical offset == 0 indicates appBar is fully expanded
            if (Math.abs(i) >= 250) {
                appBarExpanded = false;
                if (getWindow().getStatusBarColor() != mutedColor) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    getWindow().setStatusBarColor(mutedColor);
                }
                invalidateOptionsMenu();
            } else {
                appBarExpanded = true;
                if (getWindow().getStatusBarColor() != Color.TRANSPARENT) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    getWindow().setStatusBarColor(Color.TRANSPARENT);
                }
                invalidateOptionsMenu();
            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Neighbour selectedNeighbour = mApiService.getNeighbour(extras.getInt("neighbour.id"));
            if (selectedNeighbour.getId() != Integer.MAX_VALUE) {

                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(selectedNeighbour.getAvatarUrl())
                        .placeholder(new ColorDrawable(Color.BLUE)) // affichage lors du chargement
                        .error(new ColorDrawable(Color.RED)) // affichage si erreur lors du chargement
                        .fallback(new ColorDrawable(Color.GRAY))// affichage lorsqu'un Url est null
                        .into(new BitmapImageViewTarget(this.header) {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                super.onResourceReady(resource, transition);
                                Palette.from(resource).generate(palette -> {
                                    mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                                    collapsingToolbarLayout.setContentScrimColor(mutedColor);
                                });
                            }
                        });
                this.collapsingToolbarLayout.setTitle(selectedNeighbour.getName());
                // TODO: Mise à jour composants
            }
        }
    }

    private void setStatusBarColor(int statusColor) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        collapsedMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (this.collapsedMenu != null && !this.appBarExpanded) {
            // collapsed
            collapsedMenu.add("Add").setIcon(R.drawable.ic_star_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        return super.onPrepareOptionsMenu(this.collapsedMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.drawable.ic_star_white_24dp:
                Toast.makeText(this, "Clic favori", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Default case", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
