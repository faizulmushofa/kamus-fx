package io.github.kamusfx.MiniContainer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotasi untuk menandai kelas Runner utama aplikasi yang akan dieksekusi
 * setelah inisialisasi BeanManager selesai.
 *
 * @author its_me20
 * @version 1.0
 * @since 2026-05-20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Run {
}
