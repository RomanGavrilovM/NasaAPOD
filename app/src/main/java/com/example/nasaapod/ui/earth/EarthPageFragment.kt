package com.example.nasaapod.ui.earth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.text.substring
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.transition.Fade
import androidx.transition.TransitionManager
import coil.load
import com.example.nasaapod.R
import com.example.nasaapod.api.epic.EpicResponseDTO
import com.example.nasaapod.databinding.FragmentPageEarthBinding
import com.example.nasaapod.domain.EpicRepositoryImp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class EarthPageFragment : Fragment(R.layout.fragment_page_earth) {

    val FIRST_PART_URL = "https://epic.gsfc.nasa.gov/archive/natural/"
    val THIRD_PART_URL = "/jpg/"
    val LAST_PART_URL = ".jpg"


    companion object {
        private const val ARG_NUMBER = "ARG_NUMBER"

        fun instance(number: Int) = EarthPageFragment().apply {
            arguments = bundleOf(ARG_NUMBER to number)
        }
    }


    private lateinit var binding: FragmentPageEarthBinding

    private val epicViewModel: EpicViewModel by viewModels {
        EpicViewModel.EpicViewModelFactory(EpicRepositoryImp())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epicViewModel.requestEpic()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPageEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            epicViewModel.loading.collect() {
                binding.progressBarEarth.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            epicViewModel.error.collect() {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        arguments?.let {
            val id: Int = it.getInt(ARG_NUMBER)

            Log.d("HAPPY", "id in create is= " + id.toString())
            viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
                epicViewModel.epicList.collect() { list ->
                    val secondPartUrl = list[id]?.date?.let { it1 -> convertDate(it1) }
                    val url =
                        FIRST_PART_URL + secondPartUrl + THIRD_PART_URL + list[id]?.image + LAST_PART_URL
                    binding.epicImage.load(url){
                        crossfade(true)
                        crossfade(3000)
                    }
                    binding.latitude.text = list[id]?.centroidCoordinates?.lat.toString()
                    binding.longitude.text = list[id]?.centroidCoordinates?.lon.toString()
                    binding.dateImage.text = list[id]?.date

                }
            }
        }

        binding.epicImage.setOnClickListener {  }
    }

    fun convertDate(inputString: String): String {

        return inputString.replace("-", "/").substring(0, 10)
    }

    private fun showEarth() {
        TransitionManager.beginDelayedTransition(
            binding.root,
            Fade().setDuration(2000).addTarget(binding.epicImage)
        )
    }

}