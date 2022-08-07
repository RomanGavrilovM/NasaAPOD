package com.example.nasaapod.ui.mars

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nasaapod.R
import com.example.nasaapod.databinding.MarsFragmentBinding
import com.example.nasaapod.domain.MarsRepositoryImp
import com.example.nasaapod.ui.transforms.DepthPageTransformer
import com.google.android.material.tabs.TabLayoutMediator

/**
 * try add zoom
 */

class MarsFragment : Fragment(R.layout.mars_fragment) {

    private lateinit var binding: MarsFragmentBinding

    private val marsViewModel: MarsViewModel by viewModels {
        MarsViewModel.MarsViewModelFactory(MarsRepositoryImp())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            marsViewModel.requestMars()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MarsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var size = 0
        val fragment = this

        binding.marsViewPager.setPageTransformer(DepthPageTransformer())

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            marsViewModel.photos.collect { response ->
                response?.let {
                    size = it.latestPhotos.size
                    Log.d("HAPPY", "size= " + size)
                    binding.marsViewPager.adapter = MarsPagerAdapter(fragment, size)
                    TabLayoutMediator(binding.marsTabs, binding.marsViewPager) { tab, position ->
                        tab.icon = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_mars
                        )
                    }.attach()
                }
            }

        }

    }

    class MarsPagerAdapter(fragment: Fragment, count: Int) : FragmentStateAdapter(fragment) {
        val size = count
        override fun getItemCount(): Int = size
        override fun createFragment(position: Int): Fragment = MarsPageFragment.instance(position)

    }
}