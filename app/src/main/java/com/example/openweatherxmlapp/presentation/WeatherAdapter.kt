package com.example.openweatherxmlapp.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.openweatherxmlapp.databinding.WeatherItemBinding
import com.example.openweatherxmlapp.domain.models.WeatherInfo

class WeatherAdapter : ListAdapter<WeatherInfo, WeatherAdapter.WeatherViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : WeatherViewHolder {
        return  WeatherViewHolder(WeatherItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder : WeatherViewHolder , position : Int) {
        val weatherInfo = getItem(position)
        holder.bind(weatherInfo)
    }

    inner class WeatherViewHolder(private var binding : WeatherItemBinding) :
    RecyclerView.ViewHolder(binding.root){
        fun bind(weatherInfo : WeatherInfo){
            binding.tvTime.text = weatherInfo.time.toString()
            binding.tvTemperature.text = weatherInfo.temperature.toString()
            binding.ivWeather.setImageResource(weatherInfo.weatherType.iconRes)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<WeatherInfo>(){
        override fun areItemsTheSame(oldItem : WeatherInfo , newItem : WeatherInfo) : Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem : WeatherInfo , newItem : WeatherInfo) : Boolean {
            return oldItem.temperature == newItem.temperature
        }
    }
}