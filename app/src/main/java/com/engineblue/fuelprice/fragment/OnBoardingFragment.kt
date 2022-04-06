package com.engineblue.fuelprice.fragment

import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.adapter.onboarding.OnBoardingAdapter
import com.engineblue.fuelprice.databinding.OnboardingFragmentBinding
import com.engineblue.fuelprice.fragment.base.BaseFragment
import com.engineblue.presentation.entity.OnBoardingItemDisplayModel
import com.engineblue.presentation.viewmodel.OnBoardingViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnBoardingFragment : BaseFragment() {

    private val viewModel: OnBoardingViewModel by sharedViewModel()

    lateinit var binding: OnboardingFragmentBinding

    private lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnboardingFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnBoardingItems()
        setupOnBoardingIndicators()
        setupViewPager()
        setupButton()
    }

    private fun setupButton() {
        binding.buttonOnBoardingSkipAction.setOnClickListener {
            viewModel.saveOnboardingChecked(getString(R.string.pref_first_start))
            navigateToConfigurationFuelProduct()
        }

        binding.goBackButton.setOnClickListener {
            if (binding.onBoardingViewPager.currentItem >= 0) {
                binding.onBoardingViewPager.currentItem =
                    binding.onBoardingViewPager.currentItem - 1
            }

            binding.buttonOnBoardingSkipAction.visibility = View.VISIBLE
        }

        binding.buttonOnBoardingAction.setOnClickListener {
            if (binding.onBoardingViewPager.currentItem + 1 < onBoardingAdapter.itemCount) {
                binding.onBoardingViewPager.currentItem =
                    binding.onBoardingViewPager.currentItem + 1

                binding.buttonOnBoardingSkipAction.visibility = View.VISIBLE
            } else {
                binding.buttonOnBoardingSkipAction.visibility = View.GONE

                viewModel.saveOnboardingChecked(getString(R.string.pref_first_start))
                navigateToConfigurationFuelProduct()
            }
        }
    }

    private fun navigateToConfigurationFuelProduct() {
        findNavController().navigate(R.id.action_onBoardingFragment_to_configuration_fuel_product)
    }

    private fun setupViewPager() {
        binding.onBoardingViewPager.adapter = onBoardingAdapter
        binding.onBoardingViewPager.isUserInputEnabled = false
        binding.onBoardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentOnBoardingIndicator(position)
                updateButtonStatus(position)
            }
        })
    }

    private fun updateButtonStatus(currentIndex: Int) {
        if (currentIndex == 0) {
            binding.goBackButton.visibility = View.GONE
        } else {
            binding.goBackButton.visibility = View.VISIBLE
        }

        if (currentIndex == onBoardingAdapter.itemCount - 1) {
            binding.buttonOnBoardingAction.text = getString(R.string.start)
        } else {
            binding.buttonOnBoardingAction.text = getString(R.string.next)
        }
    }

    private fun setupOnBoardingIndicators() {
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutParams.setMargins(16, 0, 16, 0)

        var i = 0

        while (i < onBoardingAdapter.itemCount) {
            val image = ImageView(requireContext())

            if (i == 0) {
                image.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.onboarding_indicator_active
                    )
                )
            } else {
                image.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.onboarding_indicator_inactive
                    )
                )
            }

            image.layoutParams = layoutParams
            binding.layoutOnBoardingIndicators.addView(image)
            i++
        }
    }

    private fun setupOnBoardingItems() {
        val items = arrayListOf<OnBoardingItemDisplayModel>()

        items.add(
            OnBoardingItemDisplayModel(
                R.drawable.cost_control_boarding,
                getString(R.string.onboarding_cost_title),
                getString(R.string.onboarding_cost_description)
            )
        )

        items.add(
            OnBoardingItemDisplayModel(
                R.drawable.fuel_boarding,
                getString(R.string.onboarding_fuel_title),
                getString(R.string.onboarding_fuel_description)
            )
        )

        items.add(
            OnBoardingItemDisplayModel(
                R.drawable.secure_data_boarding,
                getString(R.string.onboarding_secure_title),
                getString(R.string.onboarding_secure_description)
            )
        )

        onBoardingAdapter = OnBoardingAdapter(items)
    }

    private fun setCurrentOnBoardingIndicator(currentIndex: Int) {
        for ((index, value) in binding.layoutOnBoardingIndicators.children.withIndex()) {
            if (value is ImageView) {
                if (index == currentIndex) {
                    val crossfader = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.transition_active_button
                    ) as TransitionDrawable
                    value.setImageDrawable(
                        crossfader
                    )

                    crossfader.startTransition(100)

                } else {
                    value.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.onboarding_indicator_inactive
                        )
                    )
                }
            }
        }
    }
}