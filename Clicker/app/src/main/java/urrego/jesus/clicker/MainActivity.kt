package urrego.jesus.clicker

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    var cuenta: Int = 0
    var cosa: String? = "pichadas"
    lateinit var btn_sumar: Button
    lateinit var tv_count: TextView
    lateinit var et_pichadas: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_sumar = findViewById(R.id.btn_clicker)
        tv_count = findViewById(R.id.tv_count)
        val btn_restar: Button = findViewById(R.id.btn_restar)
        val btn_borrar: Button = findViewById(R.id.btn_borrar)
        et_pichadas = findViewById(R.id.et_pichadas)

        btn_sumar.setOnClickListener{
            cuenta++
            tv_count.setText("$cuenta")
        }

        btn_restar.setOnClickListener{
            cuenta--
            tv_count.setText("$cuenta")
        }

        btn_borrar.setOnClickListener{
            borrar()
        }
    }

    fun borrar() {
        val alertDialog: AlertDialog? = this?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener{ dialog, id ->
                    // User clicked OK button
                    cuenta = 0
                    tv_count.setText("$cuenta")
                })
                setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener{ dialog, id ->
                    // User cancelled the dialog
                })
            }
            // Set other dialog properties
            builder?.setMessage(R.string.dialog_message).setTitle(R.string.dialog_tittle)

            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()
    }

    override fun onPause() {
        super.onPause()
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        cosa = et_pichadas.text.toString()

        editor.putInt("contador", cuenta)
        editor.putString("cosa", cosa)
        editor.commit()
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
        cuenta = sharedPref.getInt("contador", 0)
        cosa = sharedPref.getString("cosa", "pichadas")

        et_pichadas.setText("$cosa")
        tv_count.setText("$cuenta")
    }


}