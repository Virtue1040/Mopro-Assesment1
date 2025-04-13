package com.rafi0092.assesment1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rafi0092.assesment1.navigation.Screen
import com.rafi0092.assesment1.navigation.SetupNavGraph
import com.rafi0092.assesment1.ui.theme.Assesment1Theme
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assesment1Theme {
                SetupNavGraph()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = R.string.app_name
                        )
                    )

                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.About.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
    ) { padding ->
        ScreenContent(Modifier.padding(padding))
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error)
    } else {
        Text(
            text = unit
        )
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(
            text = stringResource(R.string.input_invalid)
        )

    }
}

@Composable
fun ScreenContent(modifier: Modifier) {
    val choiceUkuranFile = listOf(
        "bytes (B)",
        "kilobytes (KB)",
        "megabytes (MB)",
        "gigabytes (GB)",
        "terabytes (TB)",
        "petabytes (PB)",
        "exabytes (EB)",
        "zettabytes (ZB)",
        "yottabytes (YB)",
        "bits (b)",
        "kilobits (Kb)",
        "megabits (Mb)",
        "gigabits (Gb)",
        "terabits (Tb)",
    )

    val context = LocalContext.current

    var ukuranFile by rememberSaveable { mutableStateOf("") }
    var kecepatanInternet by rememberSaveable { mutableStateOf("") }

    var ukuranFileError by rememberSaveable { mutableStateOf(false) }
    var kecepatanInternetError by rememberSaveable { mutableStateOf(false) }

    var satuanUkuranFile by rememberSaveable { mutableStateOf(choiceUkuranFile[0]) }
    var satuanKecepatanInternet by rememberSaveable { mutableStateOf(choiceUkuranFile[0]) }

    var hasil by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        Dropdown(satuanUkuranFile, {
            satuanUkuranFile = it
        }, choiceUkuranFile)

        OutlinedTextField(
            value = ukuranFile,
            onValueChange = { ukuranFile = it },
            label = {
                Text(
                    text = stringResource(R.string.file_size)
                )
            },
            isError = ukuranFileError,
            trailingIcon = {
                IconPicker(ukuranFileError, satuanUkuranFile.substringAfter("(").substringBefore(")") )
            },
            supportingText = {
                ErrorHint(ukuranFileError)
            },

            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth().padding()
        )

        Dropdown(satuanKecepatanInternet, {
            satuanKecepatanInternet = it
        }, choiceUkuranFile)

        OutlinedTextField(
            value = kecepatanInternet,
            onValueChange = { kecepatanInternet = it },
            label = {
                Text(
                    text = stringResource(R.string.download_Speed)
                )
            },
            isError = kecepatanInternetError,
            trailingIcon = {
                IconPicker(kecepatanInternetError, satuanKecepatanInternet.substringAfter("(").substringBefore(")"))
            },
            supportingText = {
                ErrorHint(kecepatanInternetError)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        val pattern = remember { Regex("^\\d+\$") }
        Button(
            onClick = {
                ukuranFileError = (ukuranFile == "" || !ukuranFile.matches(pattern))
                kecepatanInternetError = (kecepatanInternet == "" || !kecepatanInternet.matches(pattern))
                if (kecepatanInternetError || ukuranFileError) return@Button

                hasil = hitungEstimasi(choiceUkuranFile.indexOf(satuanUkuranFile), ukuranFile.toFloat(), choiceUkuranFile.indexOf(satuanKecepatanInternet), kecepatanInternet.toFloat())
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.hitung)
            )
        }

        if (hasil != "") {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )
            Text(
                text = stringResource(R.string.hasil, hasil),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )
            val ukuranFileShare = ukuranFile + " " + satuanUkuranFile.substringAfter("(").substringBefore(")")
            val kecepatanInternetShare = kecepatanInternet + " " + satuanKecepatanInternet.substringAfter("(").substringBefore(")")
            Button(
                onClick = {
                    shareData(
                        context = context,
                        message = context.getString(R.string.bagikan_template, ukuranFileShare, kecepatanInternetShare, hasil)
                    )
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.bagikan)
                )
            }
        }
    }
}


private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(satuan : String, onValueChange : (String) -> Unit = {}, choiceUkuranFile : List<String> = listOf()) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                value = satuan,
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            choiceUkuranFile.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = { Text(option) },
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}


@SuppressLint("DefaultLocale")
fun hitungEstimasi(satuanUkuranFile: Number, ukuranFile : Float, satuanKecepatanInternet: Number, kecepatanInternet: Float): String {
    val ukuranFileInByte = ukuranFile * 1024.0.pow(satuanUkuranFile.toDouble()).toFloat()
    val kecepatanInternetInByte = kecepatanInternet * 1024.0.pow(satuanKecepatanInternet.toDouble())
        .toFloat()

    val totalDetik = ukuranFileInByte / kecepatanInternetInByte
    val hari = (totalDetik / 86400).toInt()
    val jam = (totalDetik % 86400 / 3600).toInt()
    val menit = ((totalDetik % 86400) % 3600 / 60).toInt()
    val detik = (((totalDetik % 86400) % 3600) % 60).toInt()

    return String.format("%d Hari %d Jam %d Menit %d Detik", hari, jam, menit, detik)
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    Assesment1Theme {
        SetupNavGraph(rememberNavController())
    }
}