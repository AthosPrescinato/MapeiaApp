package com.athosprescinato.mapeiaapp.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import com.athosprescinato.mapeiaapp.R


class CustomAdapter(
    private val context: Context,
    private val dataList: ArrayList<HashMap<String, String>>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var dataItem = dataList[position]

        val rowView = inflater.inflate(R.layout.adapter_view, parent, false)
        rowView.findViewById<TextView>(R.id.viagem).text = dataItem["viagem"]
        rowView.findViewById<TextView>(R.id.distancia).text = dataItem["distancia"] + "km"
        rowView.findViewById<TextView>(R.id.gastoestimado).text = "R$: " + dataItem["resultado"]

        rowView.tag = position
        return rowView

    }
}