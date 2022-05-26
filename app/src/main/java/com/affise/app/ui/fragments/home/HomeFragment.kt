package com.affise.app.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.affise.app.R
import com.affise.app.databinding.FragmentMainHomeBinding
import com.affise.app.entity.ProductEntity
import com.affise.app.extensions.hideKeyboard
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.app.ui.adapters.SpaceDecoration
import com.affise.app.ui.fragments.details.ProductDetailsFragment
import com.affise.app.ui.fragments.home.adapters.AdapterNew
import com.affise.app.ui.fragments.home.adapters.AdapterPopular
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: HomeContract.ViewModel

    @Inject
    lateinit var menuHolder: MenuHolder

    @Inject
    lateinit var adapterPopular: AdapterPopular

    @Inject
    lateinit var adapterNew: AdapterNew

    lateinit var binding: FragmentMainHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainHomeBinding.inflate(layoutInflater).apply {
        binding = this

        homeMenu.setOnClickListener {
            menuHolder.changeMenuSate()
        }

        homeNotification.setOnClickListener {

        }

        homeErrorRefresh.setOnClickListener {
            viewModel.update()
        }

        homeRefresh.setOnRefreshListener {
            viewModel.update()
        }

        homeSearchSetting.setOnClickListener {

        }

        popularMore.setOnClickListener {

        }

        homeSearchValue.addTextChangedListener {
            viewModel.setSearch(it.toString())
        }

        recyclerPopular.adapter = adapterPopular.apply {
            onClickProduct = { clickProduct(it.product) }
            onClickLike = { viewModel.clickLike(it.product) }
            onClickAdd = { viewModel.clickAdd(it.product) }
        }

        recyclerNew.adapter = adapterNew.apply {
            onClickProduct = { clickProduct(it.product) }
            onClickLike = { viewModel.clickLike(it.product) }
            onClickAdd = { viewModel.clickAdd(it.product) }
        }

        recyclerPopular.addItemDecoration(SpaceDecoration(paddingEnd = 16f))
        recyclerNew.addItemDecoration(SpaceDecoration(paddingBottom = 16f))
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.stateData.observe(viewLifecycleOwner) {
            when (it) {
                is HomeState.DataState -> {
                    binding.homeLoading.isVisible = false
                    binding.homeError.isVisible = false
                    binding.homeRefresh.isRefreshing = false

                    adapterPopular.submitList(it.popularProducts)
                    adapterNew.submitList(it.newProducts)
                }
                HomeState.ErrorState -> {
                    binding.homeLoading.isVisible = false
                    binding.homeError.isVisible = true
                    binding.homeRefresh.isRefreshing = false
                }
                HomeState.LoadingState -> {
                    binding.homeLoading.isVisible = true
                    binding.homeError.isVisible = false
                    binding.homeRefresh.isRefreshing = false
                }
                HomeState.UpdateState -> {
                    binding.homeLoading.isVisible = false
                    binding.homeError.isVisible = false
                    binding.homeRefresh.isRefreshing = true
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        requireActivity().hideKeyboard()

        viewModel.update()
    }

    private fun clickProduct(productEntity: ProductEntity) {
        findNavController().navigate(
            R.id.action_homeFragment_to_productDetailsFragment,
            Bundle().apply {
                putLong(ProductDetailsFragment.PRODUCT_DETAILS_EXTRA_KEY, productEntity.id)
            }
        )
    }
}