package com.zebricon.adstestproject

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.widget.FrameLayout
import com.google.android.gms.ads.*

private var initialLayoutComplete = false
fun loadAdaptiveAds(
    context: Context?, activity: Activity, ad_view_container: FrameLayout?,
    adcode: String?,
    adListener: AdListener
) {
        if (context != null && ad_view_container != null  && adcode != null && adcode.isNotEmpty()) {

                try {
                    val adSize: AdSize = getAdSize(activity, ad_view_container)
                    val adView = AdView(context)
                    ad_view_container.addView(adView)
                    ad_view_container.viewTreeObserver?.addOnGlobalLayoutListener {
                        if (!initialLayoutComplete) {
                            initialLayoutComplete = true
                            adView.adUnitId = adcode
                            adView.setAdSize(adSize)
                            val adRequest = AdRequest.Builder().build()
                            adView.loadAd(adRequest)
                        }
                    }
                    adView.adListener = adListener
                } catch (e: Exception) {
                    e.let { Log.e("Utils Adaptive ads:", e.toString()) }
                }
            }

    }
fun getAdSize(context: Activity, ad_view_container: FrameLayout): AdSize {
    val display = context.windowManager.defaultDisplay
    val outMetrics = DisplayMetrics()
    display.getMetrics(outMetrics)

    val density = outMetrics.density

    var adWidthPixels = ad_view_container.width.toFloat()
    if (adWidthPixels == 0f) {
        adWidthPixels = outMetrics.widthPixels.toFloat()
    }

    val adWidth = (adWidthPixels / density).toInt()
    return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)

}