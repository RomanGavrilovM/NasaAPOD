package com.example.nasaapod.ui.apod

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nasaapod.R
import com.example.nasaapod.databinding.NasaApodFragmentBinding
import com.example.nasaapod.domain.NasaApodRepositoryImp
import com.example.nasaapod.ui.MainActivity
import com.example.nasaapod.ui.transforms.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDate

class NasaApodFragment : Fragment(R.layout.nasa_apod_fragment) {

    private lateinit var binding: NasaApodFragmentBinding

    private val viewModel: NasaApodViewModel by viewModels {
        NasaApodViewModel.NasaApodViewModelFactory(NasaApodRepositoryImp())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.requestApod()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NasaApodFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = this


        binding.apodViewPager.setPageTransformer(ZoomOutPageTransformer())

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.today.collect() { day ->
                binding.apodViewPager.adapter = NasaApodPagerAdapter(fragment)
                TabLayoutMediator(binding.apodTabs, binding.apodViewPager) { tab, position ->

                    when (position) {
                        0 -> tab.text = day.date
                        1 -> tab.text = LocalDate.now().minusDays(1).toString()
                        2 -> tab.text = LocalDate.now().minusDays(2).toString()
                    }
                    (requireActivity() as MainActivity).setSplashScreen()
                    tab.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_apod
                    )
                }.attach()
            }
        }

    }

    class NasaApodPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3
        override fun createFragment(position: Int): Fragment =
            NasaApodPageFragment.instance(position)
    }

}
