package com.affise.app.ui.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.affise.app.R
import com.affise.app.databinding.FragmentMainCartBinding
import com.affise.app.entity.ProductEntity
import com.affise.app.extensions.hideKeyboard
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.app.ui.fragments.cart.adapters.AdapterCart
import com.affise.app.ui.fragments.details.ProductDetailsFragment
import dagger.android.support.DaggerFragment
import java.math.BigDecimal
import javax.inject.Inject

class CartFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: CartContract.ViewModel

    @Inject
    lateinit var menuHolder: MenuHolder

    @Inject
    lateinit var adapter: AdapterCart

    lateinit var binding: FragmentMainCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainCartBinding.inflate(layoutInflater).apply {
        binding = this

        recycles.adapter = adapter.apply {
            onClickProduct = { clickProduct(it.product) }
            onClickPlus = { viewModel.addProduct(it.product) }
            onClickMinus = { viewModel.removeProduct(it.product) }
        }

        refresh.setOnRefreshListener {
            viewModel.update()
        }

        menuSate.setOnClickListener {
            menuHolder.changeMenuSate()
        }

        notification.setOnClickListener {

        }

        checkout.setOnClickListener {

        }
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.stateData.observe(viewLifecycleOwner) {
            when (it) {
                is CartState.DataState -> {
                    binding.refresh.isRefreshing = false
                    adapter.submitList(it.cartProducts)

                    binding.totalPrice.text = it.cartProducts
                        .fold(BigDecimal.ZERO) { c, v ->
                            c + ((v.product.price ?: BigDecimal.ZERO) * v.count.toBigDecimal())
                        }
                        .takeIf { sum -> sum > BigDecimal.ZERO }
                        ?.toPlainString()
                        ?.let { price ->
                            it.cartProducts
                                .firstOrNull()
                                ?.product
                                ?.unit
                                ?.let { unit -> "$unit$price" }
                                ?: price
                        }
                        ?: ""
                }
                CartState.ErrorState -> {
                    binding.refresh.isRefreshing = false
                }
                CartState.LoadingState -> {
                    binding.refresh.isRefreshing = false
                }
                CartState.UpdateState -> {
                    binding.refresh.isRefreshing = true
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
            R.id.action_cartFragment_to_productDetailsFragment,
            Bundle().apply {
                putLong(ProductDetailsFragment.PRODUCT_DETAILS_EXTRA_KEY, productEntity.id)
            }
        )
    }
}