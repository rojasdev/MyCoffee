package com.rhix.mycoffee;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CoffeeAdapter extends BaseAdapter {

    private Context context;
    private List<CoffeeBean> coffeeList;
    private LayoutInflater inflater;

    public CoffeeAdapter(Context context, List<CoffeeBean> coffeeList) {
        this.context = context;
        this.coffeeList = coffeeList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return coffeeList.size();
    }

    @Override
    public Object getItem(int position) {
        return coffeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_coffee, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.coffeeImage);
            holder.nameText = convertView.findViewById(R.id.coffeeName);
            //holder.descText = convertView.findViewById(R.id.coffeeDescription);
            holder.originText = convertView.findViewById(R.id.coffeeOrigin);
            holder.roastText = convertView.findViewById(R.id.coffeeRoast);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CoffeeBean coffee = coffeeList.get(position);

        holder.nameText.setText(coffee.getName());
        //holder.descText.setText(coffee.getDescription());
        holder.originText.setText("Origin: " + coffee.getOrigin());
        holder.roastText.setText("Roast: " + coffee.getRoast());

        // Load image with Picasso
        if (coffee.getImageUrl() != null && !coffee.getImageUrl().isEmpty()) {
            Picasso.get()
                    .load(coffee.getImageUrl())
                    .placeholder(R.drawable.placeholder) // placeholder image
                    .error(R.drawable.error)             // error fallback
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.placeholder);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView nameText;
        //TextView descText;
        TextView originText;
        TextView roastText;
    }
}
