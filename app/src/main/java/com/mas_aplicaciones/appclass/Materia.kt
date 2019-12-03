package com.mas_aplicaciones.appclass


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.mas_aplicaciones.appclass.modelo.Materia
import kotlinx.android.synthetic.main.fragment_materia.*


//
//  Materia.kt
//  AppClass
//
//  Created by Roberto Loaeza Valerio on 2019-11-26.
//  Copyright © 2019 Roberto Loaeza Valerio. All rights reserved.
//
class Materia : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_materia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val materia: Materia = arguments?.get("materia") as Materia
        tvNombre.setText(materia.nombre)
        //ivLogo.setImageBitmap(BitmapFactory.decodeByteArray(materia.logo,0, materia.logo.size))
        ivLogo.setImageURI(materia.logo)
        tvDescripcion.setText(materia.descripcion)


        var barDataSet: BarDataSet = BarDataSet(getData(), "Asistencias")
        barDataSet.colors = ColorTemplate.createColors(ColorTemplate.COLORFUL_COLORS)

        var barData: BarData = BarData(barDataSet)

        var xAxix: XAxis = bcAsistencias.xAxis
        xAxix.position = XAxis.XAxisPosition.BOTTOM
        val months =
            arrayOf("21/Oct","27/Oct","01/Nov","05/Nov","15/Nov","18/Nov","21/Nov","25/Nov","27/Nov", "29/Nov", "01/Dic", "02/dic")
        var formartter: IndexAxisValueFormatter = IndexAxisValueFormatter(months)
        xAxix.valueFormatter = formartter

        bcAsistencias.setData(barData)
        bcAsistencias.setFitBars(true)
        //bcAsistencias.animateXY(5000,5000)
        //bcAsistencias.invalidate()
        var d: Description = Description()

        d.text = "Xyz"
        bcAsistencias.description = d
    }


    private fun getData(): ArrayList<BarEntry>? {
        val entries: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(0f, 30f))
        entries.add(BarEntry(1f, 27f))
        entries.add(BarEntry(2f, 30f))
        entries.add(BarEntry(3f, 30f))
        entries.add(BarEntry(4f, 27f))
        entries.add(BarEntry(5f, 28f))
        entries.add(BarEntry(6f, 29f))
        entries.add(BarEntry(7f, 30f))
        entries.add(BarEntry(8f, 30f))
        entries.add(BarEntry(9f, 25f))
        entries.add(BarEntry(10f, 30f))
        entries.add(BarEntry(11f, 30f))
        return entries
    }
}
