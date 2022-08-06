package com.test.kompastest.viewmodel

import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.test.kompastest.R
import com.test.kompastest.api.API
import com.test.kompastest.api.Retro
import com.test.kompastest.model.NameModel
import com.test.kompastest.model.RepoModel
import com.test.kompastest.model.SearchModel
import com.test.kompastest.view.RepoAdapter
import com.test.kompastest.view.SearchListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val mutableAvatar = MutableLiveData("")
    val Avatar: LiveData<String> get() = mutableAvatar

    private val mutableLogin = MutableLiveData("")
    val loginName: LiveData<String> get() = mutableLogin

    fun getSearchList(
        query: String,
        context: Context,
        searchRV: RecyclerView,
        navController: NavController
    ){
        val retro = Retro().getRetroClient().create(API::class.java)
        retro.getData(query).enqueue(object : Callback<SearchModel>{
            override fun onResponse(call: Call<SearchModel>, response: Response<SearchModel>) {
                if(response.isSuccessful){
                    if(response.body()!!.total_count != 0){
                        val searchAdapter = SearchListAdapter(response.body()!!.items, context)
                        searchRV.adapter = searchAdapter
                        searchAdapter.onItemClick = {
                            mutableAvatar.value = it.avatar_url
                            mutableLogin.value = it.login
                            navController.navigate(R.id.action_searchFragment_to_detailFragment)
                        }
                    }else{
                        Toast.makeText(context,"User Not Found",Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<SearchModel>, t: Throwable) {
                Log.e("Fail", t.message.toString())
            }

        })
    }

    fun getRepo(repoList: RecyclerView) {
        val retro = Retro().getRetroClient().create(API::class.java)
        retro.getRepo(mutableLogin.value.toString()).enqueue(object : Callback<List<RepoModel>>{
            override fun onResponse(
                call: Call<List<RepoModel>>,
                response: Response<List<RepoModel>>
            ) {
                val listRepo = ArrayList<RepoModel>()
                var descCheck = "No Description"
                for(q in response.body()!!){
                    if (q.description != null){
                        descCheck = q.description
                    }else{
                        descCheck = "No Description"
                    }
                    listRepo.add(RepoModel(q.name, descCheck, q.stargazers_count, q.updated_at))
                    Log.e("Result", q.description.toString() + q.name.toString() + q.stargazers_count.toString() + q.updated_at.toString())
                }
                val repoAdapter = RepoAdapter(listRepo)
                repoList.adapter = repoAdapter
            }

            override fun onFailure(call: Call<List<RepoModel>>, t: Throwable) {
                Log.e("Fail", t.message.toString())
            }


        })
    }

    fun getName(name: TextView, bio: TextView){
        val retro = Retro().getRetroClient().create(API::class.java)
        retro.getName(mutableLogin.value.toString()).enqueue(object  : Callback<NameModel>{
            override fun onResponse(call: Call<NameModel>, response: Response<NameModel>) {
                if(response.isSuccessful){
                    if(response.body()?.bio != null){
                        bio.text = response.body()!!.bio
                    }else{
                        bio.text = ""
                    }

                    if(response.body()?.name != null){
                        name.text = response.body()!!.name
                    }else{
                        name.text = mutableLogin.value
                    }
                }
            }

            override fun onFailure(call: Call<NameModel>, t: Throwable) {
                Log.e("Fail", t.message.toString())
            }

        })
    }

}