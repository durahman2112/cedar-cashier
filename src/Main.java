import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static double total_bayar;
    static List<Product> products = new ArrayList<>();
    static List<SelectedProduct> selected_products = new ArrayList<>();
    static String selected_pembayaran, selected_bank, selected_no_pembayaran;

    public static void main(String[] args) throws IOException {
        System.out.println("Selamat datang di CEDAR Cashier application");

        // Menambahkan Product ke List menggunakan method addProduct
        addProducts();
        // Menambahkan Product ke List menggunakan Method TotalHarga
        total_bayar = TotalHarga(products);

        System.out.println("Total : " + total_bayar);
        System.out.println();
        System.out.println("--------------");

        // Memanggil Method Pembayaran
        Pembayaran();
        try {
            // Membuat Format Nama untuk File Struk
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy__HH_mm_ss");

            String formattedDate = myDateObj.format(myFormatObj);
            // Memanggil Method writeStruk untuk menampilkan Struk
            writeStruk(formattedDate + ".txt", selected_products);
            System.out.println("***STRUK BERHASIL DI PRINT***");
        } catch (IOException e) {
            System.out.println("!!!Struk Gagal di Print!!!");
        }
    }

    static void addProducts() {
        // menambahkan produk ke List
        products.add(new Product("Cheese Cake", 30000));
        products.add(new Product("Macaroni Cheese", 30000));
        products.add(new Product("Bola-bola keju", 25000));
        products.add(new Product("Bola Singkong Keju", 15000));
        products.add(new Product("Kentang Goreng Keju", 20000));
        products.add(new Product("Ketan susu keju", 25000));
        products.add(new Product("Grilled Cheese", 35000));
        products.add(new Product("Bolu Pisang Keju", 30000));
        products.add(new Product("Onion Ring Cheese", 20000));
        products.add(new Product("Kroket Mozarella", 25000));
    }

    static void showProduct() {
        // Menampilkan List Product
        int index = 0;
        for (Product p : products) {
            index++;
            System.out.println(index + ". " + p.getProductName() + " (Rp" + p.getProductPrice() + ")");
        }
        System.out.println("0. LANJUT KE PEMBAYARAN >>>");
    }

    static double TotalHarga(List<Product> products) {
        double total = 0;

        while (true) {
            int pilihan, jumlah;
            // Memanggil Method showProduct
            System.out.println("***MENU***");
            showProduct();

            System.out.println("--------------");

            // Memasukkan Pilihan Menu
            pilihan = InputAngka("Masukkan Pilihan Menu : ", 0,products.size());

            if (pilihan == 0) {
                // Jika Pilihan 0 maka lanjut ke pembayaran
                break;
            } else {
                // Memasukkan jumlah pembelian
                jumlah = InputAngka("Masukkan jumlah pembelian : ", 1,100);

                int index = 0;
                for (Product p : products) {
                    index++;

                    if (pilihan == index) {
                        total += p.getProductPrice() * jumlah;
                        // Menambahkan Product yang dibeli ke List untuk Struk
                        selected_products.add(new SelectedProduct(p.getProductName(), jumlah, p.getProductPrice() * jumlah));
                    }
                }
                System.out.println("--------------");
                System.out.println("Total Sementara : " + total);
            }

            System.out.println();
        }

        return total;
    }

    static void Pembayaran() {
        String[] jenis_pembayaran = {"Cash", "Debit", "Kredit", "E-Wallet"};
        String[] bank = {"BNI", "BCA", "Mandiri", "CIMB Niaga"};
        String[] e_wallet = {"Gopay", "OVO", "Dana"};

        int pilihan_jenis, pilihan_bank, pilihan_wallet;
        double uang_cash;

        // Menampilkan jenis pembayaran
        System.out.println("***JENIS PEMBAYARAN***");
        int i = 1;
        for (String j : jenis_pembayaran) {
            System.out.println(i + ". " + j);
            i++;
        }
        System.out.println("--------------");

        // Memasukkan Jenis Pembayaran
        pilihan_jenis = InputAngka("Masukkan Jenis Pembayaran Anda : ", 1,jenis_pembayaran.length);

        selected_pembayaran = jenis_pembayaran[pilihan_jenis - 1];

        // Mengecek jenis pembayaran
        switch (pilihan_jenis) {
            case 1:
                // Case jika pembayaran Cash
                System.out.print("Masukkan Nominal Cash Anda : ");
                uang_cash = scanner.nextDouble();

                System.out.println("Kembalian anda : " + (uang_cash - total_bayar));
                break;
            case 2:
            case 3:
                // Case jika pembayaran Debit atau Kredit
                System.out.println("***PILIHAN BANK***");
                int index = 1;
                for (String b : bank) {
                    System.out.println(index + ". " + b);
                    index++;
                }
                System.out.println("--------------");
                // Memasukkan Pilihan Bank dan No Rekening
                pilihan_bank = InputAngka("Masukkan Pilihan Bank Anda : ", 1,bank.length);
                selected_bank = bank[pilihan_bank - 1];
                System.out.print("Masukkan No Rekening Bank Anda : ");
                selected_no_pembayaran = scanner.nextLine();
                break;
            case 4:
                // Case jika pembayaran e-wallet
                System.out.println("***PILIHAN E-WALLET***");
                int id = 0;
                for (String b : e_wallet) {
                    System.out.println(id + ". " + b);
                    id++;
                }
                System.out.println("--------------");
                // Memasukkan Pilihan E-wallet dan no hp
                pilihan_wallet = InputAngka("Masukkan Pilihan E-Wallet Anda : ", 1,e_wallet.length);
                selected_bank = bank[pilihan_wallet - 1];
                System.out.print("Masukkan No Handphone E-Wallet Anda : ");
                selected_no_pembayaran = scanner.nextLine();
                break;
        }

        System.out.println("Pembayaran sedang diproses...");
        // Memanggil Method setTimeoutSync untuk mendelay
        setTimeoutSync(() -> System.out.println("PEMBAYARAN BERHASIL!!!"), 2000);

        System.out.println("");
    }

    public static void setTimeoutSync(Runnable runnable, int delay) {
        // Berfungsi agar program dapat berhenti sejenak sesuai waktu
        try {
            Thread.sleep(delay);
            runnable.run();

        } catch (Exception e) {
            System.err.println(e);
            System.out.println("PEMBAYARAN GAGAL!!!");
        }
    }

    public static void writeStruk(String filename, List<SelectedProduct> x) throws IOException {
        // Berfungsi sebagai print Struk
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));

        outputWriter.write("***CEDAR Food Market***");
        outputWriter.newLine();
        outputWriter.write("-----------------------");
        outputWriter.newLine();
        int index = 0;
        for (SelectedProduct p : x) {
            index++;

            outputWriter.write(index + ". " + p.getProductName() + "\t\t\t (" + p.getProductJumlah() + ")   :  Rp" + p.getProductTotal());
            outputWriter.newLine();

        }
        outputWriter.write("-----------------------");
        outputWriter.newLine();
        outputWriter.write("SUBTOTAL : Rp" + total_bayar);
        outputWriter.newLine();
        if (selected_pembayaran == "Cash") {
            outputWriter.write("Pembayaran : " + selected_pembayaran);
        } else {
            outputWriter.write("Pembayaran : " + selected_pembayaran + " " + selected_bank);
        }
        outputWriter.newLine();
        outputWriter.write("-----------------------");
        outputWriter.newLine();
        outputWriter.write("***TERIMAKASIH TELAH BERBELANJA DI TOKO KAMI!***");

        outputWriter.flush();
        outputWriter.close();
    }

    public static int InputAngka(String title,int min, int max) {
        // Method yang berfungsi sebagai handling input dan rules nya
        int num = 0;
        while (true) {
            System.out.print(title+ " ["+min+".."+max+"] : ");
            num = scanner.nextInt();
            scanner.nextLine();

            if (num < min) {
                System.out.println("!!! Angka yang anda masukkan kurang dari " + min + " !!!");
            } else if (num > max) {
                System.out.println("!!! Angka yang anda masukkan lebih dari " + max + " !!!");
            } else {
                break;
            }
        }

        return num;
    }
}
