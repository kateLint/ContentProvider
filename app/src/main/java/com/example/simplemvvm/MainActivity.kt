package com.example.simplemvvm

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simplemvvm.model.MyContentProvider

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickAddDetails(view: View?)
    {
// class to add values in the database
        var values = ContentValues()

        var textName = findViewById<View>(R.id.textName) as EditText
        // fetching text from user
         values.put(MyContentProvider.name, textName.text.toString())

        // inserting into database through content URI
        contentResolver.insert(MyContentProvider.CONTENT_URI,values )
// displaying a toast message
        Toast.makeText(baseContext, "New Record Inserted", Toast.LENGTH_LONG).show()
    }

    fun onClickShowDetails(view: View?) {

        // inserting complete table details in this text field
        val resultView = findViewById<View>(R.id.res) as TextView

        // creating a cursor object of the
        // content URI
        val cursor = contentResolver.query(Uri.parse(MyContentProvider.CONTENT_URI.toString()), null, null, null, null)
        cursor?.let {
            if (cursor.moveToFirst()) {
                val strBuild = StringBuilder()
                while (!cursor.isAfterLast) {
                    var columnIndexID = cursor.getColumnIndex("id")
                    var columnIndexName = cursor.getColumnIndex("name")
                    strBuild.append(
                        """
    ${cursor.getString(columnIndexID)}-${cursor.getString(columnIndexName)}
    """.trimIndent()
                    )
                    cursor.moveToNext()
                }
                resultView.text = strBuild
            } else {
                resultView.text = "No Records Found"
            }
        }
    }


}