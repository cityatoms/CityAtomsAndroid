package com.github.abdularis.trackmylocation.dagger;

import com.github.abdularis.trackmylocation.network.ApiClientModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiClientModule.class})
public interface ApiComponent {

    //void inject(MainActivity mainActivity);
}