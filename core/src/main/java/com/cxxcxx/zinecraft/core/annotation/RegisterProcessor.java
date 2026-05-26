package com.cxxcxx.zinecraft.core.annotation;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.FileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

@AutoService(Processor.class)
@SupportedAnnotationTypes("com.cxxcxx.zinecraft.core.annotation.Register")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class RegisterProcessor extends AbstractProcessor {

    class RegistryInfo {
        boolean init =  false;
        String mod;
        String package_name;
        String class_name;
    }

    enum ClassType {
        Item,
        Block,
        Null
    }

    class ClassInfo {
        ClassType type;
        String name;
        String en_us;
        String zh_cn;

        public ClassInfo(ClassType classType, String name, Register annotation) {
            this.type = classType;
            this.en_us = annotation.en_us();
            this.zh_cn = annotation.zh_cn();
            this.name = name;
        }
    }

    private final RegistryInfo ItemRegistryInfo = new RegistryInfo();
    private final RegistryInfo BlockRegistryInfo =  new RegistryInfo();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Vector<ClassInfo> Items = new Vector<>();
        Vector<ClassInfo> Blocks = new Vector<>();

        Types typeUtils = processingEnv.getTypeUtils();
        Elements elementUtils = processingEnv.getElementUtils();

        TypeMirror ItemTy = elementUtils.getTypeElement("net.minecraft.world.item.Item").asType();
        TypeMirror BlockTy = elementUtils.getTypeElement("net.minecraft.world.level.block.Block").asType();

        for (Element e : roundEnv.getElementsAnnotatedWith(Register.class)) {
            if (e.getKind() != ElementKind.FIELD) continue;

            VariableElement field = (VariableElement) e;
            TypeMirror fieldType = field.asType();

            ClassType classType = ClassType.Null;
            String name = field.getSimpleName().toString();

            // DeferredItem<T>
            // DeferredBlock<T>
            if (fieldType instanceof DeclaredType declaredType) {
                List<? extends TypeMirror> typeArgs = declaredType.getTypeArguments();
                // 获取泛型里的实际类型 T
                if (!typeArgs.isEmpty()) {
                    TypeMirror genericType = typeArgs.getFirst();

                    // 判断类型
                    if(typeUtils.isSubtype(genericType, ItemTy))
                        classType = ClassType.Item;
                    else if(typeUtils.isSubtype(genericType, BlockTy))
                        classType = ClassType.Block;
                    else
                        throw new RuntimeException("Unknown generic type: " + genericType);
                }
            }

            Register anno = e.getAnnotation(Register.class);
            switch (classType) {
                case Item:
                    Items.add(new ClassInfo(classType, name, anno));
                    getRegistryInfo(field, anno, ItemRegistryInfo);
                    break;
                case Block:
                    Blocks.add(new ClassInfo(classType, name, anno));
                    getRegistryInfo(field, anno, BlockRegistryInfo);
                    break;
            }

        }

        try {
            generateLanguageProvider(Items);
        } catch (IOException e) {

        }

        return true;
    }

    private void getRegistryInfo(VariableElement e, Register r, RegistryInfo info) {
        if(info.init) return;

        // 字段的直接父元素
        Element enclosingElement = e.getEnclosingElement();
        if (enclosingElement instanceof TypeElement classElement) {
            info.mod = r.mod();
            info.class_name = classElement.getQualifiedName().toString();
            info.package_name = processingEnv.getElementUtils().getPackageOf(classElement)
                    .getQualifiedName().toString();

            info.init = true;
        }
    }

    private void generateLanguageProvider(Vector<ClassInfo> Items) throws IOException {
        String fullClassName = "com.cxxcxx.zinecraft.main.EnLanguageProvider";
        FileObject f = processingEnv.getFiler().createSourceFile(fullClassName);

        try (Writer w = f.openWriter()) {
            w.write("""
                package %s;
                
                import net.minecraft.world.item.BlockItem;
                import net.minecraft.world.item.Item;
                import net.minecraft.data.PackOutput;
                import net.neoforged.neoforge.common.data.LanguageProvider;
                import %s;
          
                public class EnLanguageProvider extends LanguageProvider {
                    public EnLanguageProvider(PackOutput output, String modid, String locale) {
                        super(output, "%s", "en_us");
                    }
                
                    @Override
                    protected void addTranslations()  {
                """.formatted(ItemRegistryInfo.package_name, ItemRegistryInfo.class_name, ItemRegistryInfo.mod));

            for (var item : Items) {
                w.write("        this.add(ItemRegistry." + item.name + ".get(), \"" + item.en_us + "\");\n");
            }

            w.write("""
                    }
                }
                """);
        }
    }
}