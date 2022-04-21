package com.example.rpncalculator_23

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.rpncalculator_23.R
import com.example.rpncalculator_23.databinding.ActivityMainBinding
import java.nio.file.spi.FileTypeDetector

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener, GestureDetector.OnGestureListener {
    private lateinit var stack: RPNStack
    private lateinit var gDetector: GestureDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stack = RPNStack()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        binding.one.setOnClickListener(this)
        binding.two.setOnClickListener(this)
        binding.three.setOnClickListener(this)
        binding.four.setOnClickListener(this)
        binding.five.setOnClickListener(this)
        binding.six.setOnClickListener(this)
        binding.seven.setOnClickListener(this)
        binding.eight.setOnClickListener(this)
        binding.nine.setOnClickListener(this)
        binding.zero.setOnClickListener(this)
        binding.dot.setOnClickListener(this)
        binding.sign.setOnClickListener(this)
        binding.add.setOnClickListener(this)
        binding.subtract.setOnClickListener(this)
        binding.multiply.setOnClickListener(this)
        binding.divide.setOnClickListener(this)
        binding.power.setOnClickListener(this)
        binding.sqrt.setOnClickListener(this)
        binding.ac.setOnClickListener(this)
        binding.drop.setOnClickListener(this)
        binding.enter.setOnClickListener(this)
        binding.undo.setOnClickListener(this)
        binding.drop.setOnClickListener(this)
        binding.swap.setOnClickListener(this)

        setContentView(view)

        val arrayAdapter: ArrayAdapter<*>
        var mListView = findViewById<ListView>(R.id.userlist)
        arrayAdapter = ArrayAdapter(this,
            R.layout.list_item_white, stack.getStack())
        mListView.adapter = arrayAdapter

        mySettings()

        gDetector = GestureDetector(this, this)
    }

    private fun mySettings() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val background = prefs.getString("background", "")
        val floatpoint = prefs.getInt("float_preference", 1)
        binding.apply {
            when(background) {
                "gray" -> binding.mainLayout.setBackgroundResource(R.color.gray)
                "red" -> binding.mainLayout.setBackgroundResource(R.color.brightred)
                "blue" -> binding.mainLayout.setBackgroundResource(R.color.blue)
                else -> binding.mainLayout.setBackgroundResource(R.color.green)
            }
            stack.setDecimalPoint(floatpoint)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
    private fun goToSettings() {
        startActivity(Intent(this,SettingsActivity::class.java))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_settings -> goToSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View?)
    {
        var toUpdate = true
        when ( view!!.id)
        {
            R.id.one->
            {
                stack.pushNumber("1")
            }
            R.id.two->
            {
                stack.pushNumber("2")
            }
            R.id.three->
            {
                stack.pushNumber("3")
            }
            R.id.four->
            {
                stack.pushNumber("4")
            }
            R.id.five->
            {
                stack.pushNumber("5")
            }
            R.id.six->
            {
                stack.pushNumber("6")
            }
            R.id.seven->
            {
                stack.pushNumber("7")
            }
            R.id.eight->
            {
                stack.pushNumber("8")
            }
            R.id.nine->
            {
                stack.pushNumber("9")
            }
            R.id.zero->
            {
                stack.pushNumber("0")
            }
            R.id.dot->
            {
                stack.addDot()
            }
            R.id.sign->
            {
                stack.changeSign()
            }
            R.id.add->
            {
                stack.add()
            }
            R.id.subtract->
            {
                stack.subtract()
            }
            R.id.multiply->
            {
                stack.multiply()
            }
            R.id.divide->
            {
                stack.divide()
            }
            R.id.power->
            {
                stack.power()
            }
            R.id.sqrt->
            {
                stack.sqrt()
            }
            R.id.ac->
            {
                stack.ac()
            }
            R.id.drop->
            {
                stack.drop()
            }
            R.id.enter->
            {
                stack.newNumber()
            }
            R.id.swap->
            {
                stack.swap()
            }
            R.id.undo->
            {
                stack.undo()
            }
            else -> toUpdate=false
        }
        if (toUpdate) {
            refreshScreen()
        }
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }

    private fun refreshScreen() {
        val arrayAdapter: ArrayAdapter<*>
        val mListView = findViewById<ListView>(R.id.userlist)
        arrayAdapter = ArrayAdapter(this,
            R.layout.list_item_white, stack.getStack())
        mListView.adapter = arrayAdapter
    }

    override fun onShowPress(p0: MotionEvent?) {}
    override fun onSingleTapUp(p0: MotionEvent?): Boolean { return false }
    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean { return false }
    override fun onLongPress(p0: MotionEvent?) {}
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        val diffX = ((p1?.x ?: 0f) - (p0?.x ?: 0f))
        if (diffX > 100) {
            stack.undo()
            refreshScreen()
            return true
        }
        return false
    }

}