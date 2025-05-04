package com.meally.meally.common.auth.di.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.meally.domain.auth.repository.AuthRepository
import com.meally.domain.common.util.Resource
import com.meally.domain.user.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val userRepository: UserRepository,
) : AuthRepository {
    override suspend fun logout() {
        firebaseAuth.signOut()
        userRepository.clearLoggedInUser()
    }

    override suspend fun loginEmailPassword(
        email: String,
        password: String,
    ): Resource<Unit> =
        try {
            logout()
            val result =
                withContext(Dispatchers.IO) {
                    firebaseAuth.signInWithEmailAndPassword(email, password).await()
                }
            if (result.user != null) {
                userRepository.me()
                Resource.Success(Unit)
            } else {
                Resource.Failure(Exception("User login failed: user is null"))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: FirebaseAuthException) {
            Resource.Failure(Exception(mapFirebaseAuthException(e)))
        } catch (e: Exception) {
            Resource.Failure(e)
        }

    override suspend fun signupEmailPassword(
        email: String,
        password: String,
    ) = try {
        logout()
        val result =
            withContext(Dispatchers.IO) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            }
        if (result.user != null) {
            userRepository.me()
            Resource.Success(Unit)
        } else {
            Resource.Failure(Exception("User signup failed: user is null"))
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: FirebaseAuthException) {
        Resource.Failure(Exception(mapFirebaseAuthException(e)))
    } catch (e: Exception) {
        Resource.Failure(e)
    }

    private fun mapFirebaseAuthException(e: FirebaseAuthException): String =
        when (e.errorCode) {
            "ERROR_INVALID_EMAIL" -> "The email address is badly formatted."
            "ERROR_EMAIL_ALREADY_IN_USE" -> "This email is already registered."
            "ERROR_WEAK_PASSWORD" -> "The password is too weak. Please use a stronger one."
            "ERROR_USER_DISABLED" -> "This user account has been disabled."
            "ERROR_OPERATION_NOT_ALLOWED" -> "Email/password sign-in is disabled."
            else -> "Authentication failed. Please try again."
        }
}
