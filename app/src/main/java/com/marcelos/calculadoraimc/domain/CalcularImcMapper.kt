package com.marcelos.calculadoraimc.domain

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

data class CalculoImcModel(
    val peso: String,
    val altura: String
)

object ImcMapper {
    private val textoAbaixoPeso = "Seu IMC é %s\nVocê está abaixo do peso"
    private val textoPesoNormal = "Seu IMC é %s\nVocê está com o peso normal"
    private val textoSobrepeso = "Seu IMC é %s\nVocê está com sobrepeso"
    private val textoObesidadeGrau1 = "Seu IMC é %s\nVocê está com obesidade grau 1"
    private val textoObesidadeGrau2 = "Seu IMC é %s\nVocê está com obesidade grau 2"
    private val textoObesidadeGrau3 = "Seu IMC é %s\nVocê está com obesidade grau 3"

    fun calcularImc(calculoImcModel: CalculoImcModel): String {
        val pesoConvertido = calculoImcModel.peso.toDouble()
        val alturaConvertida = calculoImcModel.altura.toDouble()

        val imcResult = pesoConvertido / (alturaConvertida * alturaConvertida)
        val symbols = DecimalFormatSymbols(Locale.US)
        val decimalFormat = DecimalFormat("0.00", symbols)

        val imcResultadoFormatado = decimalFormat.format(imcResult)

        return when (imcResult) {
            in 0.0..18.4 -> textoAbaixoPeso.format(imcResultadoFormatado)
            in 18.5..24.9 -> textoPesoNormal.format(imcResultadoFormatado)
            in 25.0..29.9 -> textoSobrepeso.format(imcResultadoFormatado)
            in 30.0..34.9 -> textoObesidadeGrau1.format(imcResultadoFormatado)
            in 35.0..39.9 -> textoObesidadeGrau2.format(imcResultadoFormatado)
            else -> textoObesidadeGrau3.format(imcResultadoFormatado)
        }
    }
}
