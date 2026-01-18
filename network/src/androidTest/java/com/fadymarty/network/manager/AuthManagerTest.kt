package com.fadymarty.network.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import com.fadymarty.network.common.util.Constants
import com.fadymarty.network.data.manager.AuthManagerImpl
import com.fadymarty.network.domain.manager.AuthManager
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AuthManagerTest {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var authManager: AuthManager

    @Before
    fun setUp() {
        dataStore = PreferenceDataStoreFactory.create {
            ApplicationProvider
                .getApplicationContext<Context>()
                .preferencesDataStoreFile(Constants.SETTINGS)
        }
        authManager = AuthManagerImpl(dataStore)
    }

    @Test
    fun saveSession_onClearSession_tokenAndUserIdRemoved() = runTest {
        authManager.saveSession(
            token = "token_123",
            userId = "user_id_123"
        )
        Truth.assertThat(authManager.getToken().first()).isEqualTo("token_123")
        Truth.assertThat(authManager.getUserId().first()).isEqualTo("user_id_123")
        authManager.clearSession()
        Truth.assertThat(authManager.getToken().first()).isEqualTo(null)
        Truth.assertThat(authManager.getUserId().first()).isEqualTo(null)
    }
}