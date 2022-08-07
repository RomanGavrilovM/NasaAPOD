package com.example.nasaapod.ui.earth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nasaapod.R
import com.example.nasaapod.databinding.EarthFragmentBinding
import com.example.nasaapod.domain.EpicRepositoryImp
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class EarthFargment : Fragment(R.layout.earth_fragment) {

    private lateinit var binding: EarthFragmentBinding


    private val epicViewModel: EpicViewModel by viewModels {
        EpicViewModel.EpicViewModelFactory(EpicRepositoryImp())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            epicViewModel.requestEpic()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EarthFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var size = 0
        val fragment = this

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            epicViewModel.loading.collect(){
                binding.progressBarEarth.visibility =  if (it) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            epicViewModel.epicList.collect() { response ->
                response?.let {
                    size = it.size
                    binding.earthViewPager.adapter = EpicPagerAdapter(fragment, size)
                    TabLayoutMediator(binding.earthTabs, binding.earthViewPager) { tab, position ->
                        tab.icon = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_epic
                        )
                    }.attach()
                }

            }

        }


    }

}

class EpicPagerAdapter(fragment: Fragment, count: Int) : FragmentStateAdapter(fragment) {
    val size = count
    override fun getItemCount(): Int = size
    override fun createFragment(position: Int): Fragment = EarthPageFragment.instance(position)

}

