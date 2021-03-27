package com.adda.userInfo.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adda.databinding.UserDetailsBinding
import com.adda.roomdb.entities.UserInfoModel

class UserInfoAdapter : ListAdapter<UserInfoModel, UserInfoAdapter.ViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<UserInfoModel>() {

        override fun areItemsTheSame(oldItem: UserInfoModel, newItem: UserInfoModel): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: UserInfoModel, newItem: UserInfoModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it)}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(UserDetailsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    inner class ViewHolder(private val binding: UserDetailsBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(model: UserInfoModel) {
            binding.apply {
                model.apply {
                    tvUserName.text = userName
                    tvUserGender.text = userGender
                    tvUserEmail.text = userEmail
                    tvUserStatus.text = userStatus
                }
            }
        }
    }
}