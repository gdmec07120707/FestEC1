package com.example.latte_complier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

/**
 * Created by mayn on 2018/1/21.
 */

public class AppRegisterVisitor extends SimpleAnnotationValueVisitor7<Void ,Void>{
    private final Filer FILER;
    private String mPackageName= null;

    public AppRegisterVisitor(Filer FILER) {
        this.FILER = FILER;
    }

    @Override
    public Void visitString(String s, Void p) {
        mPackageName = s;
        return super.visitString(s, p);
    }

    @Override
    public Void visitType(TypeMirror typeMirror, Void p) {
        generateJavaCode(typeMirror);
        return p;
    }

    private void generateJavaCode(TypeMirror typeMirror){
        final TypeSpec targetActivity =
                TypeSpec.classBuilder("AppRegister")
                        .addModifiers(Modifier.PUBLIC)
                        .addModifiers(Modifier.FINAL)
                        .superclass(TypeName.get(typeMirror))
                        .build();

        final JavaFile javaFile = JavaFile.builder(mPackageName + ".wxapi",targetActivity)
                .build();

        try {
            javaFile.writeTo(FILER);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
