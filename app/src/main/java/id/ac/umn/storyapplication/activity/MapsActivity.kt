package id.ac.umn.storyapplication.activity

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.ac.umn.storyapplication.R
import id.ac.umn.storyapplication.api.RetrofitClient
import id.ac.umn.storyapplication.databinding.ActivityMapsBinding
import id.ac.umn.storyapplication.model.StoryResponse
import id.ac.umn.storyapplication.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var storyAdapter : StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true




        val loginTokenSpm : String =  SharedPrefManager.getInstance(this).loginResult.token

        RetrofitClient.instance.getStoryMap(token = "Bearer $loginTokenSpm", 60).enqueue(object :
            Callback<StoryResponse> {
            override fun onResponse(
                call: Call<StoryResponse>,
                response: Response<StoryResponse>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.e(ContentValues.TAG, "onSuccess: ${response.message()}")
                    setStoryMap(responseBody.listStory)

                }else{
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure2: ${t.message}")
            }
        })
    }

    private fun setStoryMap(listPeta: List<StoryResponse.Story>) {
        for (peta in listPeta) {
            val latLng = LatLng(peta.lat!!.toDouble(), peta.lon!!.toDouble())

            mMap.addMarker(MarkerOptions()
                .position(latLng)
                .title(peta.name)
                .snippet(peta.description)
            )?.tag = peta

        }
    }

}