package com.marcelos.calculadoraimc.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class ImcMapperTest {

    @Test
    fun `calcularIMC deve retornar texto correto para abaixo do peso`() {
        val calculoImcModel = CalculoImcModel(peso = "50", altura = "1.80")
        val resultado = ImcMapper.calcularImc(calculoImcModel)
        val esperado = "Seu IMC é 15.43\nVocê está abaixo do peso"
        assertEquals(esperado, resultado)
    }

    @Test
    fun `calcularIMC deve retornar texto correto para peso normal`() {
        val calculoImcModel = CalculoImcModel(peso = "70", altura = "1.80")
        val resultado = ImcMapper.calcularImc(calculoImcModel)
        val esperado = "Seu IMC é 21.60\nVocê está com o peso normal"
        assertEquals(esperado, resultado)
    }

    @Test
    fun `calcularIMC deve retornar texto correto para sobrepeso`() {
        val calculoImcModel = CalculoImcModel(peso = "85", altura = "1.80")
        val resultado = ImcMapper.calcularImc(calculoImcModel)
        val esperado = "Seu IMC é 26.23\nVocê está com sobrepeso"
        assertEquals(esperado, resultado)
    }

    @Test
    fun `calcularIMC deve retornar texto correto para obesidade grau 1`() {
        val calculoImcModel = CalculoImcModel(peso = "105", altura = "1.80")
        val resultado = ImcMapper.calcularImc(calculoImcModel)
        val esperado = "Seu IMC é 32.41\nVocê está com obesidade grau 1"
        assertEquals(esperado, resultado)
    }

    @Test
    fun `calcularIMC deve retornar texto correto para obesidade grau 2`() {
        val calculoImcModel = CalculoImcModel(peso = "120", altura = "1.80")
        val resultado = ImcMapper.calcularImc(calculoImcModel)
        val esperado = "Seu IMC é 37.04\nVocê está com obesidade grau 2"
        assertEquals(esperado, resultado)
    }

    @Test
    fun `calcularIMC deve retornar texto correto para obesidade grau 3`() {
        val calculoImcModel = CalculoImcModel(peso = "130", altura = "1.80")
        val resultado = ImcMapper.calcularImc(calculoImcModel)
        val esperado = "Seu IMC é 40.12\nVocê está com obesidade grau 3"
        assertEquals(esperado, resultado)
    }
}
