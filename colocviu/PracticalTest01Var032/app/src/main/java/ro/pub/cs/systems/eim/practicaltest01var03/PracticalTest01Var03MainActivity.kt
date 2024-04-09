package ro.pub.cs.systems.eim.practicaltest01var03

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import ro.pub.cs.systems.eim.practicaltest01var03.ui.theme.PracticalTest01Var03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel : MainViewModel by viewModels()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                0)
        }

        setContent {
            PracticalTest01Var03Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenUI(this, viewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        stopService(Intent(this, PracticalTest01Var03Service::class.java))
        super.onDestroy()
    }
}

@Composable
fun ScreenUI(context: Context, viewModel: MainViewModel) {

    var textToDisplay by remember { mutableStateOf("") }
    var operation by remember { mutableStateOf("") }
    var service by remember { mutableIntStateOf(0) }

//    LaunchedEffect(viewModel.input1, viewModel.input2) {
//        Log.d("PracticalTest011", "Threshold reached")
//
//
//    }

    val saveContactLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Toast.makeText(
                context,
                "Corect result",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                context,
                "Incorect result",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp))

        CustomOutlinedTextField(
            value = viewModel.input1,
            onValueChange = { viewModel.input1 = it },
            label = "Enter text here"
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                if (isNumeric(viewModel.input1) && isNumeric(viewModel.input2)) {
                    viewModel.add()
                    val i1 = viewModel.input1
                    val i2 = viewModel.input2
                    textToDisplay = "$i1 + $i2 = ${viewModel.sum}"
                    operation = "sum"
                    service++

                    val intent = Intent(context, PracticalTest01Var03Service::class.java)
//            intent.putExtra(Constants.INPUT1, viewModel.input1.toInt())
//            intent.putExtra(Constants.INPUT2, viewModel.input2.toInt())
                    context.startService(intent)
                }
                else {
                    val toast = Toast.makeText(context,
                        "Invalid inputs!",
                        Toast.LENGTH_LONG)
                    toast.show()
                }
                 }) {
                Text(text = "+")
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Button(onClick = {
                if (isNumeric(viewModel.input1) && isNumeric(viewModel.input2)) {
                    viewModel.sub()
                    val i1 = viewModel.input1
                    val i2 = viewModel.input2
                    textToDisplay = "$i1 - $i2 = ${viewModel.dif}"
                    operation = "dif"
                    service++
                }
                else {
                    val toast = Toast.makeText(context,
                        "Invalid inputs!",
                        Toast.LENGTH_LONG)
                    toast.show()
                }
            }) {
                Text(text = "-")
            }
        }

        CustomOutlinedTextField(
            value = viewModel.input2,
            onValueChange = { viewModel.input2 = it },
            label = "Enter text here"
        )

        Text(
            text = textToDisplay
        )

        Button(onClick = {
            val intent = Intent(context, PracticalTest01Var03SecondaryActivity::class.java)
            intent.putExtra(Constants.OPERATION, operation)
            if (operation == "sum")
                intent.putExtra(Constants.RESULT, viewModel.sum)
            else
                intent.putExtra(Constants.RESULT, viewModel.dif)
            saveContactLauncher.launch(intent)
            service++
        }) {
            Text(text = "NAVIGATE TO SECONDARY ACTIVITY")
        }
    }
}

fun isNumeric(toCheck: String): Boolean {
    val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
    return toCheck.matches(regex)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        )
    )
}