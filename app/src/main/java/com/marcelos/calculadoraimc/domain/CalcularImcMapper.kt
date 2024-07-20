package com.marcelos.calculadoraimc.domain

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.DecimalFormat
import com.marcelos.calculadoraimc.R

data class CalculoImcModel(
    val peso: String,
    val altura: String
)

object ImcMapper {
    @SuppressLint("DefaultLocale")
    fun calcularImc(context: Context, calculoImcModel: CalculoImcModel): String {
        val pesoConvertido = calculoImcModel.peso.toDouble()
        val alturaConvertida = calculoImcModel.altura.toDouble()

        val imcResult = pesoConvertido / (alturaConvertida * alturaConvertida)
        val imcResultadoFormatado = DecimalFormat("0.00").format(imcResult)

        return when (imcResult) {
            in 0.0..18.4 -> context.getString(R.string.imc_abaixo_peso, imcResultadoFormatado)
            in 18.5..24.9 -> context.getString(R.string.imc_peso_normal, imcResultadoFormatado)
            in 25.0..29.9 -> context.getString(R.string.imc_sobrepeso, imcResultadoFormatado)
            in 30.0..34.9 -> context.getString(R.string.imc_obesidade_grau_1, imcResultadoFormatado)
            in 35.0..39.9 -> context.getString(R.string.imc_obesidade_grau_2, imcResultadoFormatado)
            else -> context.getString(R.string.imc_obesidade_grau_3, imcResultadoFormatado)
        }
    }
}