package suriyon.cs.ubru.contactnavbottom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import suriyon.cs.ubru.contactnavbottom.dao.ContactDAO;
import suriyon.cs.ubru.contactnavbottom.model.Contact;

public class AddContactFragment extends Fragment {
    private EditText edtName, edtMobile;
    private Button btnAdd;
    private ContactDAO db;
    private List<Contact> contacts;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        db = new ContactDAO(getContext());

        matchView(view);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String mobile = edtMobile.getText().toString();

                if(name.isEmpty() || mobile.isEmpty()){
                    Toast.makeText(getContext(), "Please full fill data before!", Toast.LENGTH_SHORT).show();
                }else{
                    Contact contact = new Contact(name, mobile);
                    boolean result = db.insert(contact);
                    if(result){
                        Toast.makeText(getContext(), "Insert contact successfully!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Unable to insert contact!", Toast.LENGTH_SHORT).show();
                    }
                    clearText();
                }
            }
        });
        return view;
    }

    private void clearText() {
        edtMobile.setText("");
        edtName.setText("");
        edtName.requestFocus();
    }

    private void matchView(View view) {
        edtName = view.findViewById(R.id.edt_add_name);
        edtMobile = view.findViewById(R.id.edt_add_mobile);
        btnAdd = view.findViewById(R.id.btn_add);
    }
}
