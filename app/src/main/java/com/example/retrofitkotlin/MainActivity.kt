package com.example.retrofitkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.app.AlertDialog
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitkotlin.model.Item
import com.example.retrofitkotlin.model.MainModel
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var mService: RetrofitServices
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyMovieAdapter
    lateinit var dialog: AlertDialog
    lateinit var recyclerMovieList: RecyclerView
    lateinit var search_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerMovieList= findViewById(R.id.recyclerMovieList)

        mService = Common.retrofitService
        recyclerMovieList.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerMovieList.layoutManager = layoutManager
        dialog = SpotsDialog.Builder().setCancelable(true).setContext(this).build()
        search_btn = findViewById(R.id.search_button)
        search_btn.setOnClickListener{
            getAllMovieList()
        }
    }

    private fun getAllMovieList() {
        dialog.show()
        mService.getMovieList().enqueue(object : Callback<List<Item>> {
            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
            }

            override fun onResponse(call: Call<List<Item>>,
                                    response: Response< List<Item>>) {
                adapter = MyMovieAdapter(baseContext, response.body() as MutableList<Item>)
                adapter.notifyDataSetChanged()
                recyclerMovieList.adapter = adapter
                dialog.dismiss()
            }
        })
    }
}