package com.example.nasaapod.ui.settings

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.LayoutTransition
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.transition.*
import com.example.nasaapod.R
import com.example.nasaapod.databinding.FragmentSettingsBinding
import com.example.nasaapod.ui.MainActivity.Companion.KEY_THEME

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding

    private var savedTheme: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
         * идея такая нажимаем на главный фаб
         * он исчезает получаем 2 кнопки по смене темы
         *
         * пробовал сделать обратный "съезд" кнопок и только потом чтобы было перключение темы
         * нужен колбэк
         * решил через animate()!
         *
         */


        binding.settingsButton.setOnClickListener {

            binding.settingsButton.hide()
            moveSpaceThemeButtonUp()
            moveAlterThemeButtonUp()
            moveTitleUp()

        }



        binding.themeSpaceButton.setOnClickListener {
            savedTheme = R.style.Theme_NasaAPOD
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            with(sharedPref!!.edit()) {
                putInt(KEY_THEME, savedTheme!!)
                apply()
            }
            moveThemeButtonsDown()
        }

        binding.themeAlterSpaceButton.setOnClickListener {
            savedTheme = R.style.Theme_NasaAPOD_alternative
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            with(sharedPref!!.edit()) {
                putInt(KEY_THEME, savedTheme!!)
                apply()
            }
            moveThemeButtonsDown()
        }

    }

    private fun moveSpaceThemeButtonUp() {
        binding.themeSpaceButton.apply {
            ChangeBounds()
                .apply {
                    setPathMotion(ArcMotion().apply {
                        this.maximumAngle = 90.0f
                    })
                    duration = 3000

                }
                .also {

                    TransitionManager.beginDelayedTransition(binding.root, it)

                }

            updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.UNSET
            }
            animate()
                .alpha(1f)
                .setDuration(3000)
                .setListener(object  : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                   //     Toast.makeText(requireContext(),"HEHEH",Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    /**
     *Делаем обратную анимацию
     * т.к. независимо от быбора темы кнопки сдвигаются
     * и только после этого пересоздается тема то можно привязать
     * пересоздание активити толко после сдвига какой либо из кнопки
     * тайминги одинаковы так что визуально не должно отличаться
     *
     */

    private fun moveAlterThemeButtonUp() {
        binding.themeAlterSpaceButton.apply {
            ChangeBounds()
                .apply {
                    setPathMotion(ArcMotion().apply {
                        this.maximumAngle = 90.0f
                    })
                    duration = 3000

                }
                .also {
                    TransitionManager.beginDelayedTransition(binding.root, it)
                }
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = ConstraintLayout.LayoutParams.UNSET
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
            animate()
                .alpha(1f)
                .setDuration(3000)
                .setListener(object  : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        //     Toast.makeText(requireContext(),"HEHEH",Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun moveTitleUp(){
        binding.settingsTitle.apply {
            ChangeBounds()
                .apply {
                    setPathMotion(ArcMotion().apply {
                        this.maximumAngle = 0.0f
                    })
                    duration = 3000

                }
                .also {
                    TransitionManager.beginDelayedTransition(binding.root, it)
                }
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
            animate()
                .alpha(1f)
                .setDuration(3000)
        }
    }

    private fun moveThemeButtonsDown() {
        binding.themeSpaceButton.apply {
            ChangeBounds()
                .apply {
                    setPathMotion(ArcMotion().apply {
                        this.maximumAngle = 90.0f
                    })
                    duration = 3000
                }
                .also {

                    TransitionManager.beginDelayedTransition(binding.root, it)
                }

            updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.UNSET
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
            animate()
                .alpha(1f)
                .setDuration(3000)
                .setListener(object  : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        requireActivity().recreate()
                    }
                })
        }
        binding.themeAlterSpaceButton.apply {
            ChangeBounds()
                .apply {
                    setPathMotion(ArcMotion().apply {
                        this.maximumAngle = 90.0f
                    })
                    duration = 3000

                }
                .also {
                    TransitionManager.beginDelayedTransition(binding.root, it)
                }

            updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.UNSET
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
            animate()
                .alpha(1f)
                .setDuration(3000)
                .setListener(object  : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                    }
                })
        }


    }


}