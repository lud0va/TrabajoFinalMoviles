package com.example.apolloproject.data.apollo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.example.apolloproject.data.apollo.AuthenticationInterceptor
import com.example.apolloproject.data.retrofit.AuthorizationInterceptor
import com.example.apolloproject.data.retrofit.CacheAuthorization
import com.example.apolloproject.data.retrofit.calls.CredentialApi
import com.example.apolloproject.domain.graph.CustomerClient
import com.example.apolloproject.domain.usecases.customers.GetCustomersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ConfigurationApollo {
    @Singleton
    @Provides
    fun provideInterceptor(): AuthenticationInterceptor {
        val cacheAuthorization = CacheAuthorization()
        val interceptor = AuthenticationInterceptor(cacheAuthorization)
        return interceptor
    }

    @Provides
    @Singleton
    fun createApolloClient(authenticationInterceptor: AuthenticationInterceptor): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("http://192.168.1.24:8081/graphql")
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(authenticationInterceptor)
                    .build()
            )
            .build()
    }


}
