package com.batofgotham.moviereviews.data.database

import android.content.Context
import androidx.room.Room
import com.batofgotham.moviereviews.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabaseInstance(@ApplicationContext context: Context): MovieDatabase{
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao{
        return movieDatabase.movieDao
    }

    @Singleton
    @Provides
    fun provideRemoteKeyDao(movieDatabase: MovieDatabase): RemoteKeyDao{
        return movieDatabase.remoteKeyDao
    }
}