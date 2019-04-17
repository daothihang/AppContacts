package com.daothihang.mymanagercontacts.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import com.daothihang.mymanagercontacts.R;
import com.daothihang.mymanagercontacts.UpdateDelete;
import com.daothihang.mymanagercontacts.activities.ActivityUpdate;
import com.daothihang.mymanagercontacts.activities.MainActivity;
import com.daothihang.mymanagercontacts.models.User;
import com.daothihang.mymanagercontacts.untils.DatabaseContacts;

import java.util.ArrayList;
import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.RecyclerViewHolder> implements Filterable {
    private DatabaseContacts data;
    private Context mContext;
    private ArrayList<User> dataSrc;
    private ArrayList<User> lsFilted;
    private UpdateDelete lister;

    public AdapterUser(Context context, ArrayList<User> data, DatabaseContacts datas, UpdateDelete lister) {
        mContext = context;
        this.dataSrc = data;
        this.lsFilted = data;
        this.data = datas;
        this.lister = lister;

    }

    //DIFFUTIL
    private List<User> usersArra = new ArrayList<>();

    public AdapterUser(List<User> employeeList) {
        this.usersArra.addAll(employeeList);
    }
//

    public ArrayList<User> getData() {
        return lsFilted;
    }

    @Override
    public AdapterUser.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_contacts, parent, false);
        return new RecyclerViewHolder(view);
    }

    private static final int REQUEST_CODE_UPDATE = 102;
    private static final int REQUEST_CODE_DELETE = 103;
    RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background).transform(new CenterCrop())
            .transform(new FitCenter());

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        String title = lsFilted.get(position).getName();

        String titleAvartar = title.substring(0,1);
        String titleAvartar1 = titleAvartar.toLowerCase();
        holder.tvTitle.setText(titleAvartar1);

        holder.tvId.setText(lsFilted.get(position).getId_user());
        holder.tvName.setText(lsFilted.get(position).getName());
        holder.tvPhone.setText(lsFilted.get(position).getPhone());
        holder.tvAddress.setText(lsFilted.get(position).getAddress());

        Glide.with(mContext)
                .load(R.drawable.anh)
                .thumbnail(0.4f)
                .apply(options)
                .into(holder.imgAvartar);

        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActivityUpdate.class);
                intent.putExtra("name", lsFilted.get(position).getName());
                Bundle bundle = new Bundle();
                bundle.putString("iduser", (lsFilted.get(position).getId_user()));
                intent.putExtras(bundle);
                intent.putExtra("phone", lsFilted.get(position).getPhone());
                intent.putExtra("address", lsFilted.get(position).getAddress());
                intent.putExtra("avartar", lsFilted.get(position).getAvartar());
                v.getContext().startActivity(intent);



            }
        });
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.deleteUsers(String.valueOf(lsFilted.get(position).getId_user()));
                lister.update(true);
                Toast.makeText(mContext, "Xóa thành công.", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return lsFilted == null ? 0 : lsFilted.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvId;
        TextView tvName;
        TextView tvPhone;
        TextView tvAddress;
        TextView tvTitle;
        ImageView imgAvartar;
        ImageView imgSua;
        ImageView imgXoa;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id_user);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvTitle = itemView.findViewById(R.id.id_title);
            imgAvartar = itemView.findViewById(R.id.img_avartar);
            imgSua = itemView.findViewById(R.id.btn_sua);
            imgXoa = itemView.findViewById(R.id.btn_xoa);

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                Log.d("ddd", charString);
                ArrayList<User> filteredList = new ArrayList<>();
                if (charString.isEmpty()) {
                    filteredList = dataSrc;
                } else {
                    for (User b : dataSrc) {
                        if (b.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(b);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                lsFilted = (ArrayList<User>) results.values;
                notifyDataSetChanged();
            }

        };
    }



}
