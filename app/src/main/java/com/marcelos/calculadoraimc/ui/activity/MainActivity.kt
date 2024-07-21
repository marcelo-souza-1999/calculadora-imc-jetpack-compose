package com.marcelos.calculadoraimc.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.marcelos.calculadoraimc.R
import com.marcelos.calculadoraimc.domain.CalculoImcModel
import com.marcelos.calculadoraimc.domain.ImcMapper
import com.marcelos.calculadoraimc.ui.theme.CalculadoraIMCTheme
import com.marcelos.calculadoraimc.ui.theme.DarkBlue
import com.marcelos.calculadoraimc.ui.theme.LightBlue
import com.marcelos.calculadoraimc.ui.theme.TypographyButton
import com.marcelos.calculadoraimc.ui.theme.TypographyTextFieldPeso
import com.marcelos.calculadoraimc.ui.theme.TypographyTitle
import com.marcelos.calculadoraimc.ui.theme.White
import com.marcelos.calculadoraimc.utils.emptyString

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraIMCTheme {
                setupStatusBarNative()
                MainContent()
            }
        }
    }

    private fun setupStatusBarNative() {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = DarkBlue.toArgb()
            )
        )
    }

    @Preview(showBackground = true, showSystemUi = false)
    @Composable
    fun MainContent() {
        val context = LocalContext.current

        val margin8 = dimensionResource(id = R.dimen.size_8)
        val margin16 = dimensionResource(id = R.dimen.size_16)

        var peso by remember { mutableStateOf(emptyString()) }
        var altura by remember { mutableStateOf(emptyString()) }
        var resultCalculo by remember { mutableStateOf(emptyString()) }

        val focusRequester = remember { FocusRequester() }

        Scaffold(
            topBar = {
                TopBar(
                    onRefreshClick = {
                        peso = emptyString()
                        altura = emptyString()
                        resultCalculo = emptyString()
                        focusRequester.requestFocus()
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(margin16)
                ) {
                    val (txtTitle, txtFieldPeso, txtFieldAltura, btnCalcular, txtResult) = createRefs()

                    Title(
                        modifier = Modifier
                            .constrainAs(txtTitle) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.matchParent
                            }
                            .testTag("txtTitle")
                    )

                    PesoTextField(
                        peso = peso,
                        onPesoChange = { if (it.length <= 4) peso = it },
                        modifier = Modifier
                            .constrainAs(txtFieldPeso) {
                                top.linkTo(txtTitle.bottom, margin8)
                                start.linkTo(parent.start, margin16)
                                end.linkTo(parent.end, margin16)
                                width = Dimension.matchParent
                            }
                            .testTag("txtFieldPeso")
                            .focusRequester(focusRequester)
                    )

                    AlturaTextField(
                        altura = altura,
                        onAlturaChange = { if (it.length <= 4) altura = it },
                        modifier = Modifier
                            .constrainAs(txtFieldAltura) {
                                top.linkTo(txtFieldPeso.bottom, margin8)
                                start.linkTo(parent.start, margin16)
                                end.linkTo(parent.end, margin16)
                                width = Dimension.matchParent
                            }
                            .testTag("txtFieldAltura")
                    )

                    CalcularButton(
                        onClick = {
                            if (peso.isEmpty() || altura.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    getString(R.string.txt_aviso_toats),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                resultCalculo = ImcMapper.calcularImc(
                                    calculoImcModel = CalculoImcModel(peso, altura)
                                )
                            }
                        },
                        modifier = Modifier
                            .constrainAs(btnCalcular) {
                                top.linkTo(txtFieldAltura.bottom, margin8)
                                start.linkTo(parent.start, margin16)
                                end.linkTo(parent.end, margin16)
                                bottom.linkTo(parent.bottom, margin16)
                                width = Dimension.matchParent
                            }
                            .testTag("btnCalcularImc")
                    )

                    ResultText(
                        result = resultCalculo,
                        modifier = Modifier
                            .constrainAs(txtResult) {
                                top.linkTo(btnCalcular.bottom, margin16)
                                start.linkTo(parent.start, margin16)
                                end.linkTo(parent.end, margin16)
                                bottom.linkTo(parent.bottom, margin16)
                            }
                            .testTag("txtResult")
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopBar(onRefreshClick: () -> Unit) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.title_top_bar),
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.size_8))
                )
            },
            modifier = Modifier.testTag("topBar"),
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = LightBlue,
                titleContentColor = White
            ),
            actions = {
                IconButton(
                    onClick = onRefreshClick,
                    modifier = Modifier
                        .testTag("iconButtonTopBar")
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_refresh),
                        contentDescription = stringResource(R.string.text_ic_refresh_top_bar_accessibility)
                    )
                }
            }
        )
    }

    @Composable
    private fun Title(modifier: Modifier) {
        Text(
            text = stringResource(R.string.title_screen),
            style = TypographyTitle.titleLarge,
            modifier = modifier.padding(dimensionResource(id = R.dimen.size_20)),
            textAlign = TextAlign.Center
        )
    }

    @Composable
    private fun PesoTextField(
        peso: String,
        onPesoChange: (String) -> Unit,
        modifier: Modifier
    ) {
        OutlinedTextField(
            value = peso,
            onValueChange = onPesoChange,
            label = { Text(text = stringResource(R.string.label_txt_peso)) },
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = LightBlue,
                focusedBorderColor = LightBlue,
                focusedTextColor = DarkBlue,
                focusedLabelColor = DarkBlue
            ),
            textStyle = TypographyTextFieldPeso.labelMedium,
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = modifier
        )
    }

    @Composable
    private fun AlturaTextField(
        altura: String,
        onAlturaChange: (String) -> Unit,
        modifier: Modifier
    ) {
        OutlinedTextField(
            value = altura,
            onValueChange = onAlturaChange,
            label = { Text(text = stringResource(R.string.label_txt_altura)) },
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = LightBlue,
                focusedBorderColor = LightBlue,
                focusedTextColor = DarkBlue,
                focusedLabelColor = DarkBlue
            ),
            textStyle = TypographyTextFieldPeso.labelMedium,
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = modifier
        )
    }

    @Composable
    private fun CalcularButton(onClick: () -> Unit, modifier: Modifier) {
        Button(
            onClick = onClick,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = LightBlue,
                contentColor = White
            )
        ) {
            Text(
                text = stringResource(R.string.txt_btn_calcular),
                style = TypographyButton.titleLarge,
            )
        }
    }

    @Composable
    private fun ResultText(result: String, modifier: Modifier) {
        Text(
            text = result,
            style = TypographyTitle.titleLarge.copy(color = DarkBlue),
            textAlign = TextAlign.Center,
            modifier = modifier
        )
    }
}
