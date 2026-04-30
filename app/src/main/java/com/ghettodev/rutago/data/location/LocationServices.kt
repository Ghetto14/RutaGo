package com.ghettodev.rutago.data.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.tasks.await

class LocationService(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    suspend fun getCurrentLocation(): Location? {
        if (!hasLocationPermission()) {
            Log.d("UBICACION", "Sin permiso")
            return null
        }

        return try {
            // Primero intenta la última ubicación conocida
            val lastLocation = fusedLocationClient.lastLocation.await()
            if (lastLocation != null) {
                Log.d("UBICACION", "Última ubicación: ${lastLocation.latitude}, ${lastLocation.longitude}")
                return lastLocation
            }

            // Si no hay última ubicación, pide una nueva
            Log.d("UBICACION", "Pidiendo ubicación nueva...")
            val newLocation = fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(
                        callback: OnTokenCanceledListener
                    ): CancellationToken {
                        callback.onCanceled()
                        return this
                    }
                    override fun isCancellationRequested(): Boolean = false
                }
            ).await()
            Log.d("UBICACION", "Nueva ubicación: ${newLocation?.latitude}, ${newLocation?.longitude}")
            newLocation
        } catch (e: SecurityException) {
            Log.d("UBICACION", "Error seguridad: ${e.message}")
            null
        } catch (e: Exception) {
            Log.d("UBICACION", "Error: ${e.message}")
            null
        }
    }
}