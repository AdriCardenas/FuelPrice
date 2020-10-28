package com.engineblue.fuelprice.activity

import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.viewpager2.widget.ViewPager2
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.adapter.onboarding.OnBoardingAdapter
import com.engineblue.presentation.entity.OnBoardingItemDisplayModel
import kotlinx.android.synthetic.main.activity_onboarding.*


class OnBoardingActivity : AppCompatActivity() {

    private lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        setupOnBoardingItems()
        setupOnBoardingIndicators()
        setupViewPager()
        setupButton()
    }

    private fun setupButton() {
        buttonOnBoardingSkipAction.setOnClickListener {
            startActivity(Intent(this, ConfigurationActivity::class.java))
            finish()
        }

        goBackButton.setOnClickListener {
            if (onBoardingViewPager.currentItem >= 0) {
                onBoardingViewPager.currentItem = onBoardingViewPager.currentItem - 1
            }

            buttonOnBoardingSkipAction.visibility = View.VISIBLE
        }

        buttonOnBoardingAction.setOnClickListener {
            if (onBoardingViewPager.currentItem + 1 < onBoardingAdapter.itemCount) {
                onBoardingViewPager.currentItem = onBoardingViewPager.currentItem + 1

                buttonOnBoardingSkipAction.visibility = View.VISIBLE
            } else {
                buttonOnBoardingSkipAction.visibility = View.GONE

                startActivity(Intent(this, ConfigurationActivity::class.java))
                finish()
            }
        }
    }

    private fun setupViewPager() {
        onBoardingViewPager.adapter = onBoardingAdapter
        onBoardingViewPager.isUserInputEnabled = false
        onBoardingViewPager.registerOnPageChangeCallback(object :
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
            goBackButton.visibility = View.GONE
        } else {
            goBackButton.visibility = View.VISIBLE
        }

        if (currentIndex == onBoardingAdapter.itemCount - 1) {
            buttonOnBoardingAction.text = getString(R.string.start)
        } else {
            buttonOnBoardingAction.text = getString(R.string.next)
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
            val image = ImageView(this)

            if (i == 0) {
                image.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.onboarding_indicator_active
                    )
                )
            } else {
                image.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.onboarding_indicator_inactive
                    )
                )
            }

            image.layoutParams = layoutParams
            layoutOnBoardingIndicators.addView(image)
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
        for ((index, value) in layoutOnBoardingIndicators.children.withIndex()) {
            if (value is ImageView) {
                if (index == currentIndex) {
                    val crossfader = ContextCompat.getDrawable(
                        this,
                        R.drawable.transition_active_button
                    ) as TransitionDrawable
                    value.setImageDrawable(
                        crossfader
                    )

                    crossfader.startTransition(100)

                } else {
                    value.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.onboarding_indicator_inactive
                        )
                    )
                }
            }
        }
    }
}