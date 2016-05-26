
//testing 2nd commit
package com.example.shivs.myapplication;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MyActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public final static String EXTRA_MESSAGE_IMAGE = "com.mycompany.myfirstapp.MESSAGE_IMAGE";

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final ImageView mImageView = null;
    View view = null;
    Bitmap imageBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //disabling image capture button if camera is not available in device
        PackageManager packageMgr = getPackageManager();
        if (!packageMgr.hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            Button imageCaptureButton = (Button) findViewById(R.id.capture_image);
            imageCaptureButton.setEnabled(false);
        }
    }

    public void captureNewSyllabus(View view)
    {
        //saving view in class variable to use it later to navigate to next activity in onActivityResult()
        this.view= view;

        //calling function to capture new image
        dispatchTakePictureIntent();
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
           imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);
            receiveMsg(this.view);
        }
    }
    public void receiveMsg(View view)
    {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = "Took image successfully";
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_MESSAGE_IMAGE, imageBitmap);
        startActivity(intent);
        System.out.println("****************** pixel value"+imageBitmap.getPixel(100,100));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
