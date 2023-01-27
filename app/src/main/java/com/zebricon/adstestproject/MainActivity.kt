package com.zebricon.adstestproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.android.gms.ads.*
import com.zebricon.adstestproject.databinding.ActivityMainBinding
import com.zebricon.adstestproject.databinding.AdsFramelayoutBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Log the Mobile Ads SDK version.
        Log.d(TAG, "Google Mobile Ads SDK Version: " + MobileAds.getVersion())
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this) {}

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Scaffold { paddingValues ->
                    Column(modifier = Modifier
                        .background(color = colorResource(id = R.color.white))
                        .padding(paddingValues = paddingValues)
                        .fillMaxWidth()
                        .fillMaxHeight()) {
                        Surface(
                            // on below line we are specifying modifier and color for our app
                            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                        ) {
                           AdaptiveAdsBanner()
                        }
                    }

                }

            }
        }
    }

    @Composable
    private fun AdaptiveAdsBanner() {
        AndroidViewBinding(factory = { layoutInflater, container, bool ->
            val view = AdsFramelayoutBinding.inflate(layoutInflater, container, bool)
            var adListener = object: AdListener() {
                override fun onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                    Log.e(" ads onAdClicked:",  "clicked")
                }

                override fun onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                    Log.e(" ads onAdClosed:", "onAdClosed")
                }

                override fun onAdFailedToLoad(adError : LoadAdError) {
                    // Code to be executed when an ad request fails.
                    Log.e(" ads adError:", adError.toString())
                }

                override fun onAdImpression() {
                    // Code to be executed when an impression is recorded
                    // for an ad.
                    Log.e(" ads:", "onAdImpression")
                }

                override fun onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    Log.e(" ads:", "onAdLoaded")
                }

                override fun onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                    Log.e("ads:", "onAdOpened")
                }
            }
            loadAdaptiveAds(this@MainActivity,this@MainActivity,view.adViewContainer,
                "ca-app-pub-3940256099942544/9214589741",adListener)
            view
        }){

        }
    }
}