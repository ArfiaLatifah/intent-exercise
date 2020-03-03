package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    public static final String AVATAR_KEY = "image";
    public static final String NAME_KEY = "name";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";
    public static final String CONFIRM_KEY = "confirm";
    public static final String HOMEPAGE_KEY = "homepage";
    public static final String ABOUT_KEY = "about";

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    private EditText nameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmInput;
    private EditText homepageInput;
    private EditText aboutInput;

    private ImageView avatarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameInput = findViewById(R.id.text_fullname);
        emailInput = findViewById(R.id.text_email);
        passwordInput = findViewById(R.id.text_password);
        confirmInput = findViewById(R.id.text_confirm_password);
        homepageInput = findViewById(R.id.text_homepage);
        aboutInput = findViewById(R.id.text_about);
        avatarImage = findViewById(R.id.image_profile);
    }

    public void handleOk(View view) {
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirm = confirmInput.getText().toString();
        String homepage = homepageInput.getText().toString();
        String about = aboutInput.getText().toString();

        if(!(name).equals("") && !(email).equals("") && !(password).equals("") && !(confirm).equals("") && !(homepage).equals("") && !(about).equals("")){
            if((password).equals(confirm)) {
                if (isValidEmail(emailInput.getText().toString())){

                    Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);

                    avatarImage.buildDrawingCache();
                    Bitmap image = avatarImage.getDrawingCache();
                    Bundle extras = new Bundle();

                    extras.putParcelable(AVATAR_KEY, image);

                    intent.putExtra(AVATAR_KEY, image);
                    intent.putExtra(NAME_KEY, name);
                    intent.putExtra(EMAIL_KEY, email);
                    intent.putExtra(PASSWORD_KEY, password);
                    intent.putExtra(CONFIRM_KEY, confirm);
                    intent.putExtra(HOMEPAGE_KEY, homepage);
                    intent.putExtra(ABOUT_KEY, about);


                    startActivity(intent);

                } else {
                    emailInput.setError("Format email salah");
                    Toast.makeText(RegisterActivity.this,
                            "Format email salah", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Password dan Konfirmasi Password tidak sesuai", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Semua data harus diisi", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            return;
        }
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                    avatarImage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    Toast.makeText(this, "Can't Load Image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void handleChangeAvatar(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public static boolean isValidEmail(String email) {
        boolean validate;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)) {
            validate = true;
        } else if (email.matches(emailPattern2)) {
            validate = true;
        } else {
            validate = false;
        }

        return validate;
    }

}
