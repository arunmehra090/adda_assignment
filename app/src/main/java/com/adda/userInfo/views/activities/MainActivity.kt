package com.adda.userInfo.views.activities

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.DividerItemDecoration
import com.adda.base.Injection
import com.adda.databinding.ActivityMainBinding
import com.adda.extension.gone
import com.adda.extension.show
import com.adda.models.ResultState
import com.adda.remote.RetrofitBuilder
import com.adda.userInfo.viewmodels.UserInfoViewModel
import com.adda.userInfo.views.adapters.UserInfoAdapter
import kotlinx.android.synthetic.main.content_main.view.*
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserInfoViewModel
    private val userAdapter = UserInfoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupViewModel()
        fetchUserInfoDetails()

        binding.ivRefresh.setOnClickListener {
            fetchUserInfoDetails()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this,
                Injection.provideViewModelFactory(this))
                .get(UserInfoViewModel::class.java)
    }

    private fun fetchUserInfoDetails() {
        viewModel.apply {
            binding.mainLayout.apply {
                progressBar.show()
                fetchUserDetailsService()
                mResponseLiveData.observe(this@MainActivity, {
                    progressBar.gone()
                    when (it) {
                        is ResultState.Success -> {
                            if (it.data.isNotEmpty()) {
                                userAdapter.submitList(it.data)
                                rvUserDetails.show()
                                // add dividers between RecyclerView's row items
                                rvUserDetails.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
                                rvUserDetails.adapter = userAdapter
                            }
                        }

                        is ResultState.Error -> {
                            Snackbar.make(this, "Please Retry", Snackbar.LENGTH_LONG)
                                    .setAction("Retry") { fetchUserInfoDetails() }.show()
                        }
                    }
                })
            }
        }
    }
}

