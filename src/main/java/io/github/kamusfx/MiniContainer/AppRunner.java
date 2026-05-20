package io.github.kamusfx.MiniContainer;

/**
 * Runner aplikasi bawaan untuk mengonfirmasi bahwa kontainer telah berjalan.
 *
 * @author its_me20
 * @version 1.0
 * @since 2026-05-20
 */
@Run
@Auto
public class AppRunner implements RunnableApp {

    /**
     * Menjalankan logika inisialisasi awal saat aplikasi berhasil dijalankan.
     */
    @Override
    public void run() {
        System.out.println("Application Started");
    }
}
