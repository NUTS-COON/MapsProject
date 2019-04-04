package ru.nutscoon.mapsproject.di;

import dagger.Component;
import ru.nutscoon.mapsproject.ViewModels.MainViewModel;

@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MainViewModel mainViewModel);
}
