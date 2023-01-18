package com.example.budgetku.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.budgetku.R;
import com.example.budgetku.model.object.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAutoCompleteAdapter extends ArrayAdapter<Category> {
    private List<Category> categories;

    public CategoryAutoCompleteAdapter(@NonNull Context context, @NonNull List<Category> categories) {
        super(context, 0, categories);
        this.categories = new ArrayList<>(categories);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return categoryFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.category_autocomplete_row, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.category_name);

        Category category = getItem(position);

        if (category != null) {
            textViewName.setText(category.getName());
        }

        return convertView;
    }

    private Filter categoryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Category> suggestions = new ArrayList<Category>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(categories);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Category item : categories) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Category) resultValue).getName();
        }
    };
}
