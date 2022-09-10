package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.lang.Float.max

/*
The top-secret algorithm is:
If the length of the shipment's destination street name is even, the base suitability score (SS) is the number of vowels in the driver’s
name multiplied by 1.5.
If the length of the shipment's destination street name is odd, the base SS is the number of consonants in the driver’s name multiplied by
1.
If the length of the shipment's destination street name shares any common factors (besides 1) with the length of the driver’s name, the
SS is increased by 50% above the base SS

 */
class MainActivity : AppCompatActivity() {

    val listOfStreets = mutableListOf<Street>()
    val driverMap = HashMap<String, Driver>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val p = readRaw(this, R.raw.data)

        val gson = Gson()
        val orders = gson.fromJson(p, Orders.Example::class.java)

        for (strin in orders.shipments as List<String>) {
            val p = strin.split(" ")
            listOfStreets.add(
                Street(
                    p[1] + " " + p[2],
                    p[1].length + p[2].length + 1,
                    arrayListOf()
                )
            )
        }

        for (driver in orders.drivers as List<String>) {
            var vowels = 0
            var consonants = 0
            val line = driver.toLowerCase()
            for (ch in line) {
                when (ch) {
                    'a', 'e', 'i', 'o', 'u' -> ++vowels
                    in 'a'..'z' -> ++consonants

                }
            }

            driverMap[driver] = Driver(vowels, consonants)
        }


        assigndrivers()

        val listView = findViewById<ListView>(R.id.lv)

        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                orders.drivers as List<String>
            )
        listView.adapter = arrayAdapter
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val listItem = listView.getItemAtPosition(position)
            Toast.makeText(this, driverMap.get(listItem)!!.street!!.first, Toast.LENGTH_LONG).show()
            val p = 0

        }

    }

    private fun assigndrivers() {
        var repeat = true
        while (repeat) {

            for (i in 0 until listOfStreets.size) {
                listOfStreets[i].drivers.clear()
                for (driver in driverMap) {
                    if (driver.value.street == null) {
                        if (driver.value.street == null) {

                            val score: Double = if (listOfStreets[i].size == driver.key.length) {
                                if (isEven(listOfStreets[i].size)) {
                                    driverMap.get(driver.key)!!.vowels * 1.5 * 1.5
                                } else {
                                    driverMap.get(driver.key)!!.Consonants * 1.5 * 1.5
                                }
                            } else {
                                if (isEven(listOfStreets[i].size)) {
                                    driverMap.get(driver.key)!!.vowels * 1.5
                                } else {
                                    driverMap.get(driver.key)!!.Consonants * 1.5
                                }

                            }
                            listOfStreets[i].drivers.add(Pair(driver.key, score))

                        }
                    }
                }

            }

            listOfStreets.sortByDescending { it -> it.drivers.maxOfOrNull { it.second } }
            for (st in listOfStreets) {
                if (!st.assigned) {

                    val driver = st.drivers.sortedByDescending { it.second }.first()
                    val driverc = driverMap.get(driver.first)
                    if (driverc!!.street != null) {
                        var maxStreet = ""
                        val max: Double
                        if (driverc.street!!.second <= driver.second) {

                            for (i in listOfStreets) {
                                if (i.name == driverc.street!!.first) {
                                    i.assigned = false
                                }
                            }
                            st.assigned = true
                            driverc.street = (Pair(st.name, driver.second))

                        }


                    } else {
                        driverc.street = (Pair(st.name, driver.second))
                        st.assigned = true
                    }

                }
            }
            repeat = !(listOfStreets.count { it -> it.assigned }==listOfStreets.size)
        }
    }

    private fun isEven(size: Int): Boolean {
        return size % 2 == 0
    }

    data class Driver(
        val vowels: Int,
        val Consonants: Int,
        var street: Pair<String, Double>? = null
    ) {


    }

    data class Street(
        val name: String,
        val size: Int,
        val drivers: ArrayList<Pair<String, Double>>,
        var assigned: Boolean = false
    ) {

    }

    fun readRaw(context: Context, rawId: Int): String {
        return context.resources.openRawResource(rawId)
            .bufferedReader().use { bufferedReader -> bufferedReader.readText() }
    }


}


