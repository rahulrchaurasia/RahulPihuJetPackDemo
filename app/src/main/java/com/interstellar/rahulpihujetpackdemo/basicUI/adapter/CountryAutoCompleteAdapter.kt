package com.interstellar.rahulpihujetpackdemo.basicUI.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.interstellar.rahulpihujetpackdemo.R

class CountryAutoCompleteAdapter(
    private val context: Context,
    private val countries: List<String>
) : BaseAdapter(), Filterable {

    private var filteredCountries: List<String> = countries
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int = filteredCountries.size

    override fun getItem(position: Int): Any = filteredCountries[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.dropdown_item, parent, false)
        val textView = view.findViewById<TextView>(R.id.dropdownText)
        textView.text = filteredCountries[position]
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                
                if (constraint.isNullOrEmpty() || constraint.length < 2) {
                    filteredCountries = emptyList()
                } else {
                    filteredCountries = countries.filter { country ->
                        country.contains(constraint.toString(), ignoreCase = true)
                    }.take(6) // Limit results
                }
                
                filterResults.values = filteredCountries
                filterResults.count = filteredCountries.size
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                filteredCountries = results?.values as? List<String> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}