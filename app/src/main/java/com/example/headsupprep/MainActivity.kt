package com.example.headsupprep

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var rvMain: RecyclerView
    private lateinit var rvAdapter: RecyclerViewAdapter

    private val apiInterface by lazy { APIClient().getClient().create(APIInterface::class.java) }

    private lateinit var btAdd: Button
    private lateinit var etCelebrity: EditText
    private lateinit var btDetails: Button

    private lateinit var celebrities: ArrayList<Celebrity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        celebrities = arrayListOf()

        rvMain = findViewById(R.id.rvMain)
        rvAdapter = RecyclerViewAdapter(celebrities)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this)

        btAdd = findViewById(R.id.btAdd)
        btAdd.setOnClickListener {
            intent = Intent(applicationContext, AddCelebrity::class.java)
            val celebrityNames = arrayListOf<String>()
            for (c in celebrities) {
                celebrityNames.add(c.name.lowercase())
            }
            intent.putExtra("celebrityNames", celebrityNames)
            startActivity(intent)
        }
        etCelebrity = findViewById(R.id.etCelebrity)
        btDetails = findViewById(R.id.btDetails)
        btDetails.setOnClickListener {
            if (etCelebrity.text.isNotEmpty()) {
                updateCelebrity()
            } else {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show()
            }
        }



        getCelebrities()
    }

    private fun getCelebrities() {
        apiInterface.getCelebrities().enqueue(object : Callback<ArrayList<Celebrity>> {
            override fun onResponse(
                call: Call<ArrayList<Celebrity>>,
                response: Response<ArrayList<Celebrity>>
            ) {
                celebrities = response.body()!!
                rvAdapter.update(celebrities)
            }

            override fun onFailure(call: Call<ArrayList<Celebrity>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Unable to get data", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateCelebrity() {
        var celebrityID = 0
        for (celebrity in celebrities) {
            if (etCelebrity.text.toString() == celebrity.name) {
                celebrityID = celebrity.pk
                intent = Intent(this, UpdateAndDeleteCelebrity::class.java)
                intent.putExtra("celebrityID", celebrityID)
                startActivity(intent)

            } else {

            }
        }
    }
}