package com.example.imagespliterv1

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView1.addTextChangedListener(textWatcher)


        btnPickImg.setOnClickListener() {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, IMAGE_PICK_CODE)

            myImageViewSplitTopLeft.setImageBitmap(null)
            myImageViewSplitTopRight.setImageBitmap(null)
            myImageViewSplitBottomLeft.setImageBitmap(null)
            myImageViewSplitBottomRight.setImageBitmap(null)

            textView1.text = "Filled"
        }

        btnSplit.setOnClickListener() {

            var bm = (myImageView.drawable as BitmapDrawable).bitmap
            var cutBitmapTopLeft = Bitmap.createBitmap(bm.width / 2, bm.height / 2, Bitmap.Config.ARGB_8888)
            var canvasTopLeft = Canvas(cutBitmapTopLeft)
            var desRectTopLeft = Rect(0, 0, bm.width / 2, bm.height / 2)
            var srcRectTopLeft = Rect(0, 0, bm.width /2, bm.height / 2)
            canvasTopLeft.drawBitmap(bm, srcRectTopLeft, desRectTopLeft, null)
            myImageViewSplitTopLeft.setImageBitmap(cutBitmapTopLeft)

            var cutBitmapTopRight = Bitmap.createBitmap(bm.width / 2, bm.height / 2, Bitmap.Config.ARGB_8888)
            var canvasTopRight = Canvas(cutBitmapTopRight)
            var desRectTopRight = Rect(0, 0, bm.width / 2, bm.height / 2)
            var srcRectTopRight = Rect(bm.width / 2, 0, bm.width, bm.height / 2)
            canvasTopRight.drawBitmap(bm, srcRectTopRight, desRectTopRight, null)
            myImageViewSplitTopRight.setImageBitmap(cutBitmapTopRight)

            var cutBitmapBottomLeft = Bitmap.createBitmap(bm.width / 2, bm.height / 2, Bitmap.Config.ARGB_8888)
            var canvasBottomLeft = Canvas(cutBitmapBottomLeft)
            var desRectBottomLeft = Rect(0, 0, bm.width / 2, bm.height / 2)
            var srcRectBottomLeft = Rect(0, bm.height / 2, bm.width / 2, bm.height)
            canvasBottomLeft.drawBitmap(bm, srcRectBottomLeft, desRectBottomLeft, null)
            myImageViewSplitBottomLeft.setImageBitmap(cutBitmapBottomLeft)

            var cutBitmapBottomRight = Bitmap.createBitmap(bm.width / 2, bm.height / 2, Bitmap.Config.ARGB_8888)
            var canvasBottomRight = Canvas(cutBitmapBottomRight)
            var desRectBottomRight = Rect(0, 0, bm.width / 2, bm.height / 2)
            var srcRectBottomRight = Rect(bm.width / 2, bm.height / 2, bm.width, bm.height)
            canvasBottomRight.drawBitmap(bm, srcRectBottomRight, desRectBottomRight, null)
            myImageViewSplitBottomRight.setImageBitmap(cutBitmapBottomRight)
        }

        reset.setOnClickListener() {
            myImageView.setImageBitmap(null)
            myImageViewSplitTopLeft.setImageBitmap(null)
            myImageViewSplitTopRight.setImageBitmap(null)
            myImageViewSplitBottomLeft.setImageBitmap(null)
            myImageViewSplitBottomRight.setImageBitmap(null)
            textView1.text = ""
            textView.text = ""
        }

        close.setOnClickListener() {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            exitProcess(1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            myImageView.setImageURI(data?.data)
            var test = (myImageView.drawable as BitmapDrawable).bitmap
            textView.text = String.format("Resolution: %s x %s", test.width.toString(), test.height.toString())
        }
    }

    private var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val string = textView1.text.toString()
            reset.isEnabled = string.isNotEmpty()
            btnSplit.isEnabled = string.isNotEmpty()
        }
        override fun afterTextChanged(s: Editable) {}
    }
}
