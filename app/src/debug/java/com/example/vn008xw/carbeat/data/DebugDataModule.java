package com.example.vn008xw.carbeat.data;

import com.example.vn008xw.carbeat.di.ApplicationScope;
import com.example.vn008xw.carbeat.qualifier.AnimationSpeed;
import com.example.vn008xw.carbeat.qualifier.NetworkDelay;
import com.example.vn008xw.carbeat.qualifier.NetworkFailurePercent;
import com.example.vn008xw.carbeat.qualifier.NetworkVariancePercent;
import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by vn008xw on 6/7/17.
 */
@Module
public class DebugDataModule {

  @Provides
  @ApplicationScope
  Preference<Long> provideNetworkDelay(RxSharedPreferences preferences) {
    return preferences.getLong("debug_networK_delay", 2000l);
  }

  @Provides
  @ApplicationScope
  @NetworkFailurePercent
  Preference<Integer> provideNetworkFailurePercent(RxSharedPreferences preferences) {
    return preferences.getInteger("debug_network_failure_percent", 3);
  }

  @Provides
  @ApplicationScope
  @NetworkVariancePercent
  Preference<Integer> provideNetworkVariancePercent(RxSharedPreferences preferences) {
    return preferences.getInteger("debug_network_variance_percent", 20);
  }

  @Provides
  @ApplicationScope
  @AnimationSpeed
  Preference<Integer> provideAnimationSpeed(RxSharedPreferences preferences) {
    return preferences.getInteger("debug_animation_speed", 1);
  }

  @Provides
  @ApplicationScope
  NetworkBehavior provideBehavior(@NetworkDelay Preference<Long> networkDelay,
                                  @NetworkFailurePercent Preference<Integer> networkFailurePercent,
                                  @NetworkVariancePercent Preference<Integer> networkVariancePercent) {

    NetworkBehavior behavior = NetworkBehavior.create();
    behavior.setDelay(networkDelay.get(), MILLISECONDS);
    behavior.setFailurePercent(networkFailurePercent.get());
    behavior.setVariancePercent(networkVariancePercent.get());
    return behavior;
  }

  @Provides
  @ApplicationScope
  MockRetrofit provideMockRetrofit(Retrofit retrofit, NetworkBehavior behavior) {
    return new MockRetrofit.Builder(retrofit).networkBehavior(behavior).build();
  }

}
