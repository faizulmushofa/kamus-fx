package io.github.kamusfx.MiniContainer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotasi untuk menandai suatu kelas agar dideteksi secara otomatis
 * sebagai Bean yang dikelola oleh Dependency Injection Container.
 *
 * @author its_me20
 * @version 1.0
 * @since 2026-05-20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Auto {
}
