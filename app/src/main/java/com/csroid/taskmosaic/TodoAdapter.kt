package com.csroid.taskmosaic

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.csroid.taskmosaic.databinding.ItemTodoBinding

class TodoAdapter( val todoClickDeleteInterface: TodoClickDeleteInterface,
                   val todoClickInterface: TodoClickInterface) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder> () {

    class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)
    private var todos=ArrayList<Todo>()

    fun setTodoList(newList: List<Todo>)
    {
        todos.clear()
        todos.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(ItemTodoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currTodo = todos[position]
        holder.itemView.apply{
            holder.binding.tvTodoTitle.text=currTodo.todoTitle
        }
        holder.binding.idIVDelete.setOnClickListener {
            todoClickDeleteInterface.onDeleteIconClick(todos.get(position))
        }
        holder.itemView.setOnClickListener {
            todoClickInterface.onTodoClick(todos.get(position))
        }
    }

    override fun getItemCount(): Int {
        return todos.size;
    }
}


interface TodoClickDeleteInterface {
    fun onDeleteIconClick(todo:Todo)
}

interface TodoClickInterface {
    fun onTodoClick(todo: Todo)
}