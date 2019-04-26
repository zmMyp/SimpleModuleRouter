package zm.myp.smr.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Completion;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


import zm.myp.smr.annotations.SmrLoadModule;

/**
 * Created by qianjian on 2019/4/26.
 */
@AutoService(Processor.class)
public class SmrProcessor extends AbstractProcessor {

    private Filer mFiler;
    private Types mTypeUtils;
    private static boolean fileCreated = false;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mTypeUtils = processingEnvironment.getTypeUtils();
    }

    // 1. 指定处理的版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    // 2. 给到需要处理的注解
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(SmrLoadModule.class);
        return annotations;
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        if (!fileCreated) {
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(SmrLoadModule.class);
            String[] modulespaths = null;
            for (Element element : elements) {
                System.out.println(element);
                modulespaths = element.getAnnotation(SmrLoadModule.class).modules();
            }

            TypeSpec.Builder classBuilder = TypeSpec.classBuilder("SmrApplication_Helper")
                    .addModifiers(Modifier.FINAL, Modifier.PUBLIC);

            //拼接List<String>这个返回值
            ClassName string = ClassName.get("java.lang", "String");
            ClassName list = ClassName.get("java.util", "List");
            TypeName listModules = ParameterizedTypeName.get(list, string);

            //拼接函数
            MethodSpec.Builder loadMethodBuilder = MethodSpec.methodBuilder("loadModules")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(listModules);

            loadMethodBuilder.addStatement("$T<$T> modules=new $T<>();", List.class, String.class, ArrayList.class);
            if (modulespaths != null) {
                for (int i = 0; i < modulespaths.length; i++) {
                    loadMethodBuilder.addStatement("modules.add(\"$L\")", modulespaths[i]);
                }
            }
            loadMethodBuilder.addStatement("return modules");

            classBuilder.addMethod(loadMethodBuilder.build());

            // 生成类，看下效果
            try {
                JavaFile.builder("zm.myp.smr.source", classBuilder.build())
                        .addFileComment("自动生成")
                        .build().writeTo(mFiler);
                fileCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("翻车了！");
            }
        }


        return false;
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotationMirror, ExecutableElement executableElement, String s) {

        return super.getCompletions(element, annotationMirror, executableElement, s);
    }
}
