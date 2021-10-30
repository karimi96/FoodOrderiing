package com.example.foodorderiing.design;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderiing.R;

public class BlureImage extends AppCompatActivity{
//    private static final float SCALE = 0.5f;
//    private static final float BLUR_RADIUS = 18f;


        public static Bitmap blur18(Context context , Bitmap image ,float num ){
             float SCALE = 0.5f;
            float BLUR_RADIUS = num;
//            float BLUR_RADIUS = 18f;

            int height =Math.round(image.getHeight() * SCALE);
            int width =Math.round(image.getWidth() * SCALE);
            Bitmap inputBitmap = Bitmap.createScaledBitmap(image,width,height,false);
            Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
            RenderScript renderScript = RenderScript.create(context);
            ScriptIntrinsicBlur intrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
            Allocation tmIn = Allocation.createFromBitmap(renderScript,inputBitmap);
            Allocation tmOut = Allocation.createFromBitmap(renderScript,outputBitmap);
            intrinsicBlur.setRadius(BLUR_RADIUS);
            intrinsicBlur.setInput(tmIn);
            intrinsicBlur.forEach(tmOut);
            tmOut.copyTo(outputBitmap);
            return outputBitmap;

        }



    public static Bitmap blur10(Context context , Bitmap image ){
        float SCALE = 0.5f;
        float BLUR_RADIUS = 10f;

        int height =Math.round(image.getHeight() * SCALE);
        int width =Math.round(image.getWidth() * SCALE);
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image,width,height,false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        RenderScript renderScript = RenderScript.create(context);
        ScriptIntrinsicBlur intrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        Allocation tmIn = Allocation.createFromBitmap(renderScript,inputBitmap);
        Allocation tmOut = Allocation.createFromBitmap(renderScript,outputBitmap);
        intrinsicBlur.setRadius(BLUR_RADIUS);
        intrinsicBlur.setInput(tmIn);
        intrinsicBlur.forEach(tmOut);
        tmOut.copyTo(outputBitmap);
        return outputBitmap;

    }



    public static Bitmap blur15(Context context , Bitmap image ){
        float SCALE = 0.5f;
        float BLUR_RADIUS = 15f;

        int height =Math.round(image.getHeight() * SCALE);
        int width =Math.round(image.getWidth() * SCALE);
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image,width,height,false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        RenderScript renderScript = RenderScript.create(context);
        ScriptIntrinsicBlur intrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        Allocation tmIn = Allocation.createFromBitmap(renderScript,inputBitmap);
        Allocation tmOut = Allocation.createFromBitmap(renderScript,outputBitmap);
        intrinsicBlur.setRadius(BLUR_RADIUS);
        intrinsicBlur.setInput(tmIn);
        intrinsicBlur.forEach(tmOut);
        tmOut.copyTo(outputBitmap);
        return outputBitmap;

    }

//
//        public class build_Image_Blure extends AppCompatActivity {
//            public void build(ImageView imageView, int im){
//              int img=im;
//                Bitmap bm = BitmapFactory.decodeResource(getResources(),img);
//                Bitmap bit_kebab = blur(getApplicationContext(),bm);
//                imageView.setImageBitmap(bit_kebab);
//            }
//
//        }


//    public void build(ImageView imageView, int im){
////        int img=im;
//        Bitmap bm = BitmapFactory.decodeResource(getResources(),im );
//        Bitmap bit_kebab = blur(getApplicationContext(),bm);
//        imageView.setImageBitmap(bit_kebab);
//    }




    }


