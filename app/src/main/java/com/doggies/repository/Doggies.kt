package com.doggies.repository

import android.util.Log
import com.doggies.util.Breed
import com.doggies.util.Dog
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class Doggies {
    companion object {
        private val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging){
                logger = object : Logger {
                    override fun log(message: String) {
                        if (message.startsWith("RESPONSE")){
                            Log.d("Ktor Client", message)
                        }
                    }
                }
                level = LogLevel.INFO
            }
        }

        suspend fun getAllDogs(): Dog {
            return try {
                client.get("https://dog.ceo/api/breeds/image/random/20").body()
            } catch (e: Exception) {
                Dog(emptyList(), "Error: ${e.message}")
            }
        }

        suspend fun getAllBreed(): Breed {
            return try {
                client.get("https://dog.ceo/api/breeds/list/all").body()
            } catch (e: Exception) {
                Breed(emptyMap(), "Error: ${e.message}")
            }
        }

        suspend fun getDogsByBreed(breed: String): Dog {
            return try {
                client.get("https://dog.ceo/api/breed/$breed/images/random/20").body()
            } catch (e: Exception) {
                Dog(emptyList(), "Error: ${e.message}")
            }
        }
    }
}