package com.affise.app.ui.fragments.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.affise.app.R
import com.affise.app.databinding.FragmentMainProductDetailsBinding
import com.affise.app.entity.Colors
import com.affise.app.entity.ProductEntity
import com.affise.app.extensions.hideKeyboard
import com.affise.app.ui.adapters.CenterDecoration
import com.affise.app.ui.adapters.CenterSnapHelper
import com.affise.app.ui.adapters.SpaceDecoration
import com.affise.app.ui.fragments.details.adapters.*
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProductDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: ProductDetailsContract.ViewModel

    @Inject
    lateinit var adapterImages: AdapterImages

    @Inject
    lateinit var adapterColors: AdapterColors

    @Inject
    lateinit var adapterSize: AdapterSize

    @Inject
    lateinit var adapterImagePages: AdapterImagePages

    lateinit var binding: FragmentMainProductDetailsBinding

    private val snapHelper = CenterSnapHelper()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainProductDetailsBinding.inflate(layoutInflater).apply {
        binding = this

        images.adapter = adapterImages

        images.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                adapterImagePages.setSelected(position)
            }
        })

        imagePages.adapter = adapterImagePages

        imagePages.addItemDecoration(SpaceDecoration(paddingEnd = 5f))

        colors.adapter = adapterColors

        size.adapter = adapterSize.apply {
            submitList(Size.getAllSize())
        }

        size.setHasFixedSize(true)

        snapHelper.attachToRecyclerView(size)

        size.addItemDecoration(
            DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL).apply {
                setDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.adapter_size_decoration
                    )!!
                )
            })

        size.addItemDecoration(CenterDecoration(0))

        productDetailsErrorBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        productDetailsBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        productDetailsLike.setOnClickListener {
            viewModel.clickLike(getProductId())
        }

        productDetailsRefresh.setOnRefreshListener {
            viewModel.setItemId(getProductId())
        }

        actionColor.setOnClickListener {
            viewModel.selectGroup(ProductDetailsActionGroup.COLORS)
        }

        actionSize.setOnClickListener {
            viewModel.selectGroup(ProductDetailsActionGroup.SIZE)
        }

        actionReviews.setOnClickListener {
            viewModel.selectGroup(ProductDetailsActionGroup.REVIEWS)
        }

        actionSizeEuro.setOnClickListener {
            adapterSize.currentType = SizeType.EURO

            setSelectedSize(actionSizeEuro)
            setUnSelectedSize(actionSizeUs)
            setUnSelectedSize(actionSizeAsia)
        }

        actionSizeUs.setOnClickListener {
            adapterSize.currentType = SizeType.US

            setUnSelectedSize(actionSizeEuro)
            setSelectedSize(actionSizeUs)
            setUnSelectedSize(actionSizeAsia)
        }

        actionSizeAsia.setOnClickListener {
            adapterSize.currentType = SizeType.ASIA

            setUnSelectedSize(actionSizeEuro)
            setUnSelectedSize(actionSizeUs)
            setSelectedSize(actionSizeAsia)
        }

        cart.setOnClickListener {
            viewModel.clickCart()
        }

        buyNow.setOnClickListener {
            viewModel.clickBuyNow()
        }
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setItemId(getProductId())

        viewModel.stateData.observe(viewLifecycleOwner) {
            when (it) {
                is ProductDetailsState.DataState -> {
                    binding.productDetailsLoading.isVisible = false
                    binding.productDetailsRefresh.isVisible = true
                    binding.productDetailsRefresh.isRefreshing = false
                    binding.productDetailsError.isVisible = false

                    val images = it.productsDetails.images

                    adapterImages.submitList(images)

                    if (images.size > 1) {
                        val pages = images.mapIndexed { index, image ->
                            ImagePagesItem(image, binding.images.currentItem == index)
                        }

                        adapterImagePages.submitList(pages)

                        binding.imagePages.isVisible = true
                    } else {
                        binding.imagePages.isVisible = false
                    }

                    adapterColors.submitList(
                        Colors.values().map { color ->
                            ColorItem(color, it.productsDetails.color == color)
                        }
                    )

                    val sizeValue = Size.getAllSize().first() { size ->
                        it.productsDetails.size == when (it.productsDetails.size_type) {
                            SizeType.EURO -> size.sizeEuro
                            SizeType.US -> size.sizeUs
                            SizeType.ASIA -> size.sizeAsia
                        }
                    }
                    adapterSize.currentType = it.productsDetails.size_type
                    adapterSize.currentSize = sizeValue
                    adapterSize.enabledSize = listOf(sizeValue)

                    when (it.productsDetails.size_type) {
                        SizeType.EURO -> {
                            setSelectedSize(binding.actionSizeEuro)
                            setUnSelectedSize(binding.actionSizeUs)
                            setUnSelectedSize(binding.actionSizeAsia)
                        }
                        SizeType.US -> {
                            setUnSelectedSize(binding.actionSizeEuro)
                            setSelectedSize(binding.actionSizeUs)
                            setUnSelectedSize(binding.actionSizeAsia)
                        }
                        SizeType.ASIA -> {
                            setUnSelectedSize(binding.actionSizeEuro)
                            setUnSelectedSize(binding.actionSizeUs)
                            setSelectedSize(binding.actionSizeAsia)
                        }
                    }

                    val position = Size.getAllSize().indexOf(sizeValue)
                    snapHelper.scrollTo(position, false)

                    binding.productDetailsLike.setImageResource(
                        if (it.inLike) R.drawable.ic_like_full else R.drawable.ic_like
                    )

                    binding.cartImage.setImageResource(
                        if (it.inCart) R.drawable.ic_cart_full else R.drawable.ic_cart
                    )

                    showDetails(it.productsDetails)
                }
                is ProductDetailsState.ErrorState -> {
                    binding.productDetailsLoading.isVisible = false
                    binding.productDetailsRefresh.isVisible = false
                    binding.productDetailsError.isVisible = true
                    binding.productDetailsErrorRefresh.isVisible = it.retry
                }
                ProductDetailsState.LoadingState -> {
                    binding.productDetailsLoading.isVisible = true
                    binding.productDetailsRefresh.isVisible = false
                    binding.productDetailsError.isVisible = false
                }
                ProductDetailsState.UpdateState -> {
                    binding.productDetailsLoading.isVisible = false
                    binding.productDetailsRefresh.isVisible = true
                    binding.productDetailsRefresh.isRefreshing = true
                    binding.productDetailsError.isVisible = false
                }
            }
        }

        viewModel.currentGroup.observe(viewLifecycleOwner) {
            when (it) {
                null -> Unit
                ProductDetailsActionGroup.COLORS -> showColorsGroup()
                ProductDetailsActionGroup.SIZE -> showSizeGroup()
                ProductDetailsActionGroup.REVIEWS -> showReviewsGroup()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        requireActivity().hideKeyboard()
    }

    private fun showDetails(productsDetails: ProductEntity) = with(binding) {
        price.text = productsDetails.price?.toPlainString()?.let { price ->
            productsDetails.unit?.let { unit ->
                "$unit$price"
            } ?: price
        } ?: ""

        name.text = productsDetails.name
        description.text = productsDetails.description
        rating.text = getString(R.string.rating_with_value, productsDetails.rating.toString())
    }

    private fun showColorsGroup() = with(binding) {
        setSelected(actionColorTitle, actionColor)
        setUnSelected(actionSizeTitle, actionSize)
        setUnSelected(actionReviewsTitle, actionReviews)

        colors.isVisible = true
        reviews.isVisible = false
        sizeGroup.isVisible = false
    }

    private fun showSizeGroup() = with(binding) {
        setUnSelected(actionColorTitle, actionColor)
        setSelected(actionSizeTitle, actionSize)
        setUnSelected(actionReviewsTitle, actionReviews)

        colors.isVisible = false
        reviews.isVisible = false
        sizeGroup.isVisible = true
    }

    private fun showReviewsGroup() = with(binding) {
        setUnSelected(actionColorTitle, actionColor)
        setUnSelected(actionSizeTitle, actionSize)
        setSelected(actionReviewsTitle, actionReviews)

        colors.isVisible = false
        reviews.isVisible = true
        sizeGroup.isVisible = false
    }

    private fun setSelected(textView: MaterialTextView, cardView: MaterialCardView) {
        with(textView) {
            setBackgroundResource(R.color.ebony_clay)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setTextAppearance(R.style.Selected)
            } else {
                @Suppress("DEPRECATION")
                setTextAppearance(requireContext(), R.style.Selected)
            }
        }

        cardView.alpha = 1f
    }

    private fun setUnSelected(textView: MaterialTextView, cardView: MaterialCardView) {
        with(textView) {
            setBackgroundResource(android.R.color.transparent)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setTextAppearance(R.style.UnSelected)
            } else {
                @Suppress("DEPRECATION")
                setTextAppearance(requireContext(), R.style.UnSelected)
            }
        }

        cardView.alpha = 0.3f
    }

    private fun setSelectedSize(textView: MaterialTextView) {
        with(textView) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setTextAppearance(R.style.EnabledSize)
            } else {
                @Suppress("DEPRECATION")
                setTextAppearance(requireContext(), R.style.EnabledSize)
            }
        }
    }

    private fun setUnSelectedSize(textView: MaterialTextView) {
        with(textView) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setTextAppearance(R.style.DisabledSize)
            } else {
                @Suppress("DEPRECATION")
                setTextAppearance(requireContext(), R.style.DisabledSize)
            }
        }
    }

    private fun getProductId() = arguments?.getLong(PRODUCT_DETAILS_EXTRA_KEY)

    companion object {
        const val PRODUCT_DETAILS_EXTRA_KEY = "product_details"
    }
}