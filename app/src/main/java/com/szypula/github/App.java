package com.szypula.github;

import android.app.Application;

import com.szypula.github.core.di.DaggerSingletonComponent;
import com.szypula.github.core.di.SingletonComponent;
import com.szypula.github.core.di.SingletonModule;

public class App extends Application {

    private static SingletonComponent singletonComponent;

    public static SingletonComponent getSingletonComponent() {
        return singletonComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        singletonComponent = DaggerSingletonComponent.builder()
                                                     .singletonModule(new SingletonModule(this))
                                                     .build();
    }
}
