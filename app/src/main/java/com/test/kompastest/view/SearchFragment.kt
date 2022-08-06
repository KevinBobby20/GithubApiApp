package com.test.kompastest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.kompastest.R
import com.test.kompastest.databinding.FragmentSearchBinding
import com.test.kompastest.viewmodel.MainViewModel

class SearchFragment : Fragment() {

    private lateinit var dataBinding: FragmentSearchBinding
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search,
            container, false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        dataBinding.searchField.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                linearLayoutManager = LinearLayoutManager(requireView().context)
                dataBinding.searchList.layoutManager = linearLayoutManager
                dataBinding.searchList.setHasFixedSize(false)
                viewModel.getSearchList(query.toString(), requireContext(), dataBinding.searchList, navController)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }

}