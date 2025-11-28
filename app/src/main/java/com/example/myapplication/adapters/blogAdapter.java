package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.blog_content;
import com.example.myapplication.model.blogItem;

import java.util.List;

public class blogAdapter extends RecyclerView.Adapter<blogAdapter.BlogViewHolder>{
    Context context;
    List<blogItem> blogList;

    public blogAdapter(Context context, List<blogItem> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.blog_items, parent, false);
        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        blogItem item = blogList.get(position);

        // Título
        holder.txtTitulo.setText(item.titulo);

        // Imagen usando nombre de recurso
        int imageResId = context.getResources()
                .getIdentifier(item.imagen_name, "mipmap", context.getPackageName());

        if (imageResId != 0) {
            holder.imgBlog.setImageResource(imageResId);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, blog_content.class);

            intent.putExtra("id", item.id);
            intent.putExtra("titulo", item.titulo);
            intent.putExtra("imagen_name", item.imagen_name);
            intent.putExtra("autor_id", item.autor_id);
            //intent.putExtra("fecha_publicacion", item.fecha_publicacion);
            intent.putExtra("contenido", item.contenido);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBlog;
        TextView txtTitulo;

        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBlog = itemView.findViewById(R.id.imgBlog);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
        }
    }
}
