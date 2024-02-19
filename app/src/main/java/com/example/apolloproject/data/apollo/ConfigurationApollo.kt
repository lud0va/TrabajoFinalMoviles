package com.example.apolloproject.data.apollo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.example.apolloproject.data.retrofit.CacheAuthorization
import com.example.apolloproject.utils.DataStoreTokens
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.prefs.Preferences
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "")
@Module
@InstallIn(SingletonComponent::class)
class ConfigurationApollo {

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): DataStoreTokens = DataStoreTokens(context)
    @Singleton
    @Provides
    fun cache() : CacheAuthorization =   CacheAuthorization()


    @Singleton
    @Provides
    fun provideInterceptor( cacheAuthorization :CacheAuthorization): AuthenticationInterceptor {
        val interceptor = AuthenticationInterceptor(cacheAuthorization)
        return interceptor
    }

    @Provides
    @Singleton
    fun createApolloClient(authenticationInterceptor: AuthenticationInterceptor): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("http://192.168.37.1:8081/graphql")
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(authenticationInterceptor)
                    .build()
            )
            .build()
    }


}
