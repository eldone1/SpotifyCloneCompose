package com.idone.firebasecompose.data

import com.idone.firebasecompose.FirebaseComposeApp.Companion.context
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.tasks.await

class Repository {

    companion object {
        const val MIN_VERSION = "min_version"
    }

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig.apply {
        setConfigSettingsAsync(remoteConfigSettings { minimumFetchIntervalInSeconds = 30 })
        fetchAndActivate()
    }

    suspend fun getMinAllowedVersion(): List<Int> {
        remoteConfig.fetch(0)
        remoteConfig.activate().await()
        val minVersion = remoteConfig.getString(MIN_VERSION)
        return if (minVersion.isBlank()) listOf(0, 0, 0)
        else minVersion.split(".").map { it.toInt() }
    }

    fun getCurrentVersion(): List<Int> {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName?.split(".")?.map { it.toInt() } ?: listOf(0, 0, 0)
        } catch (e:Exception) {
            listOf(0, 0, 0)
        }
    }
}