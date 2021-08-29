package com.itreallyiskyler.furblr.ui.marketplace

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itreallyiskyler.furblr.R

class MarketplaceFragment : Fragment() {

    companion object {
        fun newInstance() = MarketplaceFragment()
    }

    private lateinit var viewModel: MarketplaceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_marketplace, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MarketplaceViewModel::class.java)
        // TODO: Use the ViewModel
    }

}