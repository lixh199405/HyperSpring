package com.hyperspring.processor;

/**
 * @Author: xhl
 * @Date: 2026-04-29 19:15
 * @Description:
 */

import com.google.auto.service.AutoService;
import com.hyperspring.annotation.HyperComponent;
import com.hyperspring.annotation.HyperInject;
import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
// 注意：如果你用了 @AutoService，下面这两个注解其实可以省略，但保留也没坏处
@SupportedAnnotationTypes("com.hyperspring.annotation.HyperComponent")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class HyperProcessor extends AbstractProcessor {

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.typeUtils = processingEnv.getTypeUtils();
        this.elementUtils = processingEnv.getElementUtils();
        this.filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 1. 遍历所有被 @HyperComponent 标记的类
        for (Element element : roundEnv.getElementsAnnotatedWith(HyperComponent.class)) {
            // 确保只处理类，不处理接口或枚举
            if (element.getKind() != ElementKind.CLASS) continue;

            TypeElement typeElement = (TypeElement) element;
            // 获取包名
            String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
            // 获取类名
            String simpleName = typeElement.getSimpleName().toString();

            // 2. 生成工厂类：User$$HyperFactory
            TypeSpec factoryClass = TypeSpec.classBuilder(simpleName + "$$HyperFactory")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL) // 建议加 FINAL
                    .addMethod(createCreateMethod(typeElement)) // 这里不再报红
                    .build();

            // 3. 写入文件
            try {
                JavaFile.builder(packageName, factoryClass)
                        .build()
                        .writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 生成 create() 方法
     */
    private MethodSpec createCreateMethod(TypeElement typeElement) {
        // 获取当前类的 TypeName
        TypeName targetType = TypeName.get(typeElement.asType());

        MethodSpec.Builder builder = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(targetType)
                // 生成：TargetClass instance = new TargetClass();
                .addStatement("$T instance = new $T()", targetType, targetType);

        // 遍历字段，处理 @HyperInject
        for (Element enclosedElement : typeElement.getEnclosedElements()) {
            // 确保是字段且被注解标记
            if (enclosedElement.getKind() == ElementKind.FIELD && enclosedElement.getAnnotation(HyperInject.class) != null) {

                VariableElement field = (VariableElement) enclosedElement;
                String fieldName = field.getSimpleName().toString();
                TypeMirror fieldTypeMirror = field.asType();

                // 核心修复：将 TypeMirror 转换为 Element，再转为 TypeElement
                Element fieldElement = typeUtils.asElement(fieldTypeMirror);

                if (fieldElement instanceof TypeElement) {
                    TypeElement fieldClassElement = (TypeElement) fieldElement;

                    // 构建依赖类的工厂类名：com.example.UserService -> com.example.UserService$$HyperFactory
                    ClassName dependencyFactoryName = ClassName.get(
                            elementUtils.getPackageOf(fieldClassElement).toString(),
                            fieldClassElement.getSimpleName() + "$$HyperFactory"
                    );

                    // 生成代码：instance.userService = UserService$$HyperFactory.create();
                    builder.addStatement("instance.$L = $T.create()", fieldName, dependencyFactoryName);
                }
            }
        }

        // 生成：return instance;
        builder.addStatement("return instance");

        return builder.build();
    }
}