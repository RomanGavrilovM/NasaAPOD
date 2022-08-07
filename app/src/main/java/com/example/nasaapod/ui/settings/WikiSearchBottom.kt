package com.example.nasaapod.ui.settings

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.example.nasaapod.R
import com.example.nasaapod.databinding.SearchFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
/**
пока выпилено, передет в отлеьный фрагмент с настройками  или тип того


*/

class wikiSearchBottom: BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.search_fragment,container,false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = SearchFragmentBinding.bind(view)

        binding.searchLayout.setEndIconOnClickListener {

            binding.webView.webViewClient = object : WebViewClient () {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    url: String?
                ): Boolean {
                    if (url != null) {
                        view?.loadUrl(url)
                    }
                    return true
                }
            }
            binding.webView.loadUrl(getString(R.string.wiki_url)+binding.searchInput.text)

        }


    }
}