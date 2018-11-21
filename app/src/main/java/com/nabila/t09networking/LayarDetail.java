package com.nabila.t09networking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nabila.t09networking.Model.PostPutDelPembelian;
import com.nabila.t09networking.Rest.ApiClient;
import com.nabila.t09networking.Rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayarDetail extends AppCompatActivity {
    EditText edtIdPembelian, edtIdPembeli, edtTanggalBeli, edtIdTiket, edtTotalHarga;
    Button btInsert, btUpdate, btDelete, btBack;
    TextView tvMessage;
    ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_detail);
        edtIdPembelian = findViewById(R.id.edtIdPembelian);
        edtIdPembeli = findViewById(R.id.edtIdPembeli);
        edtTanggalBeli = findViewById(R.id.edtTanggalBeli);
        edtIdTiket = findViewById(R.id.edtIdTiket);
        edtTotalHarga = findViewById(R.id.edtTotalHarga);

        tvMessage = findViewById(R.id.tvMessage2);

        btInsert = findViewById(R.id.btInsert2);
        btUpdate = findViewById(R.id.btUpdate2);
        btDelete = findViewById(R.id.btDelete2);
        btBack = findViewById(R.id.btBack);

        Intent mIntent = getIntent();
        edtIdPembelian.setText(mIntent.getStringExtra("id_pembelian"));
        edtIdPembeli.setText(mIntent.getStringExtra("id_pembeli"));
        edtTanggalBeli.setText(mIntent.getStringExtra("tanggal_beli"));
        edtIdTiket.setText(mIntent.getStringExtra("id_tiket"));
        edtTotalHarga.setText(mIntent.getStringExtra("total_harga"));

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        //UPDATE
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<PostPutDelPembelian> updatePembelianCall = mApiInterface.putPembelian(
                        edtIdPembelian.getText().toString(),
                        edtIdPembeli.getText().toString(),
                        edtTanggalBeli.getText().toString(),
                        edtTotalHarga.getText().toString(),
                        edtIdTiket.getText().toString());
                updatePembelianCall.enqueue(new Callback<PostPutDelPembelian>() {
                    @Override
                    public void onResponse(Call<PostPutDelPembelian> call, Response<PostPutDelPembelian> response) {
                        tvMessage.setText(" Retrofit Update: " +
                                "\n " + " Status Update : " + response.body().getStatus() +
                                "\n " + " Message Update : " + response.body().getMessage());
                    }

                    @Override
                    public void onFailure(Call<PostPutDelPembelian> call, Throwable t) {
                        tvMessage.setText("Retrofit Update: \n Status Update :" + t.getMessage());
                    }
                });
            }
        });

        //INSERT
        btInsert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Call<PostPutDelPembelian> postPembelianCall = mApiInterface.postPembelian(
                        edtIdPembelian.getText().toString(),
                        edtIdPembeli.getText().toString(),
                        edtTanggalBeli.getText().toString(),
                        edtTotalHarga.getText().toString(),
                        edtIdTiket.getText().toString());
                postPembelianCall.enqueue(new Callback<PostPutDelPembelian>() {
                    @Override
                    public void onResponse(Call<PostPutDelPembelian> call, Response<PostPutDelPembelian> response) {
                        tvMessage.setText(" Retrofit Insert: " +
                                "\n " + " Status Insert : " +
                                response.body().getStatus() +
                                "\n " + " Message Insert : " + response.body().getMessage());
                    }

                    @Override
                    public void onFailure(Call<PostPutDelPembelian> call, Throwable t) {
                        tvMessage.setText("Retrofit Insert: \n Status Insert :" + t.getMessage());
                    }
                });
            }
        });

        //DELETE
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtIdPembelian.getText().toString().trim().isEmpty()){
                    Call<PostPutDelPembelian> deletePembelian = mApiInterface.deletePembelian(edtIdPembelian.getText().toString());
                    deletePembelian.enqueue(new Callback<PostPutDelPembelian>() {
                        @Override
                        public void onResponse(Call<PostPutDelPembelian> call, Response<PostPutDelPembelian> response) {
                            tvMessage.setText(" Retrofit Delete: " +
                                    "\n " + " Status Delete : " +response.body().getStatus() +
                                    "\n " + " Message Delete : "+ response.body().getMessage());
                        }

                        @Override
                        public void onFailure(Call<PostPutDelPembelian> call, Throwable t) {
                            tvMessage.setText("Retrofit Delete: \n Status Delete :"+ t.getMessage());
                        }
                    });
                }else{
                    tvMessage.setText("id_pembelian harus diisi");
                }
            }
        });

        //BACK
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mIntent);
            }
        });
    }
}
