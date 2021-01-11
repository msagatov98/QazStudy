package com.qazstudy.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.qazstudy.R
import com.qazstudy.databinding.ViewHolderCountyFlagBinding
import com.qazstudy.model.Country

class AdapterCountry(context: Context, countries: Array<Country>):
    ArrayAdapter<Country>(context, 0, countries) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, _convertView: View?, parent: ViewGroup): View {

        val binding = ViewHolderCountyFlagBinding.inflate(LayoutInflater.from(context), parent, false)

        val convertView = LayoutInflater
            .from(context)
            .inflate(R.layout.view_holder_county_flag, parent, false)


        val country = getItem(position)

        if (country != null) {
            binding.tvCountry.text = country.name
            binding.ivFlag.setImageResource(country.imageFlag)
        }

        return convertView
    }
}