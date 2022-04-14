package com.devkominfosda.moviecataloguebajp1.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devkominfosda.moviecataloguebajp1.core.R
import com.devkominfosda.moviecataloguebajp1.core.databinding.ItemListBinding
import com.devkominfosda.moviecataloguebajp1.core.domain.model.Movies
import com.devkominfosda.moviecataloguebajp1.core.utils.loadImage

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var listData = ArrayList<Movies>()
    var onItemClick: ((Movies) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<Movies>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): MovieViewHolder {
        val itemListBinding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MovieViewHolder(itemListBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class MovieViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movies) {
            val context = itemView.context
            with(binding) {
                tvTitleMovie.text = movie.title
                tvRating.text = movie.voteAverage.toString()
                context.loadImage(
                    context.getString(R.string.url_poster, movie.posterPath),
                    ivPoster
                )
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}