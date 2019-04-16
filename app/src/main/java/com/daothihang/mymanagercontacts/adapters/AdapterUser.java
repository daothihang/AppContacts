package com.daothihang.mymanagercontacts.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
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
import com.daothihang.mymanagercontacts.activities.ActivityAddContacts;
import com.daothihang.mymanagercontacts.activities.ActivityUpdate;
import com.daothihang.mymanagercontacts.models.DiffCallbackUser;
import com.daothihang.mymanagercontacts.models.User;
import com.daothihang.mymanagercontacts.untils.DatabaseContacts;

import java.util.ArrayList;
import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.RecyclerViewHolder> implements Filterable {
    private DatabaseContacts data;
    private Context mContext;
    private ArrayList<User> dataSrc;
    private ArrayList<User> lsFilted;

    public AdapterUser(Context context, ArrayList<User> data, DatabaseContacts datas) {
        mContext = context;
        this.dataSrc = data;
        this.lsFilted = data;
        this.data = datas;

    }
//DIFFUTIL
    private List<User> mEmployees = new ArrayList<>();

    public AdapterUser(List<User> employeeList) {
        this.mEmployees.addAll(employeeList);
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


    RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background).transform(new CenterCrop())
            .transform(new FitCenter());

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        //final User employee = mEmployees.get(position);

        holder.tvId.setText(lsFilted.get(position).getId_user());
        holder.tvName.setText(lsFilted.get(position).getName());
        holder.tvPhone.setText(lsFilted.get(position).getPhone());
        holder.tvAddress.setText(lsFilted.get(position).getAddress());
        Glide.with(mContext)
                .load(R.drawable.avartar)
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
               // notifyItemChanged(position);

            }
        });
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.deleteUsers(String.valueOf(lsFilted.get(position).getId_user()));
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
        ImageView imgAvartar;
        ImageView imgSua;
        ImageView imgXoa;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id_user);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvAddress = itemView.findViewById(R.id.tv_address);
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


//    public void updateEmployeeListItems(List<User> employees) {
//        final DiffCallbackUser diffCallback = new DiffCallbackUser(this.dataSrc, employees);
//        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
//
//        this.dataSrc.clear();
//        this.dataSrc.addAll(employees);
//        diffResult.dispatchUpdatesTo(this);
//    }

}
