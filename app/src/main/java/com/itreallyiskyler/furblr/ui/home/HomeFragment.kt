package com.itreallyiskyler.furblr.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.databinding.FragmentHomeBinding
import com.itreallyiskyler.furblr.util.ContentManager

class HomeFragment : Fragment() {

    //private var homeViewModel: HomeViewModel = HomeViewModel()
    //private var _binding: FragmentHomeBinding? = null
    //private var adapter : HomePageAdapter? = HomePageAdapter(listOf())

    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*val rvHomeList : RecyclerView = view.findViewById(R.id.rvHomeList)
        rvHomeList.adapter = adapter
        val root: View = binding.root*/

        super.onViewCreated(view, savedInstanceState)

        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/


        /*homeViewModel.getPosts().observe(viewLifecycleOwner, Observer {
            notifyDataChanged
        })*/
    }

    /*override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/
}