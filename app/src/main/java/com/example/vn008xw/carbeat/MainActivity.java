package com.example.vn008xw.carbeat;

import android.app.Application;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vn008xw.carbeat.base.BaseActivity;
import com.example.vn008xw.carbeat.databinding.ActivityMainBinding;
import com.example.vn008xw.carbeat.ui.NavigationController;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

  @Inject
  Application application;

  @Inject
  NavigationController navigationController;

  private DrawerLayout mDrawerLayout;
  private ActionBarDrawerToggle mToggle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    final ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    onCreateView(MainActivity.this);

    setContentView(R.layout.activity_main);

    final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    final ActionBar actionBar = getSupportActionBar();
    actionBar.setHomeButtonEnabled(true);
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setDisplayShowTitleEnabled(true);
    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mToggle = new ActionBarDrawerToggle(
            this, mDrawerLayout, toolbar, R.string.nav_open, R.string.nav_close
    );
    mDrawerLayout.setDrawerListener(mToggle);
    mToggle.setDrawerIndicatorEnabled(true);
    mToggle.syncState();


    final NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
    setupDrawerContent(navView);
    setTitle(getString(R.string.nav_menu_home));
    navigationController.navigateToMovies(this);
  }

  private void setupDrawerContent(@NonNull NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener((menuItem) -> {
      uncheckItems(navigationView.getMenu());
      switch (menuItem.getItemId()) {
        case R.id.home_nav_menu_item:
          navigationController.navigateToMovies(this);
          setTitle(getString(R.string.nav_menu_home));
          break;
        case R.id.favorites_nav_menu_item:
          setTitle(getString(R.string.nav_menu_favorites));
          navigationController.navigateToFavorites(this);
          break;
        case R.id.poster_nav_menu_item:
          // TODO navigate to poster fragment when you're created it
          break;
        case R.id.account_nav_menu_item:

          break;
        default:
          break;
      }
      menuItem.setChecked(true);
      mDrawerLayout.closeDrawers();
      return true;
    });
  }

  private void uncheckItems(@NonNull final Menu menu) {
    for(int i = 0; i< menu.size(); ++i) {
      final MenuItem item = menu.getItem(i);
      item.setChecked(false);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        mDrawerLayout.openDrawer(GravityCompat.START);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void inject(@NotNull AppComponent appComponent) {
    appComponent.inject(this);
  }

  @Override
  public void setTitle(CharSequence title) {
    getSupportActionBar().setTitle(title);
  }
}
