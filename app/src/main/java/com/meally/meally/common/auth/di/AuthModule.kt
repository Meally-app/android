package com.meally.meally.common.auth.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import org.koin.dsl.module

val authModule =
    module {
        single<FirebaseAuth> {
            Firebase.auth
        }
    }
