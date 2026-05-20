package io.github.kamusfx.MiniContainer;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.github.kamusfx.MiniContainer.Auto;
import io.github.kamusfx.MiniContainer.Run;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

/**
 * Pengelola siklus hidup objek (Bean) dan Dependency Injection secara otomatis.
 *
 * Bertanggung jawab melakukan scanning anotasi, pendaftaran instance,
 * pemecahan dependensi konstruktor, dan eksekusi runnable bean.
 *
 * @author its_me20
 * @version 1.0
 * @since 2026-05-20
 */
public class BeanManager {

    private final Map<Class<?>, Object> beans = new HashMap<>();
    private final Set<Class<?>> creating = new HashSet<>();

    /**
     * Membuat instance BeanManager, mendeteksi paket utama, dan menjalankan runner aplikasi.
     */
    public BeanManager() {
        scan("io.github.kamusfx");
        runApplication();
    }

    /**
     * Mendaftarkan bean/objek buatan secara manual ke dalam kontainer.
     *
     * @param type jenis kelas objek
     * @param instance instance objek yang didaftarkan
     * @param <T> tipe data kelas objek
     */
    public <T> void register(Class<T> type,T instance) {
        beans.put(type, instance);
    }

    /**
     * Mengambil instance bean yang sesuai dengan tipe kelas dari kontainer.
     * Jika bean belum terdaftar dan ditandai @Auto, maka kontainer akan menginstansiasinya.
     *
     * @param type tipe kelas bean yang diminta
     * @param <T> tipe objek bean
     * @return instance bean yang sesuai
     */
    public <T> T getBean(Class<T> type) {
        if (type.isInterface()) {
            
            for (Map.Entry<Class<?>, Object> entry : beans.entrySet()) {
                if (type.isAssignableFrom(entry.getKey())) {
                    return type.cast(entry.getValue());
                }
            }
            
            Class<?> implClass = findImplementation(type);

            if (implClass != null) {
                Object implInstance = getBean(implClass);
                beans.put(type, implInstance);
                return type.cast(implInstance);
            }
        }

        Object bean = beans.get(type);

        if (bean != null) {
            return type.cast(bean);
        }

        T created = createBean(type);

        beans.put(type, created);

        return created;
    }

    /**
     * Mencari kelas implementasi konkret dari suatu interface yang teranotasi @Auto.
     *
     * @param interfaceType tipe interface yang dicari implementasinya
     * @return kelas implementasi konkret, atau null jika tidak ditemukan
     */
    private Class<?> findImplementation(Class<?> interfaceType) {

        ConfigurationBuilder config = new org.reflections.util.ConfigurationBuilder()
                .setUrls(BeanManager.class.getProtectionDomain().getCodeSource().getLocation())
                .setScanners(Scanners.TypesAnnotated);

        Reflections reflections = new Reflections(config);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Auto.class);

        for (Class<?> clazz : classes) {
            if (interfaceType.isAssignableFrom(clazz) && !clazz.isInterface()) {
                return clazz;
            }
        }
        return null;
    }

    /**
     * Membuat instance bean baru berdasarkan tipe kelas dengan menyelesaikan dependensi konstruktornya.
     *
     * @param type tipe kelas bean yang akan dibuat
     * @param <T> tipe objek bean
     * @return instance bean yang berhasil dibuat
     * @throws RuntimeException jika kelas tidak memiliki @Auto atau terdeteksi circular dependency
     */
    private <T> T createBean(Class<T> type){
        if (!type.isAnnotationPresent(Auto.class)) {
            throw new RuntimeException(
                    type.getName() + " is not annotated with @Auto"
            );
        }

        if (creating.contains(type)) {
            throw new RuntimeException(
                    "Circular dependency detected: " + type.getName()
            );
        }

        creating.add(type);
        try {

            Constructor<?> constructor = type.getDeclaredConstructors()[0];
            constructor.setAccessible(true);

            Class<?>[] parameterTypes = constructor.getParameterTypes();

            Object[] dependencies = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                dependencies[i] = getBean(parameterTypes[i]);
            }

            return (T) constructor.newInstance(dependencies);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            creating.remove(type);
        }
    }

    /**
     * Memindai paket tertentu untuk menemukan seluruh kelas yang dianotasikan dengan @Auto.
     *
     * @param packageName nama paket yang akan dipindai
     */
    public void scan(String packageName) {
        ConfigurationBuilder config = new org.reflections.util.ConfigurationBuilder()
                .setUrls(BeanManager.class.getProtectionDomain().getCodeSource().getLocation())
                .setScanners(org.reflections.scanners.Scanners.TypesAnnotated);

        Reflections reflections = new Reflections(config);

        Set<Class<?>> classes =
                reflections.getTypesAnnotatedWith(Auto.class);

        for (Class<?> clazz : classes) {
            System.out.println("FOUND: " + clazz.getName());
            getBean(clazz);
        }
    }

    /**
     * Menjalankan bean bertipe RunnableApp yang memiliki anotasi @Run.
     *
     * @throws RuntimeException jika tidak ada bean beranotasi @Run yang ditemukan
     */
    private void runApplication() {

        for (Class<?> type : beans.keySet()) {

            if (type.isAnnotationPresent(Run.class)) {

                Object bean = getBean(type);

                if (bean instanceof RunnableApp app) {
                    app.run();
                }

                return;
            }
        }

        throw new RuntimeException("No @Run bean found");
    }

}
