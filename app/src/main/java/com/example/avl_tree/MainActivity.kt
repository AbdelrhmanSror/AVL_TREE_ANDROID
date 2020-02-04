package com.example.avl_tree

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.avl_tree.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var treeView: BinaryTreeView
    private lateinit var binding: ActivityMainBinding
    private val treeViewModel: TreeViewModel by lazy {
        ViewModelProvider(this).get(TreeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner=this
        binding.viewmodel=treeViewModel
        treeView = BinaryTreeView(this, binding.messageView)
        treeView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        binding.mainLayout.addView(treeView)
        setUpObserver()
    }

    private fun setUpObserver() {
        treeViewModel.onStartPressed.observe(this, Observer {
            it?.let {
                if (it) {
                    treeView.initialize()
                    treeViewModel.onStartDone()

                }
            }
        })
        treeViewModel.onInsertPressed.observe(this, Observer {
            it?.let {
                if (it) {
                    treeView.insert()
                    treeViewModel.onInsertDone()

                }
            }
        })
    }
}