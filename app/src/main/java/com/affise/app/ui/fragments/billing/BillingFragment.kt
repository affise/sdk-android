package com.affise.app.ui.fragments.billing

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.affise.attribution.Affise
import com.affise.attribution.module.subscription.fetchProducts
import com.affise.attribution.module.subscription.purchase
import com.affise.attribution.modules.subscription.AffiseProduct
import com.affise.attribution.modules.subscription.AffiseProductsResult
import com.affise.attribution.modules.subscription.AffisePurchasedInfo
import com.affise.attribution.modules.subscription.AffiseResult

class BillingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return context?.let {
            ComposeView(it).apply {
                setViewCompositionStrategy(
                    ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
                )
                setContent {
                    MaterialTheme(
                        colors = MaterialTheme.colors.copy(
                            primary = Color(0xFF2D2942)
                        ),
                    ) {
                        BillingView()
                    }
                }
            }
        } ?: super.onCreateView(inflater, container, savedInstanceState)
    }
}

val ids = listOf(
    "com.test.invalid",
    "com.test.sub_1",
    "com.test.sub_2",
    "com.test.sub_3",
    "com.test.prod_1",
    "com.test.prod_2",
    "com.test.prod_3",
)

@Preview(
    showBackground = true
)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BillingView() {
    val activity = LocalContext.current.findActivity()

    var fetchResult by remember {
        mutableStateOf<AffiseProductsResult?>(null)
    }

    LaunchedEffect(Unit) {
        fetch { result, error ->
            fetchResult = result
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(10.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray.copy(alpha = 0.6f))
                ) {
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colors.primary.copy(alpha = 0.9f))
                        ) {
                            Text(
                                text = "Products",
                                color = Color.White,
                                modifier = Modifier
                                    .padding(vertical = 4.dp, horizontal = 8.dp)
                            )
                        }
                    }
                    fetchResult?.let {
                        val products = it.products
                        itemsIndexed(products) { idx, product ->
                            ProductItem(product, activity, products.size == idx + 1)
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                fetch { result, error ->
                    fetchResult = result

                    when {
                        !error.isNullOrEmpty() -> {
                            alertDialog(activity, "Fetch result error", "$error")
                        }

                        !result?.invalidIds.isNullOrEmpty() -> {
                            alertDialog(
                                activity,
                                "Fetch result",
                                "Invalid Ids: ${result?.invalidIds}"
                            )
                        }
                    }
                }
            }, shape = RoundedCornerShape(10.dp)
        ) {
            Text("Fetch Products")
        }
    }
}

@Composable
fun ProductItem(product: AffiseProduct, activity: Activity?, last: Boolean = false) {
    Row(
        modifier = Modifier
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(product.title)
            Text(product.description, color = Color.Gray)
            product.subscription?.let {
                Text("id: ${it.offerId}", color = Color.Gray)
                Text("${it.numberOfUnits} ${it.timeUnit?.value}", color = Color.Blue.copy(0.6f))
            }
        }
        Column {
            Text(
                "${"%.2f".format(product.price?.value ?: "-")} ${product.price?.currencyCode ?: "-"}",
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }


        activity?.let {
            TextButton(
                onClick = {
                    purchase(it, product) { data, error ->
                        if (!error.isNullOrEmpty()) {
                            alertDialog(activity, "Purchase result error", "$error")
                        } else {
                            alertDialog(activity, "Purchase result", "$data")
                        }
                    }
                }
            ) {
                Text("Buy", color = Color.Blue)
            }
        }
    }
    if (!last) {
        Divider(
            color = MaterialTheme.colors.primary.copy(0.1f),
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
        )
    }
}

private fun alertDialog(
    activity: Activity?,
    title: String,
    text: String,
) {
    activity?.runOnUiThread {
        AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(text)
            .setPositiveButton("Ok") { _, _ ->
            }
            .create()
            .show()
    }
}

fun fetch(onResult: (AffiseProductsResult?, String?) -> Unit) {
    Affise.fetchProducts(ids) { result ->
        when (result) {
            is AffiseResult.Success -> {
                onResult(result.value, null)
            }

            is AffiseResult.Error -> {
                onResult(null, result.error.message)
            }
        }
    }
}

fun purchase(
    activity: Activity,
    product: AffiseProduct,
    onResult: (AffisePurchasedInfo?, String?) -> Unit,
) {
    Affise.purchase(activity, product) { result ->
        when (result) {
            is AffiseResult.Success -> onResult(result.value, null)
            is AffiseResult.Error -> onResult(null, result.error.message)
        }
    }
}

tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}