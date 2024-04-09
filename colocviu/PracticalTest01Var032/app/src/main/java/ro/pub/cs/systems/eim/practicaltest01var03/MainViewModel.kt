package ro.pub.cs.systems.eim.practicaltest01var03

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var input1 by mutableStateOf("")
    var input2 by mutableStateOf("")

    var sum by mutableIntStateOf(0)
    var dif by mutableIntStateOf(0)

    fun add() {
        sum = input1.toInt() + input2.toInt()
    }

    fun sub() {
        dif = input1.toInt() - input2.toInt()
    }

}