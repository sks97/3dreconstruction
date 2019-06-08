package com.example.ar;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.PointCloud;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TwistGesture;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ArFragment arFragment;
    Session session;
    //private Point
    private ModelRenderable fans,blue,neck,female_renderable,hand_renderable,ruby_renderable;
    ImageView f,b,n,fem,rring,hand;
    View ArrayView[];
    Vector3 vec;
    ViewRenderable names;
    int selected=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        arFragment=(ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);
        f=(ImageView)findViewById(R.id.fans);
        b=(ImageView)findViewById(R.id.blueheart);
        n=(ImageView)findViewById(R.id.necklace);
        fem=(ImageView)findViewById(R.id.female_smiling);
        rring=(ImageView)findViewById(R.id.ring_ruby);
        hand=(ImageView)findViewById(R.id.hand_img);
        setArrayView();
        setClickListener();
        setupmodel();


        // Automatically releases point cloud resources at end of try block.


        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {


                    Anchor anchor=hitResult.createAnchor();
                    AnchorNode anchornode=new AnchorNode(anchor);
                    anchornode.setParent(arFragment.getArSceneView().getScene());
                    createModel(anchornode,selected);



            }
        });

    }

    private void setupmodel() {

        ModelRenderable.builder().setSource(this,R.raw.blueheart)
                .build().thenAccept(renderable->blue=renderable)
                .exceptionally(throwable-> {Toast.makeText(this,"Unable",Toast.LENGTH_SHORT).show();
                return null;}
                );
        ModelRenderable.builder().setSource(this,R.raw.led_eyes)
                .build().thenAccept(renderable -> fans= renderable)
                .exceptionally(throwable-> {Toast.makeText(this,"Unable",Toast.LENGTH_SHORT).show();
                    return null;}
                );
        ModelRenderable.builder().setSource(this,R.raw.necklace)
                .build().thenAccept(renderable->neck=renderable)
                .exceptionally(throwable-> {Toast.makeText(this,"Unable",Toast.LENGTH_SHORT).show();
                    return null;}
                );
        ModelRenderable.builder().setSource(this,R.raw.hand)
                .build().thenAccept(renderable->hand_renderable=renderable)
                .exceptionally(throwable-> {Toast.makeText(this,"Unable",Toast.LENGTH_SHORT).show();
                    return null;}
                );
        ModelRenderable.builder().setSource(this,R.raw.rubyring)
                .build().thenAccept(renderable->ruby_renderable=renderable)
                .exceptionally(throwable-> {Toast.makeText(this,"Unable",Toast.LENGTH_SHORT).show();
                    return null;}
                );
        ModelRenderable.builder().setSource(this,R.raw.female)
                .build().thenAccept(renderable->female_renderable=renderable)
                .exceptionally(throwable-> {Toast.makeText(this,"Unable",Toast.LENGTH_SHORT).show();
                    return null;}
                );



    }

    private void createModel(AnchorNode anchornode, int selected) {
        if(selected==1){

            TransformableNode one=new TransformableNode(arFragment.getTransformationSystem());
            one.setParent(anchornode);
            one.setRenderable(fans);
            one.select();
        }
        else if(selected==2){
            TransformableNode two=new TransformableNode(arFragment.getTransformationSystem());
            two.setParent(anchornode);
            two.setRenderable(blue);
            two.select();
        }
        else if(selected==3){
            TransformableNode three=new TransformableNode(arFragment.getTransformationSystem());
            three.setParent(anchornode);
            three.setRenderable(neck);
            three.select();
        }
        else if(selected==4){
            TransformableNode four=new TransformableNode(arFragment.getTransformationSystem());
            four.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), -90f));
            four.setParent(anchornode);
            four.setRenderable(female_renderable);
            four.select();
        }
        else if(selected==5){
            TransformableNode five=new TransformableNode(arFragment.getTransformationSystem());
            //five.setLocalRotation(Quaternion.axisAngle(new Vector3(0f, 1f, 0f), -90f));
            //five.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0f, 0f), -90f));
            five.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0f, 0f), -90f));
            vec=five.getLocalPosition();
            //five.setLocalRotation(Quaternion.axisAngle(new Vector3(0f, 0f, 1f), 90f));
            five.setParent(anchornode);
            five.setRenderable(hand_renderable);
            five.select();
        }
        else{
            TransformableNode six=new TransformableNode(arFragment.getTransformationSystem());
            //six.setLocalPosition(vec);
            six.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), -90f));
            six.setParent(anchornode);
            six.setRenderable(ruby_renderable);
            six.select();
        }
    }

    private void setClickListener() {
        for(int i=0;i<ArrayView.length;i++){
            ArrayView[i].setOnClickListener(this);
        }
    }

    private void setArrayView() {
        ArrayView=new View[]{
                f,b,n,fem,rring,hand
        };

    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.fans){
            selected=1;
            setBackground(v.getId());
        }
        else if(v.getId()== R.id.blueheart){
            selected=2;
            setBackground(v.getId());
        }
        else if(v.getId()== R.id.necklace){
            selected=3;
            setBackground(v.getId());
        }
        else if(v.getId()== R.id.female_smiling){
            selected=4;
            setBackground(v.getId());
        }
        else if(v.getId()== R.id.hand_img){
            selected=5;
            setBackground(v.getId());
        }
        else{
            selected=6;
            setBackground(v.getId());
        }

    }

    private void setBackground(int id) {
        return;
    }

    public void clicked(View v){
        Intent i=new Intent(this,Scanning.class);
        startActivity(i);
    }
}
