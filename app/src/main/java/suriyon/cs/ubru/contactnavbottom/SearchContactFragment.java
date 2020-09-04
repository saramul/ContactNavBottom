package suriyon.cs.ubru.contactnavbottom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import suriyon.cs.ubru.contactnavbottom.adapter.ContactAdapter;
import suriyon.cs.ubru.contactnavbottom.dao.ContactDAO;
import suriyon.cs.ubru.contactnavbottom.model.Contact;

public class SearchContactFragment extends Fragment {
    private EditText edtSearch;
    private Button btnSearch;
    private RecyclerView rcvSearchContact;
    private List<Contact> contacts;
    private ContactAdapter adapter;
    private ContactDAO db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_contact, container, false);
        db = new ContactDAO(getContext());
        matchView(view);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contactName = edtSearch.getText().toString();
                contacts = db.selectByName(contactName);

                if(contacts.size()>0){
                    adapter = new ContactAdapter(getContext(), contacts);
                    rcvSearchContact.setAdapter(adapter);
                    rcvSearchContact.setLayoutManager(new LinearLayoutManager(getContext()));
                }else{
                    Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                }
                edtSearch.setText("");
                edtSearch.requestFocus();
            }
        });
        return view;
    }

    private void matchView(View view) {
        edtSearch = view.findViewById(R.id.edt_search);
        btnSearch = view.findViewById(R.id.btn_search);
        rcvSearchContact = view.findViewById(R.id.rcv_search_contact);
    }
}
