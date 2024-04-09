package ro.pub.cs.systems.eim.practicaltest01var03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class PracticalTest01Var03SecondaryActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val operation = intent.getStringExtra(Constants.OPERATION)
        val result = intent.getIntExtra(Constants.RESULT, 0)

        setContent {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Hello from the secondary activity!")

                Spacer(modifier = Modifier.fillMaxWidth().height(30.dp))

                Text(text = "The operation is: $operation and the result is: $result")

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        setResult(RESULT_OK)
                        finish()
                    }) {
                        Text(text = "Corect")
                    }

                    Button(onClick = {
                        setResult(RESULT_CANCELED)
                        finish()
                    }) {
                        Text(text = "Incorect")
                    }
                }
            }

        }
    }
}