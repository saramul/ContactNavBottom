package suriyon.cs.ubru.contactnavbottom;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import suriyon.cs.ubru.contactnavbottom.adapter.ContactAdapter;
import suriyon.cs.ubru.contactnavbottom.dao.ContactDAO;
import suriyon.cs.ubru.contactnavbottom.model.Contact;

public class ShowContactFragment extends Fragment {
    private TextView tvEmpty;
    private ImageView imgEmpty;
    private RecyclerView rcvContact;
    private ContactDAO db;
    private List<Contact> contacts;
    private ContactAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_contact, container, false);
        tvEmpty = view.findViewById(R.id.tv_empty);
        imgEmpty = view.findViewById(R.id.img_empty);
        rcvContact = view.findViewById(R.id.rcv_contact_list);

        db = new ContactDAO(getContext());
        displayEmptyImage();

        contacts = db.select();
        Log.d("Contact: ", contacts.get(1).getMobile());
        adapter = new ContactAdapter(getContext(), contacts);
        rcvContact.setAdapter(adapter);
        rcvContact.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void displayEmptyImage() {
        contacts = db.select();
        if(contacts.size()==0){
            imgEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);
        }else{
            imgEmpty.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);
        }
    }

}
