package com.affise.app.ui.fragments.likes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.affise.app.R
import com.affise.app.databinding.FragmentMainLikesBinding
import com.affise.app.entity.ProductEntity
import com.affise.app.extensions.hideKeyboard
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.app.ui.fragments.details.ProductDetailsFragment
import com.affise.app.ui.fragments.likes.adapters.AdapterLikes
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LikesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: LikesContract.ViewModel

    @Inject
    lateinit var menuHolder: MenuHolder

    @Inject
    lateinit var adapter: AdapterLikes

    lateinit var binding: FragmentMainLikesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainLikesBinding.inflate(layoutInflater).apply {
        binding = this

        menuSate.setOnClickListener {
            menuHolder.changeMenuSate()
        }

        recycles.adapter = adapter.apply {
            onClickProduct = { clickProduct(it.product) }
            onClickLike = { viewModel.clickLike(it.product) }
            onClickAdd = { viewModel.clickAdd(it.product) }
        }

        refresh.setOnRefreshListener {
            viewModel.update()
        }

        notification.setOnClickListener {

        }

        searchSetting.setOnClickListener {

        }

        checkout.setOnClickListener {

        }
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.stateData.observe(viewLifecycleOwner) {
            when (it) {
                is LikesState.DataState -> {
                    adapter.submitList(it.likeProducts)
                    binding.refresh.isRefreshing = false
                }
                LikesState.ErrorState -> {
                    binding.refresh.isRefreshing = false
                }
                LikesState.LoadingState -> {
                    binding.refresh.isRefreshing = true
                }
                LikesState.UpdateState -> {
                    binding.refresh.isRefreshing = false
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.update()

        requireActivity().hideKeyboard()
    }

    private fun clickProduct(productEntity: ProductEntity) {
        findNavController().navigate(
            R.id.action_likesFragment_to_productDetailsFragment,
            Bundle().apply {
                putLong(ProductDetailsFragment.PRODUCT_DETAILS_EXTRA_KEY, productEntity.id)
            }
        )
    }
}
