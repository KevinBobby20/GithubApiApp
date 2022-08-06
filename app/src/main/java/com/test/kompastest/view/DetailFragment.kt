package com.test.kompastest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.kompastest.R
import com.test.kompastest.databinding.FragmentDetailBinding
import com.test.kompastest.viewmodel.MainViewModel


class DetailFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var dataBinding: FragmentDetailBinding
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail,
            container, false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        linearLayoutManager = LinearLayoutManager(requireView().context)
        dataBinding.repoList.layoutManager = linearLayoutManager
        dataBinding.repoList.setHasFixedSize(false)
        viewModel.getRepo(dataBinding.repoList)
        viewModel.getName(dataBinding.userFullName, dataBinding.Desc)
    }

    private fun observeViewModel() {
        viewModel.Avatar.observe(viewLifecycleOwner, Observer { avatar ->
            Glide.with(requireContext()).load(avatar).apply(RequestOptions().circleCrop()).into(dataBinding.avatarDetail)
        })

        viewModel.loginName.observe(viewLifecycleOwner, Observer { login ->
            dataBinding.loginName.text = "@" + login
        })
    }

}