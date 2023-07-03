package com.example.todoapp.ui.newTaskActv

import android.icu.text.DateTimePatternGenerator.DAY
import android.os.Bundle
import android.view.Menu
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.todoapp.R
import com.example.todoapp.data.model.Priority
import com.example.todoapp.databinding.NewTaskActivityBinding
import com.example.todoapp.utils.toStringDate
import com.example.todoapp.utils.toStringResource
import com.google.android.material.datepicker.MaterialDatePicker
import com.niqr.todoapp.ui.taskEdit.model.TaskEditUiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewTaskActivity : AppCompatActivity() {

    private lateinit var binding: NewTaskActivityBinding
    private val viewModel by viewModels<NewTaskViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewTaskActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.closeNewTask.setOnClickListener{ onBackPressed() }

        setupUiEventsListener()
        showPriorityMenu()
        setupData()
        setupText()
        changeFlows()
        setUpButtons()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_in_left)
    }

    private fun setupUiEventsListener() {
        lifecycleScope.launch {
            viewModel.uiEvent.collectLatest {
                when(it) {
                    TaskEditUiEvent.NavigateBack -> onBackPressed()
                }
            }
        }
    }

    private fun changeFlows(){
        lifecycleScope.launch {
            viewModel.text.collect{
                binding.editText.setTextKeepState(it)
            }
        }
        lifecycleScope.launch {
            viewModel.deadLine.collect{
                binding.editDate.text = it.toStringDate()
                }
            }
        lifecycleScope.launch {
            viewModel.priority.collect{
                binding.priorityText.text = getText(it.toStringResource())
            }
        }
    }

    private fun setUpButtons(){
        binding.apply {
            saveButton.setOnClickListener{
                viewModel.saveTask()
                onBackPressed()
            }
        }
    }

    private fun setupText(){
        binding.editText.addTextChangedListener {
            viewModel.updateText(it.toString())
        }
    }

    private fun showPriorityMenu(){

        val priorityMenu = PopupMenu(this, binding.priorityField)

        priorityMenu.menu.apply {
            add(0, ID_NO_PRI, Menu.NONE, applicationContext.getString(R.string.priority_no))
            add(0, ID_LOW_PRI, Menu.NONE, applicationContext.getString(R.string.priority_low))
            add(0, ID_HID_PRI, Menu.NONE, applicationContext.getString(R.string.priority_high))
        }

        priorityMenu.setOnMenuItemClickListener {
            val priority = when(it.itemId){
                0 -> Priority.NO
                1 -> Priority.LOW
                2 -> Priority.HIGH
                else -> Priority.NO
            }
            viewModel.updatePriority(priority)
            true
        }

        binding.priorityField.setOnClickListener {
            priorityMenu.show()
        }
    }

    companion object {
        private const val ID_NO_PRI = 0
        private const val ID_LOW_PRI = 1
        private const val ID_HID_PRI = 2
    }

    private fun setupData(){

        val date = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_date))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds() + DAY)
            .build()

        binding.switchDate.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                binding.textDate.isClickable = true
                binding.textDate.setOnClickListener {
                    if (!date.isVisible)
                        date.show(supportFragmentManager, null)
                }
            }else {
                binding.textDate.isClickable = false
            }
        }
        date.addOnPositiveButtonClickListener (viewModel::updateDate)
    }

}