package com.informatika18c.databarang.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.informatika18c.databarang.R
import com.informatika18c.databarang.UpdateDataActivity
import com.informatika18c.databarang.network.koneksi
import com.informatika18c.databarang.network.model.DataItem
import com.informatika18c.databarang.network.model.ResponseActionBarang
import okhttp3.Response
import retrofit2.Callback
import kotlin.math.log

class ListContent (val ldata : List<DataItem?>?, val context: Context) :
    RecyclerView.Adapter<ListContent.ViewHolder>(){
    class ViewHolder(val view: View) :RecyclerView.ViewHolder(view) {
        val namaBarang = view.findViewById<TextView>(R.id.tv_nama_barang)
        val jmlBarang = view.findViewById<TextView>(R.id.tv_jumlah_barang)
        val editBarang = view.findViewById<TextView>(R.id.tv_edit)
        val deletBarang = view.findViewById<TextView>(R.id.tv_delete)
    }

    override fun onCreateViewHolder(parentr: ViewGroup,ViewType: Int): ViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.activity_item_barang, parentr,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ldata!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val  model = ldata?.get(position)
        holder.namaBarang.text = model?.namaBarang
        holder.jmlBarang.text = model?.jumlahBarang
        holder.editBarang.setOnClickListener{
            val i = Intent(context, UpdateDataActivity::class.java)
            i.putExtra("IDBARANG", model?.id)
            i.putExtra("NAMABARANG", model?.namaBarang)
            i.putExtra("JMLBARANG", model?.jumlahBarang)
            context.startActivity(i)
        }
        holder.deletBarang.setOnClickListener{
            AlertDialog.Builder(context)
                .setTitle("Delete " + model?.namaBarang)
                .setMessage("Apakah Anda Ingin Menghapus Data ini?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->

                    koneksi.service.deleteBarang(model?.id).enqueue(object : Callback<ResponseActionBarang>{
                        override fun onResponse(
                            call: retrofit2.Call<ResponseActionBarang>,
                            response: retrofit2.Response<ResponseActionBarang>
                        ) {
                            if(response.isSuccessful){
                                Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_LONG).show()
                                notifyDataSetChanged()
                                notifyItemRemoved(position)
                                notifyItemChanged(position)
                                notifyItemRangeRemoved(position, ldata!!.size)
                                Log.d("bpesan", response.body().toString())
                            }
                        }

                        override fun onFailure(
                            call: retrofit2.Call<ResponseActionBarang>,
                            t: Throwable
                        ) {
                            Log.d("pesan", t.localizedMessage)
                        }

                    })

                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
                .show()
            }
        }
    }