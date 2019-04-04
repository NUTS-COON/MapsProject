package ru.nutscoon.mapsproject.di;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nutscoon.mapsproject.Services.IOrganizationsService;
import ru.nutscoon.mapsproject.Services.OrganizationsService;

@Module
public class ApplicationModule {

    @Provides
    public IOrganizationsService getOrganizationService(){
        //return getRetrofit().create(IOrganizationsService.class);
        return new OrganizationsService();
    }

    private Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl("api_url")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
