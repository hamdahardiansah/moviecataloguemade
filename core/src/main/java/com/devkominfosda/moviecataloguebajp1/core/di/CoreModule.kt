package com.devkominfosda.moviecataloguebajp1.core.di

import androidx.room.Room
import com.devkominfosda.moviecataloguebajp1.core.BuildConfig
import com.devkominfosda.moviecataloguebajp1.core.data.source.MoviesRepository
import com.devkominfosda.moviecataloguebajp1.core.data.source.local.LocalDataSource
import com.devkominfosda.moviecataloguebajp1.core.data.source.local.room.MoviesDatabase
import com.devkominfosda.moviecataloguebajp1.core.data.source.remote.RemoteDataSource
import com.devkominfosda.moviecataloguebajp1.core.data.source.remote.service.ApiService
import com.devkominfosda.moviecataloguebajp1.core.domain.repository.IMoviesRepository
import com.devkominfosda.moviecataloguebajp1.core.utils.AppExecutors
import com.devkominfosda.moviecataloguebajp1.core.utils.Constants
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MoviesDatabase>().moviesDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(Constants.PACKAGE_NAME.toCharArray())
        val factory = SupportFactory(passphrase)

        Room.databaseBuilder(
            androidContext(),
            MoviesDatabase::class.java, Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = Constants.HOST_NAME
        val certificatePinier = CertificatePinner.Builder()
            .add(hostname, Constants.SHA1)
            .add(hostname, Constants.SHA2)
            .add(hostname, Constants.SHA3)
            .build()

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinier)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { AppExecutors() }
    single<IMoviesRepository> {
        MoviesRepository(
            get(),
            get(),
            get()
        )
    }
}