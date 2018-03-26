package com.example.shamim.nstubds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView blood_list_view;
    private List<BloodPost> post_list;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private BloodPostRecyclerAdapter bloodPostRecyclerAdapter;

    private DocumentSnapshot lastVisible;
    private boolean isFirstPageFirstLoad = true;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        blood_list_view = view.findViewById(R.id.Blood_list_view);
        post_list = new ArrayList<>();

        bloodPostRecyclerAdapter = new BloodPostRecyclerAdapter(post_list);

        blood_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        blood_list_view.setAdapter(bloodPostRecyclerAdapter);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {

            //for paginate scrolling

            blood_list_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    Boolean reachedBottom = !recyclerView.canScrollVertically(1);

                    if(reachedBottom){

                        String desc = lastVisible.getString("desc");
                        Toast.makeText(container.getContext(),"Loading... ",Toast.LENGTH_SHORT).show();
                        loadMorePost();
                    }
                }
            });

            //------------------//

        firebaseFirestore = FirebaseFirestore.getInstance();


            Query  firstQuery = firebaseFirestore.collection("Posts").orderBy("timestamp",Query.Direction.DESCENDING).limit(3);

        firstQuery.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {


                if(isFirstPageFirstLoad) {
                    lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                }

                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                    if (doc.getType() == DocumentChange.Type.ADDED) {

                        BloodPost bloodPost = doc.getDocument().toObject(BloodPost.class);

                        if(isFirstPageFirstLoad){
                            post_list.add(bloodPost);
                        }
                        else{
                            post_list.add(0,bloodPost);
                        }

                        bloodPostRecyclerAdapter.notifyDataSetChanged();
                    }
                }

                isFirstPageFirstLoad = false;
            }
        });

    }
        return view;
    }

    public  void loadMorePost(){

        Query  nextQuery = firebaseFirestore.collection("Posts")
                .orderBy("timestamp",Query.Direction.DESCENDING)
                .startAfter(lastVisible)
                .limit(3);

        nextQuery.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if(!documentSnapshots.isEmpty()){

                    lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() -1);

                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            BloodPost bloodPost = doc.getDocument().toObject(BloodPost.class);
                            post_list.add(bloodPost);
                            bloodPostRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }


        });

    }

}
